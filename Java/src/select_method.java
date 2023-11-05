import org.apache.commons.csv.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class select_method {

    public int getCountFromCSV(String jdbcURL, String username, String password, String csvFilePath, int maxRowsToRead) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalCount = 0;

        List<String> ids = new ArrayList<>();
        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) { // Assuming CSV file has a header row
            for (CSVRecord record : csvParser) {
                ids.add(record.get(0)); // Getting the data of the first column
                if (ids.size() >= maxRowsToRead) {
                    break; // Stop reading file after reaching max rows
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return totalCount; // Error handling, avoiding further execution
        }

        // Check if the list of IDs is empty
        if (ids.isEmpty()) {
            return 0; // No IDs to query, returning 0
        }

        // Create a placeholder string for our SQL query
        StringBuilder placeholderBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            placeholderBuilder.append("?");
            if (i < ids.size() - 1) {
                placeholderBuilder.append(",");
            }
        }
        String placeholders = placeholderBuilder.toString();

        try {
            // Load JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create a connection to the database
            connection = DriverManager.getConnection(jdbcURL, username, password);

            // Prepare SQL statement with placeholders for the IN clause
            String sql = String.format("SELECT COUNT(*) AS total FROM danmu WHERE danmu_mid IN (%s)", placeholders);
            statement = connection.prepareStatement(sql);

            // Set the values for the placeholders
            int index = 1;
            for (String id : ids) {
                statement.setString(index++, id);
            }

            // Execute the query
            resultSet = statement.executeQuery();

            // Get the total count from the result set
            if (resultSet.next()) {
                totalCount = resultSet.getInt("total");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources to avoid memory leaks
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalCount; // Return the total count
    }




    public int getCountFromCSV1(String jdbcURL, String username, String password, String csvFilePath, int maxRowsToRead) {
        //这个是累加计数的？？？
        int maxRowsToQuery=maxRowsToRead;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        List<String> ids = new ArrayList<>();
        int currentRow = 0;

        // 直接在此处处理CSV文件
        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) { // 假设CSV文件有标题行
            for (CSVRecord record : csvParser) {
                if (currentRow >= maxRowsToRead) {
                    break; // 如果达到最大行数，停止读取
                }
                ids.add(record.get(0)); // 获取第一列的数据
                currentRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return totalCount;  // 在这里处理错误情况，避免后续代码执行
        }

        try {
            // 加载PostgreSQL JDBC驱动程序
            Class.forName("org.postgresql.Driver");

            // 连接数据库
            connection = DriverManager.getConnection(jdbcURL, username, password);
//            long start = System.currentTimeMillis();


            // 为查询操作准备SQL命令
            String sql = "SELECT count(*) AS total FROM danmu WHERE danmu_mid = ? LIMIT ?";
            statement = connection.prepareStatement(sql);

            // 对于每个ID执行查询并累加计数
            int rowsQueried = 0;
            for (String id : ids) {
                if (rowsQueried >= maxRowsToQuery) {
                    break;  // 如果查询的行数达到最大值，则停止查询
                }
                statement.setString(1, id);
                statement.setInt(2, maxRowsToQuery - rowsQueried);  // 保证不超过最大查询行数

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt("total");
                    totalCount += count;
                }
                // 需要关闭resultSet，避免资源耗尽
                resultSet.close();
                rowsQueried++;
            }
//            long end = System.currentTimeMillis();
//
//            // 计算并打印耗费的时间
//            long elapsedTime = end - start;
//            System.out.println("总共耗时: " + elapsedTime + " 毫秒");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close(); // 关闭数据库连接
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalCount;
    }


// video
    public void getCountFromCSV2(String jdbcURL, String username, String password, String csvFilePath,int countmax) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalCount = 0;

        List<String> bvs = new ArrayList<>();
        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) { // Assuming CSV file has a header row
            int count = 0;
            for (CSVRecord record : csvParser) {
                if (count >= countmax) {
                    break;
                }
                bvs.add(record.get("bv")); // Assuming 'bv' is the column name in the CSV
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(totalCount); // Error handling, avoiding further execution
        }

        // Check if the list of BVs is empty
        if (bvs.isEmpty()) {
            System. out.print(0); // No BVs to query, returning 0
        }

        // Create a placeholder string for our SQL query
        StringBuilder placeholderBuilder = new StringBuilder();
        for (int i = 0; i < bvs.size(); i++) {
            placeholderBuilder.append("?");
            if (i < bvs.size() - 1) {
                placeholderBuilder.append(",");
            }
        }
        String placeholders = placeholderBuilder.toString();

        try {
            // Load JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create a connection to the database
            connection = DriverManager.getConnection(jdbcURL, username, password);
            long start = System.currentTimeMillis();


            // Prepare SQL statement with placeholders for the IN clause
            String sql = String.format("SELECT COUNT(*) AS total FROM danmu WHERE danmu_BV IN (%s)", placeholders);
            statement = connection.prepareStatement(sql);

            // Set the values for the placeholders
            int index = 1;
            for (String bv : bvs) {
                statement.setString(index++, bv);
            }

            // Execute the query
            resultSet = statement.executeQuery();

            // Get the total count from the result set
            if (resultSet.next()) {
                totalCount = resultSet.getInt("total");
            }
            long end = System.currentTimeMillis();
            // 计算并打印耗费的时间
            long elapsedTime = end - start;
            System.out.println("总共耗时: " + elapsedTime + " 毫秒");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources to avoid memory leaks
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println(totalCount); // Return the total count
    }



//user
    public void getCountFromCSV3(String jdbcURL, String username, String password, String csvFilePath, int maxRowsToRead) {
        //比较快的、
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        List<String> ids = new ArrayList<>();
        int currentRow = 0;

        // 直接在此处处理CSV文件
        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                if (currentRow >= maxRowsToRead) {
                    break; // 如果达到最大行数，停止读取
                }
                ids.add(record.get(0)); // 获取第一列的数据
                currentRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(totalCount); // 在这里处理错误情况，避免后续代码执行
        }

        // 构建SQL中的IN子句
        String inClause = String.join(",", Collections.nCopies(ids.size(), "?"));

        try {
            // 加载PostgreSQL JDBC驱动程序
            Class.forName("org.postgresql.Driver");

            // 连接数据库
            connection = DriverManager.getConnection(jdbcURL, username, password);
            long start = System.currentTimeMillis();
            // 为查询操作准备SQL命令
            String sql = "SELECT count(*) AS total FROM danmu WHERE danmu_mid IN (" + inClause + ")";
            statement = connection.prepareStatement(sql);

            // 将所有ID设置到查询中
            int index = 1;
            for (String id : ids) {
                statement.setString(index++, id);
            }

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalCount = resultSet.getInt("total");
            }
            long end = System.currentTimeMillis();
            // 计算并打印耗费的时间
            long elapsedTime = end - start;
            System.out.println("总共耗时: " + elapsedTime + " 毫秒");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close(); // 关闭数据库连接
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(totalCount);
    }






}








