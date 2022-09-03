import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
public class ResourcesButton extends JButton implements MouseInputListener{
    public ResourcesButton(int windowWidth, int windowHeight){
        setText("Resources");
        setFont(new Font("Helvetica", Font.PLAIN, windowWidth/85));
        setBackground(Color.decode("#f75280"));
        setForeground(Color.decode("#0c120c"));
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        addMouseListener(this);
        addMouseMotionListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Frame.resourcesBool = true;
        Frame.startDiagBool = false;
        Frame.finishedBool = false;

        MainDesign.changeHome();
     }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}