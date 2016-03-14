package eg.edu.guc.yugioh.exceptions;

public class UnknownCardTypeException extends UnexpectedFormatException{
	private String unknownType;

	public UnknownCardTypeException(String sourceFile, int sourceLine,
			String unknownType) {
		super(sourceFile, sourceLine);
		this.setUnknownType(unknownType);
	}

	public String getUnknownType() {
		return unknownType;
	}

	public void setUnknownType(String unknownType) {
		this.unknownType = unknownType;
	}
	
}
