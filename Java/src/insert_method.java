import org.apache.commons.csv.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class insert_method {

    public void insertCSVToDatabase(String csvFilePath, String dbURL, String dbUser, String dbPassword) {
        // 插入数据的SQL模板
        String insertSQL = "INSERT INTO danmu (danmu_BV, danmu_Mid, time, content) VALUES (?, ?, ?, ?)";
        try (
                // 以UTF-8编码打开CSV文件
                Reader reader = new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
                // 连接数据库
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

        ) {
            long start = System.currentTimeMillis();
            // 为批量插入准备预编译的SQL语句
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
                // 解析CSV文件，并记录行数
                int rowCount = 0; // 计数器，跟踪处理的行数

                for (CSVRecord record : csvParser) {
                    // 如果已经处理了3000行，就跳出循环
                    if (rowCount == 300) {
                        break;
                    }

                    List<String> cellValues = new ArrayList<>();
                    record.forEach(cell -> cellValues.add(cell.trim()));

                    // 根据CSV记录的列，设置值
                    for (int i = 0; i < cellValues.size(); i++) {
                        if (i == 2) { // 假设“time”是第3列，需要转换成实数
                            float realValue = Float.parseFloat(cellValues.get(i)); // 转换成float
                            preparedStatement.setFloat(i + 1, realValue);
                        } else {
                            preparedStatement.setString(i + 1, cellValues.get(i));
                        }
                    }

                    // 将此记录添加为批处理的一部分
                    preparedStatement.addBatch();

                    rowCount++; // 增加行计数器
                }

                // 执行批处理插入
                preparedStatement.executeBatch();
            }

            // 记录结束时间
            long end = System.currentTimeMillis();

            // 计算并打印耗费的时间
            long elapsedTime = end - start;
            System.out.println("总共耗时: " + elapsedTime + " 毫秒");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void insertCSVToDatabase1(String csvFilePath, String dbURL, String dbUser, String dbPassword) {
        // 插入数据的SQL模板
        String insertSQL = "INSERT INTO danmu (danmu_BV, danmu_Mid, time, content) VALUES (?, ?, ?, ?)";
        try (
                // 以UTF-8编码打开CSV文件
                Reader reader = new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
                // 连接数据库
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

        ) {
            long start = System.currentTimeMillis();
            // 为批量插入准备预编译的SQL语句
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
                // 解析CSV文件，并记录行数
                int rowCount = 0; // 计数器，跟踪处理的行数
                int batchCount = 0; // 批处理中的记录数

                for (CSVRecord record : csvParser) {
                    // 如果已经处理了300行，就跳出循环
                    if (rowCount == 300000) {
                        break;
                    }

                    List<String> cellValues = new ArrayList<>();
                    record.forEach(cell -> cellValues.add(cell.trim()));

                    // 根据CSV记录的列，设置值
                    for (int i = 0; i < cellValues.size(); i++) {
                        if (i == 2) { // 假设“time”是第3列，需要转换成实数
                            float realValue = Float.parseFloat(cellValues.get(i)); // 转换成float
                            preparedStatement.setFloat(i + 1, realValue);
                        } else {
                            preparedStatement.setString(i + 1, cellValues.get(i));
                        }
                    }

                    // 将此记录添加为批处理的一部分
                    preparedStatement.addBatch();
                    batchCount++;

                    // 每5行或者在最后一行记录执行批处理插入
                    if (batchCount == 5 || !csvParser.iterator().hasNext()) {
                        preparedStatement.executeBatch();
                        batchCount = 0; // 重置批处理计数器
                    }

                    rowCount++; // 增加行计数器
                }

                // 处理可能剩余的记录
                if (batchCount > 0) {
                    preparedStatement.executeBatch();
                }
            }

            // 记录结束时间
            long end = System.currentTimeMillis();

            // 计算并打印耗费的时间
            long elapsedTime = end - start;
            System.out.println("总共耗时: " + elapsedTime + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
