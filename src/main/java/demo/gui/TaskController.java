package demo.gui;

import demo.gui.tasks.Criteria;
import demo.gui.tasks.ImageSorter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import task.scheduler.algorithms.FifoSchedulingAlgorithm;
import task.scheduler.algorithms.SchedulingAlgorithm;
import task.scheduler.tasks.ITask;

import java.io.File;
import java.io.IOException;

public class TaskController {
    private static Stage stage;
    private static MainController mainController;
    private static File imageFile = null;
    private static Criteria criteria = null;
    private static SchedulingAlgorithm schedulingAlgorithm;
    public static void setStage(Stage mainStage){
        stage = mainStage;
    }
    public static void setSchedulingAlgorithm(SchedulingAlgorithm algorithm){
        schedulingAlgorithm = algorithm;
    }
    public static SchedulingAlgorithm getSchedulingAlgorithm() {
        return schedulingAlgorithm;
    }
    public static Scene createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("task-view.fxml"));
        return new Scene(fxmlLoader.load(), 429, 585);
    }
    public static void setMainController(MainController controller){
        mainController = controller;
    }
    @FXML private TextField taskNameTextField;
    @FXML private TextField priorityTextField;
    @FXML private CheckBox immediateStart;
    @FXML private Label priorityLabel;
    @FXML private ComboBox<String> taskTypeComboBox;
    @FXML private ComboBox<Criteria> criteriaComboBox;
    @FXML private CheckBox columnsCheckBox;
    @FXML private Button chooseImageButton;
    @FXML private ListView<ITask> tasksListView;

    @FXML public void nextButtonOnAction(ActionEvent event) throws IOException {
        if(!tasksListView.getItems().isEmpty()){
            if(mainController != null){
                mainController.setOnGui();
            }
            stage.setScene(MainController.createScene());
        }
    }
    @FXML public void addButtonOnAction(ActionEvent event) throws IOException {
        int priority = 0;
        if(!priorityTextField.getText().isEmpty()){
            priority = Integer.parseInt(priorityTextField.getText());
        }
        String name = taskNameTextField.getText();
        boolean start = immediateStart.isSelected();
        boolean column = columnsCheckBox.isSelected();
        if(imageFile != null && criteria != null){
            ImageSorter imageSorter = new ImageSorter(imageFile.getAbsolutePath(), imageFile.getAbsoluteFile().getParent(), column, criteria, name);
            tasksListView.getItems().add(imageSorter);
            imageSorter.setPriority(priority);
            imageSorter.setImmediateStart(immediateStart.isSelected());
            MainController.addTask(imageSorter);
        }
    }
    @FXML public void criteriaComboBoxOnAction(ActionEvent event){
        criteria = criteriaComboBox.getSelectionModel().getSelectedItem();
    }
    @FXML public void chooseImageOnAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select file");
        imageFile = fileChooser.showOpenDialog(stage);
        if(imageFile != null){
            chooseImageButton.setText(imageFile.getName());
        }
    }
    @FXML public void taskTypeOnAction(ActionEvent event){
        String selected = taskTypeComboBox.getSelectionModel().getSelectedItem();
        if(selected.equals("ImageSorter")){
            criteriaComboBox.setVisible(true);
            columnsCheckBox.setVisible(true);
            chooseImageButton.setVisible(true);
        }
    }
    @FXML public void initialize(){
        if(schedulingAlgorithm instanceof FifoSchedulingAlgorithm fifoSchedulingAlgorithm){
            priorityLabel.setVisible(false);
            priorityTextField.setVisible(false);
        }
        taskTypeComboBox.getItems().add("ImageSorter");
        criteriaComboBox.getItems().add(Criteria.RED);
        criteriaComboBox.getItems().add(Criteria.BLUE);
        criteriaComboBox.getItems().add(Criteria.GREEN);
        criteriaComboBox.setVisible(false);
        columnsCheckBox.setVisible(false);
        chooseImageButton.setVisible(false);
    }
}
