import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
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
        board = new JTextField[size][size];
        setLayout(new GridLayout(size,size));
        setPreferredSize(new Dimension(400, 400));
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                JTextField letter = new JTextField();
                letter.setDocument(new LetterDoc(letter));
                letter.setForeground(Color.black);
                letter.setFont(new Font("Helvetica", Font.BOLD, 350/size));
                letter.setHorizontalAlignment(SwingConstants.CENTER);
                letter.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        letter.setBackground(new Color(0x881CBA));
                        letter.setForeground(Color.white);
                    }

                    public void focusLost(FocusEvent e) {
                        letter.setBackground(Color.white);
                        letter.setForeground(Color.black);
                    }
                });
                letter.setCaret(new DefaultCaret() {

                    public boolean isVisible() {
                        return false;
                    }

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
