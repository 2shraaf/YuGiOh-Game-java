package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.*;
import eg.edu.guc.yugioh.exceptions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Deck {
	private static ArrayList<Card> monsters;
	private static ArrayList<Card> spells;
	private ArrayList<Card> deck;
	private static String monstersPath = "src/Database-Monsters.csv";
	private static String spellsPath = "src/Database-Spells.csv";

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}

	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String monstersPath) {
		Deck.monstersPath = monstersPath;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String spellsPath) {
		Deck.spellsPath = spellsPath;
	}

	public Deck() throws IOException, FileNotFoundException,
			UnexpectedFormatException {
		Scanner sc = new Scanner(System.in);

		try {
			monsters = loadCardsFromFile(monstersPath);

		} catch (FileNotFoundException | UnexpectedFormatException e) {

			try {
				monstersPath = sc.nextLine();
				monsters = loadCardsFromFile(monstersPath);

			} catch (FileNotFoundException | UnexpectedFormatException e1) {
				try {
					monstersPath = sc.nextLine();
					monsters = loadCardsFromFile(monstersPath);
				} catch (FileNotFoundException | UnexpectedFormatException e2) {
					try {
						monstersPath = sc.nextLine();
						monsters = loadCardsFromFile(monstersPath);

					} catch (FileNotFoundException | UnexpectedFormatException e3) {
						e3.printStackTrace();
						sc.close();
						throw e3;
					}
				}
			}
		}

		try {
			spells = loadCardsFromFile(spellsPath);
		} catch (FileNotFoundException | UnexpectedFormatException e) {
			try {
				spellsPath = sc.nextLine();
				spells = loadCardsFromFile(spellsPath);
			} catch (FileNotFoundException | UnexpectedFormatException e1) {
				try {
					spellsPath = sc.nextLine();
					spells = loadCardsFromFile(spellsPath);
				} catch (FileNotFoundException | UnexpectedFormatException e2) {
					try {
						spellsPath = sc.nextLine();
						spells = loadCardsFromFile(spellsPath);
					} catch (FileNotFoundException | UnexpectedFormatException e3) {
						e3.printStackTrace();
						sc.close();
						throw e3;
					}
				}
			}
		}
		sc.close();
		this.deck = new ArrayList<Card>();
		buildDeck(monsters, spells);
		shuffleDeck();
	}

	// Loading from csv
	public ArrayList<Card> loadCardsFromFile(String path) throws IOException,
			FileNotFoundException, UnexpectedFormatException {
		ArrayList<Card> res = new ArrayList<Card>();

		String line = "";
		FileReader fr;
		try {
			fr = new FileReader(path);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(path);
		}
		BufferedReader br = new BufferedReader(fr);
		for (int lineNumber = 1; (line = br.readLine()) != null; lineNumber++) {
			String[] c = line.split(",");
			for (int i = 0; i < c.length; i++)
				if (c[i].isEmpty() || c[i].equals(" ")) {
					br.close();
					throw new EmptyFieldException(path, lineNumber, i + 1);
				}
			if (c[0].equals("Monster")) {
				if (c.length != 6) {
					br.close();
					throw new MissingFieldException(path, lineNumber);
				}
				res.add(new MonsterCard(c[1], c[2], Integer.parseInt(c[5]),
						Integer.parseInt(c[3]), Integer.parseInt(c[4])));
			} else if (c[0].equals("Spell")) {
				if (c.length != 3) {
					br.close();
					throw new MissingFieldException(path, lineNumber);
				}
				SpellCard s = null;
				switch (c[1]) {
				case "Card Destruction":
					s = new CardDestruction(c[1], c[2]);
					break;
				case "Change Of Heart":
					s = new ChangeOfHeart(c[1], c[2]);
					break;
				case "Dark Hole":
					s = new DarkHole(c[1], c[2]);
					break;
				case "Graceful Dice":
					s = new GracefulDice(c[1], c[2]);
					break;
				case "Harpie's Feather Duster":
					s = new HarpieFeatherDuster(c[1], c[2]);
					break;
				case "Heavy Storm":
					s = new HeavyStorm(c[1], c[2]);
					break;
				case "Mage Power":
					s = new MagePower(c[1], c[2]);
					break;
				case "Monster Reborn":
					s = new MonsterReborn(c[1], c[2]);
					break;
				case "Pot of Greed":
					s = new PotOfGreed(c[1], c[2]);
					break;
				case "Raigeki":
					s = new Raigeki(c[1], c[2]);
					break;
				default:
					throw new UnknownSpellCardException(path, lineNumber, c[1]);
				}
				res.add(s);
			} else {
				br.close();
				throw new UnknownCardTypeException(path, lineNumber, c[0]);
			}
		}

		br.close();
		return res;
	}

	public Card drawOneCard() {
		return deck.remove(deck.size() - 1);
	}

	public ArrayList<Card> drawNCards(int n) {
		int size = deck.size();

		ArrayList<Card> res = new ArrayList<Card>();
		int j = 0;
		for (int i = size - 1; i >= 0 && j < n; i--, j++)
			res.add((Card) deck.remove(i));

		return res;
	}

	private void shuffleDeck() {
		Collections.shuffle(deck);
	}

	private void buildDeck(ArrayList<Card> monsters, ArrayList<Card> spells) {
		// Monsters
		for (int i = 0; i < 15; i++) {
			int rand = new Random().nextInt(monsters.size());
			MonsterCard tmp;
			try {
				tmp = (MonsterCard) monsters.get(rand).clone();
				deck.add(tmp);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Spells
		for (int i = 0; i < 5; i++) {
			int rand = new Random().nextInt(spells.size());
			SpellCard tmp;
			try {
				tmp = (SpellCard) spells.get(rand).clone();
				deck.add(tmp);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
}
