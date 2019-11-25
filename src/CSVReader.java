import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    private static double income = 0.0;
    private static double expenses = 0.0;

    private static HashMap<String, Double> map = new HashMap<>();

    public static void main(String[] args) {

        double localExpenses = 0;

        try {
            List<String> lines = Files.readAllLines(Paths.get("resourse/movementList.csv"));
            lines.remove(0);
            for (String line : lines) {
                String[] fragments = line.split(",", 8);
                try {
                    localExpenses = Double.parseDouble(fragments[7]);
                    expenses = expenses + localExpenses;
                }catch (NumberFormatException e){

                    localExpenses = Double.parseDouble(fragments[7].replace("\"", "")
                                        .replace(",","."));
                    expenses = expenses + localExpenses;

                }
                income = income + Double.parseDouble(fragments[6]);

                String[] a = fragments[5].split("    ",3);

                if(a[1].matches(".+\\\\.+")) {
                    String[] b = a[1].split("\\\\");
                    String key = b[b.length-1].strip();
                    if(!map.containsKey(key)){
                        map.put(key, localExpenses);
                    }
                    else{
                        map.put(key, map.get(key) + localExpenses);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String key: map.keySet()){
            System.out.println("Расходавано на " + key + " --- " + map.get(key) + " руб");
        }
        System.out.println(income + " " + expenses);
    }
}
