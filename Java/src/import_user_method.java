import java.io.*;
import java.sql.*;
import org.apache.commons.csv.*;

public class import_user_method {
    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {

                connection.setAutoCommit(false);
                String sql = "INSERT INTO user_information (Mid, Name, Sex, Birthday, Level, Sign, Identity) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {
                        String column1 = record.get(0);
                        String column2 = record.get(1);
                        String column3 = record.get(2).trim();
                        String column4 = record.get(3);
                        int column5 = Integer.parseInt(record.get(4));
                        String column6 = record.get(5);
                        String column7 = record.get(7);

                        statement.setString(1, column1);
                        statement.setString(2, column2);
                        statement.setString(3, column3);
                        statement.setString(4, column4);
                        statement.setInt(5, column5);
                        statement.setString(6, column6);
                        statement.setString(7, column7);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

