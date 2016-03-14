package eg.edu.guc.yugioh.exceptions;

public class DefenseMonsterAttackException extends RuntimeException{
	public DefenseMonsterAttackException()
	{
		super("Can't Attack in Defense Mode");
	}
}
