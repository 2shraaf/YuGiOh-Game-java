package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class DarkHole extends Raigeki {

	public DarkHole(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		Board b = getBoard();
		Player p1 = b.getActivePlayer();

		Field f1 = p1.getField();// p1 Field

		super.action(monster);
		f1.removeMonsterToGraveyard(f1.getMonstersArea());
	}
}
