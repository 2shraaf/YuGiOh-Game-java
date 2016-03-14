package eg.edu.guc.yugioh.cards.spells;

import java.util.Random;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class GracefulDice extends SpellCard {

	public GracefulDice(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		Board b = getBoard();
		Player p1 = b.getActivePlayer();

		Field f1 = p1.getField();// p1 Field
		
		int factor = (new Random().nextInt(10) + 1) * 100;
		f1.changeAtkDefForAll(factor);
	}
}
