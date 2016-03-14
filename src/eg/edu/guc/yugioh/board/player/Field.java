package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

import java.io.IOException;
import java.util.ArrayList;

public class Field {
	private Phase phase;
	private ArrayList<MonsterCard> monstersArea;
	private ArrayList<SpellCard> spellArea;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;
	private Deck deck;

	public Field() throws IOException, UnexpectedFormatException {
		monstersArea = new ArrayList<MonsterCard>();
		spellArea = new ArrayList<SpellCard>();
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new Deck();
		phase = Phase.MAIN1;
	}

	// Setters and getters
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public ArrayList<MonsterCard> getMonstersArea() {
		return monstersArea;
	}

	public ArrayList<SpellCard> getSpellArea() {
		return spellArea;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public Deck getDeck() {
		return deck;
	}

	/* TBM */public void addMonsterToField(MonsterCard monster, Mode m,
			boolean isHidden) {
		
		Location location = monster.getLocation();
		 if (location == Location.HAND)
			hand.remove(monster);
		monster.setMode(m);
		monster.setHidden(isHidden);
		monster.setLocation(Location.FIELD);
		monstersArea.add(monster);

	}

	public void addMonsterToField(MonsterCard monster, Mode mode,
			ArrayList<MonsterCard> sacrifices) {
		
			removeMonsterToGraveyard(sacrifices);

			addMonsterToField(monster, mode, false);
	}

	public void removeMonsterToGraveyard(MonsterCard monster) {
		if (monstersArea.contains(monster)) {
			monster.setLocation(Location.GRAVEYARD);
			monstersArea.remove(monster);
			graveyard.add(monster);
		}
	}

	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters) {
		int n = monsters.size();
		for (int i = n - 1; i >= 0; i--) 
				removeMonsterToGraveyard((MonsterCard) monsters.get(i));
		
	}

	public void addSpellToField(SpellCard action, MonsterCard monster,
			boolean hidden) {
		if (spellArea.size() < 5) {
			action.setHidden(hidden);
			action.setLocation(Location.FIELD);
			spellArea.add(action);
			hand.remove(action);

			if (!hidden)
				activateSetSpell(action, monster);

			else
				action.setHidden(true);
		}
	}

	public void activateSetSpell(SpellCard action, MonsterCard monster) {
		if (spellArea.contains(action) && this.getPhase() != Phase.BATTLE) {
			action.setHidden(false);
			action.action(monster);
			removeSpellToGraveyard(action);
		}
	}

	public void removeSpellToGraveyard(SpellCard spell) {
		if (spellArea.contains(spell)) {
			spellArea.remove(spell);
			spell.setLocation(Location.GRAVEYARD);
			graveyard.add(spell);
		}
	}

	public void removeSpellToGraveyard(ArrayList<SpellCard> spells) {
		int n = spells.size();
		for (int i = n - 1; i >= 0; i--) {
			if (spellArea.contains(spells.get(i)))
				removeSpellToGraveyard(spells.get(i));
		}
	}

	public void addCardToHand() {
		
		Card card = (Card) deck.drawOneCard();
		card.setLocation(Location.HAND);
		hand.add(card);
	}

	public void addNCardsToHand(int n) {
		ArrayList<Card> d = deck.drawNCards(n);
		for (Card card : d) {
			card.setLocation(Location.HAND);
			hand.add(card);
		}
	}

	// Extra
	public MonsterCard getMaxGraveyard() // Maximum Attack Monster in
											// Graveyard,for Monster Reborn
	{
		MonsterCard m = null;
		for (int i = 0; i < graveyard.size(); i++) {
			if (graveyard.get(i) instanceof MonsterCard) {
				MonsterCard tmp = (MonsterCard) graveyard.get(i);
				if (m == null || m.compareTo(tmp) == -1)
					m = tmp;
			}
		}
		return m;
	}

	public void removeFromGraveyard(MonsterCard monster) {// Monster
																	// Reborn
		graveyard.remove(monster);
	}

	public void removeMonster(MonsterCard monster)// For Change of Heart
	{
		monstersArea.remove(monster);
	}

	public int removeHandToGraveyard() // for Card Destruction
	{
		int size = hand.size();
		for (int i = size - 1; i >= 0; i--) {

			Card tem = hand.remove(i);
			tem.setLocation(Location.GRAVEYARD);
			graveyard.add(tem);

		}
		return size;
	}

	public void changeAtkDefForAll(int factor) // Graceful Dice
	{
		for (int i = 0; i < monstersArea.size(); i++) {
			MonsterCard mons = monstersArea.get(i);
			mons.setAttackPoints(mons.getAttackPoints() + factor);
			mons.setDefensePoints(mons.getDefensePoints() + factor);
		}
	}

	public boolean CheckAddingCard() {
		if(this.phase==Phase.BATTLE)
			throw new WrongPhaseException();
		return ((this.phase == Phase.MAIN1 || this.phase == Phase.MAIN2)
				&& Card.GetActiveField() == this && Card.getBoard().getWinner() == null);

	}

	public void clearAll() {
		for (int i = 0; i < monstersArea.size(); i++) {
			monstersArea.get(i).setAttakingOption(true);
			monstersArea.get(i).setSwitchingOption(true);

		}

	}
}
