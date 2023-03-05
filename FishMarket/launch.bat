javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\auction\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\performatifs\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\seller\*.java
javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\buyer\*.java

javac -cp "bin\;lib\jade.jar" -d bin\ src\fishmarket\agents\broker\*.java

@REM REMOVE OR ADD COMMENTS TO EXECUTE DIFFERENT TEST SCENARIOS
@REM appTest1
@REM java -cp "bin\;lib\jade.jar" jade.Boot -agents BROKER:fishmarket.agents.broker.Broker(MAN1,MAN2);SELLER1:fishmarket.agents.seller.Seller(BROKER);MAN1:fishmarket.agents.buyer.Buyer;MAN2:fishmarket.agents.buyer.Buyer
@REM appTest2
@REM java -cp "bin\;lib\jade.jar" jade.Boot -agents BROKER:fishmarket.agents.broker.Broker(MAN,AUTO);SELLER1:fishmarket.agents.seller.Seller(BROKER);MAN:fishmarket.agents.buyer.Buyer;AUTO:fishmarket.agents.buyer.Buyer
@REM appTest3
@REM java -cp "bin\;lib\jade.jar" jade.Boot -agents BROKER:fishmarket.agents.broker.Broker(AUTO1,AUTO2,AUTO3);SELLER1:fishmarket.agents.seller.Seller(BROKER);AUTO1:fishmarket.agents.buyer.Buyer;AUTO2:fishmarket.agents.buyer.Buyer;AUTO3:fishmarket.agents.buyer.Buyer;
@REM appTest
java -cp "bin\;lib\jade.jar" jade.Boot -agents BROKER:fishmarket.agents.broker.Broker(AUTO1,AUTO2,AUTO3);SELLER1:fishmarket.agents.seller.Seller(BROKER);SELLER2:fishmarket.agents.seller.Seller(BROKER);AUTO1:fishmarket.agents.buyer.Buyer;AUTO2:fishmarket.agents.buyer.Buyer;AUTO3:fishmarket.agents.buyer.Buyer
