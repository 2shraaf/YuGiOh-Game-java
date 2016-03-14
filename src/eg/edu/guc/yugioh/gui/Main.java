package eg.edu.guc.yugioh.gui;

import java.io.IOException;

import javax.swing.JOptionPane;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.listeners.Controller;
import eg.edu.guc.yugioh.listeners.Music;

public class Main {
	 static GUI g;
	public Main() throws IOException, UnexpectedFormatException {
		String p1N = JOptionPane.showInputDialog("Player 1 (Yami Yugi)");
		if (p1N != null) {
			String p2N = JOptionPane.showInputDialog("Player 2 (Marik)");
			if (p2N != null) {
				Player p1 = null,p2 = null;
				try{
				 p1 = new Player(p1N);
				 p2 = new Player(p2N);
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				Board b = new Board();
				b.startGame(p1, p2);
				Music m = new Music();
				 g = new GUI(p1, p2);
				new Controller(b, g, m);
			}
		}
	}

	public static void main(String[] args) throws IOException,
			UnexpectedFormatException {
		new Main();

	}
}
