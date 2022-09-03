import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.GridLayout;

public class Frame extends JFrame implements MouseInputListener {
    static Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int windowWidth = (int) windowSize.getWidth();
    static int windowHeight = (int) windowSize.getHeight();
    static TopBar topBar = new TopBar();
    static MainDesign mainDesign = new MainDesign();
    public static boolean startDiagBool = false;
    public static boolean resourcesBool = false;
    public static boolean finishedBool = false;
    static JTextField textPane = new JTextField();
    static JScrollPane textScrollPane = new JScrollPane();
    JScrollPane sympPane = new JScrollPane();
    JPanel similarSymptsList = new JPanel();
    JScrollPane sb = new JScrollPane();
    JPanel finishedPanel = new JPanel();
    static ArrayList<String> allSymptoms = new ArrayList<>();
    static String text = " " + textPane.getText().trim();
    public Frame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                pack();
                setSize(windowSize);
                setLocationRelativeTo(null);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setResizable(true);
                add(topBar);
                add(mainDesign);
                setMinimumSize(new Dimension(720, 405));
                setState(java.awt.Frame.NORMAL);
            }
        });
        setIconImage(new ImageIcon("SySLogo.png").getImage());
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        windowWidth = getWidth();
        windowHeight = getHeight();
        changeHome();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        windowWidth = getWidth();
        windowHeight = getHeight();
        startDiagProcess(startDiagBool);
        showFinished(finishedBool, resourcesBool, startDiagBool);
        changeHome();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        windowWidth = getWidth();
        windowHeight = getHeight();
        startDiagProcess(startDiagBool);
        showFinished(finishedBool, resourcesBool, startDiagBool);
        changeHome();
    }
    //Add the textpane when the diagnosis begins 
    public void startDiagProcess(boolean startDiagBool){
        if (startDiagBool) {
            textPane = new JTextField();
            textPane.setText(text);
            textPane.setFont(new Font("Ariel", Font.PLAIN, windowWidth/85));
            textPane.setMaximumSize(new Dimension((int) (windowWidth / 5.5), windowHeight / 24));
            textPane.setBorder(BorderFactory.createLineBorder(Color.decode("#c7c7c7")));
            textPane.setForeground(Color.decode("#0c120c"));
            textPane.setBackground(Color.WHITE);
            textPane.setOpaque(true);
            textScrollPane.setVisible(false);
            textScrollPane = new ScrollBarCustom(textPane);
            textScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            textScrollPane.setMaximumSize(new Dimension((int) (windowWidth / 5.5), windowHeight / 28));
            textScrollPane.setBounds((int) (windowWidth / 3.22), (int) (windowHeight / 2.95), (int) (windowWidth / 5.5),windowHeight / 24);
            textScrollPane.setBorder(BorderFactory.createEmptyBorder());
            textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            add(textScrollPane);
            if(text.trim().length() > 2){
                ArrayList<String> symptoms = Algorithm.getMatchingSymptoms(text.trim().toLowerCase());
                addRelatedSympt(symptoms);
            }
            if(text.trim().length() <= 2){
                similarSymptsList.setVisible(false);
                remove(sb);
            }
        }
    }
    //Add the scrollpane of symptoms that the user can choose from based on what they wrote
    private void addRelatedSympt(ArrayList<String> symptoms) {
        similarSymptsList.setVisible(false);
        similarSymptsList = new JPanel();
        similarSymptsList.setBackground(Color.WHITE);
        if (symptoms.size() > 0) {
            similarSymptsList.setLayout(new GridLayout(symptoms.size(), 1));
            similarSymptsList.setBounds((int) (windowWidth / 3.22),(int) (windowHeight / 2.95) + (int) (windowHeight / 16.1), (int) (windowWidth / 5.5), (windowHeight / 24)*4);
            similarSymptsList.setBorder(new LineBorder(Color.decode("#c7c7c7")));
            for (int symptom = 0; symptom < symptoms.size(); symptom++) {
                JButton button = new JButton();
                final int index = symptom;
                button.setBounds((int) (windowWidth / 3.22), ((int) (windowHeight / 2.95) + (int) (windowHeight / 16)) * (symptom + 1), (int) (windowWidth / 5.5), (int) (windowHeight / 24));
                button.setText(symptoms.get(index).substring(0, 1).toUpperCase() + symptoms.get(index).substring(1).toLowerCase());
                button.setFont(new Font("Ariel", Font.PLAIN, windowWidth / 85));
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setBackground(Color.WHITE);
                button.setForeground(Color.decode("#0c120c"));
                button.setOpaque(true);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        button.setBackground(Color.decode("#e3e3e3"));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        button.setBackground(Color.WHITE);
                    }
                    public void mouseClicked(java.awt.event.MouseEvent evt){
                        if(!allSymptoms.contains(symptoms.get(index))){
                            allSymptoms.add(symptoms.get(index));
                            windowWidth = getWidth();
                            windowHeight = getHeight();
                            startDiagProcess(startDiagBool);
                            showFinished(finishedBool, resourcesBool, startDiagBool);
                            changeHome();
                        }
                    }
                });
                similarSymptsList.add(button);
            }
        }
        else if(symptoms.size() == 0){
            similarSymptsList.setBounds((int) (windowWidth / 3.22),(int) (windowHeight / 2.95) + (int) (windowHeight / 16.1), (int) (windowWidth / 5.5), (windowHeight / 24)*3);
            similarSymptsList.setBorder(new LineBorder(Color.decode("#c7c7c7")));
            JLabel label = new JLabel();
            label.setFont(new Font("Ariel", Font.PLAIN, windowWidth / 140));
            label.setBounds((int) (windowWidth / 3.22),(int) (windowHeight / 2.95) + (int) (windowHeight / 16.1), (int) (windowWidth / 5.5), (windowHeight / 24)*3);
            label.setText("<html><h2>Symptom Not Found</h2><p>Your symptom was not found</p><ul><li>Try using common words</li><li>If our database doesn't include your word, <br>it will not appear</br></li></ul></html>");
            similarSymptsList.add(label);
        }
        sb.setVisible(false);
        sb = new ScrollBarCustom(similarSymptsList);
        sb.setBounds((int) (windowWidth / 3.22),(int) (windowHeight / 2.95) + (int) (windowHeight / 16.1), (int) (windowWidth / 5.5), (windowHeight / 24) * 4);
        sb.setBorder(new LineBorder(Color.decode("#c7c7c7")));
        sb.setBackground(Color.WHITE);
        sb.getVerticalScrollBar().setUnitIncrement(9);
        sb.setOpaque(true);
        sb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(sb);
    }
    //Remove and and the TextPane
    public void showFinished(boolean finishedBool, boolean resourcesBool, boolean startDiagBool){
        if((finishedBool || resourcesBool) || (!finishedBool && !resourcesBool && !startDiagBool)){
            textScrollPane.setVisible(false);
            textPane.setVisible(false);
            sb.setVisible(false);
        }
        if(startDiagBool){
            textScrollPane.setVisible(true);
            textPane.setVisible(true);
            sb.setVisible(true);
        }
    }
    //Change what is displayed based on what is clicked 
    private void changeHome() {
        topBar.setVisible(false);
        topBar = new TopBar();
        add(topBar);
        mainDesign.setVisible(false);
        text = " " + textPane.getText().trim();
        mainDesign = new MainDesign();
        add(mainDesign);
    }   
}
