javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\auction\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\performatifs\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\seller\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\broker\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\buyer\*.java

java -cp "bin\;lib\jade.jar" jade.Boot -agents BROKER:fishmarket.agents.broker.Broker();SELLER1:fishmarket.agents.seller.Seller(BROKER);SELLER2:fishmarket.agents.seller.Seller(BROKER);