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
                createNavButton("Dashboard", "dashboard.png", DashboardLogic::openDashboard),
                createHighlightedNavButton("Light", "sun.png"),
                createNavButton("Temperature", "temperature.png", DashboardLogic::clickTempButton),
                createNavButton("Soil Moisture", "shovel.png", DashboardLogic::clickSoilButton),
                createNavButton("Settings", "settings.png", null)
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
        VBox.setMargin(titleLabel, new Insets(0, 0, 30, 0));

        // Green Circle
        Circle circle = new Circle(30);
        circle.setFill(Color.web("#3DAA3D"));

        // "OPTIMAL" Label
        Label optimalLabel = new Label("OPTIMAL");
        optimalLabel.setFont(new Font("Malgun Gothic Bold", 20));
        optimalLabel.setTextFill(Color.web("#3DAA3D"));

        // Current Measurement
        Label measurementLabel = new Label("Current Measurement: 500 Lux");
        measurementLabel.setFont(new Font("Malgun Gothic Bold", 18));
        measurementLabel.setTextFill(Color.web("#000000"));

        // Intensity Description Box (without "Low Light")
        VBox descriptionBox = new VBox(10);
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.setStyle("-fx-background-color: #f7f6f2; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc;");
        VBox.setMargin(descriptionBox, new Insets(20, 0, 0, 0));

        Label intermediateDescLabel = new Label("Optimal: 200 - 500 Lux");
        intermediateDescLabel.setFont(new Font("Malgun Gothic", 14));
        intermediateDescLabel.setTextFill(Color.web("#ffa500"));

        Label brightDescLabel = new Label("Suboptimal: 500 - 2000 Lux");
        brightDescLabel.setFont(new Font("Malgun Gothic", 14));
        brightDescLabel.setTextFill(Color.web("#006400"));

        descriptionBox.getChildren().addAll(intermediateDescLabel, brightDescLabel);

        card.getChildren().addAll(titleLabel, circle, optimalLabel, measurementLabel, descriptionBox);
        return card;
    }

    private HBox createNavButton(String text, String iconPath, Runnable onClickAction) {
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
        hBox.setOnMouseClicked(event -> {
            if (onClickAction != null) {
                onClickAction.run();
            }
        });

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
