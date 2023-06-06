package demo.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 302, 358);
        StartController.setStage(stage);
        TaskController.setStage(stage);
        MainController.setStage(stage);
        stage.setTitle("Task Scheduler");
        stage.getIcons().add(new Image(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "demo" + File.separator + "gui" + File.separator + "icons" + File.separator + "icon.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}