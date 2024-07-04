package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchDTO {
    private String BatchId;
    private String BatchColor;
    private String Des;
    private int qtyOnHand;
    private Date date;
    private String EmployeeId;
    private String OrderId;
    private double Price;
}
