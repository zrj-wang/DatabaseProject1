import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class update {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();


        String csvFile = "D:\\demo\\user_information_input.csv";
        String outputCsvFile = "D:\\demo\\users1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        List<String[]> data = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] row = line.split(cvsSplitBy, -1);

                for (int i = 0; i < row.length; i++) {

                    if (row[i].isEmpty()) {
                        row[i] = "project";
                    }
                }
                data.add(row);
            }


            BufferedWriter bw = new BufferedWriter(new FileWriter(outputCsvFile));
            for (String[] row : data) {
                bw.write(String.join(cvsSplitBy, row) + "\n");
            }
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        long end = System.currentTimeMillis();

        System.out.println("Update " + (end - start) + " milliseconds.");
    }
}

