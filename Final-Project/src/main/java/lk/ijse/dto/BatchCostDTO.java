package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchCostDTO {
    private List<MaterialDetailDTO> bcList;

    public BatchCostDTO(MaterialDTO material, List<MaterialDetailDTO> bcList) {
    }
}
