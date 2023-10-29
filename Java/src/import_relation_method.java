import java.io.*;
import java.sql.*;
import org.apache.commons.csv.*;

public class import_relation_method {
    public void import_like(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO like_relation (video_like_id,user_like_Mid ) " +
                        "VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {

                        String column1 = record.get(0);
                        String column2 = record.get(10); // 第二列的原始字符串

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



    public void import_coin(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO coin_relation (video_coin_id,user_coin_Mid ) " +
                        "VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {

                        String column1 = record.get(0);
                        String column2 = record.get(11); // 第二列的原始字符串

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




    public void import_favorite(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO favorite_relation (video_favorite_id,user_favorite_Mid ) " +
                        "VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {

                        String column1 = record.get(0);
                        String column2 = record.get(12); // 第二列的原始字符串

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


    public void import_view(String jdbcURL, String username, String password, String csvFilePath) {
        try {
            try (
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                    Reader reader = new FileReader(csvFilePath);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO view_relation (video_view_id,user_view_Mid ,view_time) " +
                        "VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int count = 0;
                    for (CSVRecord record : csvParser) {

                        String column1 = record.get(0);
                        String column2 = record.get(13); // 第二列的原始字符串

                        column2 = column2.trim();
                        if (column2.startsWith("(")) {
                            // 去掉括号和引号
                            String cleanedValue = column2.substring(1, column2.length() - 1).replace("'", "").trim();
                            String[] parts = cleanedValue.split(",\\s*");

                            if (parts.length == 2) {
                                // 现在parts[0]是左边的数，parts[1]是右边的数
                                statement.setString(1, column1);
                                statement.setString(2, parts[0]);
                                statement.setString(3, parts[1]);

                                statement.addBatch();
                                count++;

                                if (count % 1000 == 0) {
                                    statement.executeBatch();
                                }
                            }
                        }

//
//                        String[] values = column2.replaceAll("\\[|\\]", "").split(",\\s*");
//
//                        for (String value : values) {
//
//                            String cleanedValue = value.replace("'", "").trim();
//                            if (cleanedValue.isEmpty()) {
//                                // 如果值为空，则跳过这条记录
//                                continue;
//                            }
//                            statement.setString(1, column1); // 注意这里变为1，因为我们不再设置follow_id
//                            statement.setString(2, cleanedValue); // 这里变为2
//                            statement.addBatch();
//                            count++;
//                        }
//
//                        statement.addBatch();
//
//                        if (count % 1000 == 0) {
//                            statement.executeBatch();
//                        }
//
//
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