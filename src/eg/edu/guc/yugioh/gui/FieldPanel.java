package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.listeners.LifePoints;

public class FieldPanel extends JPanel {
	protected ArrayList<MonsterButton> monstersButtons = new ArrayList<MonsterButton>();
	protected ArrayList<SpellButton> spellsButtons = new ArrayList<SpellButton>();
	private ArrayList<HandButton> handButtons = new ArrayList<HandButton>();
	private ImagePanel hand, msPanel, gdPanel, information;
	private JPanel monstersArea, spellsArea;
	private Player p;
	private JLabel deck, graveyard;
	private LifePoints lp;
	private int whichPlayer;

	private String mainPath = "src/eg/edu/guc/yugioh/gui/images/";

	public FieldPanel(Player p, int whichPlayer) {
		this.p = p;
		this.whichPlayer = whichPlayer;
		setLayout(null);
		setBounds(0, 0, 1575, 500);
		// Info bar
		infoBar();
		// Deck_graveyard
		deck_grave();
		// Hand
		hand();
		// Monster and Spells
		mons_spell();
	}

	private void deck_grave() {
		String path = whichPlayer == 1 ? "src/eg/edu/guc/yugioh/gui/images/GraveDeck1"
				: "src/eg/edu/guc/yugioh/gui/images/GraveDeck2";
		gdPanel = new ImagePanel(path + ".png");

		gdPanel.setLayout(null);
		gdPanel.setBounds(0, whichPlayer == 2 ? 140 : 0, 155, 360);

		graveyard = new JLabel();
		graveyard.setVisible(false);
		graveyard.setBounds(19, whichPlayer == 1 ? 23 : 203, 112, 135);

		deck = new JLabel(p.getField().getDeck().getDeck().size() + "");
		deck.setBounds(55, whichPlayer == 1 ? 180 : 0, 112, 135);
		deck.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
		deck.setForeground(Color.WHITE);
		gdPanel.add(whichPlayer == 1 ? graveyard : deck);
		gdPanel.add(whichPlayer == 1 ? deck : graveyard);
		add(gdPanel);
	}

	private void infoBar() {
		information = new ImagePanel(mainPath
				+ (checkPlayerTurn() ? "/activeInfo.png" : "/oppInfo.png"));
		information.setLayout(null);
		information.setBackground(Color.BLACK);
		information.setBounds(0, whichPlayer == 1 ? 360 : 0, 1575, 140);

		JLabel name = new JLabel(p.getName());
		lp = new LifePoints();
		lp.setFont(new Font("Arial", Font.BOLD, 60));
		lp.setBounds(960, 30, 420, 100);

		name.setFont(new Font("Arial", Font.BOLD, 60));
		name.setForeground(Color.WHITE);
		name.setBounds(550, 30, 420, 100);

		information.add(name);
		information.add(lp);
		add(information);
	}

	public boolean checkPlayerTurn() {
		return p == Card.getBoard().getActivePlayer();
	}

	private void hand() {
		hand = new ImagePanel(mainPath + "/board.png");
		hand.setBackground(Color.WHITE);
		hand.setLayout(new FlowLayout());
		hand.setBounds(155, whichPlayer == 2 ? 140 : 0, 650, 360);
		hand.setPreferredSize(new Dimension(650, 360));
		ArrayList<Card> handArea = p.getField().getHand();
		for (int i = 0; i < handArea.size(); i++) {
			HandButton h = new HandButton();
			Card c = handArea.get(i);
			String path = mainPath;
			if (!checkPlayerTurn())
				path += "/set/facedown.png";
			else {
				path += "/normal/" + c.getName() + ".png";
				h.setToolTipText((c instanceof MonsterCard ? "Summon"
						: "Activate") + " or Set");
			}
			h.setIcon(new ImageIcon(path));
			h.setPreferredSize(new Dimension(112, 135));
			handButtons.add(h);
			hand.add(h);
		}
		add(hand);
	}

	private void mons_spell() {
		String path = mainPath
				+ (whichPlayer == 1 ? "MonsterSpellArea1" : "MonsterSpellArea2");
		msPanel = new ImagePanel(path + ".png");
		msPanel.setLayout(null);
		msPanel.setBounds(805, whichPlayer == 2 ? 140 : 0, 774, 360);
		add(msPanel);
	}

