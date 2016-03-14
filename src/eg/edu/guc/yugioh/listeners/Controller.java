package eg.edu.guc.yugioh.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Field;
import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.SwitchMonsterException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.HandButton;
import eg.edu.guc.yugioh.gui.Main;
import eg.edu.guc.yugioh.gui.MonsterButton;
import eg.edu.guc.yugioh.gui.SpellButton;

public class Controller implements ActionListener, MouseListener {

	Board b;
	GUI gui;
	JButton firstClick;
	JButton secondClick;
	JButton thirdClick;
	MonsterCard firstM, toSummon, firstSac, secondSac;
	String prev;
	SpellCard firstS;
	Music m;

	// Calls
	ArrayList<MonsterButton> monsterButtons1, monsterButtons2;

	ArrayList<SpellButton> spellButtons1, spellButtons2;

	ArrayList<HandButton> hand1, hand2;

	public Controller(Board b, GUI gui, Music m) {
		this.gui = gui;
		this.b = b;
		this.m = m;

		monsterButtons1 = gui.getBoardPanel().getFieldPaneldown()
				.getMonstersButtons();
		monsterButtons2 = gui.getBoardPanel().getFieldPanelup()
				.getMonstersButtons();

		spellButtons1 = gui.getBoardPanel().getFieldPaneldown()
				.getSpellsButtons();

		spellButtons2 = gui.getBoardPanel().getFieldPanelup()
				.getSpellsButtons();

		hand1 = gui.getBoardPanel().getFieldPaneldown().getHandButtons();
		hand2 = gui.getBoardPanel().getFieldPanelup().getHandButtons();
		addActionListenersToButtons();
	}

