package demo.gui;

import demo.gui.tasks.Criteria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import task.scheduler.algorithms.FifoSchedulingAlgorithm;
import task.scheduler.algorithms.PrioritySchedulingAlgorithm;
import task.scheduler.algorithms.SchedulingAlgorithm;

import java.io.IOException;

public class StartController {
    private static Stage stage;
    private static int maxTasks;
    private SchedulingAlgorithm algorithm;
    public static void setStage(Stage mainStage){
        stage = mainStage;
    }
    public static int getMaxTasks() {
        return maxTasks;
    }
    @FXML private ComboBox<SchedulingAlgorithm> comboBoxAlgorithm;
    @FXML private TextField maxConcurrentTasks;

    @FXML public void initialize(){
        comboBoxAlgorithm.getItems().add(new FifoSchedulingAlgorithm());
        comboBoxAlgorithm.getItems().add(new PrioritySchedulingAlgorithm());
    }
    @FXML public void comboBoxOnAction(ActionEvent event) throws IOException {
        algorithm = comboBoxAlgorithm.getSelectionModel().getSelectedItem();
    }
    @FXML public void nextButtonOnAction(ActionEvent event) throws IOException {
        if(!maxConcurrentTasks.getText().isEmpty() && algorithm != null){
            maxTasks = Integer.parseInt(maxConcurrentTasks.getText());
            TaskController.setSchedulingAlgorithm(algorithm);
            stage.setScene(TaskController.createScene());
        }
    }
}