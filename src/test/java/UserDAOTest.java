import dao.UserDAO;
import model.User;
import org.junit.Test;

import java.text.MessageFormat;

public class UserDAOTest {
    @Test
    public void test_GetUserByID() throws Exception {
        UserDAO userDAO=new UserDAO();
        User userModel= userDAO.findById(2);
         System.out.println(MessageFormat.format("id={0} user的值 account:{1} password:{2}",
                userModel.getUsrID(),userModel.getAccount(),userModel.getPassword()));
    }
}
