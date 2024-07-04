package lk.ijse.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MachineTm {
    private String MaId;
    private String name;
    private String description;
    private JFXButton btnSave;
}
