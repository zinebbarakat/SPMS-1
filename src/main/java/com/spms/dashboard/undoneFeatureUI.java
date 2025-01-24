package com.spms.dashboard;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class undoneFeatureUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // AnchorPane as root
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(920, 620);

        // BorderPane for main layout
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(920, 620);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);

        // Centered VBox for the message
        VBox messageBox = new VBox(20);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPrefSize(400, 200);
        messageBox.setStyle("-fx-background-color: #fef9e7; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Title Label
        Label titleLabel = new Label("We're sorry...");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#a56336"));

        // Description Labels
        Label description1 = new Label("This feature is part of the premium plan.");
        description1.setFont(new Font("Malgun Gothic", 16));
        description1.setTextFill(Color.web("#a56336"));

        Label description2 = new Label("Please upgrade your plan to gain access.");
        description2.setFont(new Font("Malgun Gothic", 16));
        description2.setTextFill(Color.web("#a56336"));

        // Add labels to the message box
        messageBox.getChildren().addAll(titleLabel, description1, description2);

        // Add message box to the center of the BorderPane
        borderPane.setCenter(messageBox);

        anchorPane.getChildren().add(borderPane);

        // Scene and Stage
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Feature Unavailable");
        primaryStage.show();
    }
}
