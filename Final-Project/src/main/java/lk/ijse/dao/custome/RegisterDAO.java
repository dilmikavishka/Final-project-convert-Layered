package lk.ijse.dao.custome;

import java.sql.SQLException;

public interface RegisterDAO {
    boolean saveUser(String userId, String name, String password) throws SQLException, ClassNotFoundException;
}
