package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import java.io.File;
import java.io.*;

public class Main extends Application {
    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;
    private javafx.scene.shape.Line line;
    private Stage stage = new Stage();
    private Label wordCountLabel =  new Label();
    private Label wordGoalLabel = new Label();
    private Label progressLabel = new Label();
    private File file;
    private ProgressBar progressBar;
    private ProgressBar timeBar;
    int wordGoal = 0;
    double progress = 0;

    @Override
    public void start(Stage stage) throws Exception {
        stage = this.stage;

        BorderPane root = new BorderPane();

        this.progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(WINDOW_WIDTH - 100);
        root.setCenter(progressBar);

        Button setGoalsBtn = new Button("Enter goals");
        setGoalsBtn.setOnAction(e-> {
            showGoalsPopup();
        });

        Button uploadBtn = new Button("Upload File");
        uploadBtn.setOnAction(e-> {
            this.file = uploadFile();
        });

        Button progressBtn = new Button("Show Progress");
        progressBtn.setOnAction(e-> {
            try {
                progressBar.setProgress(getProgress(this.file));
                startTimer();
            } catch (IOException e1) {

            }
        });

        this.line = new Line();

        root.getChildren().add(line);

        VBox boxOfButtons = new VBox(10);
        boxOfButtons.setAlignment(Pos.CENTER);
        boxOfButtons.getChildren().addAll(setGoalsBtn, uploadBtn, progressBtn );
        root.setBottom(boxOfButtons);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                line.setStartX((double)(newValue)/2);
                line.setEndX((double)(newValue)/2);
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                line.setStartY((double)(newValue)/2 + 5);
                line.setEndY((double)(newValue)/2 - 5);
            }
        });

        root.setTop(buildLabels());

        stage.setScene(scene);
        stage.setTitle("Essay Whip");
        stage.show();
    }

    public void startTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    progressBar.setProgress(getProgress(file));
                } catch (IOException exception) {

                }
            }
        };
        timer.start();
    }

    public HBox buildLabels() {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(this.wordGoalLabel, this.wordCountLabel, this.progressLabel);
        vBox2.getChildren().addAll(new Label(
                "Word Goal"), new Label("Word Count"), new Label("Progress"));
        hBox.getChildren().addAll(vBox2, vBox1);
        return hBox;
    }

    public File uploadFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(this.stage);
        return file;
    }

    public void showGoalsPopup() {
        Stage goalsStage = new Stage();

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        Label wordLabel = new Label("Enter amount of words");
        TextField wordCount = new TextField();
        Button done = new Button("Done");
        done.setOnAction(e-> {
            try {
                ;
                this.wordGoal = Integer.parseInt(wordCount.getText());
                this.wordGoalLabel.setText(String.valueOf(this.wordGoal));
                goalsStage.close();
            } catch (NullPointerException exception) {
            } catch (NumberFormatException exception) {
            }
        });

        vBox.getChildren().addAll(wordLabel, wordCount, done);

        BorderPane bp = new BorderPane();
        bp.setCenter(vBox);
        goalsStage.setScene(new Scene(bp, 400, 300));
        goalsStage.show();

    }

    public double getProgress(File file) throws IOException {
        int wordCount = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (int r; (r = br.read()) !=-1;) {
                if ((char)r == ' ') {
                    wordCount += 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        wordCount += 1;
        float progress = ((float)wordCount)/wordGoal;
        this.wordCountLabel.setText(String.valueOf(wordCount));
        this.progressLabel.setText(String.valueOf(progress));
        return progress;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
