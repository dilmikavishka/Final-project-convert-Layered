package lk.ijse.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTm {
    private String oId;
    private Date date;
    private String CusId;

}
