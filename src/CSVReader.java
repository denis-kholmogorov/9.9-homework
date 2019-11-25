import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSVReader {

    private static double income = 0.0;
    private static double expenses = 0.0;

    private static HashMap<String, Double> map = new HashMap<>();

    public static void main(String[] args)
    {

        double localExpenses;

        try
        {
            List<String> lines = Files.readAllLines(Paths.get("resourse/movementList.csv"));
            lines.remove(0);

            for (String line : lines)
            {
                String[] fragments = line.split(",", 8);

                income = income + Double.parseDouble(fragments[6]);

                try
                {
                    localExpenses = Double.parseDouble(fragments[7]);
                    expenses = expenses + localExpenses;
                }
                catch (NumberFormatException e)
                {
                    localExpenses = Double.parseDouble(fragments[7].replace("\"", "")
                                        .replace(",","."));
                    expenses = expenses + localExpenses;
                }


                String[] a = fragments[5].split("        ",2);

                if(a[0].matches(".+\\\\.+"))
                {
                    String key = a[0].substring(a[0].lastIndexOf("\\")+1).strip();
                    System.out.println(key);
                    if(map.containsKey(key))
                    {
                        map.put(key, map.get(key) + localExpenses);
                    }
                    else
                    {
                        map.put(key, localExpenses);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for(String key: map.keySet())
        {
            System.out.println("Расходавано на " + key + " --- " + map.get(key) + " руб");
        }
        System.out.println("Общий доход - " + income + " руб\nОбщий расход - " + expenses + " руб");
    }
}
