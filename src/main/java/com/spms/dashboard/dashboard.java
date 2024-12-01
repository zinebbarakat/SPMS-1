package com.spms.dashboard;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(920, 620);

        // BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(200, 200);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);

        // Left VBox
        VBox leftVBox = new VBox(10);
        leftVBox.setPrefSize(300, 200);
        leftVBox.setStyle("-fx-background-color: #2F5233;");

        // Title HBox
        HBox titleBox = new HBox(10);
        titleBox.setPrefSize(300, 150);

        Label titleLabel = new Label("SPMS");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 42));
        titleLabel.setTextFill(javafx.scene.paint.Color.web("#a7c957"));

        ImageView logoImageView = new ImageView(new Image("file:img/leaves.png"));
        logoImageView.setFitWidth(60);
        logoImageView.setFitHeight(150);

        titleBox.getChildren().addAll(titleLabel, logoImageView);

        // Buttons HBox for each menu option
        HBox dashboardBox = createMenuItem("Dashboard", "file:img/dashboard.png");
        HBox lightBox = createMenuItem("Light", "file:img/sun.png");
        HBox waterBox = createMenuItem("Water", "file:img/drop.png");
        HBox soilMoistureBox = createMenuItem("Soil Moisture", "file:img/shovel.png");
        HBox settingsBox = createMenuItem("Settings", "file:img/settings.png");

        leftVBox.getChildren().addAll(titleBox, dashboardBox, lightBox, waterBox, soilMoistureBox, settingsBox);

        // Center VBox
        VBox centerVBox = new VBox(10);
        centerVBox.setPrefSize(100, 200);

        // Top Menu (My Account)
        HBox topMenu = new HBox();
        topMenu.setPrefSize(620, 79);

        MenuButton myAccountMenu = new MenuButton("My Account");
        myAccountMenu.setFont(new Font("Malgun Gothic Bold", 18));
        myAccountMenu.setStyle("-fx-background-color: #dda15e; -fx-text-fill: #000000;");
        myAccountMenu.getItems().add(new MenuItem("Logout"));

        topMenu.getChildren().add(myAccountMenu);

        centerVBox.getChildren().add(topMenu);

        // Set Layout
        borderPane.setLeft(leftVBox);
        borderPane.setCenter(centerVBox);
        anchorPane.getChildren().add(borderPane);

        // Scene and Stage
        Scene scene = new Scene(anchorPane, 920, 620);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }

    private HBox createMenuItem(String text, String iconPath) {
        HBox hBox = new HBox(10);
        hBox.setPrefSize(300, 80);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(25);
        icon.setFitHeight(25);

        Button button = new Button(text);
        button.setFont(new Font("Malgun Gothic Bold", 20));
        button.setPrefSize(218, 42);
        button.setStyle("-fx-background-color: #6AA84F; -fx-text-fill: #f2e8cf;");

        hBox.getChildren().addAll(icon, button);
        return hBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
