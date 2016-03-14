package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class CardDestruction extends SpellCard {

	public CardDestruction(String name, String description) {
		super(name, description);
	}

	@Override
	public void action(MonsterCard monster) {
		Board b = getBoard();
		Player p1 = b.getActivePlayer();
		Player p2 = b.getOpponentPlayer();
		Field f1 = p1.getField();// p1 Field
		Field f2 = p2.getField();// p2 Field

		int size1 = f1.removeHandToGraveyard(); // no. of cards of p1 before
												// removal
		int size2 = f2.removeHandToGraveyard(); // no. of cards of p2 before
		if (size2 > f2.getDeck().getDeck().size())
			Card.getBoard().setWinner(p1);
		if (size1 > f1.getDeck().getDeck().size())
			Card.getBoard().setWinner(p2);


		f1.addNCardsToHand(size1);
		f2.addNCardsToHand(size2);

	}

}
