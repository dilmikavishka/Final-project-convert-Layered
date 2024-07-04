package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDTO {
    private String SupId;
    private String name;
    private Date date;
    private String tel;;
    private String payId;


}
