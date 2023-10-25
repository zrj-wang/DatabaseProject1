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

//    public static void main(String[] args) {
//        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
//        String username = "postgres";
//        String password = "";
//        String csvFilePath = "D:/downloadnormal/videos.csv";
//
//        CsvToDatabaseImporter importer = new CsvToDatabaseImporter();
//        importer.importCsvToDatabase(jdbcURL, username, password, csvFilePath);
//    }
}


//以下为原来的代码，直接放在main里面就可以用



//
//    try {
//            String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
//            String username = "postgres";
//            String password = "";
//
//
//            String csvFilePath = "D:/downloadnormal/videos.csv";
//
//            try (
//            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
////                    BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
//            Reader reader = new FileReader(csvFilePath);
//            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
//            )
//            {
//            // 跳过表头
//            String lineText;
//
//
//            // 开始
//            connection.setAutoCommit(false);
//
//            String sql = "INSERT INTO videos (BV, title, owner_Mid,commit_time,review_time,public_time,duration,description,reviewer) " +
//            "VALUES (?, ?, ?,?, ?, ?,?,?,?)";
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 适应您的时间格式
//
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            int count=0;
////                    while ((lineText = lineReader.readLine()) != null) {
//            for (CSVRecord record : csvParser) {
////                        String[] data = lineText.split(",");
////                        // 根据您的表结构，可能需要调整数据类型
////                        String column1 = data[0];
////                        String column2 = data[1];
////                        String column3 = data[2];
////                        String column4 = data[3];
////                        int column5 =  Integer.parseInt(data[4]);
////                        String column6 = data[5];
////                        String column7 = data[7];
//
//            String column1 = record.get(0);
//            String column2 = record.get(1);
//            String column3 = record.get(2);
//
//            String column4 = record.get(4);
//            String column5 = record.get(5);
//            String column6 = record.get(6);
//
//            int column7 =Integer.parseInt(record.get(7)) ;
//            String column8 = record.get(8);
//            String column9 = record.get(9);
//
//            Timestamp timestamp3 = null;
//            Timestamp timestamp4 = null;
//            Timestamp timestamp5 = null;
//
////                        try {
////                            Date parsedDate4 = (Date) dateFormat.parse(column4);
////                            timestamp3 = new Timestamp(parsedDate4.getTime());
////
////                            Date parsedDate5 = (Date) dateFormat.parse(column5);
////                            timestamp4 = new Timestamp(parsedDate4.getTime());
////
////                            Date parsedDate6 = (Date) dateFormat.parse(column6);
////                            timestamp5 = new Timestamp(parsedDate5.getTime());
////                        } catch (ParseException e) {
////                            System.err.println("解析日期时出错: " + e.getMessage());
////                            // 如果日期解析失败，您可能需要决定如何处理。这里我们只是简单地打印错误信息。
////                            // 您可以选择跳过这条记录，或设置一个默认值，或采取其他任何适当的行动。
////                        }
//
//            java.util.Date utilDate4 = dateFormat.parse(record.get(4)); // 调整索引，如果需要的话
//            // 创建一个新的Timestamp对象，基于java.util.Date
//            timestamp3 = new Timestamp(utilDate4.getTime());
//
//            java.util.Date utilDate5 = dateFormat.parse(record.get(5)); // 调整索引，如果需要的话
//            timestamp4 = new Timestamp(utilDate5.getTime());
//
//            java.util.Date utilDate6 = dateFormat.parse(record.get(6)); // 调整索引，如果需要的话
//            timestamp5 = new Timestamp(utilDate6.getTime());
//
//
//
//            statement.setString(1, column1);
//            statement.setString(2, column2);
//            statement.setString(3, column3);
//
//            statement.setTimestamp(4, timestamp3);
//            statement.setTimestamp(5, timestamp4);
//            statement.setTimestamp(6, timestamp5);
//
//            statement.setInt(7, column7);
//            statement.setString(8, column8);
//            statement.setString(9, column9);
//
//            statement.addBatch();
//
//            // 可以设置每多少条记录执行一次批处理，这里假设是1000
//            if (count % 1000 == 0) {
//            statement.executeBatch();
//            }
//            count++;
//            }
//
//            // 执行剩余的批处理
//            statement.executeBatch();
//
//            // 提交事务
//            connection.commit();
//            connection.setAutoCommit(true);
//            System.out.println("数据已成功导入数据库表中.");
//            }
//            } catch (SQLException ex) {
//            System.err.println("数据库操作出错: " + ex.getMessage());
//            ex.printStackTrace();
//            } catch (FileNotFoundException ex) {
//            System.err.println("未找到CSV文件: " + ex.getMessage());
//            } catch (IOException ex) {
//            System.err.println("读取文件时出错: " + ex.getMessage());
//            }
//
//
//            }catch (Exception e) {
//            e.printStackTrace(); // 这会打印错误的堆栈跟踪，帮助诊断问题
//            }
//// 数据库URL，用户名，密码，CSV文件路径
//
