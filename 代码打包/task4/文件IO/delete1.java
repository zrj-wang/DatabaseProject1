import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class delete1 {

    public static void main(String[] args) {
        String inputFilePath = "D:\\demo\\danmu_input.csv";
        String outputFilePath = "D:\\demo\\danmu1.csv";


        int n = 2;  // 起始行
        int m = 300001; // 结束行


        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int lineNumber = 0;

            long startTime = System.nanoTime();

            while ((line = reader.readLine()) != null) {
                lineNumber++;


                if (lineNumber >= n && lineNumber <= m) {
                    continue;
                }

                writer.write(line);
                writer.newLine();
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds
            System.out.println(duration + " milliseconds.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

