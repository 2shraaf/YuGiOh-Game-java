package eg.edu.guc.yugioh.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HomePanel extends ImagePanel {

	private JButton startGame, credits, quit;
	private static String mainpath = "src/eg/edu/guc/yugioh/gui/images/";

	public HomePanel() {
		super(mainpath + "yugi.png");
		startGame = new JButton();
		credits = new JButton();
		quit = new JButton();

		setLayout(null);
		startGame.setBounds(485, 585, 500, 128);
		credits.setBounds(485, 585 + 128, 500, 98);
		quit.setBounds(485, 585 + 128 + 98, 500, 128);

		startGame.setIcon(new ImageIcon(mainpath + "/startgame.png"));
		credits.setIcon(new ImageIcon(mainpath + "/credits.png"));
		quit.setIcon(new ImageIcon(mainpath + "/quit.png"));
		add(startGame);
		add(credits);
		add(quit);
	}

	public JButton getStartGame() {
		return startGame;
	}

	public void setStartGame(JButton startGame) {
		this.startGame = startGame;
	}

	public JButton getCredits() {
		return credits;
	}

	public void setCredits(JButton credits) {
		this.credits = credits;
	}

	public JButton getQuit() {
		return quit;
	}

	public void setQuit(JButton quit) {
		this.quit = quit;
	}

}
