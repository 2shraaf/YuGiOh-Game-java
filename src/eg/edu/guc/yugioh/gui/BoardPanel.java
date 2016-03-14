package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import eg.edu.guc.yugioh.board.player.Player;

public class BoardPanel extends JPanel {
	FieldPanel fieldPanelup, fieldPaneldown;

	public BoardPanel(Player p1, Player p2) {
		setLayout(null);
		setBounds(0, 0, 1575, 1000);
		
		fieldPanelup = new FieldPanel(p2, 2);
		JPanel f1 = new JPanel();
		f1.setLayout(null);
		f1.setBounds(0, 0, 1575, 500);
		f1.add(fieldPanelup);
		
		fieldPaneldown = new FieldPanel(p1, 1);
		JPanel f2 = new JPanel();
		f2.setLayout(null);
		f2.setBounds(0, 500, 1575, 500);
		f2.add(fieldPaneldown);
		add(f1);
		add(f2);
	}

	public void update() {
		fieldPanelup.update();
		fieldPaneldown.update();
		validate();
	}
	public FieldPanel getFieldPaneldown() {
		return fieldPaneldown;
	}

	public void setFieldPaneldown(FieldPanel fieldPaneldown) {
		this.fieldPaneldown = fieldPaneldown;
	}

	public FieldPanel getFieldPanelup() {
		return fieldPanelup;
	}

	public void setFieldPanelup(FieldPanel fieldPanelup) {
		this.fieldPanelup = fieldPanelup;
	}

	
}
