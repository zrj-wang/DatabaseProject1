import java.io.*;
import java.sql.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

class import_videos_method {
    public void importCsvToDatabase(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO videos (BV, title, owner_Mid, commit_time, review_time, public_time, duration, description, reviewer) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {
                        String column1 = record.get(0);
                        String column2 = record.get(1);
                        String column3 = record.get(2);
                        String column4 = record.get(4);
                        String column5 = record.get(5);
                        String column6 = record.get(6);
                        int column7 = Integer.parseInt(record.get(7));
                        String column8 = record.get(8);
                        String column9 = record.get(9);

                        Timestamp timestamp3 = new Timestamp(dateFormat.parse(column4).getTime());
                        Timestamp timestamp4 = new Timestamp(dateFormat.parse(column5).getTime());
                        Timestamp timestamp5 = new Timestamp(dateFormat.parse(column6).getTime());

                        statement.setString(1, column1);
                        statement.setString(2, column2);
                        statement.setString(3, column3);
                        statement.setTimestamp(4, timestamp3);
                        statement.setTimestamp(5, timestamp4);
                        statement.setTimestamp(6, timestamp5);
                        statement.setInt(7, column7);
                        statement.setString(8, column8);
                        statement.setString(9, column9);


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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}