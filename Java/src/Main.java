import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.*;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
<<<<<<< HEAD
    long begin=System.currentTimeMillis();
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "James6112";
//
        String csvFilePath = "D:\\data\\danmu.csv";
//
//        select_method a=new select_method();
//       a.getCountFromCSV2
//                (jdbcURL,username,password,csvFilePath,30000);



        import_danmu_method b=new import_danmu_method();
        b.importCsvToDatabase(jdbcURL,username,password,csvFilePath);
        long end=System.currentTimeMillis();
        System.out.print(end-begin );
=======
        long st=System.currentTimeMillis();
//
//        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
//        String username = "postgres";
//        String password = "";
//        String csvFilePath = "D:/downloadnormal/data/videos1.csv";

        String jdbcURL = "jdbc:mysql://localhost:3306/mysql";
        String username = "root";
        String password = "a85144492";
        String csvFilePath = "D:/downloadnormal/data/danmu.csv";

//

//        select_method a=new select_method();
//       a.getCountFromCSV2
//                (jdbcURL,username,password,csvFilePath,10000);



//        import_mysql a=new import_mysql();
//        a.importCsvToDatabase(jdbcURL,username,password,csvFilePath);

//        import_user_method b=new import_user_method();
//        b.importCsvToDatabase(jdbcURL,username,password,csvFilePath);
>>>>>>> 263630ee9c7dda0e7e5e08004ded3ad17e7aded1

//        import_videos_method b=new import_videos_method();
//        b.importCsvToDatabase(jdbcURL,username,password,csvFilePath);

//        better2_import_method a=new better2_import_method();
//        a.importCsvToDatabase(jdbcURL,username,password,csvFilePath);
//

      //  delete
//        delete_method a=new delete_method();
//        a.deleteRowsFrom1(jdbcURL,username,password,1200000,300);
//
//
        //update
//            update_method u=new update_method();
//            u.replaceNullWithSpecifiedValue(jdbcURL,username,password,
//         "user_information2","sign", "project");
//        u.replaceNullWithSpecifiedValue(jdbcURL,username,password,
//                "user_information2","birthday", "project");
//

//      //  insert
//        insert_method d=new insert_method();
//        d.insertCSVToDatabase1(csvFilePath,jdbcURL,username,password);


    long en= System.currentTimeMillis();
    System.out.print(en-st);
    }




}