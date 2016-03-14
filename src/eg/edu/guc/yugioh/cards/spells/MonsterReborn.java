package eg.edu.guc.yugioh.cards.spells;


import javax.swing.JOptionPane;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class MonsterReborn extends SpellCard {

	public MonsterReborn(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		Board b = getBoard();
		Player p1 = b.getActivePlayer();
		Player p2 = b.getOpponentPlayer();

		Field f1 = p1.getField();// p1 Field
		Field f2 = p2.getField();// p2 Field

		MonsterCard max1 = f1.getMaxGraveyard(); // Max attack monster of p1
													// grave yard
		MonsterCard max2 = f2.getMaxGraveyard();
		// Max attack monster of p2

		if (max1 == null && max2 == null)
		{
			JOptionPane.showMessageDialog(null, "No monsters in both graveyards");
		}
		else {
			MonsterCard max; // max of both
			if (max1 == null)
			{
				max = max2;
				f2.removeFromGraveyard(max2);
			}
			else if (max2 == null)
			{
				max = max1;
				f1.removeFromGraveyard(max1);
			}
				else {
				if (max1.compareTo(max2) == 1) {
					max = max1;
					f1.removeFromGraveyard(max1);

				} else {
					max = max2;
					f2.removeFromGraveyard(max2);
				}
			}
			f1.addMonsterToField(max, Mode.ATTACK, false);
		}
	}
}
