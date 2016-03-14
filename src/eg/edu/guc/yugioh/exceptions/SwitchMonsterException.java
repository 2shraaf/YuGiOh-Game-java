package eg.edu.guc.yugioh.exceptions;

public class SwitchMonsterException extends RuntimeException{
	
	public SwitchMonsterException()
	{
		super("Already Switched this turn");
	}
}
