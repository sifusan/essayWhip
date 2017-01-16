package essaywhip.main;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import essaywhip.util.ProgressChecker;
import essaywhip.ui.MainScene;
import java.io.File;

/**
 * Created by sifu on 23/12/16.
 */

public class Main extends Application {
    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;
    private File file;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        MainScene scene = new MainScene(new BorderPane(), WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.adjustBarsWidth(WINDOW_WIDTH - 100);
        applyProgressChecker(scene);
        stage.setScene(scene);
        stage.setTitle("Essay Whip");
        stage.show();
    }

    private File selectFile() {
        FileChooser fileChooser = new FileChooser();
        this.file = fileChooser.showOpenDialog(this.stage);
        return this.file;
    }

    private void applyProgressChecker(MainScene scene) {
        scene.getUploadFilebtn().setOnAction(event -> {
            selectFile();
        });

        scene.getShowProgessbtn().setOnAction(e -> {
            ProgressChecker pc = new ProgressChecker(
                    scene.getWordGoalNumberValue(), file);
            pc.startProgressTimer(scene.getProgressBar(), scene.getProgressNumber(), scene.getWordCountNumber());
        });
    }
}