package essaywhip.ui;

/**
 * Created by sifu on 23/12/16.
 */

import essaywhip.util.ProgressChecker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Modality;

public class MainScene extends Scene {
    private Button enterGoalbtn = new Button("Enter Goal");
    private Button uploadFilebtn = new Button("Upload File");
    private Button showProgessbtn = new Button("Show Progress");
    private Label wordGoalString = new Label("Goal: ");
    private Label wordCountString = new Label("Word Count: ");
    private Label progessString = new Label("Progress: ");
    private Label wordGoalNumber = new Label();
    private Label wordCountNumber = new Label();
    private Label progressNumber = new Label();
    private ProgressBar progressBar = new ProgressBar(0);
    private ProgressBar timeBar = new ProgressBar(0);
    private ProgressChecker pc = new ProgressChecker();

    public MainScene(BorderPane pane, double width, double height) {
        super(pane, width, height);
        HBox labelBox = new HBox(5);
        VBox barBox = new VBox(5);
        VBox buttonBox = new VBox(5);
        pane.setTop(buildLabels(labelBox));
        pane.setCenter(buildBars(barBox));
        pane.setBottom(buildButtons(buttonBox));

        enterGoalbtn.setOnAction(e -> {
            GoalsPopup gp = new GoalsPopup(wordGoalNumber);
            gp.initModality(Modality.APPLICATION_MODAL);
            gp.show();
        });

        showProgessbtn.setOnAction(e -> {
            pc.setWordGoal(Integer.parseInt(wordGoalNumber.getText()));
        });
    }

    public int getWordGoalNumberValue() {
        return Integer.parseInt(wordGoalNumber.getText());
    }

    public int getWordCountNumberValue() {
        return Integer.parseInt(wordCountNumber.getText());
    }

    public Button getShowProgessbtn() {
        return showProgessbtn;
    }

    public Button getUploadFilebtn() {
        return uploadFilebtn;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ProgressBar getTimeBar() {
        return timeBar;
    }

    public Label getWordCountNumber() {
        return wordCountNumber;
    }
    public Label getProgressNumber() {
        return progressNumber;
    }

    public void adjustBarsWidth(double width) {
        this.progressBar.setPrefWidth(width);
        this.timeBar.setPrefWidth(width);
    }

    private VBox buildButtons(VBox buttonBox) {
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(enterGoalbtn, uploadFilebtn, showProgessbtn);
        return buttonBox;
    }

    private VBox buildBars(VBox barBox) {
        barBox.setAlignment(Pos.CENTER);
        barBox.getChildren().addAll(progressBar, timeBar);
        return barBox;
    }

    private HBox buildLabels(HBox labelBox) {
        VBox stringsBox = new VBox(5);
        VBox numbersBox = new VBox(5);
        stringsBox.setAlignment(Pos.CENTER);
        numbersBox.setAlignment(Pos.CENTER);
        stringsBox.getChildren().addAll(wordGoalString, wordCountString, progessString);
        numbersBox.getChildren().addAll(wordGoalNumber, wordCountNumber, progressNumber);

        labelBox.getChildren().addAll(stringsBox, numbersBox);
        labelBox.setAlignment(Pos.CENTER);
        return labelBox;
    }
}
