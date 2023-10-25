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
//        String csvFilePath = "D:/downloadnormal/users.csv";
//
//        CsvToDatabaseUserImporter importer = new CsvToDatabaseUserImporter();
//        importer.importCsvToDatabase(jdbcURL, username, password, csvFilePath);
//    }
}



//以下为原来的方法，可以直接用；

//    public static void import_user(){
//
//        try {
//            String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
//            String username = "postgres";
//            String password = "";
//
//
//            String csvFilePath = "D:/downloadnormal/users.csv";
//
//            try (
//                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
////                    BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
//                    Reader reader = new FileReader(csvFilePath);
//                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
//            )
//            {
//                // 跳过表头
//                String lineText;
//
//
//                // 开始
//                connection.setAutoCommit(false);
//
//                String sql = "INSERT INTO user_information (Mid, Name, Sex,Birthday,Level,Sign,Identity) " +
//                        "VALUES (?, ?, ?,?, ?, ?,?)";
//                try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                    int count=0;
////                    while ((lineText = lineReader.readLine()) != null) {
//                    for (CSVRecord record : csvParser) {
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
//                        String column1 = record.get(0);
//                        String column2 = record.get(1);
//                        String column3 = record.get(2).trim();
//                        String column4 = record.get(3);
//                        int column5 = Integer.parseInt(record.get(4));
//                        String column6 = record.get(5);
//                        String column7 = record.get(7);
//
//
//                        statement.setString(1, column1);
//                        statement.setString(2, column2);
//                        statement.setString(3, column3);
//                        statement.setString(4, column4);
//                        statement.setInt(5, column5);
//                        statement.setString(6, column6);
//                        statement.setString(7, column7);
//
//                        statement.addBatch();
//
//                        // 可以设置每多少条记录执行一次批处理，这里假设是1000
//                        if (count % 1000 == 0) {
//                            statement.executeBatch();
//                        }
//                        count++;
//                    }
//
//                    // 执行剩余的批处理
//                    statement.executeBatch();
//
//                    // 提交事务
//                    connection.commit();
//                    connection.setAutoCommit(true);
//                    System.out.println("数据已成功导入数据库表中.");
//                }
//            } catch (SQLException ex) {
//                System.err.println("数据库操作出错: " + ex.getMessage());
//                ex.printStackTrace();
//            } catch (FileNotFoundException ex) {
//                System.err.println("未找到CSV文件: " + ex.getMessage());
//            } catch (IOException ex) {
//                System.err.println("读取文件时出错: " + ex.getMessage());
//            }
//
//
//        }catch (Exception e) {
//            e.printStackTrace(); // 这会打印错误的堆栈跟踪，帮助诊断问题
//        }
//        // 数据库URL，用户名，密码，CSV文件路径
//
//
//    }