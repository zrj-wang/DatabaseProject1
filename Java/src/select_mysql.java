import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class select_mysql {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mysql"; // 数据库URL
        String user = "root"; // 数据库用户名
        String password = "a85144492"; // 数据库密码
        String query = "select count(*) as count from danmu where danmu_Mid=?"; // 使用占位符

        try {
            // 加载PostgreSQL数据库驱动
            Class.forName("org.postgresql.Driver");
            // 建立数据库连接
            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = con.prepareStatement(query)) {
                long startTime=System.currentTimeMillis();

                // 设置参数
                pstmt.setString(1, "1703941");

                // 模拟多次SELECT操作
                for (int i = 0; i < 1000000; i++) { // 假设模拟10次
                    try (ResultSet rs = pstmt.executeQuery()) {
                        // 处理查询结果
                        if (rs.next()) {
                            // 获取count(*)的值
                            int count = rs.getInt("count");
                            // 打印结果
                            System.out.println("Count: " + count);
                        }
                    }
                }
                long endTime = System.currentTimeMillis();

                // 计算并打印总时间
                System.out.println("总耗时: " + (endTime - startTime) + "ms");
            }
            // 记录结束时间

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
