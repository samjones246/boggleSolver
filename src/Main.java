import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class Main extends JFrame{
    LettersPanel lettersPanel;
    JButton solveButton;
    JButton resetButton;
    Solver solver;
    Main(String title){
        super(title);
        initialise();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
    public void initialise(){
        Color highlightColor = new Color(0x881CBA);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        lettersPanel = new LettersPanel(4);
        mainPanel.add(lettersPanel, BorderLayout.CENTER);
        solveButton = new JButton();
        solveButton.setText("SOLVE");
        solveButton.addActionListener(e -> solve());
        solveButton.setBackground(highlightColor);
        solveButton.setForeground(Color.white);
        resetButton = new JButton();
        resetButton.setText("RESET");
        resetButton.addActionListener(e -> reset());
        resetButton.setBackground(highlightColor);
        resetButton.setForeground(Color.white);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        JPanel sizePanel = new JPanel(new GridLayout(1 ,2));
        sizePanel.add(new JLabel("Grid Size: "));
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 10, 1));
        sizeSpinner.addChangeListener(e -> {
            lettersPanel.changeSize((Integer) sizeSpinner.getValue());
            pack();
        });
        sizePanel.add(sizeSpinner);
        mainPanel.add(sizePanel, BorderLayout.NORTH);
        setContentPane(mainPanel);
        try {
            solver = new Solver();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        new Main("Boggle Solver");
    }

    public void reset(){
        lettersPanel.clear();
    }

    public void solve(){
        List<String> solution;
        try {
            solution = solver.solve(lettersPanel.getLetters());
            new ResultsWindow(solution);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
