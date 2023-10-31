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
//
        String csvFilePath = "D:/downloadnormal/data/danmu.csv";
//
//        select_method a=new select_method();
//        System.out.println(a.getCountFromCSV2
//                (jdbcURL,username,password,csvFilePath,8000)
//);

//        import_danmu_method b=new import_danmu_method();
//        b.importCsvToDatabase(jdbcURL,username,password,csvFilePath);

//        better_import_method a=new better_import_method();
//        a.importCsvToDatabase(jdbcURL,username,password,csvFilePath);


     //   delete
        delete_method a=new delete_method();
        a.deleteRowsFrom1(jdbcURL,username,password,100000,300000);

//
//        //update
//            update_method u=new update_method();
//            u.replaceNullWithSpecifiedValue(jdbcURL,username,password,
//         "user_information","sign", "project");
//        u.replaceNullWithSpecifiedValue(jdbcURL,username,password,
//                "user_information","birthday", "project");
//

        //insert
//        insert_method d=new insert_method();
//        d.insertCSVToDatabase(csvFilePath,jdbcURL,username,password);


        // 记录结束时间
        long end = System.currentTimeMillis();

        // 计算并打印耗费的时间
        long elapsedTime = end - start;
        System.out.println("总共耗时: " + elapsedTime + " 毫秒");
    }




}