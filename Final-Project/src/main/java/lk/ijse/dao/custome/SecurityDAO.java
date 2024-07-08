package lk.ijse.dao.custome;

import java.sql.SQLException;

public interface SecurityDAO {
    String check(String userId) throws SQLException, ClassNotFoundException;

    boolean updatePassword(String newPassword) throws SQLException, ClassNotFoundException;
}
