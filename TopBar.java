import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class TopBar extends JPanel implements MouseInputListener {
    int windowWidth;
    int windowHeight;
    static int pixs = 0;

    public TopBar() {
        this.windowWidth = Frame.windowWidth;
        this.windowHeight = Frame.windowHeight;
        addMouseListener(this);
        addMouseMotionListener(this);

        
        ResourcesButton resourcesButton = new ResourcesButton(windowWidth, windowHeight);
        StartButton startButton = new StartButton(windowWidth, windowHeight);
        setLayout(null);
        setBounds(0, 0, windowWidth, (int) (windowHeight / 6.5));
        while ((int) (windowHeight / 6.5) < ((int) (windowHeight / 6.5) / 2 / 3 - (pixs + 2) + (int) (9 * (windowWidth / 8.33) / 16))) {
            pixs++;
        }

        add(resourcesButton);
        resourcesButton.setBounds((int) (windowWidth / 1.5), (int) (windowHeight / 6.5) / 2 / 3 - 1, (int) (windowWidth / 8.33), (int) (9 * (windowWidth / 8.33) / 16) - (pixs + 2));
        resourcesButton.setBorder(new RoundedBorder(Color.decode("#f75280"), Color.decode("#449d9d"), 2, 16, (int) (windowWidth / 8.33)));
        add(startButton);
        startButton.setBounds((int) (windowWidth / 1.25), (int) (windowHeight / 6.5) / 2 / 3 - 1, (int) (windowWidth / 8.33), (int) (9 * (windowWidth / 8.33) / 16) - (pixs + 2));
        startButton.setBorder(new RoundedBorder(Color.decode("#f75280"), Color.decode("#449d9d"), 2, 16, (int) (windowWidth / 8.33)));
        pixs = 0;

    }

    @Override
    protected void paintComponent(Graphics graphic) {
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(new File("SySLogo.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.paintComponent(graphic);
        Graphics2D g2d = (Graphics2D) graphic.create();
        g2d.setColor(Color.decode("#449d9d"));
        g2d.fillRect(0, 0, windowWidth, (int) (windowHeight / 6.5));
        g2d.drawImage(resizeImage(logo, (int) (windowWidth / 8.33), (int) (windowHeight / 8.6481)), windowWidth / 16, (int) (windowHeight / 48), this);
        g2d.setColor(Color.decode("#f2eee3"));
        g2d.drawLine(0, (int) (windowHeight / 6.5), windowWidth, (int) (windowHeight / 6.5));
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point mousePosition = e.getPoint();
        Rectangle imageBounds = new Rectangle((int)(windowWidth / 16), (int) (windowHeight / 48), (int) (windowWidth / 8.33), (int) (windowHeight / 8.6481));
        System.out.println(mousePosition);
        System.out.println((int) (windowWidth / 8.33));
        if (imageBounds.contains(mousePosition)) {
            System.out.println("fddsfsadfsdaf");
            Frame.resourcesBool = false;
            Frame.startDiagBool = false;
            Frame.finishedBool = false;

            MainDesign.changeHome();
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
        Point mousePosition = e.getPoint();
        Rectangle imageBounds = new Rectangle((int)(windowWidth / 16), (int) (windowHeight / 48), (int) (windowWidth / 8.33), (int) (windowHeight / 8.6481));
        if(imageBounds.contains(mousePosition)) setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Point mousePosition = e.getPoint();
        Rectangle imageBounds = new Rectangle((int)(windowWidth / 16), (int) (windowHeight / 48), (int) (windowWidth / 8.33), (int) (windowHeight / 8.6481));
        if(!imageBounds.contains(mousePosition)) setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MainDesign.changeHome();
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
