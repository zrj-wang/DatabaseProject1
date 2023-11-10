import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PostgresStoredProcedureTest {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(100); // 队列的大小

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    queue.put("1703941");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 CallableStatement cstmt = conn.prepareCall("{? = call get_danmu_count(?)}")) {

                // 记录开始时间
                long startTime = System.currentTimeMillis();

                for (int i = 0; i < 100; i++) {
                    String danmuMid = queue.take();
                    cstmt.registerOutParameter(1, Types.INTEGER);
                    cstmt.setString(2, danmuMid);

                    cstmt.execute();

                    int count = cstmt.getInt(1);
                    System.out.println("查询结果: " + count);
                }

                // 记录结束时间
                long endTime = System.currentTimeMillis();

                // 计算并打印总时间
                System.out.println("总耗时: " + (endTime - startTime) + "ms");
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
