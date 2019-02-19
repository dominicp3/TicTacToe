import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TicTacToe extends JFrame {

//	square size = 90
//	leftX = 115
//  rightX = 385
//	AI is O
	
	private static final long serialVersionUID = 1L;
	JPanel panel;
	
	int top = 0;
	
	boolean cpu = false;
	char first = 'X';

	JLabel[] numbers;
	JLabel[] grid;

	JLabel xWon;
	JLabel oWon;

	JLabel draw;
	
	JCheckBox box;
	JCheckBox box2;

	JLabel xPlayer;
	JLabel oPlayer;
	JLabel oIsAi;
	
	int coordinate = 1;

	boolean isGameOver = false;

	char player = first;

	char[] board;
	
	char[] tempBoard;

	public TicTacToe() {

		this.setTitle("Tic Tac Toe");
		this.setSize(new Dimension(500, 500));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		panel = new DrawLine();

		panel.addKeyListener(new KeyStroke());

		panel.setFocusable(true);
		panel.requestFocusInWindow();

		this.getContentPane().add(panel);

		numbers = new JLabel[9];
		grid = new JLabel[9];
		board = new char[9];

		xWon = new JLabel("X has won!");
		xWon.setBounds(201, 50, 103, 16);
		xWon.setFont(new Font("", Font.PLAIN, 20));
		xWon.setVisible(false);
		panel.add(xWon);

		oWon = new JLabel("O has won!");
		oWon.setBounds(200, 50, 105, 16);
		oWon.setFont(new Font("", Font.PLAIN, 20));
		oWon.setVisible(false);
		panel.add(oWon);

		xPlayer = new JLabel("X's turn");
		xPlayer.setBounds(217, 420, 70, 16);
		xPlayer.setFont(new Font("", Font.PLAIN, 20));
		
		if (first == 'O') {
			xPlayer.setVisible(false);
		}
		
		
		panel.add(xPlayer);

		oPlayer = new JLabel("O's turn");
		oPlayer.setBounds(214, 418, 73, 20);
		oPlayer.setFont(new Font("", Font.PLAIN, 20));
		
		if (first == 'X') {
			oPlayer.setVisible(false);
		}
		
		
		panel.add(oPlayer);

		draw = new JLabel("Draw!");
		draw.setBounds(224, 418, 53, 20);
		draw.setFont(new Font("", Font.PLAIN, 20));
		draw.setVisible(false);
		panel.add(draw);

		for (int i = 0; i < 9; i++) {
			numbers[i] = new JLabel(Integer.toString(i + 1));
			numbers[i].setFont(new Font("", Font.PLAIN, 12));
			panel.add(numbers[i]);

			grid[i] = new JLabel("");
			grid[i].setFont(new Font("", Font.BOLD, 22));
			grid[i].setVisible(false);
			panel.add(grid[i]);
		}

		int k = 0;

		for (int i = 2; i >= 0; i--) {
			for (int j = 0; j < 3; j++) {
				numbers[k].setBounds(152 + (90 * j), 152 + (90 * i), 17, 17);
				grid[k].setBounds(152 + (90 * j), 152 + (90 * i), 17, 17);
				k++;
			}
		}
		
		
		box = new JCheckBox("Play computer?");
		box.setBounds(10, 10, 120, 20);
		box.addKeyListener(new KeyStroke());
		box.setActionCommand("cpu");
		box.addActionListener(new BoxListener());
		panel.add(box);
		
		box2 = new JCheckBox("O goes first?");
		box2.setBounds(10, 40, 120, 20);
		box2.addKeyListener(new KeyStroke());
		box2.setActionCommand("first");
		box2.addActionListener(new BoxListener());
		panel.add(box2);
		
		oIsAi = new JLabel("O is AI");
		oIsAi.setBounds(420, 10, 120, 20);
		panel.add(oIsAi);
		
		
		if (cpu && first == 'O') {
			computer();
		}	
		
	}

	public boolean hasWon(char[] board) {

		for (int i = 0; i < 7; i += 3) {
			if (board[i] == board[i + 1] && board[i] != 0) {
				if (board[i + 1] == board[i + 2]) {
					return true;
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			if (board[i] == board[i + 3] && board[i] != 0) {
				if (board[i + 3] == board[i + 6]) {
					return true;
				}
			}
		}

		if (board[0] == board[4] && board[0] != 0) {
			if (board[4] == board[8]) {
				return true;
			}
		}

		if (board[2] == board[4] && board[2] != 0) {
			if (board[4] == board[6]) {
				return true;
			}
		}

		return false;
	}

	public boolean isDraw(char[] board) {
		for (char c : board) {
			if (c == 0) {
				return false;
			}
		}
		return true;
	}

	public void reset() {
		for (int i = 0; i < 9; i++) {
			grid[i].setText("");
			grid[i].setVisible(false);
			board[i] = 0;
			numbers[i].setVisible(true);
		}
		
		xWon.setVisible(false);
		oWon.setVisible(false);

		isGameOver = false;
		
		player = first;
		
		if (cpu) {
			computer();
		}
		
		
		if (first == 'O') {
			xPlayer.setVisible(false);
			oPlayer.setVisible(true);
		} else if (first == 'X') {
			xPlayer.setVisible(true);
			oPlayer.setVisible(false);
		}
		
		draw.setVisible(false);

		this.repaint();
	}

	public int immediateWin(char[] b, char ai) {
		
		tempBoard = new char[9];
		
		for (int i = 0; i < 9; i++) {	
			
			for (int j = 0; j < 9; j++) {
				tempBoard[j] = b[j];
			}	
			
			if (tempBoard[i] == 0) {
				tempBoard[i] = ai;
				if (hasWon(tempBoard)) {
					return i;
				}
			}	
			
		}
		
		return -1;		
	}

	public int minimax(char[] board, char player) {
		
		char[] temp = new char[9];
		
		int[] points = new int[9];
		
		if (player == 'X') {
			for (int i = 0; i < 9; i++) {
				points[i] = Integer.MAX_VALUE;
			}
		} else if (player == 'O') {
			for (int i = 0; i < 9; i++) {
				points[i] = Integer.MIN_VALUE;
			}
		}
		
		for (int i = 0; i < 9; i++) {
			temp[i] = board[i];
		}
		
		
		int x = immediateWin(board, player);
		
		if (x != -1) {
			if (player == 'O') {
				return 10;
			} else if (player == 'X') {
				return -10;
			}
		}
		
		
		for (int i = 0; i < 9; i++) {
			
			for (int j = 0; j < 9; j++) {
				temp[j] = board[j];
			}
			
			if (temp[i] == 0) {
				temp[i] = player;
				
				if (isDraw(temp)) {
					coordinate = i;
					return 0;
				}
				
				if (player == 'X') {
					points[i] = minimax(temp, 'O');
				} else if (player == 'O') {
					points[i] = minimax(temp, 'X');
				}
				
			}
		}
		
		if (player == 'O') {
			coordinate = highestValueIndex(points);
			return points[coordinate];
		} else {
			coordinate = lowestValueIndex(points);
			return points[coordinate];
		}
		
	}

	public void computer() {
		
		int x = immediateWin(board, 'O');
		
		if (player == 'O') {
			if (x != -1) {
				updateBoard(x);
			} else {
				minimax(board, 'O');	
				updateBoard(coordinate);	
			}	
		}	
					
	}
	
	public void updateBoard(int num) {
		
		numbers[num].setVisible(false);
		
		if (grid[num].getText().equals("")) {
			grid[num].setText(Character.toString(player));
			grid[num].setVisible(true);
			board[num] = player;
			
			if (player == 'O') {
				oPlayer.setVisible(false);
				xPlayer.setVisible(true);
				player = 'X';
			} else {
				oPlayer.setVisible(true);
				xPlayer.setVisible(false);
				player = 'O';				
			}
			
		}

		this.repaint();
	}

	public int highestValueIndex(int[] nums) {
		
		Random rand = new Random();
		
		int result = Integer.MIN_VALUE;
		
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		
		int size = 0;
		
		for (int e : nums) {
			if (e >= result) {
				result = e;
			}
		}
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == result) {
				indicies.add(i);
				size++;
			}
		}
		
		return indicies.get(rand.nextInt(size));
	}
	
	public int lowestValueIndex(int[] nums) {
		Random rand = new Random();
		int result = Integer.MAX_VALUE;
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		int size = 0;
		for (int e : nums) {
			if (e <= result) {
				result = e;
			}
		}
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == result) {
				indicies.add(i);
				size++;
			}
		}
		return indicies.get(rand.nextInt(size));
	}
	
	public static void main(String[] args) {
		TicTacToe t = new TicTacToe();
		t.setVisible(true);
	}

	private class KeyStroke implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

			char command = e.getKeyChar();

			if (!isGameOver) {

				switch (command) {
				case '1': updateBoard(0);
						break;
				case '2': updateBoard(1);
						break;
				case '3': updateBoard(2);
						break;
				case '4': updateBoard(3);
						break;
				case '5': updateBoard(4);
						break;
				case '6': updateBoard(5);
						break;
				case '7': updateBoard(6);
						break;
				case '8': updateBoard(7);
						break;
				case '9': updateBoard(8);
						break;
				}
				
				if (cpu) {
					computer();
				}				
				

				if (hasWon(board)) {

					if (player == 'O') {
						xWon.setVisible(true);
					} else {
						oWon.setVisible(true);
					}

					xPlayer.setVisible(false);
					oPlayer.setVisible(false);
					isGameOver = true;

				} else if (isDraw(board)){
					xPlayer.setVisible(false);
					oPlayer.setVisible(false);
					draw.setVisible(true);
					isGameOver = true;
				}

			}

			if (command == 'r') {
				reset();
			}


		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}

	}

	private class BoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String command = e.getActionCommand();
			
			if (command.equals("cpu")) {
				
				if (box.isSelected()) {
					cpu = true;
				} else {
					cpu = false;
				}				
				
				reset();
			} else if (command.equals("first")) {
				if (box2.isSelected()) {
					first = 'O';
				} else {
					first = 'X';
				}
				
				reset();
			}
			
			
		}
		
	}
	
}
