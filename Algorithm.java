import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Algorithm {
    public Algorithm(){
    }
    //Returns an arraylits of all diseases whose symptoms match with the inputted symptoms
    public static ArrayList<String> getRankings(ArrayList<String> symptoms){
        HashMap<String, Double> rankings = new HashMap<>();
        HashSet<String> seenDiseases = new HashSet<>();
        rankings.put("Common Cold", 1d);
        rankings.put("Flu", 1d);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("diseases.txt"));
            String line = "";
            while((line = reader.readLine()) != null){
                String diseaseName = "";
                ArrayList<String> dsymptoms = new ArrayList<>(Arrays.asList(line.substring(line.indexOf("[")+1, line.indexOf("]")).split(", ")));
                diseaseName = line.substring(0, line.indexOf("[")).trim();
                for(String str : symptoms){
                    if(dsymptoms.contains(str) && !seenDiseases.contains(diseaseName)) rankings.put(diseaseName, rankings.getOrDefault(diseaseName, 0d)+1);
                }
                seenDiseases.add(diseaseName);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>(); 
        }
        rankings = sortMapByKey(rankings);
        ArrayList<String> keys = new ArrayList<>(rankings.keySet());
        ArrayList<Double> values = new ArrayList<>();
        for(String key : keys)
            values.add(rankings.get(key));
        for(int outer = 0; outer < keys.size()-1; outer++){
            for(int inner = 0; inner < keys.size()-1; inner++){
                if(values.get(inner) < values.get(inner+1)){
                    double temp = values.get(inner);
                    values.set(inner, values.get(inner+1));
                    values.set(inner+1, temp);

                    String str = keys.get(inner);
                    keys.set(inner, keys.get(inner+1));
                    keys.set(inner+1, str);
                }
            }
        }
        return keys;
    }
    //Returns an arraylist of all symptoms that contain a substring of the inputed symptom
    public static ArrayList<String> getMatchingSymptoms(String symptom){
        HashSet<String> hash = new HashSet<>();
        if(symptom.length() <= 2) return new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("counter.txt"));
            String line = "";
            while((line = reader.readLine()) != null){
                line = line.substring(0, line.indexOf("=")).trim();
                String[] words = symptom.split(" ");
                for(String str : words){
                    if(line.contains(str) && str.length() > 2)
                        hash.add(line);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        ArrayList<String> ans = new ArrayList<>(hash);
        HashMap<String, Double> freq = new HashMap<>();
        for(String str : ans){
            freq.put(str, (double) str.length()/symptom.length());
        }
        freq = sortMapByKey(freq);
        System.out.println(freq);
        ArrayList<String> keys = new ArrayList<>(freq.keySet());
        ArrayList<Double> values = new ArrayList<>();
        for(String str : keys) 
            values.add(freq.get(str));
        for(int outer = 0; outer < keys.size()-1; outer++){
            for(int inner = 0; inner < keys.size()-1; inner++){
                if(values.get(inner) < values.get(inner+1)){
                    double temp = values.get(inner);
                    values.set(inner, values.get(inner+1));
                    values.set(inner+1, temp);

                    String str = keys.get(inner);
                    keys.set(inner, keys.get(inner+1));
                    keys.set(inner+1, str);
                }
            }
        }
        return keys;
    }
    public static HashMap<String, Double> sortMapByKey(HashMap<String, Double> map ){
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2){
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> e : list){
            temp.put(e.getKey(), e.getValue());
        }
        return temp;
    }
}
