package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;

public class GUI extends JFrame {
	private HomePanel homePanel;
	private BoardPanel boardPanel;
	private RightPanel rightPanel;
	private boolean visiblity;

	public GUI(Player p1, Player p2) {
		super();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// setBounds(0, 0, screenSize.width, screenSize.height - 40);
		setPreferredSize(new Dimension(1280, 1000));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		homePanel = new HomePanel();
		homePanel.setBounds(0, 0, 1280, 1000);
		boardPanel = new BoardPanel(p1, p2);
		rightPanel = new RightPanel(Card.GetActiveField().getPhase() + "");
		add(homePanel);
		add(boardPanel);
		add(rightPanel);
		homePanel.setVisible(true);
		boardPanel.setVisible(false);
		rightPanel.setVisible(false);
		homePanel.setEnabled(true);
		boardPanel.setEnabled(false);
		rightPanel.setEnabled(false);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
	}

	public void update() {
		boardPanel.update();
		rightPanel.update(Card.GetActiveField().getPhase() + "");
		homePanel.setVisible(!visiblity);
		boardPanel.setVisible(visiblity);
		rightPanel.setVisible(visiblity);
		homePanel.setEnabled(!visiblity);
		boardPanel.setEnabled(visiblity);
		rightPanel.setEnabled(visiblity);
		if (visiblity) {
			setPreferredSize(new Dimension(1920, 1040));
			pack();
		}
	}

	public RightPanel getRightPanel() {
		return rightPanel;
	}

	public void setRightPanel(RightPanel rightPanel) {
		this.rightPanel = rightPanel;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public boolean isVisiblity() {
		return visiblity;
	}

	public void setVisiblity(boolean visiblity) {
		this.visiblity = visiblity;
	}

	public HomePanel getHomePanel() {
		return homePanel;
	}

	public void setHomePanel(HomePanel homePanel) {
		this.homePanel = homePanel;
	}

}
