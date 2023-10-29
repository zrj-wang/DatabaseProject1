import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class delete_method {


    public  void deleteRowsFrom(String jdbcURL,String username,String password,int startingId, int numOfRowsToDelete) {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        ResultSet resultSet = null;

        try {
            // 加载PostgreSQL JDBC驱动程序
            Class.forName("org.postgresql.Driver");

            // 连接数据库
            connection = DriverManager.getConnection(jdbcURL,username,password);
            connection.setAutoCommit(false); // 对于性能很重要

            // 查询ID的SQL命令，我们从特定ID开始
            String selectSql = "SELECT danmu_id FROM danmu WHERE danmu_id >= ? ORDER BY danmu_id";
            selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, startingId);

            resultSet = selectStatement.executeQuery();

            // 存储检索到的ID
            List<Integer> ids = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("danmu_id"));
            }

            // 关闭查询所用的资源
            resultSet.close();
            selectStatement.close();

            // 为删除操作准备SQL命令
            String deleteSql = "DELETE FROM danmu WHERE danmu_id = ?";
            deleteStatement = connection.prepareStatement(deleteSql);

            // 逐一删除行
            int count = 0;
            for (Integer id : ids) {
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();

                count++;

                // 如果达到指定的删除行数，停止删除
                if (count >= numOfRowsToDelete) {
                    break;
                }

                if (count % 300 == 0) {
                    connection.commit();
                }
            }
            // 如果有未提交的更改，请确保在循环结束时提交
            connection.commit();

            System.out.println(count + " 行数据已被删除!");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // 在出现错误时回滚事务
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (deleteStatement != null) {
                    deleteStatement.close();
                }
                if (connection != null) {
                    connection.close(); // 关闭数据库连接
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
