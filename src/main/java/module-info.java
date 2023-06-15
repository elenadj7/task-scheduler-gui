module demo.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires task.scheduler;
    requires java.desktop;


    opens demo.gui to javafx.fxml;
    exports demo.gui;
}