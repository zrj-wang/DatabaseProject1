import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class delete_method {

    public void deleteRowsFrom(String jdbcURL, String username, String password, int startingId, int numOfRowsToDelete) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                   ) {
                connection.setAutoCommit(false);
                String sql = "DELETE FROM danmu WHERE danmu_id BETWEEN "+startingId +" AND "+(startingId+numOfRowsToDelete);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                    connection.commit();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
