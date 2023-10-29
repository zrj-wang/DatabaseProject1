import java.io.*;
import java.sql.*;
import org.apache.commons.csv.*;

public class import_danmu_method {
    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser
                            (reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO danmu (danmu_BV, danmu_Mid, time, content) " +
                        "VALUES (?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {
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
                    connection.setAutoCommit(true);
                    System.out.println("数据已成功导入数据库表中.");
                }
            } catch (SQLException ex) {
                System.err.println("数据库操作出错: " + ex.getMessage());
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                System.err.println("未找到CSV文件: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println("读取文件时出错: " + ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}