package demo.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import task.scheduler.processing.TaskScheduler;
import task.scheduler.tasks.ITask;
import task.scheduler.tasks.Task;
import task.scheduler.tasks.TaskState;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class MainController {
    private static Stage stage;
    private static Scene scene = null;
    private static TaskScheduler taskScheduler;
    private static final LinkedList<ITask> tasks = new LinkedList<>();
    public static final LinkedList<ProgressBar> progress = new LinkedList<>();
    private static final LinkedList<Button> play = new LinkedList<>();
    private static final LinkedList<Button> pause = new LinkedList<>();
    private static final LinkedList<Button> stop = new LinkedList<>();
    private static final LinkedList<Label> states = new LinkedList<>();
    private static final LinkedList<Timeline> statesTimeline = new LinkedList<>();
    private static int space = 0;

    public static void setStage(Stage mainStage) {
        stage = mainStage;
    }

    public static void addTask(ITask task) {
        tasks.add(task);
    }

    public static Scene createScene() throws IOException {
        if (scene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 699, 391);
        }
        return scene;
    }


    @FXML
    private AnchorPane pane;

    @FXML
    public void newTaskButtonOnAction(ActionEvent event) throws IOException {
        TaskController.setMainController(this);
        stage.setScene(TaskController.createScene());
    }

    @FXML
    public void initialize() {
        taskScheduler = new TaskScheduler(TaskController.getSchedulingAlgorithm(), StartController.getMaxTasks());
        setOnGui();
    }

    public void setOnGui() {

        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "demo" + File.separator + "gui" + File.separator + "buttons" + File.separator;
        for (ITask t : tasks) {
            Label label = new Label(t.toString());
            label.setPrefHeight(26);
            label.setPrefWidth(86);
            label.setLayoutX(14);
            label.setLayoutY(36 + space);
            label.setAlignment(Pos.CENTER);
            pane.getChildren().add(label);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(200);
            progressBar.setLayoutX(114);
            progressBar.setLayoutY(40 + space);
            progressBar.setStyle("-fx-accent: green;");
            progress.addLast(progressBar);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
                progressBar.setProgress(t.getProgress());
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            pane.getChildren().add(progressBar);

            Button button1 = new Button();
            button1.setPrefWidth(15);
            button1.setPrefHeight(15);
            button1.setLayoutX(340);
            button1.setLayoutY(37 + space);
            ImageView view1 = new ImageView(new Image(path + "play.png"));
            view1.setFitHeight(15);
            view1.setFitWidth(15);
            button1.setGraphic(view1);
            play.add(button1);
            pane.getChildren().add(button1);

            Button button2 = new Button();
            button2.setPrefWidth(15);
            button2.setPrefHeight(15);
            button2.setLayoutX(389);
            button2.setLayoutY(37 + space);
            ImageView view2 = new ImageView(new Image(path + "pause.png"));
            view2.setFitHeight(15);
            view2.setFitWidth(15);
            button2.setGraphic(view2);
            pause.addLast(button2);
            pane.getChildren().add(button2);

            Button button3 = new Button();
            button3.setPrefWidth(15);
            button3.setPrefHeight(15);
            button3.setLayoutX(436);
            button3.setLayoutY(37 + space);
            ImageView view3 = new ImageView(new Image(path + "stop.png"));
            view3.setFitHeight(15);
            view3.setFitWidth(15);
            button3.setGraphic(view3);
            stop.addLast(button3);
            pane.getChildren().add(button3);

            Label label2 = new Label(t.toString());
            label2.setPrefHeight(26);
            label2.setPrefWidth(150);
            label2.setLayoutX(523);
            label2.setLayoutY(36 + space);
            label2.setAlignment(Pos.CENTER);
            states.addLast(label2);
            pane.getChildren().add(label2);

            Task tmp = null;
            if (t.getImmediateStart()) {
                tmp = taskScheduler.schedule(t, t.getPriority());
            } else {
                tmp = taskScheduler.scheduleWithoutStarting(t, t.getPriority());
            }

            if (tmp != null) {
                label2.setText(tmp.getTaskContext().getTaskState().toString());
                Task finalTmp = tmp;
                button1.setOnAction(event -> {
                    if (finalTmp.getTaskContext().getTaskState().equals(TaskState.NOTSTARTED)) {
                        finalTmp.start(taskScheduler);
                    } else {
                        finalTmp.requestContinue();
                    }
                    label2.setText(finalTmp.getTaskContext().getTaskState().toString());
                });

                button2.setOnAction(event -> {
                    try {
                        finalTmp.requestPause();
                        label2.setText(finalTmp.getTaskContext().getTaskState().toString());
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                });

                button3.setOnAction(event -> {
                    try {
                        finalTmp.stop();
                        label2.setText(finalTmp.getTaskContext().getTaskState().toString());
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                });

                Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(50), event -> {
                    label2.setText(finalTmp.getTaskContext().getTaskState().toString());
                }));
                timeline2.setCycleCount(Animation.INDEFINITE);
                timeline2.play();
                statesTimeline.addLast(timeline2);
            }

            space += 28;
        }
        tasks.clear();
    }
}
