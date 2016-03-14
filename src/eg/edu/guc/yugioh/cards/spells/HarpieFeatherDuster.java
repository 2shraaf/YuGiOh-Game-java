package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class HarpieFeatherDuster extends SpellCard {

	public HarpieFeatherDuster(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		Board b = getBoard();
		Player p2 = b.getOpponentPlayer();

		Field f2 = p2.getField();// p2 Field

		f2.removeSpellToGraveyard(f2.getSpellArea());
	}
}
