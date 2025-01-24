package com.spms.dashboard;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class adminUI extends Application {

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
                createHighlightedNavButton("Dashboard", "dashboard.png"),
                createNavButton("Light", "sun.png"),
                createNavButton("Temperature", "temperature.png"),
                createNavButton("Soil Moisture", "shovel.png"),
                createNavButton("Settings", "settings.png")
        );

        leftVBox.getChildren().addAll(logoContainer, navContainer);

        // Center layout for main content
        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setStyle("-fx-padding: 20;");

        HBox rowOne = new HBox(30);
        rowOne.setAlignment(Pos.CENTER);

        HBox rowTwo = new HBox(30);
        rowTwo.setAlignment(Pos.CENTER);

        rowOne.getChildren().addAll(createLightLevelCard(), createSoilMoistureCard());
        rowTwo.getChildren().addAll(createTemperatureCard(), createAdminButton());

        centerVBox.getChildren().addAll(rowOne, rowTwo);

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
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();
    }

    private VBox createLightLevelCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(300, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Light Level");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 24));
        titleLabel.setTextFill(Color.web("#a56336"));

        Circle statusCircle = new Circle(30);
        statusCircle.setFill(Color.web("#28a745"));

        Label statusLabel = new Label("OPTIMAL");
        statusLabel.setFont(new Font("Malgun Gothic Bold", 18));
        statusLabel.setTextFill(Color.web("#28a745"));

        card.getChildren().addAll(titleLabel, statusCircle, statusLabel);
        return card;
    }

    private VBox createSoilMoistureCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(300, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Soil Moisture");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 24));
        titleLabel.setTextFill(Color.web("#a56336"));

        Circle statusCircle = new Circle(30);
        statusCircle.setFill(Color.web("#ffcc00"));

        Label statusLabel = new Label("SATISFACTORY");
        statusLabel.setFont(new Font("Malgun Gothic Bold", 18));
        statusLabel.setTextFill(Color.web("#ffcc00"));

        card.getChildren().addAll(titleLabel, statusCircle, statusLabel);
        return card;
    }

    private VBox createTemperatureCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(300, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Temperature");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 24));
        titleLabel.setTextFill(Color.web("#a56336"));

        ProgressBar tempBar = new ProgressBar(0.5);
        tempBar.setPrefWidth(300);
        tempBar.setPrefHeight(30);
        tempBar.setStyle("-fx-accent: red;");

        HBox tempLabels = new HBox(50);
        tempLabels.setAlignment(Pos.CENTER);
        Label coldLabel = new Label("COLD");
        coldLabel.setFont(new Font("Malgun Gothic Bold", 14));
        coldLabel.setTextFill(Color.web("#8b0000"));

        Label warmLabel = new Label("WARM");
        warmLabel.setFont(new Font("Malgun Gothic Bold", 14));
        warmLabel.setTextFill(Color.web("#ffa500"));

        Label hotLabel = new Label("HOT");
        hotLabel.setFont(new Font("Malgun Gothic Bold", 14));
        hotLabel.setTextFill(Color.web("#ff4500"));

        tempLabels.getChildren().addAll(coldLabel, warmLabel, hotLabel);

        card.getChildren().addAll(titleLabel, tempBar, tempLabels);
        return card;
    }

    private Button createAdminButton() {
        Button adminButton = new Button("Admin Permission");
        adminButton.setFont(new Font("Malgun Gothic Bold", 18));
        adminButton.setStyle("-fx-background-color: #dda15e; -fx-text-fill: #ffffff; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;");
        adminButton.setPrefWidth(200);
        return adminButton;
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