	private void update() throws IOException, UnexpectedFormatException {
		gui.update();

		if (b.getWinner() != null) {

			int winner = b.getP1() == b.getWinner() ? 1 : 2;
			JOptionPane.showMessageDialog(null, (winner == 1 ? b.getP1()
					.getName() : b.getP2().getName()) + " Wins!");

			String[] options = { "Yes", "Kfaya keda" };
			int select = JOptionPane.showOptionDialog(null,
					"Would you like to play again ? ", "Game",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			gui.dispose();
			m.close();
			if (select == 0)
				new Main();
		} else {
			addActionListenersToButtons();
			gui.repaint();
		}
	}

	public void addActionListenersToButtons() {

		JButton startGame = gui.getHomePanel().getStartGame();
		JButton credits = gui.getHomePanel().getCredits();
		JButton quit = gui.getHomePanel().getQuit();
		JButton endPhase = gui.getRightPanel().getEndPhase();
		JButton endTurn = gui.getRightPanel().getEndTurn();

		JLabel grave1 = gui.getBoardPanel().getFieldPaneldown().getGraveyard();
		JLabel grave2 = gui.getBoardPanel().getFieldPanelup().getGraveyard();

		if (startGame.getActionListeners().length == 0) {
			startGame.addActionListener(this);
			startGame.setActionCommand("start");
		}
		if (credits.getActionListeners().length == 0) {
			credits.addActionListener(this);
			credits.setActionCommand("credits");
		}
		if (quit.getActionListeners().length == 0) {
			quit.addActionListener(this);
			quit.setActionCommand("quit");
		}
		if (grave1.getMouseListeners().length == 0) {
			grave1.addMouseListener(this);
			grave1.setName("1");
		}
		if (grave2.getMouseListeners().length == 0) {
			grave2.addMouseListener(this);
			grave2.setName("2");
		}
		if (endPhase.getActionListeners().length == 0) {
			endPhase.addActionListener(this);
			endPhase.setActionCommand("phase");
		}
		if (endTurn.getActionListeners().length == 0) {
			endTurn.addActionListener(this);
			endTurn.setActionCommand("turn");
		}
		for (MonsterButton monsterButton : monsterButtons1) {
			if (monsterButton.getText().equals("Empty")) {
				monsterButton.setVisible(false);
				monsterButton.setEnabled(false);
			} else {
				monsterButton.setEnabled(true);
				if (monsterButton.getActionListeners().length == 0) {
					monsterButton.addActionListener(this);
					monsterButton.addMouseListener(this);
					monsterButton.setActionCommand("1");
				}
			}
		}
		for (MonsterButton monsterButton : monsterButtons2) {
			monsterButton.setEnabled(true);
			if (monsterButton.getActionListeners().length == 0) {
				monsterButton.addActionListener(this);
				monsterButton.addMouseListener(this);
				monsterButton.setActionCommand("2");
			}
		}
		for (SpellButton spellButton : spellButtons1) {
			if (spellButton.getActionListeners().length == 0) {
				spellButton.addMouseListener(this);
				spellButton.addActionListener(this);
				spellButton.setActionCommand("1");
			}

		}
		for (SpellButton spellButton : spellButtons2) {

			spellButton.setEnabled(true);
			if (spellButton.getActionListeners().length == 0) {

				spellButton.addActionListener(this);
				spellButton.addMouseListener(this);
				spellButton.setActionCommand("2");

			}
		}
		for (HandButton handButton : hand1) {
			if (handButton.getActionListeners().length == 0) {
				handButton.addActionListener(this);
				handButton.addMouseListener(this);
				handButton.setActionCommand("1");
			}
		}
		for (HandButton handButton : hand2) {
			if (handButton.getActionListeners().length == 0) {
				handButton.addActionListener(this);
				handButton.addMouseListener(this);
				handButton.setActionCommand("2");
			}
		}
	}

	public boolean checkPlayerActive(int p) {
		if (p == 1)
			return gui.getBoardPanel().getFieldPaneldown().getP() == b
					.getActivePlayer();
		else
			return gui.getBoardPanel().getFieldPanelup().getP() == b
					.getActivePlayer();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String p = "";
		if (firstClick == null) {
			if (e.getSource() instanceof MonsterButton) {
				firstClick = (MonsterButton) e.getSource();
				p = e.getActionCommand();
				if (checkPlayerActive(Integer.parseInt(p)))
					monsterFirst(Integer.parseInt(p));
				else {
					JOptionPane.showMessageDialog(null,
							"Not this player's turn");
					firstClick = null;
				}
			} else if (e.getSource() instanceof SpellButton) {
				firstClick = (SpellButton) e.getSource();
				p = e.getActionCommand();
				if (checkPlayerActive(Integer.parseInt(p)))
					spellFirst(Integer.parseInt(p));
				else {
					JOptionPane.showMessageDialog(null,
							"Not this player's turn");
					firstClick = null;
				}
			} else if (e.getSource() instanceof HandButton) {

				firstClick = (HandButton) e.getSource();
				p = e.getActionCommand();
				if (checkPlayerActive(Integer.parseInt(p))) {
					handFirst(Integer.parseInt(p));
				} else {
					JOptionPane.showMessageDialog(null,
							"Not this player's turn");
					firstClick = null;
				}
			} else if (e.getActionCommand().equals("phase"))
				b.getActivePlayer().endPhase();
			else if (e.getActionCommand().equals("turn"))
				b.getActivePlayer().endTurn();
			else if (e.getActionCommand().equals("start"))
				gui.setVisiblity(true);
			else if (e.getActionCommand().equals("credits"))
				JOptionPane
						.showMessageDialog(null,
								"The Amazing (Code Of Duty) Team : Ahmed Awwad & Hisham Zahran");
			else if (e.getActionCommand().equals("quit")) {
				gui.dispose();
				m.close();
			}
		}

		else if (secondClick == null) {
			if (e.getSource() instanceof MonsterButton) {
				secondClick = (MonsterButton) e.getSource();
				p = e.getActionCommand();
				if (prev.equals("Attack")) {
					if (!checkPlayerActive(Integer.parseInt(p)))
						monsterAttack(Integer.parseInt(p));
					else {
						JOptionPane.showMessageDialog(null,
								"Please Select An Opponent Monster");
						secondClick = null;
					}
				} else if (prev.equals("Spell")) {
					if (firstS instanceof ChangeOfHeart) {
						if (!checkPlayerActive(Integer.parseInt(p)))
							monsterSpell(Integer.parseInt(p));
						else {
							JOptionPane.showMessageDialog(null,
									"Please select an opponent monster");
							secondClick = null;
						}
					} else
						// Mage Power
						monsterSpell(Integer.parseInt(p));

				} else if (prev.equals("Sacrifice1")
						|| prev.equals("Sacrifice2")) {
					if (checkPlayerActive(Integer.parseInt(p)))
						monsterSacrifice1(Integer.parseInt(p));
					else {
						JOptionPane.showMessageDialog(null,
								"Please select an monster from your field!");
						secondClick = null;
					}
				} else {
					firstClick = null;
					secondClick = null;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a monster");
				secondClick = null;
			}

		} else if (thirdClick == null) {
			if (prev.equals("Sacrifice2")) {
				if (e.getSource() instanceof MonsterButton) {
					thirdClick = (MonsterButton) e.getSource();
					p = e.getActionCommand();
					if (checkPlayerActive(Integer.parseInt(p)))
						monsterSacrifice2(Integer.parseInt(p));
					else {
						JOptionPane.showMessageDialog(null,
								"Please select an monster from your field!");
						secondClick = null;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Please select a monster");
					thirdClick = null;
				}
			} else {
				firstClick = null;
				secondClick = null;
				thirdClick = null;
			}
		}

		try {
			update();
		} catch (IOException | UnexpectedFormatException e1) {
			e1.printStackTrace();
		}
	}

	private void monsterSacrifice2(int p) {

		ArrayList<MonsterButton> active = p == 1 ? monsterButtons1
				: monsterButtons2;
		Field f = b.getActivePlayer().getField();
		ArrayList<MonsterCard> monstersArea = f.getMonstersArea();
		int mi = active.indexOf(thirdClick);
		MonsterCard m = monstersArea.get(mi);
		secondSac = m;
		ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
		sacrifices.add(firstSac);
		sacrifices.add(secondSac);

		String[] options = { "Set", "Summon" };
		int select = JOptionPane.showOptionDialog(null, "Set or Summon ?",
				"Monster", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);

		try {
			if (select == 0)
				b.getActivePlayer().setMonster(toSummon, sacrifices);
			else if(select==1)
				b.getActivePlayer().summonMonster(toSummon, sacrifices);
		} catch (NoMonsterSpaceException | MultipleMonsterAdditionException
				| WrongPhaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			firstClick = null;
			secondClick = null;
			thirdClick = null;
		}

	}

	private void monsterSacrifice1(int p) {

		ArrayList<MonsterButton> active = p == 1 ? monsterButtons1
				: monsterButtons2;
		Field f = b.getActivePlayer().getField();
		ArrayList<MonsterCard> monstersArea = f.getMonstersArea();
		int mi = active.indexOf(secondClick);
		MonsterCard m = monstersArea.get(mi);
		firstSac = m;

		if (prev.equals("Sacrifice1")) {
			ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
			sacrifices.add(firstSac);
			String[] options = { "Set", "Summon" };
			int select = JOptionPane.showOptionDialog(null, "Set or Summon ?",
					"Monster", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);

			try {
				if (select == 0)
					b.getActivePlayer().setMonster(toSummon, sacrifices);
				else if(select==1)
					b.getActivePlayer().summonMonster(toSummon, sacrifices);
			} catch (NoMonsterSpaceException | MultipleMonsterAdditionException
					| WrongPhaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} finally {
				firstClick = null;
				secondClick = null;
			}
		} else
		{
			secondClick.setBorder(BorderFactory.createLineBorder(
					new Color(34, 139, 34), 5));
			JOptionPane.showMessageDialog(null, "Select another monster");
		}
	}

	private void monsterSpell(int p) {

		ArrayList<MonsterButton> active = p == 1 ? monsterButtons1
				: monsterButtons2;

		Field f1 = b.getActivePlayer().getField();
		Field f2 = b.getOpponentPlayer().getField();
		ArrayList<MonsterCard> monstersArea = checkPlayerActive(p) ? f1
				.getMonstersArea() : f2.getMonstersArea();
		int mi = active.indexOf(secondClick);
		MonsterCard m = monstersArea.get(mi);
		try {
			b.getActivePlayer().activateSpell(firstS, m);
		} catch (NoSpellSpaceException | WrongPhaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			firstClick = null;
			secondClick = null;
		}
	}

	private void monsterAttack(int p) {

		ArrayList<MonsterButton> active = p == 1 ? monsterButtons1
				: monsterButtons2;
		Field f = b.getOpponentPlayer().getField();
		ArrayList<MonsterCard> monstersArea2 = f.getMonstersArea();
		int mi = active.indexOf(secondClick);
		MonsterCard m = monstersArea2.get(mi);

		try {
			b.getActivePlayer().declareAttack(firstM, m);
		} catch (DefenseMonsterAttackException | MonsterMultipleAttackException
				| WrongPhaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			firstClick = null;
			secondClick = null;
		}
	}

	private void handFirst(int p) {

		ArrayList<HandButton> active = p == 1 ? hand1 : hand2;
		Field f = b.getActivePlayer().getField();
		Field f2 = b.getOpponentPlayer().getField();

		int mons1 = f.getMonstersArea().size();
		int mons2 = f2.getMonstersArea().size();
		ArrayList<Card> hand = f.getHand();

		int hi = active.indexOf(firstClick);
		Card c = hand.get(hi);
		if (c instanceof SpellCard) {
			SpellCard s = (SpellCard) c;
			String[] options = { "Set", "Activate" };
			int select = JOptionPane.showOptionDialog(null,
					"Set or Activate Spell?", "Spell",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			if (select == 0) {// Set
				try {
					b.getActivePlayer().setSpell(s);
					return;
				} catch (NoSpellSpaceException | WrongPhaseException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} finally {
					firstClick = null;
				}
			} else if (select == 1) {// Activate
				try {
					if (!(s instanceof ChangeOfHeart || s instanceof MagePower)) {
						firstClick = null;
						b.getActivePlayer().activateSpell(s, null);
					} else {
						if ((s instanceof ChangeOfHeart && mons2 == 0)
								|| (s instanceof MagePower && mons1 == 0 && mons2 == 0)) {
							JOptionPane.showMessageDialog(null,
									"No Monsters to Select");
							b.getActivePlayer().activateSpell(s, null);
							firstClick = null;
						} else {
							firstClick
									.setBorder(BorderFactory.createLineBorder(
											new Color(34, 139, 34), 5));
							JOptionPane.showMessageDialog(null,
									"Select a monster");
							firstS = s;
							prev = "Spell";
						}
					}
				} catch (NoSpellSpaceException | WrongPhaseException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			} else {
				firstClick = null;
			}
		} else// Monster Card
		{
			MonsterCard m = (MonsterCard) c;
			if (m.getLevel() >= 5) {
				if (m.getLevel() < 7) {

					if (b.getActivePlayer().getField().getMonstersArea().size() < 1) {
						JOptionPane.showMessageDialog(null,
								"No enough sacrifices");
						firstClick = null;
					} else {
						firstClick.setBorder(BorderFactory.createLineBorder(
								new Color(34, 139, 34), 5));
						JOptionPane.showMessageDialog(null,
								"Select 1 Sacrifice");
						toSummon = m;
						prev = "Sacrifice1";
					}
				} else {

					if (b.getActivePlayer().getField().getMonstersArea().size() < 2) {
						JOptionPane.showMessageDialog(null,
								"No enough sacrifices");
						firstClick = null;
					} else {
						firstClick.setBorder(BorderFactory.createLineBorder(
								new Color(34, 139, 34), 5));
						JOptionPane.showMessageDialog(null,
								"Select 2 Sacrifices");
						toSummon = m;
						prev = "Sacrifice2";
					}
				}
				return;
			} else {
				String[] options = { "Set", "Summon" };
				int select = JOptionPane.showOptionDialog(null,
						"Set or Summon Monster?", "Monster",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				if (select == 0) {// Set Monster
					try {
						b.getActivePlayer().setMonster(m);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					} finally {
						firstClick = null;
					}
				} else if (select == 1)// Summon
				{
					try {
						b.getActivePlayer().summonMonster(m);
						return;
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					} finally {
						firstClick = null;
					}
				} else
					firstClick = null;
			}
		}

	}

	private void spellFirst(int p) {

		ArrayList<SpellButton> active = p == 1 ? spellButtons1 : spellButtons2;

		Field f = b.getActivePlayer().getField();
		Field f2 = b.getOpponentPlayer().getField();
		int mons1 = f.getMonstersArea().size();
		int mons2 = f2.getMonstersArea().size();
		ArrayList<SpellCard> s1 = f.getSpellArea();

		int si = active.indexOf(firstClick);

		SpellCard s = s1.get(si);
		try {
			if (!(s instanceof ChangeOfHeart || s instanceof MagePower)) {
				b.getActivePlayer().activateSpell(s, null);
				firstClick = null;
			} else if ((s instanceof ChangeOfHeart && mons2 == 0)
					|| (s instanceof MagePower && mons1 == 0 && mons2 == 0)) {
				JOptionPane.showMessageDialog(null, "No Monsters to Select");
				firstClick = null;
			} else {
				JOptionPane.showMessageDialog(null, "Please Select A Monster");
				firstS = s;
				prev = "Spell";
			}

		} catch (WrongPhaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void monsterFirst(int p) {

		ArrayList<MonsterButton> active = p == 1 ? monsterButtons1
				: monsterButtons2;

		Field f1 = b.getActivePlayer().getField();
		Field f2 = b.getOpponentPlayer().getField();
		ArrayList<MonsterCard> monstersArea1 = f1.getMonstersArea();
		ArrayList<MonsterCard> monstersArea2 = f2.getMonstersArea();

		int mi = active.indexOf(firstClick);

		MonsterCard m = monstersArea1.get(mi);
		if (f1.getPhase() != Phase.BATTLE) {
			try {
				String[] options = { "Continue", "No" };
				int select = JOptionPane.showOptionDialog(null, "Switch ?",
						"Monster", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if (select == 0)
					b.getActivePlayer().switchMonsterMode(m);

			} catch (SwitchMonsterException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} finally {
				firstClick = null;
			}

		} else {
			if (monstersArea2.isEmpty()) {

				try {
					b.getActivePlayer().declareAttack(m);
				} catch (DefenseMonsterAttackException
						| MonsterMultipleAttackException | WrongPhaseException e) {

					JOptionPane.showMessageDialog(null, e.getMessage());
				} finally {
					firstClick = null;
				}

			} else {
				firstClick.setBorder(BorderFactory.createLineBorder(
						new Color(34, 139, 34), 5));
				firstM = m;
				prev = "Attack";
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		String path = "";
		if (e.getSource() instanceof MonsterButton) {
			MonsterButton mb = (MonsterButton) e.getSource();
			int p = Integer.parseInt(mb.getActionCommand());
			MonsterCard m = null;

			if (p == 1) {
				ArrayList<MonsterCard> mons = b.getP1().getField()
						.getMonstersArea();
				m = mons.get(monsterButtons1.indexOf(mb));
			} else {
				ArrayList<MonsterCard> mons = b.getP2().getField()
						.getMonstersArea();
				m = mons.get(monsterButtons2.indexOf(mb));
			}

			path = !checkPlayerActive(p) && m.isHidden() ? "hoverDefault" : m
					.getName();
		} else if (e.getSource() instanceof SpellButton) {
			SpellButton mb = (SpellButton) e.getSource();
			int p = Integer.parseInt(mb.getActionCommand());
			SpellCard m = null;
			if (checkPlayerActive(p)) {
				if (p == 1) {
					ArrayList<SpellCard> spls = b.getP1().getField()
							.getSpellArea();
					m = spls.get(spellButtons1.indexOf(mb));
				} else {
					ArrayList<SpellCard> spls = b.getP2().getField()
							.getSpellArea();
					m = spls.get(spellButtons2.indexOf(mb));
				}
				path = m.getName();
			} else
				path = "hoverDefault";
		} else if (e.getSource() instanceof HandButton) {
			HandButton mb = (HandButton) e.getSource();
			int p = Integer.parseInt(mb.getActionCommand());
			Card m = null;
			if (checkPlayerActive(p)) {
				if (p == 1) {
					ArrayList<Card> hand = b.getP1().getField().getHand();
					m = hand.get(hand1.indexOf(mb));
				} else {
					ArrayList<Card> hand = b.getP2().getField().getHand();
					m = hand.get(hand2.indexOf(mb));
				}
				path = m.getName();
			} else
				path = "hoverDefault";
		} else if (e.getSource() instanceof JLabel) {

			JLabel graveyard = (JLabel) e.getSource();
			int p = Integer.parseInt(graveyard.getName());
			Card m = null;
			if (p == 1) {
				ArrayList<Card> grave = b.getP1().getField().getGraveyard();
				m = grave.get(grave.size() - 1);
			} else {
				ArrayList<Card> grave = b.getP2().getField().getGraveyard();
				m = grave.get(grave.size() - 1);
			}
			path = m.getName();
		}

		gui.getRightPanel().updateHover(path);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		gui.getRightPanel().updateHover("hoverDefault");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
