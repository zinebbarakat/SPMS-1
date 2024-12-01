package com.spms.dashboard;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class DashboardUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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
        leftVBox.setStyle("-fx-background-color: #386641;");
        leftVBox.setPadding(new Insets(20)); // Added 20px padding around the VBox content

        // Title HBox
        StackPane titlePane = new StackPane();
        titlePane.setPrefSize(300, 150);

        Label titleLabel = new Label("SPMS");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 42));
        titleLabel.setTextFill(javafx.scene.paint.Color.web("#a7c957"));

        ImageView logoImageView = new ImageView(loadImage("leaves.png"));
        logoImageView.setFitWidth(60);
        logoImageView.setFitHeight(60);

        // StackPane aligns content to center
        titlePane.getChildren().addAll(logoImageView, titleLabel);
        StackPane.setAlignment(titleLabel, Pos.CENTER);
        StackPane.setAlignment(logoImageView, Pos.CENTER_LEFT);

        // Buttons HBox for each menu option
        HBox dashboardBox = createMenuItem("Dashboard", "dashboard.png");
        HBox lightBox = createMenuItem("Light", "sun.png");
        HBox tempBox = createMenuItem("Temperature", "temperature.png");
        HBox soilMoistureBox = createMenuItem("Soil Moisture", "shovel.png");
        HBox settingsBox = createMenuItem("Settings", "settings.png");

        leftVBox.getChildren().addAll(titlePane, dashboardBox, lightBox, tempBox, soilMoistureBox, settingsBox);

        // Center VBox
        VBox centerVBox = new VBox(10);
        centerVBox.setPrefSize(100, 200);

        // Top Menu (My Account)
        HBox topMenu = new HBox();
        topMenu.setPrefSize(620, 79);
        topMenu.setAlignment(Pos.CENTER_RIGHT); // Align My Account menu to the right
        topMenu.setPadding(new Insets(10));

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

        ImageView icon = new ImageView(loadImage(iconPath));
        icon.setFitWidth(25);
        icon.setFitHeight(25);

        Button button = new Button(text);
        button.setFont(new Font("Malgun Gothic Bold", 20));
        button.setPrefSize(218, 42);
        // Changed background color to #386641
        button.setStyle("-fx-background-color: #386641; -fx-text-fill: #f2e8cf;");

        hBox.getChildren().addAll(icon, button);
        return hBox;
    }

    private Image loadImage(String imageName) {
        String path = "/com/NLS/spms/" + imageName;
        return new Image(getClass().getResource(path).toExternalForm());
    }



}
