package eg.edu.guc.yugioh.exceptions;

public class UnknownSpellCardException extends UnexpectedFormatException{
	private String unknownSpell;

	public UnknownSpellCardException(String sourceFile, int sourceLine,
			String unknownSpell) {
		super(sourceFile, sourceLine);
		this.setUnknownSpell(unknownSpell);
	}

	public String getUnknownSpell() {
		return unknownSpell;
	}

	public void setUnknownSpell(String unknownSpell) {
		this.unknownSpell = unknownSpell;
	}
	
}
