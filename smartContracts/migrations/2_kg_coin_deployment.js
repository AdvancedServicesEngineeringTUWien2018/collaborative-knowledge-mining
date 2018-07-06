const KnowledgeGraphCoin = artifacts.require("./KnowledgeGraphCoin");

module.exports = function(deployer) {
  deployer.deploy(KnowledgeGraphCoin);
};