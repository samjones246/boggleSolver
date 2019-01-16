import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.List;

public class LettersPanel extends JPanel {
    private JTextField[][] board;
    private int size;
    LettersPanel(int size){
        this.size = size;
        initialise();
    }
    void initialise(){
        removeAll();
        Font font = new Font("Helvetica", Font.BOLD, 60);
        board = new JTextField[size][size];
        setLayout(new GridLayout(size,size));
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                JTextField letter = new JTextField();
                letter.setDocument(new LetterDoc(letter));
                letter.setPreferredSize(new Dimension(75, 75));
                letter.setFont(font);
                letter.setForeground(Color.black);
                letter.setHorizontalAlignment(SwingConstants.CENTER);
                letter.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        letter.setBackground(new Color(0x0f52ba));
                        letter.setForeground(Color.white);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        letter.setBackground(Color.white);
                        letter.setForeground(Color.black);
                    }
                });
                letter.setCaret(new DefaultCaret() {

                    @Override
                    public void paint(Graphics g) {
                    }

                    @Override
                    public boolean isVisible() {
                        return false;
                    }

                    @Override
                    public boolean isSelectionVisible() {
                        return false;
                    }

                });
                letter.setHighlighter(null);
                board[i][j] = letter;
                add(letter);
            }
        }
    }
    public String[][] getLetters(){
        String[][] letters = new String[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                letters[i][j] = board[i][j].getText().toLowerCase();
            }
        }
        return letters;
    }

    public void clear() {
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                board[i][j].setText(" ");
            }
        }
        board[0][0].requestFocus();
    }
    public void changeSize(int newSize){
        size = newSize;
        initialise();
        repaint();
    }
}

class LetterDoc extends PlainDocument {
    JTextField field;
    LetterDoc(JTextField field){
        this.field = field;
    }
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        List<String> alphabet = new ArrayList<>();
        Collections.addAll(alphabet, "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
        if (str != null) {
            if (str.length() == 1 && alphabet.contains(str.toLowerCase())) {
                if(getLength()==1) {
                    super.replace(0, 1, str, a);
                }else {
                    super.insertString(offs, str.toUpperCase(), a);
                }
                field.transferFocus();
            }
        }
    }


}
