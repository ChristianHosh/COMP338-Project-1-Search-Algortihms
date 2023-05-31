package ai.comp338_project_1;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField textfield_AS_time;

    @FXML
    private TextField textfield_UCS_time;

    @FXML
    public void initialize(){
        textfield_AS_time.setText("21 ms");
        textfield_UCS_time.setText("35 ms");
    }
}
