import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.awt.GridLayout;

public class ResourcesDesign extends JPanel{
    int windowWidth;
    int windowHeight;
    JScrollPane scrollPane = new JScrollPane();
    JPanel scrollPanel = new JPanel();
    SymptomsList sympsList = new SymptomsList(MainDesign.diseaseName);
    private class LetterChoice extends JPanel{
        private LetterChoice(){
            setBounds(windowWidth/4, (int)(windowHeight/14), windowWidth/2, (int)(windowWidth/2/6.5));
            setBackground(Color.decode("#449d9d"));
            setLayout(new GridLayout(2, 13));
            String alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for(char letter : alph.toCharArray()){
                JButton letterButton = new JButton();
                letterButton.setBackground(Color.decode("#e7e7e7"));
                letterButton.setForeground(Color.decode("#0c120c"));
                letterButton.setBorder(new LineBorder(Color.decode("#c7c7c7"), 1));
                letterButton.setFont(new Font("Ariel", Font.PLAIN, windowWidth / 85));
                letterButton.setHorizontalAlignment(SwingConstants.CENTER);
                letterButton.setVerticalAlignment(SwingConstants.CENTER);
                letterButton.setText(String.valueOf(letter));
                letterButton.setOpaque(true);
                letterButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt){
                        MainDesign.resourcesLetter = letter;
                        MainDesign.changeHome();
                    }
                });
                add(letterButton);
            }
        }
    }
    private class SymptomsList extends JPanel{
        private SymptomsList(String diseaseName){
            if(diseaseName.length() > 0){
                setBounds((int) (windowWidth / 1.98), (int)(windowHeight/4), (int) (windowWidth / 4), (int) (windowHeight / 2));
                setBackground(Color.decode("#f2eee3"));
                setBorder(new LineBorder(Color.decode("#c7c7c7"), 1));
                setOpaque(true);
                setLayout(null);
                
                JLabel title = new JLabel();
                title.setText("Symptoms For " + diseaseName + ":");
                title.setFont(new Font("Helvetica", Font.BOLD, windowWidth/85));
                title.setBounds((int) (windowWidth / 1000), (int)(windowHeight/1000), (int) (windowWidth / 4)-2, (int) (windowHeight / 32));
                title.setBorder(new LineBorder(Color.decode("#c7c7c7"), 1));
                title.setBackground(Color.decode("#f2eee3"));
                title.setForeground(Color.decode("#0c120c"));
                title.setOpaque(true);
                title.setHorizontalAlignment(SwingConstants.CENTER);
                title.setVerticalAlignment(SwingConstants.CENTER);
                add(title);

                TreeMap<String, ArrayList<String>> data = getData(diseaseName.charAt(0));
                ArrayList<String> symptoms = data.get(diseaseName);
                JPanel showSymptoms = new JPanel();
                showSymptoms.setBounds((int) (windowWidth / 1000), (int)(windowHeight / 32), (int) (windowWidth / 4)-2, windowHeight - (int)(windowHeight / 32));
                showSymptoms.setBackground(Color.decode("#f2eee3"));
                showSymptoms.setForeground(Color.decode("#0c120c"));
                showSymptoms.setOpaque(true);
                showSymptoms.setLayout(new GridLayout(symptoms.size(), 1));
                for(int index = 0; index < symptoms.size(); index++){
                    JLabel label = new JLabel();
                    label.setFont(new Font("Ariel", Font.PLAIN, windowWidth/100));
                    String text = symptoms.get(index);
                    if(text.split(" ").length > 8){
                        int counter = 0;
                        int indexOfSpace = -1;
                        System.out.println(text.length()/2-1);
                        outer: for(int i = 0; i < text.length(); i++){
                            if(text.charAt(i) == ' '){
                                counter++;
                                if(counter == text.split(" ").length/2-1){
                                    indexOfSpace = i;
                                    break outer;
                                }
                            }
                        }
                        String str1 =  text.substring(0, indexOfSpace).trim();
                        String str2 ="<br>" + text.substring(indexOfSpace, text.length()).trim() + "</br>";
                        text = str1 + str2;
                    }
                    label.setText("<html><ul><li>" + text + "</li></ul></html>");
                    label.setBackground(Color.decode("#f2eee3"));
                    label.setForeground(Color.decode("#0c120c"));
                    label.setOpaque(true);
                    showSymptoms.add(label);
                }

                JScrollPane scroll = new ScrollBarCustom(showSymptoms);
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.getVerticalScrollBar().setUnitIncrement(10);
                scroll.setBounds((int) (windowWidth / 1000), (int)(windowHeight / 32), (int) (windowWidth / 4)-2, windowHeight - (int)(windowHeight / 32));
                scroll.setBorder(new LineBorder(Color.decode("#c7c7c7"), 1));
                add(scroll);
            }
        }
    }
    public ResourcesDesign(char letter){
        this.windowWidth = Frame.windowWidth;
        this.windowHeight = Frame.windowHeight;
        setLayout(null);
        setBounds(0, (int)(windowHeight/6.5)+1, windowWidth, (int)(windowHeight )+1);
        setBackground(Color.decode("#449d9d"));

        LetterChoice letterChoice = new LetterChoice();
        add(letterChoice);

        JLabel title = new JLabel();
        title.setFont(new Font("Ariel", Font.BOLD, windowWidth/85));
        title.setText("Find Symptoms For Diseases");
        title.setBounds(windowWidth/4, (int)(windowHeight/48), windowWidth/2, (int)(windowWidth/48));
        title.setBackground(Color.decode("#449d9d"));
        title.setForeground(Color.decode("#0c120c"));
        title.setOpaque(true);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title);

        allDiseases(MainDesign.resourcesLetter);
        add(scrollPane);

        System.out.println(MainDesign.diseaseName);
        if(MainDesign.diseaseName.length() > 0){
            sympsList.setVisible(false);
            sympsList = new SymptomsList(MainDesign.diseaseName);
            add(sympsList);
        }
    }
    private void allDiseases(char letter){
        TreeMap<String, ArrayList<String>> data = getData(letter);
        ArrayList<String> diseases = new ArrayList<>(data.keySet());
        scrollPanel.setBounds((int) (windowWidth / 4.10), (int)(windowHeight/4), (int) (windowWidth / 4), (int) (windowHeight / 2));
        scrollPanel.setBorder(new LineBorder(Color.decode("#e3e3e3")));
        
        scrollPanel.setLayout(new GridLayout(diseases.size(), 1));
        Collections.sort(diseases);
        for(int index = 0; index < diseases.size(); index++){
            JButton disease = new JButton();
            disease.setText(diseases.get(index));
            disease.setBounds((int) (windowWidth / 4.10), (int)(windowHeight/4) + (int) (windowHeight / 2 / 10 * index), (int) (windowWidth / 4), (int) (windowHeight / 2 / 15));
            disease.setFont(new Font("Helvetica", Font.BOLD, windowWidth/120));
            disease.setBackground(Color.decode("#f2eee3"));
            disease.setForeground(Color.decode("#0c120c"));
            final int i = index;
            disease.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt){
                    MainDesign.diseaseName = diseases.get(i);
                    MainDesign.changeHome();
                }
            });
            scrollPanel.add(disease);
        }
        if(diseases.size() == 0){
            JLabel disease = new JLabel();
            disease.setText("No Data Found");
            disease.setBackground(Color.decode("#f2eee3"));
            disease.setForeground(Color.decode("#0c120c"));
            disease.setOpaque(true);
            disease.setHorizontalAlignment(SwingConstants.CENTER);
            disease.setVerticalAlignment(SwingConstants.CENTER);
            scrollPanel.add(disease);
        }
        scrollPane.setVisible(false);
        scrollPane = new ScrollBarCustom(scrollPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.decode("#f2eee3"));
        scrollPane.setBounds((int) (windowWidth / 4.10), (int)(windowHeight/4), (int) (windowWidth / 4), (int) (windowHeight / 2));
        scrollPane.setBorder(new LineBorder(Color.decode("#e3e3e3")));
    }
    private TreeMap<String, ArrayList<String>> getData(char letter){
        TreeMap<String, ArrayList<String>> ans = new TreeMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("diseases.txt"));
            String line = "";
            while((line = reader.readLine()) != null){
                if(line.charAt(0) == letter){
                    ArrayList<String> temp = new ArrayList<>(Arrays.asList(line.substring(line.indexOf("[")+1, line.indexOf("]")).split(",")));
                    ans.put(line.substring(0, line.indexOf("[")).trim(), temp);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