	public void update() {
		// Monster & Spells
		remove(msPanel);
		String path1 = mainPath
				+ (whichPlayer == 1 ? "MonsterSpellArea1" : "MonsterSpellArea2");
		msPanel = new ImagePanel(path1 + ".png");

		msPanel.setLayout(null);
		msPanel.setBounds(805, whichPlayer == 2 ? 140 : 0, 774, 360);

		monstersButtons.clear();
		spellsButtons.clear();

		monstersArea = new JPanel();
		spellsArea = new JPanel();
		monstersArea.setLayout(new GridLayout(1, 5, 48, 0));
		spellsArea.setLayout(new GridLayout(1, 5, 48, 0));

		// monstersArea.setLayout(new FlowLayout(FlowLayout.LEFT, 48, 0));
		// spellsArea.setLayout(new FlowLayout(FlowLayout.LEFT, 48, 0));
		// monstersArea.setBounds(19, 23, 730, 135);
		// spellsArea.setBounds(19, 203, 730, 135);

		monstersArea.setLayout(null);
		spellsArea.setLayout(null);
		monstersArea.setOpaque(false);
		spellsArea.setOpaque(false);

		monstersArea.setBounds(0, whichPlayer == 1 ? 23 : 203, 774, 135);
		spellsArea.setBounds(0, whichPlayer == 1 ? 203 : 23, 774, 135);
		ArrayList<MonsterCard> monstersAreaCards = p.getField()
				.getMonstersArea();
		ArrayList<SpellCard> spellsAreaCards = p.getField().getSpellArea();

		for (int i = 0; i < monstersAreaCards.size(); i++) {
			MonsterCard m = monstersAreaCards.get(i);
			MonsterButton mb = new MonsterButton();
			String path2 = mainPath;
			if (m.getMode() == Mode.DEFENSE) {
				mb.setBounds(i * 155 + (19 / 2), 10, 135, 112);
				path2 += "/set/" + (m.isHidden() ? "facedownR" : m.getName())
						+ ".png";
			} else {
				mb.setBounds(i * 155 + 19, 0, 112, 135);
				path2 += "/normal/" + m.getName() + ".png";
			}
			mb.setIcon(new ImageIcon(path2));
			String possibleMove = Card.getBoard().getActivePlayer().getField()
					.getPhase() == Phase.BATTLE ? "Attack" : "Switch";
			if (checkPlayerTurn())
				mb.setToolTipText(m.toString(possibleMove));
			else
				mb.setToolTipText(m.isHidden() ? "" : m.toString(""));

			mb.setVisible(true);
			monstersButtons.add(mb);
			monstersArea.add(mb);
		}
		for (int i = 0; i < spellsAreaCards.size(); i++) {
			SpellCard s = spellsAreaCards.get(i);
			SpellButton sb = new SpellButton();

			String path3 = mainPath + "/set/"
					+ (s.isHidden() ? "facedown" : s.getName()) + ".png";
			sb.setIcon(new ImageIcon(path3));
			sb.setBounds(i * 155 + 19, 0, 112, 135);
			sb.setToolTipText(s.toString());
			spellsButtons.add(sb);
			spellsArea.add(sb);
		}
		msPanel.add(whichPlayer == 1 ? monstersArea : spellsArea);
		msPanel.add(whichPlayer == 1 ? spellsArea : monstersArea);
		add(msPanel);

		// Hand
		remove(hand);
		handButtons.clear();
		hand = new ImagePanel(mainPath + "board.png");
		hand.setBackground(Color.WHITE);
		hand.setLayout(new FlowLayout());
		hand.setBounds(155, whichPlayer == 2 ? 140 : 0, 650, 360);
		hand.setPreferredSize(new Dimension(650, 360));
		ArrayList<Card> handArea = p.getField().getHand();
		for (int i = 0; i < handArea.size(); i++) {
			HandButton h = new HandButton();
			Card c = handArea.get(i);
			String path = mainPath;
			if (!checkPlayerTurn())
				path += "/set/facedown.png";
			else {
				path += "/normal/" + c.getName() + ".png";
				h.setToolTipText((c instanceof MonsterCard ? "Summon"
						: "Activate") + " or Set");
			}
			h.setIcon(new ImageIcon(path));
			h.setPreferredSize(new Dimension(112, 135));
			handButtons.add(h);
			hand.add(h);
		}
		add(hand);

		// Info
		information.setImage(mainPath
				+ (checkPlayerTurn() ? "/activeInfo.png" : "/oppInfo.png"));
		deck.setText("" + p.getField().getDeck().getDeck().size());

		lp.update(p.getLifePoints());
		// lp.setText(p.getLifePoints() + "");
		// int oldLp = Integer.parseInt(lp.getText());
		// int newLp = p.getLifePoints();
		// while (oldLp > newLp) {
		//
		// oldLp -= 50;
		//
		// try {
		// Thread.sleep(5);
		// lp.setText(oldLp + "");
		// } catch (InterruptedException e) {
		// Thread.currentThread().interrupt();
		// }
		// Main.g.repaint();
		// }
		ArrayList<Card> grave = p.getField().getGraveyard();
		if (grave.size() != 0) {
			graveyard.setVisible(true);
			String path5 = mainPath + "/normal/"
					+ grave.get(grave.size() - 1).getName() + ".png";
			graveyard.setIcon(new ImageIcon(path5));
		} else
			graveyard.setVisible(false);

	}

	public ArrayList<MonsterButton> getMonstersButtons() {
		return monstersButtons;
	}

	public void setMonstersButtons(ArrayList<MonsterButton> monstersButtons) {
		this.monstersButtons = monstersButtons;
	}

	public ArrayList<SpellButton> getSpellsButtons() {
		return spellsButtons;
	}

	public void setSpellsButtons(ArrayList<SpellButton> spellsButtons) {
		this.spellsButtons = spellsButtons;
	}

	public ArrayList<HandButton> getHandButtons() {
		return handButtons;
	}

	public void setHandButtons(ArrayList<HandButton> handButtons) {
		this.handButtons = handButtons;
	}

	public int getWhichPlayer() {
		return whichPlayer;
	}

	public void setWhichPlayer(int whichPlayer) {
		this.whichPlayer = whichPlayer;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public JLabel getGraveyard() {
		return graveyard;
	}

	public void setGraveyard(JLabel graveyard) {
		this.graveyard = graveyard;
	}

}