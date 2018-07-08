# Collaborative Knowledge Mining

The basic idea of the ’collaborative knowledge mining’ scenario is to bring together human agents in the Web that contribute knowledge to the movie domain and exploiters that want to access such a knowledge graph directly through query languages (SPARQL, Gremlin) or to request advanced services (entity recognition, related entities, etc.) in this domain. The contributors should be rewarded for their contributions and exploiters have to pay for the provided services. This is illustrated in the following figure.

![Collaborative Knowledge Mining Concept](/doc/images/high-level-ASE.png)

## Architecture

![Collaborative Knowledge Mining Archietcture](/doc/images/ASE-architecture.png)

The system is accessible to the exploiter over an API gateway with an fixed static IP address. Behind the load balancer at this IP, can be mulitple API gateways working concurrently (kuberntes allows to specify client-aware routing). Those API gateways are the public interface to methods provided by the microservices in the background.

In the center of those microservices, is the exploratory search service, which allows the exploiter to define arbitary search queries based on predefined operators. For example the following JSON defines such an exploration flow, which will apply a FTS  of "The Shining", rank the results using Page rank and then describe the results.

`
{
  "steps": [
    {
      "name": "esm.source.fts",
      "param": {
        "keyword": "The Shining"
      }
    },
    {
      "name": "esm.exploit.centrality.pagerank"
    },
    {
      "name": "esm.aggregate.orderby",
      "param": {
        "path": "/centrality/esm.exploit.centrality.pagerank",
        "strategy": "DESC"
      }
    },
    {
      "name": "esm.aggregate.limit",
      "param": {
        "number": 10
      }
    },
    {
      "name": "esm.exploit.describe",
      "param": {
        "content": {
          "label": {
            "@type": "text",
            "properties": [
              "http://www.w3.org/2000/01/rdf-schema#label"
            ]
          },
          "thumb": {
            "@type": "iri",
            "properties": [
              "http://dbpedia.org/ontology/thumbnail"
            ]
          },
          "class": {
            "@type": "iri",
            "properties": [
              "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"
            ]
          }
        }
      }
    }
  ]
}
`

Then the correspondign result will be returned. If an exploiter wants to call such a method, he has to pay with API credits. The amount of credits that have to be paid depend on the computational complexity of the exploration flow. It is an elastic pricing model. It urges exploiter also to write efficient flows. The cost estimator microservice can be used to estimate the cost of an exploration flow in beforehand. The current cost estimator implementation uses a simple cost map for the operators, but it could be done more elastic by tracking the computation of exploration flows and analyzing them.

How does an exploiter get API credits. First, the exploiter has to register itself over the corresponding API method. The exploiter will get an unique UID, which (s)he has to remember. The exploiter can then use a Ethereum client/wallet of its choice, and buy API credits over the designed smart contract (exploiter has to know the address of this smart contract) passing its UID. A successfull deposit will trigger an event on the Ethereum blockhain.

The blockchain bridge is decoupled from the other microservices over a Pub/Sub middleware. It is responsible for calling certain methods of the smart contract and listen for events such as mentioned above. If the blockchain bridge detects a deposit event, it will be wrapped into a JSON Dto and published to the `API Credit Deposit` topic.

The exploiter management microservice maintains a record of the exploiters and their API credits using a conventional relational database (Cloud SQL on the Google PLatform). This must be done, because the Ethereum blockchain is too slow, as to use it for fast reads and writes. However, the API credits are reflected as immutable records on the blockchain as well as in the database. The API gateway will call this microservice for each API call to the search API that was successfull (no 5xx code), and this microservice will change the API credits in the database accordingly and publish it further into the `API Calls` topic. There it will be read by a blockchain bridge instance and it will call the corresponding smart contract method for tracking API calls for exploiters.

The integrator and crawler part are unfortunately missing and were not implemented.

## Deployment

A Kubernetes configuration (`ase.yml`) is provided for easy deployment on Kubernetes. However, the configuration relies on external endpoints that must be configured. 

### Smart Contract
The payment process of API credits relies on a smart contract, and thus, this smart contract must be deployed. I used truffle for this purpose. The configuration is given in the `truffle.js` file and must be adapted accordingly. I used my own Ethereum node, of which the deployment is described in the next section, with an exposed RPC port. This is reflected in the configuration of truffle. Deployment is then easily done with:

`truffle migrate`

This deployment will return an address for the smart contract. This address is import for the blockchain bridge, which is listening for events on this smart contract and can also call methods. This must be adaptoed in the `ase.yml` for the blockchain bridge.

### Ethereum node
The approach chosen for connecting to the Ethereum blockchain was to run an Ethereum node on a VM instance with a fixed IP address. For this application, the test network Rinkeby was chosen. I used an instance with Ubuntu and installed geth over the default repository of Ubuntu. Then, geth was started with the following command, where `unlock` refers to the address of the operator of the smart contract (who deployed it).

`geth --rinkeby --light --rpc --rpcaddr 0.0.0.0 --rpcport 8545 --rpccorsdomain "*" --unlock 0x0e40ab4c8b83aa8d488f0c164ff96b867c0f00e6 --password ./password-primary.txt
`
### GraphDB instance
GraphDB (free version) was chosen as triplestore and it will contain all the statements of the knowledge graph. Firts, it was part of the Kubernetes deployment, but this worked not well. So, now the Kubernetes configuration expects a running GraphDB instance on a fixed IP address. Like with the Ethereum node, I chose to use an Ubuntu VM instance on the Compute Engine of Google Cloud. The scripts needed for initializing the GraphDb instance can be found in the `graphdb` folder. 

### Cloud SQL
The exploiter manager mincroservice keeps track of the current exploiters and their API credits. All this information is stored in a regular relational database. I chose to use Cloud SQL for it. The Kubernetes configuration expects an PostgreSQL instance with a table `exploiterDB`. It will try to access this table with certain credentials (exploiterManager and corresponding password).

## State of the System
Depositing API credits and the call of API methods by the exploiter with elastic pricing works in the current system. What is missing, is the contribution part of the system. The user interface would be ready, but is missing the feedback mechanism for statements.
