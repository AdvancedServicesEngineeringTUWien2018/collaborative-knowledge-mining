package at.ac.tuwien.ase.blockhain.bridge;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class KnowledgeGraphCoin extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610c78806100606000396000f300608060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100885780630fac394f14610118578063486591351461018c578063570ca7351461026757806395d89b41146102be578063e6b564d21461034e578063f8a8fd6d146103cb575b600080fd5b34801561009457600080fd5b5061009d6103fa565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100dd5780820151818401526020810190506100c2565b50505050905090810190601f16801561010a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610172600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610433565b604051808215151515815260200191505060405180910390f35b34801561019857600080fd5b5061024d600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610721565b604051808215151515815260200191505060405180910390f35b34801561027357600080fd5b5061027c610acb565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102ca57600080fd5b506102d3610af0565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103135780820151818401526020810190506102f8565b50505050905090810190601f1680156103405780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561035a57600080fd5b506103b5600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610b29565b6040518082815260200191505060405180910390f35b3480156103d757600080fd5b506103e0610b9e565b604051808215151515815260200191505060405180910390f35b6040805190810160405280601581526020017f4b6e6f776c656467654772617068205468616c6572000000000000000000000081525081565b60006001826040518082805190602001908083835b60208310151561046d5780518252602082019150602081019050602083039250610448565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060408051908101604052803373ffffffffffffffffffffffffffffffffffffffff168152602001348152509080600181540180825580915050906001820390600052602060002090600202016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155505050346003836040518082805190602001908083835b602083101515610583578051825260208201915060208101905060208303925061055e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054016003836040518082805190602001908083835b6020831015156105f057805182526020820191506020810190506020830392506105cb565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055507f3604bc1aa40fbbeeb618d32ea259be4324e855cb4570361def9a3fb501788d7b82333442434060405180806020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018581526020018481526020018360001916600019168152602001828103825287818151815260200191508051906020019080838360005b838110156106da5780820151818401526020810190506106bf565b50505050905090810190601f1680156107075780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a160019050919050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561080e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252603c8152602001807f5468652041504920637265646974206f662075736572732063616e206f6e6c7981526020017f206265207265647563656420627920746865206f70657261746f722e0000000081525060400191505060405180910390fd5b6002866040518082805190602001908083835b6020831015156108465780518252602082019150602081019050602083039250610821565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060606040519081016040528087815260200186815260200185815250908060018154018082558091505090600182039060005260206000209060030201600090919290919091506000820151816000015560208201518160010190805190602001906108e6929190610ba7565b50604082015181600201555050506003866040518082805190602001908083835b60208310151561092c5780518252602082019150602081019050602083039250610907565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050848110156109e05760006003876040518082805190602001908083835b6020831015156109a4578051825260208201915060208101905060208303925061097f565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902081905550610abe565b846003876040518082805190602001908083835b602083101515610a1957805182526020820191506020810190506020830392506109f4565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054036003876040518082805190602001908083835b602083101515610a865780518252602082019150602081019050602083039250610a61565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505b6001915050949350505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6040805190810160405280600381526020017f4b4754000000000000000000000000000000000000000000000000000000000081525081565b60006003826040518082805190602001908083835b602083101515610b635780518252602082019150602081019050602083039250610b3e565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b60006001905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610be857805160ff1916838001178555610c16565b82800160010185558215610c16579182015b82811115610c15578251825591602001919060010190610bfa565b5b509050610c239190610c27565b5090565b610c4991905b80821115610c45576000816000905550600101610c2d565b5090565b905600a165627a7a72305820f73120a55236950bd03b538026e8071f56075363467793e81d66ba215c22e9150029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OPERATOR = "operator";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_BUYAPICREDIT = "buyAPICredit";

    public static final String FUNC_BALANCEOFAPICREDITS = "balanceOfAPICredits";

    public static final String FUNC_REDUCEAPICREDIT = "reduceAPICredit";

    public static final String FUNC_TEST = "test";

    public static final Event APICREDITDEPOSITEVENT_EVENT = new Event("APICreditDepositEvent", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
       // _addresses.put("4", "0x3b2dced58546eb0c32f64b1d074ec5b44fba615f");
        _addresses.put("4", "0x058cb0fddf4a2aba75cf50c0e798c1a84f0a75e3");
    }

    protected KnowledgeGraphCoin(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected KnowledgeGraphCoin(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> operator() {
        final Function function = new Function(FUNC_OPERATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<KnowledgeGraphCoin> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(KnowledgeGraphCoin.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<KnowledgeGraphCoin> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(KnowledgeGraphCoin.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<APICreditDepositEventEventResponse> getAPICreditDepositEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APICREDITDEPOSITEVENT_EVENT, transactionReceipt);
        ArrayList<APICreditDepositEventEventResponse> responses = new ArrayList<APICreditDepositEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            APICreditDepositEventEventResponse typedResponse = new APICreditDepositEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.uid = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<APICreditDepositEventEventResponse> aPICreditDepositEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, APICreditDepositEventEventResponse>() {
            @Override
            public APICreditDepositEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APICREDITDEPOSITEVENT_EVENT, log);
                APICreditDepositEventEventResponse typedResponse = new APICreditDepositEventEventResponse();
                typedResponse.log = log;
                typedResponse.uid = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<APICreditDepositEventEventResponse> aPICreditDepositEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APICREDITDEPOSITEVENT_EVENT));
        return aPICreditDepositEventEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> buyAPICredit(String uid, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYAPICREDIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(uid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> balanceOfAPICredits(String uid) {
        final Function function = new Function(FUNC_BALANCEOFAPICREDITS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(uid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> reduceAPICredit(String uid, BigInteger _cost, String _payload, BigInteger _timestamp) {
        final Function function = new Function(
                FUNC_REDUCEAPICREDIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(uid), 
                new org.web3j.abi.datatypes.generated.Uint256(_cost), 
                new org.web3j.abi.datatypes.Utf8String(_payload), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> test() {
        final Function function = new Function(FUNC_TEST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static KnowledgeGraphCoin load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new KnowledgeGraphCoin(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static KnowledgeGraphCoin load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new KnowledgeGraphCoin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class APICreditDepositEventEventResponse {
        public Log log;

        public String uid;

        public String sender;

        public BigInteger amount;

        public BigInteger timestamp;

        public byte[] hash;
    }
}
