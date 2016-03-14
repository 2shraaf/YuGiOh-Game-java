package eg.edu.guc.yugioh.board.player;

import java.io.IOException;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.SwitchMonsterException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class Player implements Duelist {
	private String name;
	private int lifePoints;
	private Field field;
	private boolean AddMonsterOption;
	private final int lp = 8000;

	public Player(String name) throws IOException, UnexpectedFormatException {
		this.name = name;
		lifePoints = lp;
		AddMonsterOption = true;
		field = new Field();
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public String getName() {
		return name;
	}

	public Field getField() {
		return field;
	}

	@Override
	public boolean summonMonster(MonsterCard monster) {
		if (!this.isAddMonsterOption())
			throw new MultipleMonsterAdditionException();
		if (this.getField().getMonstersArea().size() >= 5)
			throw new NoMonsterSpaceException();

		if (this.getField().CheckAddingCard()
				&& this.getField().getHand().contains((MonsterCard) monster)
				&& this.isAddMonsterOption() && monster.getLevel() < 5) {
			this.getField().addMonsterToField(monster, Mode.ATTACK, false);
			this.setAddMonsterOption(false);
			return true;

		}
		return false;
	}

	@Override
	public boolean summonMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) {
		if (!this.isAddMonsterOption())
			throw new MultipleMonsterAdditionException();
		if (this.getField().getMonstersArea().size() >= 5)
			throw new NoMonsterSpaceException();

		if (this.getField().CheckAddingCard()
				&& this.getField().getHand().contains(monster)) {
			this.getField().addMonsterToField(monster, Mode.ATTACK, sacrifices);
			monster.setHidden(false);
			this.setAddMonsterOption(false);
			return true;
		}
		return false;
	}

	@Override
	public boolean setMonster(MonsterCard monster) {
		if (this.getField().getMonstersArea().size() >= 5)
			throw new NoMonsterSpaceException();
		if (!this.isAddMonsterOption())
			throw new MultipleMonsterAdditionException();

		if (this.getField().CheckAddingCard()
				&& this.getField().getHand().contains((MonsterCard) monster)) {
			this.getField().addMonsterToField(monster, Mode.DEFENSE, true);
			monster.setHidden(true);
			this.setAddMonsterOption(false);
			return true;

		}
		return false;
	}

	@Override
	public boolean setMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) {
		if (this.getField().getMonstersArea().size() >= 5)
			throw new NoMonsterSpaceException();
		if (!this.isAddMonsterOption())
			throw new MultipleMonsterAdditionException();

		if (this.getField().CheckAddingCard()
				&& this.getField().getHand().contains((MonsterCard) monster)
				&& (monster.getLevel() >= 7 && sacrifices.size() == 2 || monster
						.getLevel() < 7 && sacrifices.size() == 1)
				&& this.isAddMonsterOption()) {
			this.getField()
					.addMonsterToField(monster, Mode.DEFENSE, sacrifices);
			this.setAddMonsterOption(false);
			return true;
		}
		return false;
	}

	@Override
	public boolean setSpell(SpellCard spell) {
		if (this.getField().getSpellArea().size() >= 5)
			throw new NoSpellSpaceException();
		if (this.getField().CheckAddingCard()
				&& this.getField().getHand().contains((SpellCard) spell)) {
			this.getField().addSpellToField(spell, null, true);
			return true;
		}
		return false;
	}

	@Override
	public boolean activateSpell(SpellCard spell, MonsterCard monster) {
		if (this.getField().getSpellArea().size() >= 5)
			throw new NoSpellSpaceException();

		if (Card.GetActiveField().getPhase() == Phase.BATTLE)
			throw new WrongPhaseException();

		if (Card.getBoard().getActivePlayer() == this
				&& Card.getBoard().getWinner() == null
				&& ((this.getField().getHand().contains((SpellCard) spell) && this
						.getField().getSpellArea().size() < 5) || this
						.getField().getSpellArea().contains((SpellCard) spell))) {

			if (spell.getLocation() == Location.HAND)
				this.getField().addSpellToField(spell, monster, false);
			else
				this.getField().activateSetSpell(spell, monster);

			return true;
		}

		return false;
	}

	@Override
	public boolean declareAttack(MonsterCard activeMonster,
			MonsterCard opponentMonster) {
		if (!activeMonster.isAttakingOption())
			throw new MonsterMultipleAttackException();

		if (Card.GetActiveField().getPhase() != Phase.BATTLE)
			throw new WrongPhaseException();

		if (activeMonster.getMode() == Mode.DEFENSE)
			throw new DefenseMonsterAttackException();
		if (Card.GetActiveField().getMonstersArea().contains(activeMonster)
				&& Card.GetOppField().getMonstersArea()
						.contains(opponentMonster)
				&& this == Card.getBoard().getActivePlayer()
				&& Card.getBoard().getWinner() == null
				&& activeMonster.isAttakingOption()) {
			activeMonster.action(opponentMonster);
			activeMonster.setAttakingOption(false);
			Card.getBoard().isThereAWinner();

			return true;
		}
		return false;
	}

	@Override
	public boolean declareAttack(MonsterCard activeMonster) {
		if (!activeMonster.isAttakingOption())
			throw new MonsterMultipleAttackException();

		if (Card.GetActiveField().getPhase() != Phase.BATTLE)
			throw new WrongPhaseException();

		if (activeMonster.getMode() == Mode.DEFENSE)
			throw new DefenseMonsterAttackException();

		if (Card.GetActiveField().getMonstersArea().contains(activeMonster)
				&& this == Card.getBoard().getActivePlayer()
				&& Card.GetOppField().getMonstersArea().size() == 0
				&& Card.getBoard().getWinner() == null) {
			activeMonster.action();
			activeMonster.setAttakingOption(false);

			Card.getBoard().isThereAWinner();

			return true;
		}
		return false;
	}

	@Override
	public void addCardToHand() {
		if (this == Card.getBoard().getActivePlayer()
				&& Card.getBoard().getWinner() == null) {
			if (this.getField().getDeck().getDeck().size() == 0)
				Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
			else
				this.getField().addCardToHand();
		}
	}

	@Override
	public void addNCardsToHand(int n) {
		if (this == Card.getBoard().getActivePlayer()
				&& Card.getBoard().getWinner() == null) {
			if (this.getField().getDeck().getDeck().size() < n)
				Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());

			this.getField().addNCardsToHand(n);
		}
	}

	@Override
	public void endPhase() {
		if (Card.getBoard().getWinner() == null
				&& this == Card.getBoard().getActivePlayer()) {
			if (this.getField().getPhase() == Phase.MAIN1)
				this.getField().setPhase(Phase.BATTLE);
			else if (this.getField().getPhase() == Phase.BATTLE)
				this.getField().setPhase(Phase.MAIN2);
			else if (this.getField().getPhase() == Phase.MAIN2)
				this.endTurn();
		}

	}

	@Override
	public boolean switchMonsterMode(MonsterCard monster)
			throws SwitchMonsterException, WrongPhaseException {
		if (!monster.isSwitchingOption())
			throw new SwitchMonsterException();

		if (this.getField().CheckAddingCard()
				&& this.getField().getMonstersArea().contains(monster)) {
			if (monster.getMode() == Mode.ATTACK)
				monster.setMode(Mode.DEFENSE);
			else {
				monster.setMode(Mode.ATTACK);
				monster.setHidden(false);
			}
			monster.setSwitchingOption(false);
			return true;

		}
		return false;
	}

	@Override
	public boolean endTurn() {
		if ((Card.GetActiveField() == this.getField() && Card.getBoard()
				.getWinner() == null)) {
			this.AddMonsterOption = true;
			Card.getBoard().nextPlayer();

			Card.getBoard().isThereAWinner();
			return true;
		}
		return false;
	}

	public boolean isAddMonsterOption() {
		return AddMonsterOption;
	}

	public void setAddMonsterOption(boolean addMonsterOption) {
		AddMonsterOption = addMonsterOption;
	}

}
