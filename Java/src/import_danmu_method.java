import java.io.*;
import java.sql.*;
import org.apache.commons.csv.*;

public class import_danmu_method {
    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO danmu (danmu_id, danmu_BV, danmu_Mid, time, content) " +
                        "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 1;
                    for (CSVRecord record : csvParser) {
                        int column1 = count;
                        String column2 = record.get(0);
                        String column3 = record.get(1);
                        Float column4 = Float.parseFloat(record.get(2));
                        String column5 = record.get(3);


                        statement.setInt(1, column1);
                        statement.setString(2, column2);
                        statement.setString(3, column3);
                        statement.setFloat(4, column4);
                        statement.setString(5, column5);

                        statement.addBatch();

                        if (count % 1000 == 0) {
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