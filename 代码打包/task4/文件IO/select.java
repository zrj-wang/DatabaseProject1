import java.io.*;
import java.util.*;

public class select {
    public static void main(String[] args) {

        Set<String> userIds = new HashSet<>();


        int totalDanmuCount = 0;


        String userFilePath = "D:\\data\\users.csv";
        String danmuFilePath = "D:\\demo\\danmu_input.csv";

        long start = System.currentTimeMillis();


        try (BufferedReader userReader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            int userCount = 0;
            int n = 30000;

            while ((line = userReader.readLine()) != null && userIds.size() < n) {
                if(userCount==0){
                    userCount++;
                    continue;
                }
                String[] userData = line.split(",");

                userIds.add(userData[0]);
                userCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        try (BufferedReader danmuReader = new BufferedReader(new FileReader(danmuFilePath))) {
            String line;

            while ((line = danmuReader.readLine()) != null) {
                String[] danmuData = line.split(",");
                if (danmuData.length > 1) {
                    String userId = danmuData[1];

                    if (userIds.contains(userId)) {
                        totalDanmuCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();


        System.out.println("Total danmus from the first " + userIds.size() + " users: " + totalDanmuCount);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
