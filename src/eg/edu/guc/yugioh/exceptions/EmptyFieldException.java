package eg.edu.guc.yugioh.exceptions;

public class EmptyFieldException extends UnexpectedFormatException {

	private int sourceField;

	public EmptyFieldException(String sourceFile, int sourceLine,
			int sourceField) {
		super(sourceFile, sourceLine);
		this.sourceField = sourceField;
	}

	public int getSourceField() {
		return sourceField;
	}

	public void setSourceField(int sourceField) {
		this.sourceField = sourceField;
	}
}
