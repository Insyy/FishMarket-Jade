javac src\fishmarket\agents\Broker.java
javac src\fishmarket\agents\Buyer.java
javac src\fishmarket\agents\Seller.java

javac src\fishmarket\PerformativeCreator.java
javac src\fishmarket\PerformativeCreator.java

java -cp "lib\jade.jar;src\;" jade.Boot -gui market:fishmarket.agents.Broker;buyer:fishmarket.agents.Buyer(market);seller:fishmarket.agents.Seller(market);
