# Collaborative Knowledge Mining

The basic idea of the ’collaborative knowledge mining’ scenario is to bring together human agents in the Web that contribute knowledge to the movie domain and exploiters that want to access such a knowledge graph directly through query languages (SPARQL, Gremlin) or to request advanced services (entity recognition, related entities, etc.) in this domain. The contributors should be rewarded for their contributions and exploiters have to pay for the provided services. This is illustrated in the following figure.

![Collaborative Knowledge Mining](/doc/images/high-level-ASE.png)

## Deployment

A Kubernetes configuration (`ase.yml`) is provided for easy deployment on Kubernetes. However, the configuration relies on external endpoints that must be configured. 

## Smart Contract
The payment process of API credits relies on a smart contract, and thus, this smart contract must be deployed. I used truffle for this purpose. The configuration is given in the `truffle.js` file and must be adapted accordingly. I used my own Ethereum node, of which the deployment is described in the next section, with an exposed RPC port. This is reflected in the configuration of truffle. Deployment is then easily done with:

`truffle migrate`

This deployment will return an address for the smart contract. This address is import for the blockchain bridge, which is listening for events on this smart contract and can also call methods. This must be adaptoed in the `ase.yml` for the blockchain bridge.

## Ethereum node
The approach chosen for connecting to the Ethereum blockchain was to run an Ethereum node on a VM instance with a fixed IP address. For this application, the test network Rinkeby was chosen. I used an instance with Ubuntu and installed geth over the default repository of Ubuntu. Then, geth was started with the following command, where `unlock` refers to the address of the operator of the smart contract (who deployed it).

`geth --rinkeby --light --rpc --rpcaddr 0.0.0.0 --rpcport 8545 --rpccorsdomain "*" --unlock 0x0e40ab4c8b83aa8d488f0c164ff96b867c0f00e6 --password ./password-primary.txt
`
## GraphDB instance
GraphDB (free version) was chosen as triplestore and it will contain all the statements of the knowledge graph. Firts, it was part of the Kubernetes deployment, but this worked not well. So, now the Kubernetes configuration expects a running GraphDB instance on a fixed IP address. Like with the Ethereum node, I chose to use an Ubuntu VM instance on the Compute Engine of Google Cloud. The scripts needed for initializing the GraphDb instance can be found in the `graphdb` folder. 

## Cloud SQL
The exploiter manager mincroservice keeps track of the current exploiters and their API credits. All this information is stored in a regular relational database. I chose to use Cloud SQL for it. The Kubernetes configuration expects an PostgreSQL instance with a table `exploiterDB`. It will try to access this table with certain credentials (exploiterManager and corresponding password). 
