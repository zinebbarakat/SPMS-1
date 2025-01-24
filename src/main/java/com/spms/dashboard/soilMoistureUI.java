package com.spms.dashboard;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class soilMoistureUI extends Application {

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

        // Left VBox for navigation menu
        VBox leftVBox = new VBox(10);
        leftVBox.setPrefSize(250, 620);
        leftVBox.setStyle("-fx-background-color: #386641;");
        leftVBox.setAlignment(Pos.TOP_CENTER);

        // SPMS logo and title
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setSpacing(10);
        Label titleLabel = new Label("SPMS");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 42));
        titleLabel.setTextFill(Color.web("#a7c957"));

        ImageView logoImage = new ImageView(loadImage("leaves.png"));
        logoImage.setFitWidth(60);
        logoImage.setFitHeight(60);
        logoContainer.getChildren().addAll(logoImage, titleLabel);

        VBox navContainer = new VBox(15);
        navContainer.setAlignment(Pos.TOP_LEFT);
        navContainer.setStyle("-fx-padding: 20;");

        navContainer.getChildren().addAll(
                createNavButton("Dashboard", "dashboard.png"),
                createNavButton("Light", "sun.png"),
                createNavButton("Temperature", "temperature.png"),
                createHighlightedNavButton("Soil Moisture", "shovel.png"),
                createNavButton("Settings", "settings.png")
        );

        leftVBox.getChildren().addAll(logoContainer, navContainer);

        // Center layout for main content
        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setStyle("-fx-padding: 20;");

        centerVBox.getChildren().add(createSoilMoistureCard());

        // Top menu bar
        HBox topMenu = new HBox();
        topMenu.setStyle("-fx-padding: 10 20; -fx-background-color: transparent;");
        topMenu.setAlignment(Pos.CENTER_RIGHT);

        MenuButton myAccountMenu = new MenuButton("My Account");
        myAccountMenu.setFont(new Font("Malgun Gothic Bold", 18));
        myAccountMenu.setStyle("-fx-background-color: #f2e8cf; -fx-text-fill: #dda15e;");
        myAccountMenu.getItems().add(new MenuItem("Logout"));

        topMenu.getChildren().add(myAccountMenu);

        // Set the layout in BorderPane
        borderPane.setLeft(leftVBox);
        borderPane.setCenter(centerVBox);
        borderPane.setTop(topMenu);

        anchorPane.getChildren().add(borderPane);

        // Scene and Stage
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Soil Moisture Dashboard");
        primaryStage.show();
    }

    private VBox createSoilMoistureCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefSize(600, 400);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Title
        Label titleLabel = new Label("Soil Moisture");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#a56336"));

        // Current Moisture Label
        Label currentMoistureLabel = new Label("Current Moisture (%): 40");
        currentMoistureLabel.setFont(new Font("Malgun Gothic Bold", 18));
        currentMoistureLabel.setTextFill(Color.web("#000000"));

        // Add Image (Soil Moisture Gauge)
        ImageView moistureImage = new ImageView(new Image(getClass().getResource("/soil.jpg").toExternalForm()));
        moistureImage.setFitWidth(500);
        moistureImage.setPreserveRatio(true);
        moistureImage.setSmooth(true);

        // Simple Moisture Indicator Box
        VBox indicatorBox = new VBox(20);
        indicatorBox.setAlignment(Pos.CENTER);
        indicatorBox.setStyle("-fx-background-color: #f7f6f2; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc;");

        Label dryLabel = new Label("Dry: 10% - 30%");
        dryLabel.setFont(new Font("Malgun Gothic", 14));
        dryLabel.setTextFill(Color.web("#8b0000"));

        Label moderateLabel = new Label("Moderate: 40% - 60%");
        moderateLabel.setFont(new Font("Malgun Gothic", 14));
        moderateLabel.setTextFill(Color.web("#ffa500"));

        Label wetLabel = new Label("Wet: 70% - 90%");
        wetLabel.setFont(new Font("Malgun Gothic", 14));
        wetLabel.setTextFill(Color.web("#006400"));

        indicatorBox.getChildren().addAll(dryLabel, moderateLabel, wetLabel);

        card.getChildren().addAll(titleLabel, currentMoistureLabel, moistureImage, indicatorBox);
        return card;
    }

    private HBox createNavButton(String text, String iconPath) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 10; -fx-background-color: #386641; -fx-background-radius: 5;");

        ImageView icon = new ImageView(loadImage(iconPath));
        icon.setFitWidth(25);
        icon.setFitHeight(25);

        Label label = new Label(text);
        label.setFont(new Font("Malgun Gothic Bold", 16));
        label.setTextFill(Color.web("#f2e8cf"));

        hBox.getChildren().addAll(icon, label);
        return hBox;
    }

    private HBox createHighlightedNavButton(String text, String iconPath) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 10; -fx-background-color: #a7c957; -fx-background-radius: 5;");

        ImageView icon = new ImageView(loadImage(iconPath));
        icon.setFitWidth(25);
        icon.setFitHeight(25);

        Label label = new Label(text);
        label.setFont(new Font("Malgun Gothic Bold", 16));
        label.setTextFill(Color.web("#ffffff"));

        hBox.getChildren().addAll(icon, label);
        return hBox;
    }

    private Image loadImage(String imageName) {
        String path = "/com/NLS/spms/" + imageName;
        return new Image(getClass().getResource(path).toExternalForm());
    }
}
