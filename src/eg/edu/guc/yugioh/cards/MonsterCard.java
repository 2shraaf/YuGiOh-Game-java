package eg.edu.guc.yugioh.cards;

public class MonsterCard extends Card implements Comparable<MonsterCard> {

	private int level;
	private int defensePoints;
	private int attackPoints;
	private Mode mode;
	private boolean attakingOption;
	private boolean switchingOption;

	public MonsterCard(String name, String description, int level, int attack,
			int defence) {
		super(name, description);
		this.level = level;
		this.defensePoints = defence;
		this.attackPoints = attack;
		this.mode = Mode.DEFENSE;
		this.attakingOption = true;
		this.switchingOption = true;
	}

	// Getters
	public int getLevel() {
		return level;
	}

	public int getDefensePoints() {
		return defensePoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public Mode getMode() {
		return mode;
	}

	// Setters

	public void setDefensePoints(int d) {
		defensePoints = d;
	}

	public void setAttackPoints(int a) {
		attackPoints = a;
	}

	public void setMode(Mode m) {
		mode = m;
	}

	@Override
	public int compareTo(MonsterCard tmp) {
		int atk1 = this.attackPoints;
		int atk2 = tmp.attackPoints;
		int def1 = this.defensePoints;
		int def2 = tmp.defensePoints;
		if (atk1 > atk2)
			return 1;
		else if (atk1 == atk2)
			return def1 >= def2 ? 1 : -1;

		return -1; // atk2>atk1
	}

	@Override
	public void action(MonsterCard monster) {
		if ((this.getAttackPoints() > monster.getDefensePoints() && monster
				.getMode() == Mode.DEFENSE)
				|| (this.getAttackPoints() > monster.getAttackPoints() && monster
						.getMode() == Mode.ATTACK)) {
			if (monster.mode == Mode.DEFENSE)
				Card.GetOppField().removeMonsterToGraveyard(monster);
			else {
				Card.GetOppField().removeMonsterToGraveyard(monster);
				Card.DecreaseLifePointsOfOpp(this.getAttackPoints()
						- monster.getAttackPoints());
			}
		} else if ((this.getAttackPoints() < monster.getDefensePoints() && monster
				.getMode() == Mode.DEFENSE)
				|| (this.getAttackPoints() < monster.getAttackPoints() && monster
						.getMode() == Mode.ATTACK)) {
			if (monster.mode == Mode.DEFENSE) {
				Card.DecreaseLifePointsOfActive(monster.getDefensePoints()
						- this.getAttackPoints());
				monster.setHidden(false);
			} else {
				Card.GetActiveField().removeMonsterToGraveyard(this);
				Card.DecreaseLifePointsOfActive(monster.getAttackPoints()
						- this.getAttackPoints());
			}

		} else if (monster.mode != Mode.DEFENSE) {
			Card.GetOppField().removeMonsterToGraveyard(monster);
			Card.GetActiveField().removeMonsterToGraveyard(this);

		}

	}

	public void action() {

		Card.DecreaseLifePointsOfOpp(this.getAttackPoints());

	}

	public boolean isAttakingOption() {
		return attakingOption;
	}

	public void setAttakingOption(boolean attakingOption) {
		this.attakingOption = attakingOption;
	}

	public boolean isSwitchingOption() {
		return switchingOption;
	}

	public void setSwitchingOption(boolean switchingOption) {
		this.switchingOption = switchingOption;
	}

	public String toString(String possibleMove) {
		// TODO Auto-generated method stub
		return String.format("<html>Name: %s<br />Level: %d<br />Attack: %d<br />Defense: %d<br />Possible Move: %s</html>",
				getName(), getLevel(), getAttackPoints(), getDefensePoints(),
				possibleMove);
	}
}
