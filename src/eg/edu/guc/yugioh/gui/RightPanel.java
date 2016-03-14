package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RightPanel extends JPanel {
	private JLabel currentPhase, hover;
	private JButton endPhase, endTurn;
	private String mainPath = "src/eg/edu/guc/yugioh/gui/images/";

	public RightPanel(String currPhase) {

		setBounds(1575, 0, 345, 1000);
		setLayout(null);

		hover = new JLabel();
		hover.setBounds(0, 200, 345, 380);
		hover.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		hover.setIcon(new ImageIcon(
				"src/eg/edu/guc/yugioh/gui/images/hover/hoverDefault.png"));
		JPanel phase = new JPanel();
		phase.setBounds(0, 580, 345, 220);
		phase.setLayout(new GridLayout(3, 1));

		currentPhase = new JLabel(currPhase);
		currentPhase
				.setIcon(new ImageIcon(mainPath + "/" + currPhase + ".png"));
		currentPhase.setPreferredSize(new Dimension(345, 75));
		currentPhase.setForeground(Color.GREEN);

		endPhase = new JButton();
		endPhase.setIcon(new ImageIcon(mainPath + "/endphase.png"));
		endPhase.setPressedIcon(new ImageIcon(mainPath + "/endphasePress.png"));
		endPhase.setPreferredSize(new Dimension(345, 72));

		endTurn = new JButton();
		endTurn.setIcon(new ImageIcon(mainPath + "/endturn.png"));
		endTurn.setPressedIcon(new ImageIcon(mainPath + "/endturnPress.png"));
		endTurn.setPreferredSize(new Dimension(345, 72));

		phase.add(currentPhase);
		phase.add(endPhase);
		phase.add(endTurn);
		phase.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		JLabel p1 = new JLabel();
		JLabel p2 = new JLabel();
		p1.setIcon(new ImageIcon(mainPath + "/yami.png"));
		p2.setIcon(new ImageIcon(mainPath + "/marik.png"));
		p2.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		p1.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));

		p2.setBounds(0, 0, 345, 200);
		p1.setBounds(0, 800, 345, 200);
		add(hover);
		add(phase);
		add(p1);
		add(p2);
	}

	public void update(String string) {
		currentPhase.setIcon(new ImageIcon(mainPath + "/" + string + ".png"));
	}

	public void updateHover(String buttonName) {
		String path = "src/eg/edu/guc/yugioh/gui/images/hover/" + buttonName
				+ ".png";
		hover.setIcon(new ImageIcon(path));

	}

	public JLabel getHover() {
		return hover;
	}

	public void setHover(JLabel hover) {
		this.hover = hover;
	}

	public JLabel getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(JLabel currentPhase) {
		this.currentPhase = currentPhase;
	}

	public JButton getEndPhase() {
		return endPhase;
	}

	public void setEndPhase(JButton endPhase) {
		this.endPhase = endPhase;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(JButton endTurn) {
		this.endTurn = endTurn;
	}
}
