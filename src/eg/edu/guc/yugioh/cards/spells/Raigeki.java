package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class Raigeki extends SpellCard {

	public Raigeki(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {

		Field f2 = getBoard().getOpponentPlayer().getField();// p2 Field

		f2.removeMonsterToGraveyard(f2.getMonstersArea());
	}
}