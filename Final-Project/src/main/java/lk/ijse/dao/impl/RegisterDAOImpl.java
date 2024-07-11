package lk.ijse.dao.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custome.RegisterDAO;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDAOImpl implements RegisterDAO {
    @Override
    public boolean saveUser(String userId, String name, String password) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?)",userId,name,password);
    }
}
