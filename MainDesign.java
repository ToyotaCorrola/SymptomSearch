import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

public class MainDesign extends JPanel{
    JLabel moreThanTwoSymps = new JLabel();
    JLabel label = new JLabel();
    Icon virus = new ImageIcon("Virus.gif");
    static char resourcesLetter = 'A';
    static String diseaseName = "";
    int windowWidth;
    int windowHeight;
    //inner design (white area) of the home frame 
    private class InnerDesign extends JPanel{
        int innerWidth;
        int innerHeight;
        private InnerDesign(){
            innerWidth = windowWidth/16*14;
            innerHeight = (int)(windowHeight/2);
            setLayout(null);
            setBounds(windowWidth/16, (int)(windowHeight/5.5), innerWidth, innerHeight);
            setBackground(Color.decode("#f2eee3"));
            setOpaque(true);
            setBorder(new RoundedBorder(Color.decode("#f2eee3"), Color.decode("#449d9d"),2,16, innerWidth));

            StartButton startButton = new StartButton(innerWidth,innerHeight);
            startButton.setBounds(innerWidth/32, (int)(innerHeight/1.4), innerWidth/6,innerHeight/4);
            startButton.setBorder(new RoundedBorder(Color.decode("#f75280"), Color.decode("#f2eee3"), 2,16, innerWidth));
            startButton.setVisible(true);
            add(startButton);        
            
            label.setVisible(false);
            virus = new ImageIcon("Virus.gif");
            label = new JLabel(virus);
            label.setBounds((int)(windowWidth/16*6), (int)(this.getY()/4), virus.getIconWidth(), virus.getIconHeight());
            add(label);

        }
    }
    //When the diagnosis begins, this is the inner design
    private class TextArea extends JPanel{
        int innerWidth;
        int innerHeight;
        private TextArea(){
            innerWidth = windowWidth/16*10;
            innerHeight = (int)(windowHeight/2);
            setLayout(null);
            setBounds(windowWidth/4, (int)(windowHeight/5.5), innerWidth, innerHeight);
            setBackground(Color.decode("#f2eee3"));
            setBorder(new RoundedBorder(Color.decode("#f2eee3"), Color.decode("#449d9d"),2,16, innerWidth));

            JLabel showSympts = new JLabel();
            showSympts.setLayout(new GridLayout(1,1));
            showSympts.setText("Your symptoms are here: (press to delete)");
            showSympts.setFont(new Font("Helvetica", Font.BOLD, windowWidth/95));
            showSympts.setBounds((int) (windowWidth / 3.445), (int)(3*windowHeight/28), (int)(windowWidth/4), (int)(windowHeight/24));
            showSympts.setBackground(Color.decode("#f2eee3"));
            showSympts.setForeground(Color.decode("#0c120c"));
            showSympts.setOpaque(true);
            add(showSympts);
            
            JLabel sfys = new JLabel();
            sfys.setText("Search Symptoms");
            sfys.setFont(new Font("Helvetica", Font.BOLD, windowWidth/87));
            sfys.setBounds((int)(windowWidth/16), (int)(windowHeight/14), (int)(windowWidth/10), (int)(windowHeight/28));
            sfys.setBackground(Color.decode("#f2eee3"));
            sfys.setForeground(Color.decode("#0c120c"));
            sfys.setOpaque(true);
            add(sfys);
            
            JLabel description = new JLabel();
            description.setText("<html><body>Search for all the Symptoms<br>that are affecting you</body></html>");
            description.setFont(new Font("Helvetica", Font.PLAIN, windowWidth/105));
            description.setBounds(windowWidth/16, (int)(windowHeight/14)+windowHeight/28, (int)(windowWidth/7.5), (int)(windowHeight/21));
            description.setBackground(Color.decode("#f2eee3"));
            description.setForeground(Color.decode("#0c120c"));
            description.setOpaque(true);
            add(description);
        }
    }
    //Displays all the current symptoms the user chose
    private class SymptomsList extends JPanel{
        int innerWidth;
        int innerHeight;
        private SymptomsList(ArrayList<String> symps){
            innerWidth = (int) (windowWidth / 5.5 * 1.5);
            innerHeight = (int)(windowHeight / 24);
            setBounds((int) (windowWidth / 1.85), (int) (windowHeight / 2.95), innerWidth, (int)(innerHeight*Math.ceil((double)symps.size()/7)));
            setLayout(new GridLayout((int)Math.ceil((double)symps.size()/7), 7));
            setBackground(Color.WHITE);
            setOpaque(true);

            for(int symp = 0; symp < symps.size(); symp++){
                final int index = symp;
                JButton item = new JButton();
                item.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        item.setBackground(Color.decode("#e3e3e3"));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        item.setBackground(Color.WHITE);
                    }
                    public void mouseClicked(java.awt.event.MouseEvent evt){
                        Frame.allSymptoms.remove(symps.get(index));

                        Main.frame.startDiagProcess(Frame.startDiagBool);
                        Main.frame.showFinished(Frame.finishedBool, Frame.resourcesBool, Frame.startDiagBool);
                        Frame.topBar.setVisible(false);
                        Frame.topBar = new TopBar();
                        Main.frame.add(Frame.topBar);
                        Frame.mainDesign.setVisible(false);
                        Frame.text = " " + Frame.textPane.getText().trim();
                        Frame.mainDesign = new MainDesign();
                        Main.frame.add(Frame.mainDesign);
                    }
                });
                item.setText(symps.get(index).substring(0, 1).toUpperCase() + symps.get(index).substring(1).toLowerCase());
                item.setHorizontalAlignment(SwingConstants.CENTER);
                item.setVerticalAlignment(SwingConstants.CENTER);
                item.setBackground(Color.WHITE);
                item.setOpaque(true);
                item.setBorder(new LineBorder(Color.decode("#c7c7c7")));
                JScrollPane sb = new ScrollBarCustom(item);
                add(sb);
            }
        }
    }
    //Submit Diagnosis Button when the user is done
    private class FinishedDiagButton extends JButton{
        int innerWidth;
        int innerHeight;
        private FinishedDiagButton(){
            innerWidth = (int)(windowWidth/8.33);
            innerHeight = (int)(9*(windowWidth/8.33)/24);
            setText("Finish");
            setFont(new Font("Helvetica", Font.PLAIN, windowWidth/85));
            setBounds((int) (windowWidth / 3.22), (int)(windowHeight/1.7), innerWidth, innerHeight);
            setBorder(new RoundedBorder(Color.decode("#53db65"), Color.decode("#53db65"), 2, 16, innerWidth));
            setBackground(Color.decode("#53db65"));
            setForeground(Color.decode("#0c120c"));
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt){
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(java.awt.event.MouseEvent evt){
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                public void mouseClicked(java.awt.event.MouseEvent evt){
                    if(Frame.allSymptoms.size() > 2){
                        Frame.resourcesBool = false;
                        Frame.startDiagBool = false;
                        Frame.finishedBool = true;

                        MainDesign.changeHome();
                    }
                    else if(Frame.allSymptoms.size() <= 2){
                        moreThanTwoSymps.setText("Add More than 2 Symptoms");
                        moreThanTwoSymps.setBounds((int)(windowWidth / 1.85), (int)(windowHeight/1.7), innerWidth, innerHeight);
                        moreThanTwoSymps.setFont(new Font("Helvetica", Font.BOLD, windowWidth/125));
                        moreThanTwoSymps.setBackground(Color.decode("#f2eee3"));
                        moreThanTwoSymps.setForeground(Color.decode("#0c120c"));
                        moreThanTwoSymps.setOpaque(true);
                        moreThanTwoSymps.setVisible(true);

                    }
                }
            });
        }
    }
    //Displays all the results to the user
    private class FinishedDiag extends JPanel{
        int innerWidth;
        int innerHeight;
        private FinishedDiag(){
            innerWidth = (int)(windowWidth / 1.5);
            innerHeight = (int)(windowHeight / 2);
            ArrayList<String> rankings = Algorithm.getRankings(Frame.allSymptoms);
            setLayout(null);
            setBorder(new RoundedBorder(Color.decode("#449d9d"), Color.decode("#449d9d"), 2, 16, innerWidth));
            setBackground(Color.decode("#f2eee3"));
            setForeground(Color.decode("#0c120c"));
            setOpaque(true);
            setBounds((int) (windowWidth / 6), (int) (windowHeight / 4), innerWidth, innerHeight);

            JLabel sfys = new JLabel();
            sfys.setText("Based on your symptoms, here are the Top 10 diseases you may have:");
            sfys.setFont(new Font("Helvetica", Font.BOLD, windowWidth/87));
            sfys.setBounds((int)(windowWidth/8), (int)(windowHeight/24), (int)(windowWidth/2.5), (int)(windowHeight/28));
            sfys.setBackground(Color.decode("#f2eee3"));
            sfys.setForeground(Color.decode("#0c120c"));
            sfys.setOpaque(true);
            add(sfys);

            for(int index = 0; index < rankings.size() && index < 10; index++){
                JLabel symptoms = new JLabel();
                symptoms.setText(String.valueOf(index+1) + ". " + rankings.get(index));
                symptoms.setFont(new Font("Helvetica", Font.PLAIN, windowWidth/87));
                symptoms.setBounds((int)(windowWidth/8), (int)(windowHeight/12)+((int)(windowHeight/28)*index), (int)(windowWidth/2.5), (int)(windowHeight/28));
                symptoms.setBackground(Color.decode("#f2eee3"));
                symptoms.setForeground(Color.decode("#0c120c"));
                symptoms.setOpaque(true);
                add(symptoms);
            }
        }
    }
    public MainDesign(){
        this.windowWidth = Frame.windowWidth;
        this.windowHeight = Frame.windowHeight;
        setLayout(null);
        setBounds(0, (int)(windowHeight/6.5)+1, windowWidth, (int)(windowHeight/6.5)+1);
        setBackground(Color.decode("#449d9d"));
        add(moreThanTwoSymps);
        moreThanTwoSymps.setVisible(false);
        if(!Frame.startDiagBool && !Frame.resourcesBool && !Frame.finishedBool){
            InnerDesign innerDesign = new InnerDesign();
            add(innerDesign);
        }
        if(Frame.startDiagBool){
            FinishedDiagButton finished = new FinishedDiagButton();
            add(finished);
            SymptomsList sympList = new SymptomsList(Frame.allSymptoms);
            add(sympList);
            TextArea textArea = new TextArea();
            add(textArea);
        }
        if(Frame.resourcesBool){
            ResourcesDesign resources = new ResourcesDesign(resourcesLetter);
            add(resources);
        }
        if(Frame.finishedBool){
            FinishedDiag finished = new FinishedDiag();
            add(finished); 
        }

    }
    public static void changeHome(){
        Main.frame.startDiagProcess(Frame.startDiagBool);
        Main.frame.showFinished(Frame.finishedBool, Frame.resourcesBool, Frame.startDiagBool);
        Frame.topBar.setVisible(false);
        Frame.topBar = new TopBar();
        Main.frame.add(Frame.topBar);
        Frame.mainDesign.setVisible(false);
        Frame.text = " " + Frame.textPane.getText().trim();
        Frame.mainDesign = new MainDesign();
        Main.frame.add(Frame.mainDesign);
    }
    @Override
    protected void paintComponent (Graphics graphic){
        super.paintComponent(graphic);
        Graphics2D g2d = (Graphics2D) graphic.create();
        g2d.setColor(Color.decode("#f2eee3"));
        g2d.drawLine(0, (int)(windowHeight/6.5), windowWidth, (int)(windowHeight/6.5));
    }
}