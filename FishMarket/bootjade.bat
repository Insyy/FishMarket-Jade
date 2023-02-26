javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\auction\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\performatifs\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\seller\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\broker\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\buyer\*.java


java -cp "bin\;lib\jade.jar" jade.Boot -gui market:fishmarket.agents.broker.Broker;buyer:fishmarket.agents.buyer.Buyer(market);seller:fishmarket.agents.seller.Seller(market);