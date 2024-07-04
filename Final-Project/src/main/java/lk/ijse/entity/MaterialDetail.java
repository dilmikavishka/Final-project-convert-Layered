package lk.ijse.entity;

import lk.ijse.db.DbConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDetail {
    private String batId;
    private String matId;
    private int qty;


}
