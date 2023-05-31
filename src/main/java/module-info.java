module ai.comp338_project_ {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens ai.comp338_project_1 to javafx.fxml;
    exports ai.comp338_project_1;
}