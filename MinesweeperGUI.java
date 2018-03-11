
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nk331
 */
public class MinesweeperGUI extends JFrame {

    private Minefield minefield;
    private JPanel gridPanel, extrasPanel;
    private JLabel flagsCounterLabel, timerLabel;
    private int flags;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem beginnerItem, intermediateItem, expertItem, customItem;
    private JButton[][] gridOfButtons;
    private JFrame minesweeper;
    private Timer timer;
    private int time;
    private Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 10);

    private ImageIcon flagimg = new ImageIcon(getClass().getClassLoader().getResource("images/flag.png"));
    private ImageIcon flag = new ImageIcon(flagimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon tilenotrevimg = new ImageIcon(getClass().getClassLoader().getResource("images/tilenotrev.jpg"));
    private ImageIcon tilenotrev = new ImageIcon(tilenotrevimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon zeroimg = new ImageIcon(getClass().getClassLoader().getResource("images/emptytile.png"));
    private ImageIcon zero = new ImageIcon(zeroimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon oneimg = new ImageIcon(getClass().getClassLoader().getResource("images/one.png"));
    private ImageIcon one = new ImageIcon(oneimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon twoimg = new ImageIcon(getClass().getClassLoader().getResource("images/two.png"));
    private ImageIcon two = new ImageIcon(twoimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon threeimg = new ImageIcon(getClass().getClassLoader().getResource("images/three.png"));
    private ImageIcon three = new ImageIcon(threeimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon fourimg = new ImageIcon(getClass().getClassLoader().getResource("images/four.png"));
    private ImageIcon four = new ImageIcon(fourimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon fiveimg = new ImageIcon(getClass().getClassLoader().getResource("images/five.png"));
    private ImageIcon five = new ImageIcon(fiveimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon siximg = new ImageIcon(getClass().getClassLoader().getResource("images/six.png"));
    private ImageIcon six = new ImageIcon(siximg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon sevenimg = new ImageIcon(getClass().getClassLoader().getResource("images/seven.png"));
    private ImageIcon seven = new ImageIcon(sevenimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon redbombimg = new ImageIcon(getClass().getClassLoader().getResource("images/redbomb.jpg"));
    private ImageIcon redbomb = new ImageIcon(redbombimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon bombimg = new ImageIcon(getClass().getClassLoader().getResource("images/bomb.jpg"));
    private ImageIcon bomb = new ImageIcon(bombimg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon sadimg = new ImageIcon(getClass().getClassLoader().getResource("images/sad.png"));
    private ImageIcon sad = new ImageIcon(sadimg.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    private ImageIcon happyimg = new ImageIcon(getClass().getClassLoader().getResource("images/happy.png"));
    private ImageIcon happy = new ImageIcon(happyimg.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

    //boolean to control the buttons' mouselisteners
    private boolean enabled;

    /**
     * Constructor for MinesweeperGUI
     */
    public MinesweeperGUI() {
        super("Minesweeper");
        minefield = new Minefield(9, 9, 10);
        minefield.populate();
        createWindow();
    }

    public static void main(String[] args) {
        MinesweeperGUI game = new MinesweeperGUI();
    }

    /**
     * Creates a new game
     *
     * @param row
     * @param col
     * @param mines
     */
    public void newgame(int row, int col, int mines) {

        minefield = new Minefield(row, col, mines);
        minefield.populate();
        createWindow();
    }

    /**
     * Create window containing the whole graphical game
     */
    private void createWindow() {
        enabled = true;

        minesweeper = new JFrame("Minesweeper");
        minesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set timer
        time = 000;
        DecimalFormat format = new DecimalFormat("000");
        timerLabel = new JLabel();
        timerLabel.setFont(new Font("font", Font.BOLD, 15));
        timerLabel.setBorder(emptyBorder);
        timer = new Timer(1000, e -> {
            if (e.getSource() == timer) {
                time++;

            }
            timerLabel.setText(format.format(time));

        });
        timer.restart();

        //set the flags counter
        flags = minefield.getMines();
        flagsCounterLabel = new JLabel("Flags:" + flags);
        flagsCounterLabel.setOpaque(true);
        flagsCounterLabel.setBorder(emptyBorder);
        flagsCounterLabel.setFont(new Font("font", Font.BOLD, 15));

        //set the extras panel
        extrasPanel = new JPanel(new BorderLayout());
        extrasPanel.add(flagsCounterLabel, BorderLayout.WEST);
        extrasPanel.add(timerLabel, BorderLayout.EAST);
        extrasPanel.setSize(new Dimension(35 * minefield.getColumns(), 60));
        extrasPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 5, 10, Color.lightGray));

        //set the grid panel
        gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 10, Color.lightGray));
        gridPanel.setSize(30 * minefield.getColumns(), 30 * minefield.getRows());
        gridOfButtons = new JButton[minefield.getRows()][minefield.getColumns()];
        for (int i = 0; i < minefield.getRows(); i++) {
            for (int j = 0; j < minefield.getColumns(); j++) {
                JButton button = new JButton();
                button.setIcon(tilenotrev);
                button.addMouseListener(new ButtonListener());
                gridOfButtons[i][j] = button;
                button.setPreferredSize(new Dimension(30, 30));
                gridPanel.add(button);
                gridPanel.setLayout(new GridLayout(minefield.getRows(), minefield.getColumns()));
            }
        }

        //set the options menu        
        beginnerItem = new JMenuItem("Beginner");
        beginnerItem.addActionListener((ActionEvent e) -> {
            minesweeper.dispose();
            newgame(9, 9, 10);
        });

        intermediateItem = new JMenuItem("Intermediate");
        intermediateItem.addActionListener((ActionEvent e) -> {
            minesweeper.dispose();
            newgame(16, 16, 50);
        });
        expertItem = new JMenuItem("Expert");
        expertItem.addActionListener((ActionEvent e) -> {
            minesweeper.dispose();
            newgame(16, 30, 99);
        });
        customItem = new JMenuItem("Custom...");
        customItem.addActionListener((ActionEvent e) -> {
            customGameDialog();
        });
        optionsMenu = new JMenu("New Game");
        optionsMenu.add(beginnerItem);
        optionsMenu.add(intermediateItem);
        optionsMenu.add(expertItem);
        optionsMenu.add(customItem);
        menuBar = new JMenuBar();
        menuBar.add(optionsMenu);
        minesweeper.setJMenuBar(menuBar);

        // set frame
        minesweeper.getContentPane().setLayout(new BorderLayout());
        minesweeper.getContentPane().add(extrasPanel, BorderLayout.NORTH);
        minesweeper.getContentPane().add(gridPanel, BorderLayout.CENTER);
        minesweeper.pack();
        minesweeper.setResizable(false);
        minesweeper.setLocationRelativeTo(null);
        minesweeper.setVisible(true);
    }

    /**
     * Listener for the buttons
     */
    public class ButtonListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!enabled) {
                return;
            } else {
                //find the button that was clicked    
                for (int i = 0; i < minefield.getRows(); i++) {
                    for (int j = 0; j < minefield.getColumns(); j++) {

                        if (e.getSource().equals(gridOfButtons[i][j])) {
                            //if right-click then mark a tile
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                minefield.markTile(i, j);
                                if (gridOfButtons[i][j].getIcon().equals(tilenotrev)) {
                                    gridOfButtons[i][j].setIcon(flag);
                                    //maintain the flagsCounter
                                    if (flags > 0) {
                                        flags = flags - 1;
                                    }
                                    flagsCounterLabel.setText("Flags: " + flags);
                                } else {
                                    if (gridOfButtons[i][j].getIcon().equals(flag)) {
                                    gridOfButtons[i][j].setIcon(tilenotrev);
                                }
                                }
                                //if game is won, show dialog
                                if (checkWin()) {
                                    gameWon();
                                }
                                //if left-click step on a tile
                            } else {
                                minefield.step(i, j);
                                stepDisplay(i, j);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        /**
         * Step on a tile, revealing number of neighbours or a bomb
         *
         * @param row
         * @param column
         * @return true if stepped on an unmined tile, false if stepped on a
         * bomb
         */
        public boolean stepDisplay(int row, int column) {

            if (!minefield.step(row, column)) {
                gridOfButtons[row][column].setIcon(redbomb);

                for (int i = 0; i < minefield.getRows(); i++) {
                    for (int j = 0; j < minefield.getColumns(); j++) {
                        if (minefield.getMineTile(i, j).isMined() && !gridOfButtons[i][j].getIcon().equals(redbomb)) {
                            gridOfButtons[i][j].setIcon(bomb);
                        }
                    }
                }
                //stepped on a bomb, so show lost game dialog
                gameLost();
                return false;

            } else {
                switch (minefield.getMineTile(row, column).getMinedNeighbours()) {
                    case 0:
                        gridOfButtons[row][column].setIcon(zero);
                        break;
                    case 1:
                        gridOfButtons[row][column].setIcon(one);
                        break;
                    case 2:
                        gridOfButtons[row][column].setIcon(two);
                        break;
                    case 3:
                        gridOfButtons[row][column].setIcon(three);
                        break;
                    case 4:
                        gridOfButtons[row][column].setIcon(four);
                        break;
                    case 5:
                        gridOfButtons[row][column].setIcon(five);
                        break;
                    case 6:
                        gridOfButtons[row][column].setIcon(six);
                        break;
                    case 7:
                        gridOfButtons[row][column].setIcon(seven);
                        break;
                }
                if (gridOfButtons[row][column].getIcon().equals(zero)) {
                    for (int i = row - 1; i <= row + 1; i++) {
                        for (int j = column - 1; j <= column + 1; j++) {
                            if (i >= 0 && i < gridOfButtons.length && j >= 0 && j < gridOfButtons[i].length) {
                                if (gridOfButtons[i][j].getIcon().equals(tilenotrev)) {
                                    stepDisplay(i, j);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
    }

    /**
     * Shows a dialog box if game is lost
     *
     */
    public void gameLost() {
        //disable the buttons on minefield
        enabled = false;

        String[] options = {"Start new game", "Quit"};

        int optionDialog = JOptionPane.showOptionDialog(minesweeper,
                "You lost!",
                "BOOM",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                sad, options, null);

        if (optionDialog == 0) {
            minesweeper.dispose();
            newgame(9, 9, 10);
        } else if (optionDialog == 1) {

            System.exit(0);
        }
    }

    /**
     * Checks if game is won
     *
     * @return true if game won, false otherwise
     */
    public boolean checkWin() {

        for (int i = 0; i < minefield.getRows(); i++) {
            for (int j = 0; j < minefield.getColumns(); j++) {
                if ((gridOfButtons[i][j].getIcon().equals(flag) && !minefield.getMineTile(i, j).isMined())
                        || (!gridOfButtons[i][j].getIcon().equals(flag) && minefield.getMineTile(i, j).isMined())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Shows a dialog box if game won
     */
    public void gameWon() {
        //disable the buttons on minefield
        enabled = false;

        String[] options = {"Start new game", "Quit"};

        int optionDialog = JOptionPane.showOptionDialog(minesweeper,
                "You won!",
                "YAY",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                happy, options, null);

        if (optionDialog == 0) {
            minesweeper.dispose();
            newgame(9, 9, 10);
        } else if (optionDialog == 1) {
            System.exit(0);
        }
    }

    /**
     * Shows a dialog box to customise the game
     */
    public void customGameDialog() {
        //disable the buttons on minefield
        enabled = false;

        //arrange custom dialog panel with text fields
        JPanel customDialogPanel = new JPanel(new GridLayout(3, 3));
        JTextField rowsField = new JTextField();
        JTextField columnsField = new JTextField();
        JTextField minesField = new JTextField();
        JLabel rowsLabel = new JLabel("Rows (4 - 30):");
        JLabel columnsLabel = new JLabel("Columns (4 - 80):");
        JLabel minesLabel = new JLabel("Mines:");
        customDialogPanel.add(rowsLabel);
        customDialogPanel.add(rowsField);
        customDialogPanel.add(columnsLabel);
        customDialogPanel.add(columnsField);
        customDialogPanel.add(minesLabel);
        customDialogPanel.add(minesField);

        // show dialog
        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(minesweeper, customDialogPanel, "Customise your game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
            String row = rowsField.getText();
            String col = columnsField.getText();
            String mine = minesField.getText();
            
            if (row.length() > 0 && col.length() > 0 && mine.length() > 0) {
                int rows = Integer.parseInt(row);
                int columns = Integer.parseInt(col);
                int mines = Integer.parseInt(mine);
                
                //check for if text fields not empty and for bounds
                if (rows > 3 && rows < 31 && columns > 3 && columns < 81 && mines > 0 && mines < rows * columns) {
                    minesweeper.dispose();
                    newgame(rows, columns, mines);
                } else {
                    customGameDialog();
                }

                // if out of bounds, show the dialog box again
            } else {
                customGameDialog();
            }
        }

    }

}
