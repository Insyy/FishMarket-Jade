package fishmarket.agents.buyer;

public class TooBrokeException extends Exception{
	public TooBrokeException(){
		super("Too broke!");
	}
}