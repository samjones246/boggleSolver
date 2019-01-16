import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class Main extends JDialog{
    LettersPanel lettersPanel;
    JButton solveButton;
    JButton resetButton;
    Solver solver;
    Main(String title){
        super(new JFrame(), title);
        initialise();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        pack();
        setVisible(true);
    }
    public void initialise(){
        Color sapphire = new Color(0x0f52ba);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        lettersPanel = new LettersPanel(4);
        mainPanel.add(lettersPanel, BorderLayout.CENTER);
        solveButton = new JButton();
        solveButton.setText("SOLVE");
        solveButton.addActionListener(e -> solve());
        solveButton.setBackground(sapphire);
        solveButton.setForeground(Color.white);
        resetButton = new JButton();
        resetButton.setText("RESET");
        resetButton.addActionListener(e -> reset());
        resetButton.setBackground(sapphire);
        resetButton.setForeground(Color.white);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
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
        List<String> solution = solver.solve(lettersPanel.getLetters());
        new ResultsWindow(solution);
    }

}
