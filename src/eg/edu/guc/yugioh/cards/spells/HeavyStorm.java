package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class HeavyStorm extends HarpieFeatherDuster {

	public HeavyStorm(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		super.action(monster);

		Board b = getBoard();
		Player p1 = b.getActivePlayer();

		Field f1 = p1.getField();// p1 Field

		f1.removeSpellToGraveyard(f1.getSpellArea());
	}

}
