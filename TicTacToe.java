import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {
    public TicTacToe() {
        JFrame frame = new JFrame();
        Game game = new Game();

        frame.add(game);
        frame.setTitle("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private class Game extends JPanel {
        private final String X = "X";
        private final String O = "O";
        private JLabel label;
        private String[] board = {"", "", "", "", "", "", "", "", ""};
        private Refree refree = new Refree(board);

        public String turn = X;

        public Game() {
            super();
            setLayout( new GridLayout(4, 1) );
            setPreferredSize( new Dimension(300, 400) );
            

            label = new JLabel("It's X turn!", SwingConstants.CENTER);
            add(label);
            
            JPanel row1 = new JPanel();
            row1.setLayout( new GridLayout(1, 3) );
            row1.add( new Element(this, 0) );
            row1.add( new Element(this, 1) );
            row1.add( new Element(this, 2) );
            add(row1);

            JPanel row2 = new JPanel();
            row2.setLayout( new GridLayout(1, 3) );
            row2.add( new Element(this, 3) );
            row2.add( new Element(this, 4) );
            row2.add( new Element(this, 5) );
            add(row2);

            JPanel row3 = new JPanel();
            row3.setLayout( new GridLayout(1, 3) );
            row3.add( new Element(this, 6) );
            row3.add( new Element(this, 7) );
            row3.add( new Element(this, 8) );
            add(row3);
        }

        public void play(int pos) {
            board[pos] = turn;
            if (turn == X) turn = O;
            else turn = X;

            if (refree.gameWon(X)) {
                label.setText(X + " won!");
                turn = "";
            } else if (refree.gameWon(O)) {
                label.setText(O + " won!");
                turn = "";
            } else if (refree.turnsExpired()) {
                label.setText("It's a draw!");
                turn = "";
            } 
            else label.setText("It's " + turn + " turn!");
        }
    }

    private class Refree {
        private String[] board;

        public Refree(String[] board) {
            this.board = board;
        }

        public boolean gameWon(String character) {
            // horizontals
            for (int i = 0; i < 3; i++)
                if (board[i + 0] == board[i + 1] && board[i + 1] == board[i + 2] && board[i] == character) 
                    return true;

            // verticals 
            for (int i = 0; i < 3; i++)
            if (board[i] == board[i + 3] && board[i + 3] == board[i + 6] && board[i] == character)
                return true;
            
            // diagonals
            if ((
                (board[0] == board[4] && board[4] == board[8])
                || (board[2] == board[4] && board[4] == board[6])
                ) && board[4] == character
            ) return true;

            return false;
        }

        public boolean turnsExpired() {
            for (int i = 0; i < board.length; i++) {
                if (board[i] == "") return false;
            }
            return true;
        }
    }

    private class Element extends JButton implements ActionListener {
        private Game game;
        private int pos;

        public Element(Game game, int pos) {
            super("");
            this.game = game;
            this.pos = pos;
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (game.turn != "") {
                setText(game.turn);
                game.play(pos);
            }
        }
    }

    public static void main( String[] args ) {
        new TicTacToe();
    }
}