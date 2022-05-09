import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TTT extends JPanel {
	private final int NUM_BUTTONS = 9;
	private JButton[] buttons = new JButton[NUM_BUTTONS];
	private JLabel label;
	private JPanel buttonPanel;
	private boolean playerTurn;
	private JButton resetButton, exitButton;
	private JPanel optionPanel;
	private int dimensions = 500;
	private int counter = 0;
	
	public TTT() {
		setUp();
		turnStart();
	}
	
	private void turnStart() {
		int start = (int) Math.random() * 2;
		
		if(start == 0) {
			playerTurn = true;
		}
		else {
			playerTurn = false;
		}
		
		if(playerTurn) {
			label.setText("Player X's turn");
		}
		else {
			label.setText("Player O's turn");
		}
	}
	
	private void setUp() {
		label = new JLabel();
		resetButton = new JButton();
		exitButton = new JButton();
		buttonPanel = new JPanel();
		optionPanel = new JPanel();
		
		
		optionPanel.add(resetButton);
		optionPanel.add(exitButton);
		optionPanel.setBackground(Color.black);
		
		exitButton.setText("Exit");
		exitButton.addActionListener(new ExitListener());
		
		resetButton.setText("Reset");
		resetButton.addActionListener(new ResetListener());
		
		label.setFont(new Font("Arial", Font.BOLD, 25));
		label.setForeground(Color.green);
		label.setBackground(Color.white);
		
		buttonPanel.setLayout(new GridLayout(3,3));
		
		makeButtons();
		add(label);
		add(optionPanel);
		add(buttonPanel);
		setBackground(Color.black);
		setFocusable(true);
		setPreferredSize(new Dimension(dimensions, dimensions));
	}
	
	private void makeButtons() {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].addActionListener(new ButtonListener());
			buttons[i].setFont(new Font("Arial", Font.BOLD, 50));
			buttons[i].setPreferredSize(new Dimension(100, 100));
			buttons[i].setFocusable(false);
			buttonPanel.add(buttons[i]);
		}
	}
	
	private void reset() {
		for(int i = 0; i < NUM_BUTTONS; i++) {
			buttons[i].setEnabled(true);
		}
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		
		counter = 0;
		turnStart();
	}
	
	private void turn() {
		playerTurn = !playerTurn;
		
		if(playerTurn) {
			label.setText("Player X's turn");
		}
		else {
			label.setText("Player O's turn");
		}

	}
	
	//Check rows for win condition
	private boolean rowWin() {
		//X wins	
		if((buttons[0].getText().equals("X") && buttons[1].getText().equals("X") && buttons[2].getText().equals("X")) ||
		   (buttons[3].getText().equals("X") && buttons[4].getText().equals("X") && buttons[5].getText().equals("X")) ||
		   (buttons[6].getText().equals("X") && buttons[7].getText().equals("X") && buttons[8].getText().equals("X"))) {
			return true;
		}
			
		//O wins
		if((buttons[0].getText().equals("O") && buttons[1].getText().equals("O") && buttons[2].getText().equals("O")) ||
		   (buttons[3].getText().equals("O") && buttons[4].getText().equals("O") && buttons[5].getText().equals("O")) ||
		   (buttons[6].getText().equals("O") && buttons[7].getText().equals("O") && buttons[8].getText().equals("O"))) {
			return true;
		}
		
		return false;	
	}
	
	//Check columns for win condition
	private boolean columnWin() {
		//X wins
		if((buttons[0].getText().equals("X") && buttons[3].getText().equals("X") && buttons[6].getText().equals("X")) ||
		   (buttons[1].getText().equals("X") && buttons[4].getText().equals("X") && buttons[7].getText().equals("X")) ||
		   (buttons[2].getText().equals("X") && buttons[5].getText().equals("X") && buttons[8].getText().equals("X"))) {
			return true;
		}
		
		//O wins
		if((buttons[0].getText().equals("O") && buttons[3].getText().equals("O") && buttons[6].getText().equals("O")) ||
		   (buttons[1].getText().equals("O") && buttons[4].getText().equals("O") && buttons[7].getText().equals("O")) ||
		   (buttons[2].getText().equals("O") && buttons[5].getText().equals("O") && buttons[8].getText().equals("O"))) {
			return true;
		}
		
		return false;	
	}
	
	//Check diagonals for win condition
	private boolean diagonalWin() {
		//X wins
		if((buttons[0].getText().equals("X") && buttons[4].getText().equals("X") && buttons[8].getText().equals("X")) ||
		   (buttons[2].getText().equals("X") && buttons[4].getText().equals("X") && buttons[6].getText().equals("X"))) {
			return true;
		}
		   

		//O wins
		if((buttons[0].getText().equals("O") && buttons[4].getText().equals("O") && buttons[8].getText().equals("O")) ||
		   (buttons[2].getText().equals("O") && buttons[4].getText().equals("O") && buttons[6].getText().equals("O"))) {
			return true;
		}
		
		return false;
	}
	
	private void checkWinner() {
		
		if(rowWin() || columnWin() || diagonalWin()) {
			if(playerTurn) {
				JOptionPane.showMessageDialog(null, "X wins");
			}
			else {
				JOptionPane.showMessageDialog(null, "O wins");
			}
	
			
			int option = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
			
			if(option == JOptionPane.YES_OPTION) {
				reset();
			}
			else {
				for(int i = 0; i < NUM_BUTTONS; i++) {
					buttons[i].setEnabled(false);
				}
			}
		}
	}
	
	private void checkTie() {
		if(counter >= 9) {
			JOptionPane.showMessageDialog(null, "This game is a tie");
			int option = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
			
			if(option == JOptionPane.YES_OPTION) {
				reset();
			}
			else {
				for(int i = 0; i < NUM_BUTTONS; i++) {
					buttons[i].setEnabled(false);
				}
			}
		}
	}
		
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < buttons.length; i++) {
				if(e.getSource() == buttons[i]) {
					if(buttons[i].getText().equals("X") || buttons[i].getText().equals("O")) {
						JOptionPane.showMessageDialog(null, "Try another space");
					}
					else {
						if(playerTurn) {
							buttons[i].setText("X");
						} 
						else {
							buttons[i].setText("O");
						}
						checkWinner();
						counter++;
						turn();
					}
				}
			}
			checkTie();
		}
	}
	
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			reset();
		}
	}
	
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}
}
