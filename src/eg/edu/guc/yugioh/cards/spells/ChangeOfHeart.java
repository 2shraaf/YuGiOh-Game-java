package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.*;

public class ChangeOfHeart extends SpellCard {
	public ChangeOfHeart(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard monster) {
		if (monster != null) {
			Board b = getBoard();
			Player p1 = b.getActivePlayer();
			Player p2 = b.getOpponentPlayer();

			Field f1 = p1.getField();// p1 Field
			Field f2 = p2.getField();// p2 Field

			f2.removeMonster(monster);
			f1.addMonsterToField(monster, monster.getMode(), false);
		}
	}
}
