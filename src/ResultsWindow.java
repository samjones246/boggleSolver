import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultsWindow extends JDialog{
    public ResultsWindow(List<String> solution) {
        JTextArea resultsArea = new JTextArea();
        JScrollPane panel = new JScrollPane(resultsArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.setPreferredSize(new Dimension(200, 250));
        add(panel);
        for (String word : solution){
            resultsArea.append(word+"\n");
        }
        setTitle("Solution");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
