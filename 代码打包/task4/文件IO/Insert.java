import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Insert {

    public static void main(String[] args) {
        String filePath = "D:\\demo\\danmu_input.csv";
        int n = 2; // 起始行号
        int m = 30000; // 结束行
        int x = 12478996; // 在第x行后插入内容

        // 记录操作开始时间
        long startTime = System.currentTimeMillis();

        List<String> lines = new ArrayList<>();
        List<String> linesToCopy = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                if (lineNumber >= n && lineNumber <= m) {
                    linesToCopy.add(line);
                }
                lines.add(line);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        lines.addAll(x, linesToCopy);


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("time : " + duration + " ms");
    }


}
