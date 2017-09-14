package dao;
import  java.sql.*;

public class ConnectionFactory {
    public static  String jdbcUrl="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
    public static  String jdbcDriver="com.mysql.jdbc.Driver";
    public static  String mysqlUser="root";
    public static  String mysqlPassword="!QAZ2wsx";
    public static String jdbcDriver2 = "com.mysql.jdbc.Driver";
    public static String jdbcURLs = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=!QAZ2wsx&useUnicode=true&characterEncoding=utf-8";
//    用于保存单例的连接对象, 连接对象被保存在threadLocal对象中
    private static final ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();

    public static Connection getMysqlConnetion() throws ClassNotFoundException {
        Connection conn=threadLocal.get();
        if(conn==null){
            try {
                Class.forName(jdbcDriver);
                conn=DriverManager.getConnection(jdbcUrl,mysqlUser,mysqlPassword);
                //conn=DriverManager.getConnection(jdbcURLs);
                if(conn.isClosed()){
                    closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("报错："+e.getMessage());
            }
        }
        return  conn;
    }

    /**
     * 关闭数据库连接
     * @return
     */
    public static void closeConnection(){
        Connection connection = threadLocal.get();
        try {
            if(connection!=null) {
                connection.close();
                threadLocal.set(null);
            }else {
                threadLocal.set(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
