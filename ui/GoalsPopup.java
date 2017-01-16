package essaywhip.ui;
/**
 * Created by sifu on 23/12/16.
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class GoalsPopup extends Stage {

    GoalsPopup(Label wordCountNumber) {
        Label wordsLabel = new Label("Enter amount of words below");
        TextField wordCountField = new TextField();
        Button donebtn = new Button("Done");

        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(wordsLabel, wordCountField, donebtn);

        BorderPane pane = new BorderPane();
        pane.setCenter(box);

        setScene(new Scene(pane, 500, 400));

        donebtn.setOnAction(e -> {
            try {
                Integer.parseInt(wordCountField.getText());
                wordCountNumber.setText(wordCountField.getText());
                this.close();
            } catch (NumberFormatException exception) {
                displayNumberError();
            }
        });
    }

    private void displayNumberError() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Button btn = new Button("ok");
        Label lbl = new Label("You must enter a number");
        VBox box = new VBox(5);

        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(lbl, btn);

        btn.setOnAction(e -> stage.close());

        BorderPane pane = new BorderPane();
        pane.setCenter(box);
        Scene scene = new Scene(pane, 400, 300);

        stage.setScene(scene);
        stage.show();
    }

}
