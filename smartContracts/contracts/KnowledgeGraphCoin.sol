pragma solidity ^0.4.24;

//import "./EIP20Interface.sol";

/// @title getting access to KG API
contract KnowledgeGraphCoin { //is EIP20Interface {

    uint256 constant private MAX_UINT256 = 2**256 - 1;

    // the operator that deployed this smart contract
    address public operator;

    string public constant name = "KnowledgeGraph Thaler";
    string public constant symbol = "KGT";

    struct APICreditDeposit {
        address sender;
        uint256 amount;
    }

    struct APICall {
        uint256 cost;
        string payload;
        uint timestamp;
    }
    
   event APICreditDepositEvent(
        string uid,
        address sender,
        uint256 amount,
        uint timestamp,
        bytes32 hash
    );

    mapping(string => APICreditDeposit[]) exploiterAPICredits;
    mapping(string => APICall[]) exploiterAPICalls;
    mapping(string => uint256) apiCreditBalance;
    
    constructor() public {
        operator = msg.sender;
        //totalSupply = MAX_UINT256;
    }
    
    // this method can be called to buy API credits, 
    function buyAPICredit(string uid) public payable returns (bool success) {
       exploiterAPICredits[uid].push(APICreditDeposit({
            sender: msg.sender,
            amount: msg.value
        }));
        apiCreditBalance[uid] = apiCreditBalance[uid] + msg.value;
        emit APICreditDepositEvent(uid, msg.sender, msg.value, now, blockhash(block.number));
        return true;
    }
    
    // gets the credits of the exploiter with the given uid. 
    function balanceOfAPICredits(string uid) public view returns (uint256 credit) {
        return apiCreditBalance[uid];
    }

    // reduces the credit of the exploiter with the given id. This is called, when
    // the exploiter used the KG API and has to pay the given cost for tha call.
    function reduceAPICredit(string uid, uint256 _cost, string _payload, uint _timestamp) public returns (bool success){
        require(msg.sender == operator, "The API credit of users can only be reduced by the operator.");
        exploiterAPICalls[uid].push(APICall({
            cost: _cost,
            payload: _payload,
            timestamp: _timestamp
        }));
        uint256 credit = apiCreditBalance[uid];
        if (credit < _cost) {
            // balance shall go below zero, operator has to ensure, that this
            // will not be exploited maliciously.
            apiCreditBalance[uid] = 0;
        } else {
            apiCreditBalance[uid] = apiCreditBalance[uid] - _cost;
        }
        return true;
    }

    function test() public view returns (bool success){
        return true;
    }
    
}
