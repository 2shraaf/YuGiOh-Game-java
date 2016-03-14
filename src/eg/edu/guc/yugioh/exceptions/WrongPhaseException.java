package eg.edu.guc.yugioh.exceptions;

public class WrongPhaseException extends RuntimeException{
	public WrongPhaseException()
	{
		super("Wrong Phase");
	}
}
