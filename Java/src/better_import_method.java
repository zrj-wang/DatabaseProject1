import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.concurrent.*;
import org.apache.commons.csv.*;

public class better_import_method {
    private static final int THREAD_COUNT = 6;


    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<CSVRecord> records = csvParser.getRecords();
            int size = ((List<?>) records).size();
            int chunkSize = size / THREAD_COUNT;

            for (int i = 0; i < THREAD_COUNT; i++) {
                final int startIndex = i * chunkSize;
                final int endIndex = (i == THREAD_COUNT - 1) ? size : startIndex + chunkSize;

                executor.submit(() -> insertData(jdbcURL, username, password, records.subList(startIndex, endIndex)));
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData(String jdbcURL, String username, String password, List<CSVRecord> records) {
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO danmu (danmu_BV, danmu_Mid, time, content) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int count = 0;
                for (CSVRecord record : records) {
                    String column1 = record.get(0);
                    String column2 = record.get(1);
                    Float column3 = Float.parseFloat(record.get(2));
                    String column4 = record.get(3);

                    statement.setString(1, column1);
                    statement.setString(2, column2);
                    statement.setFloat(3, column3);
                    statement.setString(4, column4);

                    statement.addBatch();

                    if (count % 300 == 0) {
                        statement.executeBatch();
                    }
                    count++;
                }

                statement.executeBatch();
                connection.commit();
                System.out.println("数据已成功导入数据库表中.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
