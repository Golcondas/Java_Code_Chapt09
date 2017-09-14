package dao;

import java.sql.*;
import java.util.*;

public class BaseDAO {
    /**
     * 执行无返回值的SQL语句
     *
     * @param strSQL 要执行的SQL语句
     * @return int 操作条数
     */
    public int excuteSQL(String strSQL) {
        System.out.println(strSQL);
        int updateRow = 0;
        Statement state;
        try {
            state = ConnectionFactory.getMysqlConnetion().createStatement();
            updateRow = state.executeUpdate(strSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection();//关闭数据库连接
        }
        return updateRow;
    }

    /**
     * 查询
     * @param strSQL 要执行的查询SQL语句
     * @return
     */
    public List<Map<String,String>> querySQL(String strSQL){
        System.out.println(strSQL);
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        try {
            Statement state = ConnectionFactory.getMysqlConnetion().createStatement();
            ResultSet rset = state.executeQuery(strSQL);
            ResultSetMetaData metadata = rset.getMetaData();
            while (rset.next()) {
                Map<String, String> map = new HashMap<String, String>();
                //将每一列转换为一个  字段名-值 的键值对，字段名全部转换为大写，值全部转换为字符串
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    map.put(metadata.getColumnName(i).toLowerCase(), rset.getString(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return list;
    }
}
