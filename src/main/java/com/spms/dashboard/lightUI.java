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
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class lightUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Root Pane
        AnchorPane rootPane = new AnchorPane();
        rootPane.setPrefSize(920, 620);

        // BorderPane for layout
        BorderPane mainLayout = new BorderPane();
        AnchorPane.setTopAnchor(mainLayout, 0.0);
        AnchorPane.setBottomAnchor(mainLayout, 0.0);
        AnchorPane.setLeftAnchor(mainLayout, 0.0);
        AnchorPane.setRightAnchor(mainLayout, 0.0);

        // Left Navigation Menu
        VBox navigationMenu = new VBox(10);
        navigationMenu.setPrefWidth(250);
        navigationMenu.setStyle("-fx-background-color: #386641;");
        navigationMenu.setAlignment(Pos.TOP_CENTER);
        navigationMenu.setPadding(new Insets(20));

        // SPMS Logo and Title
        VBox logoContainer = new VBox(10);
        logoContainer.setAlignment(Pos.CENTER);

        Label logoLabel = new Label("SPMS");
        logoLabel.setFont(new Font("Malgun Gothic Bold", 42));
        logoLabel.setTextFill(Color.web("#a7c957"));

        ImageView logoImage = new ImageView(loadImage("leaves.png"));
        logoImage.setFitWidth(60);
        logoImage.setFitHeight(60);

        logoContainer.getChildren().addAll(logoImage, logoLabel);

        // Navigation Buttons
        VBox navButtons = new VBox(15);
        navButtons.setAlignment(Pos.TOP_LEFT);

        navButtons.getChildren().addAll(
                createNavButton("Dashboard", "dashboard.png"),
                createHighlightedNavButton("Light", "sun.png"), // Highlighted "Light"
                createNavButton("Temperature", "temperature.png"),
                createNavButton("Soil Moisture", "shovel.png"),
                createNavButton("Settings", "settings.png")
        );

        navigationMenu.getChildren().addAll(logoContainer, navButtons);

        // Center Content (Light Intensity Card)
        StackPane centerContent = new StackPane();
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setStyle("-fx-padding: 30;");

        centerContent.getChildren().add(createLightIntensityCard());

        // Top Menu Bar
        HBox topMenuBar = new HBox();
        topMenuBar.setStyle("-fx-padding: 10 20; -fx-background-color: transparent;");
        topMenuBar.setAlignment(Pos.CENTER_RIGHT);

        MenuButton accountMenu = new MenuButton("My Account");
        accountMenu.setFont(new Font("Malgun Gothic Bold", 18));
        accountMenu.setStyle("-fx-background-color: #f2e8cf; -fx-text-fill: #dda15e;");
        accountMenu.getItems().add(new MenuItem("Logout"));

        topMenuBar.getChildren().add(accountMenu);

        // Add Layouts to BorderPane
        mainLayout.setLeft(navigationMenu);
        mainLayout.setCenter(centerContent);
        mainLayout.setTop(topMenuBar);

        rootPane.getChildren().add(mainLayout);

        // Scene and Stage
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Light Dashboard");
        primaryStage.show();
    }

    private VBox createLightIntensityCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefSize(600, 400);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Title
        Label titleLabel = new Label("Light Intensity");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#a56336"));
        VBox.setMargin(titleLabel, new Insets(0, 0, 30, 0)); // Add space below title

        // Current Intensity
        Label intensityLabel = new Label("Current Light Intensity (Lux): 500");
        intensityLabel.setFont(new Font("Malgun Gothic Bold", 18));
        intensityLabel.setTextFill(Color.web("#000000"));

        // Horizontal Bar to Represent Intensity
        ProgressBar intensityBar = new ProgressBar(0.5);
        intensityBar.setPrefWidth(500);
        intensityBar.setPrefHeight(60); // Make bar even thicker
        intensityBar.setStyle("-fx-accent: #ffa500;");

        // Intensity Value Below Bar
        Label intensityValueLabel = new Label("500 Lux");
        intensityValueLabel.setFont(new Font("Malgun Gothic Bold", 16));
        intensityValueLabel.setTextFill(Color.web("#000000"));
        VBox.setMargin(intensityValueLabel, new Insets(5, 0, 20, 0)); // Add space below bar

        // Intensity Description Box
        VBox descriptionBox = new VBox(10);
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.setStyle("-fx-background-color: #f7f6f2; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc;");
        VBox.setMargin(descriptionBox, new Insets(20, 0, 0, 0)); // Move box further down

        Label lowDescLabel = new Label("Low Light: 50 - 200 Lux");
        lowDescLabel.setFont(new Font("Malgun Gothic", 14));
        lowDescLabel.setTextFill(Color.web("#8b0000"));

        Label intermediateDescLabel = new Label("Intermediate Light: 200 - 500 Lux");
        intermediateDescLabel.setFont(new Font("Malgun Gothic", 14));
        intermediateDescLabel.setTextFill(Color.web("#ffa500"));

        Label brightDescLabel = new Label("Bright Light: 500 - 2000 Lux");
        brightDescLabel.setFont(new Font("Malgun Gothic", 14));
        brightDescLabel.setTextFill(Color.web("#006400"));

        descriptionBox.getChildren().addAll(lowDescLabel, intermediateDescLabel, brightDescLabel);

        card.getChildren().addAll(titleLabel, intensityLabel, intensityBar, intensityValueLabel, descriptionBox);
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
