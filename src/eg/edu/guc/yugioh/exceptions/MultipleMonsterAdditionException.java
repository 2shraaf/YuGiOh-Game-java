package eg.edu.guc.yugioh.exceptions;

public class MultipleMonsterAdditionException extends RuntimeException{
	public MultipleMonsterAdditionException()
	{
		super("Already summoned a monster");
	}
}
