import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;

import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class delete2 {

    public static void main(String[] args) {
        String inputFilePath = "D:\\demo\\danmu_input.csv";
        String outputFilePath = "D:\\demo\\danmu1.csv";


        int startLine = 2;
        int endLine = 30001;

        long startTime = System.currentTimeMillis();

        try (
                Reader in = new BufferedReader(new FileReader(inputFilePath));
                Writer out = new BufferedWriter(new FileWriter(outputFilePath));
                CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
                CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)
        ) {
            int lineNumber = 0;
            for (CSVRecord record : parser) {
                lineNumber++;
                if (lineNumber < startLine || lineNumber > endLine) {
                    printer.printRecord(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println( duration + " milliseconds.");
    }
}
