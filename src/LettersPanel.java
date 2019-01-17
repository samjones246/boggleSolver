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
                        letter.setBackground(new Color(0x0f52ba));
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
    public int getFontSize(JTextField text, int columnsToHold){
        //Create a sample test String (we will it later in our calculations)
        String testString = "";
        for(int i = 0; i<columnsToHold; i++){
            testString = testString + "5";
        }


        //size will hold the optimal Vertical point size for the font
        Boolean up = null;
        int size = text.getHeight();
        Font font;
        while (true) {
            font = new Font("Default", Font.PLAIN, size);
            int testHeight = getFontMetrics(font).getHeight();
            if (testHeight < text.getHeight() && up != Boolean.FALSE) {
                size++;
                up = Boolean.TRUE;
            } else if (testHeight > text.getHeight() && up != Boolean.TRUE) {
                size--;
                up = Boolean.FALSE;
            } else {
                break;
            }
        }
        //At this point, size holds the optimal Vertical font size

        //Now we will calculate the width of the sample string
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.createGraphics().getFontMetrics();
        int width = fm.stringWidth(testString);

        //Using Martijn's answer, we calculate the optimal Horizontal font size
        int newFontSize = size * text.getWidth()/width;

        //The perfect font size will be the minimum between both optimal font sizes.
        //I have subtracted 2 from each font so that it is not too tight to the edges
        return Math.min(newFontSize-2, size-2);
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
