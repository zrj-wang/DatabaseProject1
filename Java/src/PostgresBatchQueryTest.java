import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PostgresBatchQueryTest {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    private static final String SQL_QUERY = "SELECT COUNT(*) FROM danmu WHERE danmu_Mid=?";
    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10000); // 队列的大小

    public static void main(String[] args) {

        // 模拟生产者线程，模拟查询请求
        new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                try {
                    queue.put("1703941"); // 将请求放入队列
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 模拟消费者线程，执行查询
        new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY)) {

                // 记录开始时间
                long startTime = System.currentTimeMillis();

                for (int i = 0; i < 10000; i++) {
                    String danmuMid = queue.take(); // 从队列中取出请求
                    pstmt.setString(1, danmuMid);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            int count = rs.getInt(1);
                            System.out.println("查询结果: " + count);
                        }
                    }
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
