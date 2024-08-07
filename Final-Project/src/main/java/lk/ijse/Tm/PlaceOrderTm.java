package lk.ijse.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceOrderTm {
    private String baId;
    private String description;
    private int  qty;
    private double unitPrice;
    private double total;
    private JFXButton btnRemove;
}
