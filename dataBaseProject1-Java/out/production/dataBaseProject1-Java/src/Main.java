import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.*;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();
            String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "";

            String csvFilePath = "D:/downloadnormal/users.csv";
import_following_method b=new import_following_method();
b.importCsvToDatabase(jdbcURL,username,password,csvFilePath);


        // 记录结束时间
        long end = System.currentTimeMillis();

        // 计算并打印耗费的时间
        long elapsedTime = end - start;
        System.out.println("总共耗时: " + elapsedTime + " 毫秒");
    }




}
