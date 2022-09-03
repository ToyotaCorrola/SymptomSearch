import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
//Class that fetches and scrapes all the data on diseases and symptoms on this medical website: "https://www.medicinenet.com/symptoms_and_signs/alpha_a.htm" stored into a text file named "disease.txt"
//where alpha_{letter} represents the first letter of every disease on that domain.
public class getDiseaseData{
    public static void getAllData(){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        for(int alphl = 0; alphl < 3; alphl++){
            String url = "https://www.medicinenet.com/symptoms_and_signs/alpha_"+alph.charAt(alphl)+".htm";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(alph.charAt(alphl)+".txt"));
                final Document doc = Jsoup.connect(url).get();

                Elements links = doc.select("a[href~=https://www.medicinenet.com/]");
                for(Element link1s : links){
                    String info = getInfo(link1s.toString());
                    if(info.length() > 0) {
                        writer.write(info + "\n");
                    }
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getInfo(String url){
        try{
            String ans = "";
            if(!url.toString().contains("onclick") && !url.toString().contains("target=")){
                final String dlink = url.toString().substring(url.toString().indexOf("https://www.medicinenet.com/"), url.toString().indexOf("htm")+3);
                String disease = "";
                outer: for(int index = 0; index < Jsoup.connect(dlink).get().title().toString().split(" ").length; index++){
                    String[] arr = Jsoup.connect(dlink).get().title().toString().split(" ");
                    if(arr[index].equals("Symptoms,") || arr[index].equals("Signs") || arr[index].equals("Cause")) 
                        break outer;
                    if(arr[index].equals("And") || arr[index].equals("and") || arr[index].contains(",") || arr[index].equals("In") || arr[index].equals("in"))
                        return "";
                    disease += arr[index] + " ";
                }
                Elements dlinks = Jsoup.connect(dlink).get().select("li");
                HashSet<String> allsymps = new HashSet<>();
                for(Element link : dlinks){
                    String slink = link.toString();
                    if(link.childrenSize() == 0 || slink.contains("wmdTrack('embd-lnk');")){
                        boolean cont = true;
                        String symp = "";
                        for(int index = 0; index < slink.toString().length(); index++){
                            char cha = slink.charAt(index);
                            if(cha == ',' || cha == '.' || cha == '&' || cha == '(' || cha == ')') continue;
                            if(cha == '<') cont = false;
                            if(cha == '>') cont = true;
                            if(!(cha == '>') && cont) symp += cha;
                        }
                        if(!symp.split(" ")[0].equals("MedTerms")){
                            String[] arr1 = symp.split(" ");
                            symp = "";
                            for(String str : arr1){
                                if(!str.equals("a") && !str.equals("an") && !str.equals("and"))
                                    symp += str;
                                if(!arr1[arr1.length-1].equals(symp)) symp += " ";
                            }
                            String temp = symp + " ";
                            symp = "";
                            for(int index = 0; index < temp.length()-1; index++){
                                if(temp.charAt(index) != ' ') symp += temp.charAt(index);
                                if(temp.charAt(index) == ' '){
                                    if(temp.charAt(index+1) == ' '){
                                        allsymps.add(symp.trim().toLowerCase());
                                        symp = "";
                                    }
                                    else symp += " ";
                                }
                            }
                            allsymps.add(symp.trim().toLowerCase());
                        }
                    }
                }
                ArrayList<String> add = new ArrayList<>(allsymps);
                int length = add.size()-1;
                while(length >= 0){
                    if(add.get(length).length() == 0){
                        add.remove(length);
                        length--;
                    }
                    length--;
                }
                if(!add.isEmpty()){
                    ans += disease + add.toString();
                }
            }
            return ans;
        }
        catch(IOException e){
            e.printStackTrace();
        }   
        return "";
        }
    }
    