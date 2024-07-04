package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDTO {
    private String id;
    private String name;
    private Date date;
    private int matQty;
    private String supId;
    private double price;

    public MaterialDTO(String materialId, String matName, double price, int qtyOnHand) {
    }
}
