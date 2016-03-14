package eg.edu.guc.yugioh.cards;
import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;

public abstract class Card implements Cloneable {
	private String name;
	private String description;
	private boolean isHidden;
	private Location location;
	private static Board board;

	public Card(String name, String description) {
		this.name = name;
		this.description = description;
		this.isHidden = true;
		this.location = Location.DECK;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public Location getLocation() {
		return location;
	}

	public static Board getBoard() {
		return board;
	}

	// Setters
	public void setHidden(boolean b) {
		isHidden = b;
	}

	public void setLocation(Location l) {
		location = l;
	}

	public static Field GetOppField() {
		return board.getOpponentPlayer().getField();
	}

	public static Field GetActiveField() {
		return board.getActivePlayer().getField();

	}

	public static void DecreaseLifePointsOfActive(int n) {
		Card.getBoard()
				.getActivePlayer()
				.setLifePoints(
						Card.getBoard().getActivePlayer().getLifePoints() - n);

	}

	public static void DecreaseLifePointsOfOpp(int n) {
		Card.getBoard()
				.getOpponentPlayer()
				.setLifePoints(
						Card.getBoard().getOpponentPlayer().getLifePoints() - n);

	}

	public static void setBoard(Board b) {
		board = b;
	}

	 abstract public void action(MonsterCard monster);

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static void main(String[] args) {

	}

	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

}