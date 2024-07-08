package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.SecurityDAO;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityDAOImpl implements SecurityDAO {

    @Override
    public String check(String userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT userId, password FROM user WHERE userId = ?",userId);
        if(resultSet.next()){
            return resultSet.getString("password");
        }else {
            return null;
        }
    }
    public boolean updatePassword(String newPassword) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE user SET password = ? WHERE userId = 'U001'",newPassword);
    }
}
