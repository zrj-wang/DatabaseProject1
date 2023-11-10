import java.io.*;
import java.util.*;

public class select1 {
    public static void main(String[] args) {

        Set<String> videos = new HashSet<>();


        int totalDanmuCount = 0;


        String videoFilePath = "C:\\Users\\24289\\Documents\\Tencent Files\\2428950578\\FileRecv\\videos.csv";
        String danmuFilePath = "D:\\demo\\danmu_input.csv";

        long start = System.currentTimeMillis();


        try (BufferedReader videoReader = new BufferedReader(new FileReader(videoFilePath))) {
            String line;
            int videoCount = 0;
            int n = 30000;

            while ((line = videoReader.readLine()) != null && videos.size() < n) {
                if(videoCount==0){
                    videoCount++;
                    continue;
                }
                String[] videoData = line.split(",");

                if(videoData.length>7) {
                    videos.add(videoData[0]);
                }
                videoCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        try (BufferedReader danmuReader = new BufferedReader(new FileReader(danmuFilePath))) {
            String line;

            while ((line = danmuReader.readLine()) != null) {
                String[] danmuData = line.split(",");
                if (danmuData.length > 1) {
                    String userId = danmuData[0];

                    if (videos.contains(userId)) {
                        totalDanmuCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        long end = System.currentTimeMillis();


        System.out.println("Total danmus from the first " + videos.size() + " videos: " + totalDanmuCount);
        System.out.println("Time: " + (end - start) + " ms");
    }
}
