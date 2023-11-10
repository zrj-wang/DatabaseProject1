import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class select_method {

    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath,  String[] mid) {
        try (
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser
                        (reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            connection.setAutoCommit(false);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < mid.length ; i++) {
                String sql = "select count(*) as count from danmu where danmu_Mid=" + mid[i];
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        try (ResultSet rs = statement.executeQuery()) {//执行select
                            if (rs.next()) {
                                int count = rs.getInt("count");
                                System.out.println("Count: " + count);
                            }
                        }
                }
            }
            long endTime = System.currentTimeMillis();

            System.out.println("总耗时: " + (endTime - startTime) + "ms");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }//可以select多个不同的用户


    public void importCsvToDatabase1(String jdbcURL, String username, String password, String csvFilePath, String mid ,int turn) {
        try (
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser
                        (reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            connection.setAutoCommit(false);
            long startTime = System.currentTimeMillis();
            String sql = "select count(*) as count from danmu where danmu_Mid=" + mid;


                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    for (int i = 0; i < turn ; i++) {
                    try (ResultSet rs = statement.executeQuery()) {//执行select
                        if (rs.next()) {
                            int count = rs.getInt("count");
                            System.out.println("Count: " + count);
                        }
                    }
                }
            }
            long endTime = System.currentTimeMillis();

            System.out.println("总耗时: " + (endTime - startTime) + "ms");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }//测试用单个用户
}


