import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import java.awt.Font;


public class PlaceShip extends JFrame {
	private JButton playerCells[][] = new JButton[10][10];
	private JButton computerCells[][] = new JButton[10][10];
	private ArrayList<Point> points = new ArrayList<Point>();
	private Ship ship;
	private Field playerField = new Field();
	private Field computerField = new Field();
	private boolean isGameNow;
	private String login;
	private DB db;
	private int score;
	JLabel scoreLabel = new JLabel("Score: ");
	Random rand = new Random();
	
	

	PlaceShip(final String login) {
		
		this.login = login;
		setTitle("Game");
		setBounds(100, 100, 1600, 800);
		setResizable(false);
		getContentPane().setLayout(null);
		try {
			db = new DB("jdbc:mysql://localhost/", "authorization", "root",
					"5813");
			db.update("use authorization;");
		} catch (SQLException e1) {
			System.out.println("Error to connect to database");
		}
		score = db.getScore(login);
		Font font = new Font("Game", Font.ROMAN_BASELINE, 11);
		JMenuBar Menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(font);

		JMenu Option = new JMenu("Option");
		Option.setFont(font);

		JMenu newMenu = new JMenu("New");

		newMenu.setFont(font);
		fileMenu.add(newMenu);

		JMenuItem newGame = new JMenuItem("Game");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem clear = new JMenuItem("Сброс всех пользователей");

		exitItem.setFont(font);
		clear.setFont(font);
		fileMenu.add(exitItem);
		Option.add(clear);

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					db.update("drop database authorization;");
					db.update("create database IF NOT EXISTS authorization;");
					db.update("use authorization;");
					JOptionPane.showMessageDialog(null,
							"База данных очищена!\nВыход из игры...");
					System.exit(0);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Pizdec");
					e1.printStackTrace();
				}

			}

		});

		newGame.setFont(font);
		newMenu.add(newGame);

		Menu.add(fileMenu);
		Menu.add(Option);

		setJMenuBar(Menu);

		setPreferredSize(new Dimension(740, 550));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		JButton addShipButt = new JButton("Добавить Корабль");
		addShipButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!playerField.addShip(Ship.Create(points))) {
						JOptionPane.showMessageDialog(null,
								"Нельзя так ставить", "Error", 1);
						cancelSelect(points);
					}
					points = new ArrayList<Point>();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
							"Выберите точку для коробля", "Error", 1);
				}

			}
		});
		addShipButt.setBounds(30, 350, 150, 40);
		getContentPane().add(addShipButt);
		addShipButt.setFont(font);

		JButton Start = new JButton("Start");
		Start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (playerField.isReady()) {
					JOptionPane.showMessageDialog(null,
							"Корабли к бою готовы!", "Ok", 1);
					isGameNow = true;
				}
			}
		});
		Start.setBounds(200, 350, 130, 40);
		getContentPane().add(Start);
		Start.setFont(font);

		JButton compShowButt = new JButton("Расставить случайно");
		compShowButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerField.generateShips();
				showShips(playerField.getArrayShips(), playerCells);
				System.out.println(computerField);
			}
		});
		computerField.generateShips();
		compShowButt.setBounds(30, 410, 150, 40);
		getContentPane().add(compShowButt);
		compShowButt.setFont(font);

		JLabel compNameLabel = new JLabel("Computer");
		compNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		compNameLabel.setBounds(400, 320, 130, 50);
		getContentPane().add(compNameLabel);

		JLabel userNameLabel = new JLabel("User");
		userNameLabel.setText(login);
		userNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		userNameLabel.setBounds(30, 320, 200, 50);
		getContentPane().add(userNameLabel);

		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlaceShip pole = new PlaceShip(login);
				dispose();
			}
		});

		scoreLabel.setText("Score: " + score);
		scoreLabel.setForeground(Color.BLACK);
		scoreLabel.setFont(font);

		scoreLabel.setBounds(200, 400, 200, 50);
		getContentPane().add(scoreLabel);

		int x = 30;
		int y = 30;
		for (int i = 0; i < playerCells.length; i++) {
			for (int j = 0; j < playerCells[i].length; j++) {
				playerCells[i][j] = new JButton();
				playerCells[i][j].setBounds(x, y, 30, 30);
				playerCells[i][j].setBackground(Color.WHITE);
				x = x + 30;
				getContentPane().add(playerCells[i][j]);
				playerCells[i][j].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < playerCells.length; i++) {
							for (int j = 0; j < playerCells[i].length; j++) {
								if (e.getSource() == playerCells[i][j]) {
									if (playerCells[i][j].getBackground() == Color.WHITE
											&& !isGameNow) {
										playerCells[i][j]
												.setBackground(Color.green);
										points.add(new Point(j, i));
									} else {
										playerCells[i][j]
												.setBackground(Color.WHITE);
										for (Point p : points) {
											if (p.getX() == j && p.getY() == i) {
												points.remove(p);
												break;
											}
										}
									}
								}
							}
						}
					}
				});
			}
			x = 30;
			y = y + 30;
		}

		x = 400;
		y = 30;
		for (int i = 0; i < computerCells.length; i++) {
			for (int j = 0; j < computerCells[i].length; j++) {
				computerCells[i][j] = new JButton();
				computerCells[i][j].setBounds(x, y, 30, 30);
				computerCells[i][j].setBackground(Color.WHITE);
				x = x + 30;
				getContentPane().add(computerCells[i][j]);
				computerCells[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < computerCells.length; i++) {
							for (int j = 0; j < computerCells[i].length; j++) {
								if (e.getSource() == computerCells[i][j]
										&& isGameNow
										&& computerCells[i][j].getBackground() == Color.WHITE) {
									ArrayList<Point> answer = computerField
											.Attack(j, i);
									drawAnswer(answer, computerCells);
									if (answer.get(0).getType() == 3)
										computerThink2();
									if (computerField.isAllShipDead()) {
										JOptionPane.showMessageDialog(null,
												"Победа!", "Win", 1);
										isGameNow = false;
										score++;
										db.setScore(login, score);
										scoreLabel.setText("Score: " + score);
									}
								}
							}
						}
					}
				});
			}
			x = 400;
			y = y + 30;
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void cancelSelect(ArrayList<Point> points) {
		for (Point p : points) {
			playerCells[p.getY()][p.getX()].setBackground(Color.WHITE);
		}
	}

	public void showShips(ArrayList<Ship> shipList, JButton[][] buttons) {
		for (Ship ship : shipList) {
			for (Point p : ship.getPoints()) {
				buttons[p.getY()][p.getX()].setBackground(Color.green);
			}
		}
	}

	public void drawAnswer(ArrayList<Point> points, JButton[][] buttons) {
		for (Point p : points) {
			if (p.getType() == 4) {
				buttons[p.getY()][p.getX()].setBackground(Color.RED);

			}
			if (p.getType() == 2 || p.getType() == 3) {

				buttons[p.getY()][p.getX()].setBackground(Color.CYAN);
			}
		}
	}

	public void computerThink() {
		ArrayList<Point> answer;
		do {
			int x = rand.nextInt(10);
			int y = rand.nextInt(10);
			answer = new ArrayList<Point>();
			answer = playerField.Attack(x, y);
			if (answer.get(0).getType() != -1) {
				drawAnswer(answer, playerCells);
			}
		} while (answer.get(0).getType() == -1 || answer.get(0).getType() == 4);
	}

	private Point MemPoint = null;
	private Point direction;
	private int counter = 0;
	private Point pToAttack;
	ArrayList<Point> answer;
	private boolean isBarrier = false;

	public void computerThink2() {
		do {
			answer = new ArrayList<Point>();
			if (MemPoint == null) {
				int x = rand.nextInt(10);
				int y = rand.nextInt(10);
				pToAttack = new Point(x, y);
				MemPoint = null;
			}
			if (MemPoint != null) {
				if (counter < 2) {
					direction = genDirectionPt(MemPoint);
					pToAttack = MemPoint.plus(direction);
				}
				if (counter >= 2) {
					pToAttack = MemPoint.plus(direction);
				}
			}
			answer = playerField.Attack(pToAttack.getX(), pToAttack.getY());
			if (answer.get(0).getType() == 4 && answer.size() == 1) {
				MemPoint = answer.get(0);
				counter++;
			}
			if ((answer.get(0).getType() == 3 || answer.get(0).getType() == -1)
					&& answer.size() == 1 && counter >= 2) {

				direction = direction.invers();
				for (int i = 0; i < counter - 1; i++)
					MemPoint = MemPoint.plus(direction);
			}

			if (answer.get(0).getType() == 4 && answer.size() > 1) {
				MemPoint = null;
				counter = 0;
				isBarrier = false;
				System.out.println(answer);
			}

			if (answer.get(0).getType() != -1) {
				drawAnswer(answer, playerCells);
			}
		} while (answer.get(0).getType() == -1 || answer.get(0).getType() == 4);
		if (playerField.isAllShipDead()) {
			JOptionPane.showMessageDialog(null, "Вы проиграли", "Фу", 1);
			isGameNow = false;
			score--;
			db.setScore(login, score);
			scoreLabel.setText("Score: " + score);
		}
	}

	public Point genDirectionPt(Point pt) {
		Point[] directions = { new Point(-1, 0), new Point(1, 0),
				new Point(0, -1), new Point(0, 1) };
		Random rand = new Random();
		Point direction;
		Point p;
		do {
			direction = directions[rand.nextInt(4)];
			p = pt.plus(direction);
		} while (p.getX() < 0 || p.getX() > 9 || p.getY() < 0 || p.getY() > 9);
		return direction;
	}
}