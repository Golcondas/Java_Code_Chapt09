package dao;

import model.User;

import java.util.List;
import java.util.Map;

public class UserDAO {
    /**
     * 保存user对象，如果该对象在数据库中不存在，则新建（insert语句）<br>
     * 如果该对象在数据库中已存在，则更新（update语句）
     * @param user
     */
    public void save(User user) {
        String sql;	// 这些代码是针对user表的，
        if (findById(user.getUsrID()) == null) { // 查找该对象
            // 如果不存在，插入新记录
            if(user.getUsrID()==0){		// 如果ID为0，让MySQL数据库自动生成主键
                sql = "insert into user (account, password,fullname) values('"
                        + user.getAccount() + "','"
                        + user.getPassword() + "','"
                        + user.getFullname() + "')";
            }else{					// 如果ID不为0，使用指定的主键
                sql = "insert into user (usrID, account, password,fullname) values("
                        + user.getUsrID() + ",'"
                        + user.getAccount() + "','"
                        + user.getPassword() + "','"
                        + user.getFullname() + "')";
            }
        } else {
            // 如果存在，更新记录
            sql = "update user set "
                    + " account='" + user.getAccount()
                    + "', password='" + user.getPassword()
                    + "', fullname='" + user.getFullname()
                    + "' where usrID=" + user.getUsrID();
        }
        BaseDAO baseDAO = new BaseDAO();
        // 执行SQL语句
        baseDAO.excuteSQL(sql);
    }

    /**
     * 通过usrID的值查找记录，最多只有一条记录。
     * @param usrID
     * @return user
     */
    public User findById(int usrID) {
        String sql = "select * from user where usrID=" + usrID;
        BaseDAO baseDAO = new BaseDAO();
        List<Map<String,String>> list = baseDAO.querySQL(sql); // 返回一个List

        if(list!=null && !list.isEmpty()){
            Map<String,String> map = list.get(0); // 最多只有一个元素，转换为User对象
            return map2User(map);
        }
        return null;
    }

    /*
	 * 将Map表示的user表的一行记录转换为 User对象
	 * 该Map是由BaseDAO.executeQuery返回的List中的元素
	 */
    private User map2User(Map<String,String> map){
        User user = new User();
        user.setUsrID(Integer.parseInt(map.get("usrid"))); // 注意数据类型
        user.setAccount(map.get("account")); // 注意列名全部是小写
        user.setPassword(map.get("password"));
        user.setFullname(map.get("fullname"));
        return user; // 返回User对象
    }
}
