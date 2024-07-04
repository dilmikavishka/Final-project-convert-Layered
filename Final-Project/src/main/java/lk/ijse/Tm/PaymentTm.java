package lk.ijse.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTm {
    private String paymentId;
    private Date paymentDate;
    private double  amount;
    private String type;
    private String oId;

}
