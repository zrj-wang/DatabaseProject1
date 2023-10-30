import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class update_method {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/yourdatabase";
    private static final String USER = "yourusername";
    private static final String PASS = "yourpassword";

    public  void replaceNullWithSpecifiedValue(String j,String u,String p,String tableName, String columnName, String replaceValue) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // 加载PostgreSQL JDBC驱动程序
            Class.forName("org.postgresql.Driver");

            // 建立连接
            connection = DriverManager.getConnection(j,u,p);

            // 构建SQL语句
            String updateSQL = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE " + columnName + "= '' ";

            pstmt = connection.prepareStatement(updateSQL);
            pstmt.setString(1, replaceValue);

            int updatedRows = pstmt.executeUpdate();
            System.out.println("更新了 " + updatedRows + " 行.");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
