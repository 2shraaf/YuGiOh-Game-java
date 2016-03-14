package eg.edu.guc.yugioh.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class LifePoints extends JLabel implements ActionListener {

	private int lpOld, lpNew;
	private Timer t;
	private int r = 34, g = 139, b = 34;

	public LifePoints() {
		setLp(8000);
		setText("" + lpOld);
		setForeground(new Color(r, g, b));
	}

	public void update(int n) {
		t = new Timer(1, null);
		t.addActionListener(this);
		t.start();
		lpNew = n;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (lpOld <= lpNew) {
			t.stop();
			setText("" + lpOld);
		} else {
			lpOld -= 10;
			setText("" + lpOld);
			if (lpOld % 50 == 0) {
				if (r < 230)
					r++;
				if (g > 0)
					g--;
				setForeground(new Color(r, g, b));
			}
		}
	}

	public int getLp() {
		return lpOld;
	}

	public void setLp(int lp) {
		this.lpOld = lp;
	}

}
