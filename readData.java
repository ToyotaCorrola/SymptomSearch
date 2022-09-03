import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//Class that reads all the medical data that was scraped from the class **getDiseaseData.java** on this domain: "https://www.medicinenet.com/symptoms_and_signs/alpha_a.htm" which was stored in a text file named ""
public class readData {
    public static void getAllData(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("diseases.txt"));
            String line = "";
            HashMap<String, Integer> count = new HashMap<>();
            while((line = reader.readLine()) != null){
                for(String str : line.substring(line.indexOf("[")+1, line.indexOf("]")).split(",")){
                    str = str.trim();
                    count.put(str, count.getOrDefault(str, 0)+1);
                }
            }
            reader.close();
            count = sortMapByKey(count);
            BufferedWriter writer = new BufferedWriter(new FileWriter("counter.txt"));
            writer.write(count.toString());
            writer.close();
            System.out.println("done");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static HashMap<String, Integer> sortMapByKey(HashMap<String, Integer> map ){
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> e : list){
            temp.put(e.getKey(), e.getValue());
        }
        return temp;
    }
}
