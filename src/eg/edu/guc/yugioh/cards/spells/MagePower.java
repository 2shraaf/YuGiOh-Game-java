package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class MagePower extends SpellCard {

	public MagePower(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		if (monster != null) {
			Board b = getBoard();
			Player p1 = b.getActivePlayer();
			Field f1 = p1.getField();// p1 Field

			int factor = 500 * f1.getSpellArea().size();

			monster.setAttackPoints(monster.getAttackPoints() + factor);
			monster.setDefensePoints(monster.getDefensePoints() + factor);
		}
	}
}
