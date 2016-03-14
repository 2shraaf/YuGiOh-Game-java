package eg.edu.guc.yugioh.board;

import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;

public class Board {
	private Player activePlayer;
	private Player opponentPlayer;
	private Player winner;
	private Player p1;
	private Player p2;

	public Board() {
		this.activePlayer = null;
		this.opponentPlayer = null;
		this.winner = null;
		Card.setBoard(this);
	}

	public void whoStarts(Player p1, Player p2) {
		int rand = (int) (Math.random() * 2) + 1;
		activePlayer = rand == 1 ? p1 : p2;
		opponentPlayer = rand == 1 ? p2 : p1;
	}

	public void startGame(Player p1, Player p2) {
		this.setP1(p1);
		this.setP2(p2);
		whoStarts(p1, p2);
		activePlayer.getField().addNCardsToHand(6);
		opponentPlayer.getField().addNCardsToHand(5);
	}

	public void nextPlayer() {
		// Swapping
		if (getWinner() == null) {
			Player tmp = activePlayer;
			tmp.getField().clearAll();
			activePlayer = opponentPlayer;
			opponentPlayer = tmp;
			activePlayer.addCardToHand();
			activePlayer.getField().setPhase(Phase.MAIN1);
		}
	}

	// Setters and Getters
	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Player getOpponentPlayer() {
		return opponentPlayer;
	}

	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}

	public Player getWinner() {
		Card.getBoard().isThereAWinner();

		return winner;

	}

	public void isThereAWinner() {
		if (activePlayer.getLifePoints() < 1) {
			activePlayer.setLifePoints(0);
			this.setWinner(opponentPlayer);
		} else if (opponentPlayer.getLifePoints() < 1) {
			opponentPlayer.setLifePoints(0);
			this.setWinner(activePlayer);
		}
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

}
