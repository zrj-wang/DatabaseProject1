import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class update_method {

    public void importCsvToDatabase(String jdbcURL, String username, String password,String tableName, String columnName, String replaceValue) {
        try (
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        ) {
            connection.setAutoCommit(false);
            long startTime = System.currentTimeMillis();
            String updateSQL = "UPDATE " + tableName + " SET " + columnName + " = "+replaceValue+" WHERE " + columnName + "='' ";
            PreparedStatement statement =connection.prepareStatement(updateSQL);
            statement.executeUpdate();
            long endTime = System.currentTimeMillis();
            System.out.println("总耗时: " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
