import java.io.*;
import java.sql.*;
import org.apache.commons.csv.*;

public class import_following_method {
    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO following (user_Mid, follow_Mid) " +
                        "VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {

                        String column1 = record.get(0);
                        String column2 = record.get(6); // 第二列的原始字符串

                        String[] values = column2.replaceAll("\\[|\\]", "").split(",\\s*");

                        for (String value : values) {

                            String cleanedValue = value.replace("'", "").trim();
                            if (cleanedValue.isEmpty()) {
                                // 如果值为空，则跳过这条记录
                                continue;
                            }
                            statement.setString(1, column1); // 注意这里变为1，因为我们不再设置follow_id
                            statement.setString(2, cleanedValue); // 这里变为2
                            statement.addBatch();
                            count++;
                        }

                        statement.addBatch();

                        if (count % 1000 == 0) {
                            statement.executeBatch();
                        }
                    }
                    statement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    System.out.println("数据已成功导入数据库表中.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}