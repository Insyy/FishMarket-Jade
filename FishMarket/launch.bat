javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\auction\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\performatifs\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\seller\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\buyer\*.java

javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\broker\*.java


java -cp "bin\;lib\jade.jar" jade.Boot -agents BROKER:fishmarket.agents.broker.Broker(BUYER1,BUYER2);SELLER1:fishmarket.agents.seller.Seller(BROKER);SELLER2:fishmarket.agents.seller.Seller(BROKER);BUYER1:fishmarket.agents.buyer.Buyer(9999);BUYER2:fishmarket.agents.buyer.Buyer(9999)