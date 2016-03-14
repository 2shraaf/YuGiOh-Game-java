package eg.edu.guc.yugioh.exceptions;

public class MonsterMultipleAttackException extends RuntimeException{
	public MonsterMultipleAttackException()
	{
		super("Already attacked with this monster");
	}
}
