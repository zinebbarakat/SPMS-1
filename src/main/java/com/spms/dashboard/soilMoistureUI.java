package com.spms.dashboard;

import javafx.application.Application;
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

public class soilMoistureUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(920, 620);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(920, 620);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);

        VBox leftVBox = new VBox(10);
        leftVBox.setPrefSize(250, 620);
        leftVBox.setStyle("-fx-background-color: #386641;");
        leftVBox.setAlignment(Pos.TOP_CENTER);

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
                createNavButton("Dashboard", "dashboard.png", DashboardLogic::openDashboard),
                createNavButton("Light", "sun.png", DashboardLogic::clickLightButton),
                createNavButton("Temperature", "temperature.png", DashboardLogic::clickTempButton),
                createHighlightedNavButton("Soil Moisture", "shovel.png"),
                createNavButton("Settings", "settings.png", null)
        );

        leftVBox.getChildren().addAll(logoContainer, navContainer);

        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setStyle("-fx-padding: 20;");

        centerVBox.getChildren().add(createSoilMoistureCard());

        HBox topMenu = new HBox();
        topMenu.setStyle("-fx-padding: 10 20; -fx-background-color: transparent;");
        topMenu.setAlignment(Pos.CENTER_RIGHT);

        MenuButton myAccountMenu = new MenuButton("My Account");
        myAccountMenu.setFont(new Font("Malgun Gothic Bold", 18));
        myAccountMenu.setStyle("-fx-background-color: #f2e8cf; -fx-text-fill: #dda15e;");
        myAccountMenu.getItems().add(new MenuItem("Logout"));

        topMenu.getChildren().add(myAccountMenu);

        borderPane.setLeft(leftVBox);
        borderPane.setCenter(centerVBox);
        borderPane.setTop(topMenu);

        anchorPane.getChildren().add(borderPane);

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

        // Get humidity value from your data source
        double humidity = getHumidity();

        // Circle and status indicators
        Circle circle = new Circle(30);
        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Malgun Gothic Bold", 20));

        // Set color and text based on humidity range
        Color statusColor;
        String statusText;

        if ((humidity >= 0 && humidity < 30) || (humidity > 70 && humidity <= 100)) {
            statusColor = Color.RED;
            statusText = "SUBOPTIMAL";
        } else if ((humidity >= 30 && humidity < 40) || (humidity > 60 && humidity <= 70)) {
            statusColor = Color.web("#ffcc00"); // Yellow
            statusText = "SATISFACTORY";
        } else { // Between 40 and 60
            statusColor = Color.web("#28a745"); // Green
            statusText = "OPTIMAL";
        }

        circle.setFill(statusColor);
        statusLabel.setText(statusText);
        statusLabel.setTextFill(statusColor);

        // Current measurement display
        Label measurementLabel = new Label(String.format("CURRENT MEASUREMENT: %.1f %%", humidity));
        measurementLabel.setFont(new Font("Malgun Gothic Bold", 18));
        measurementLabel.setTextFill(Color.web("#666666"));

        // Indicator box with ranges
        VBox indicatorBox = new VBox(20);
        indicatorBox.setAlignment(Pos.CENTER);
        indicatorBox.setStyle("-fx-background-color: #f7f6f2; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc;");

        Label suboptimalLabel = new Label("Suboptimal: 0% - 30% or 70% - 100%");
        suboptimalLabel.setFont(new Font("Malgun Gothic", 14));
        suboptimalLabel.setTextFill(Color.RED);

        Label satisfactoryLabel = new Label("Satisfactory: 30% - 40% or 60% - 70%");
        satisfactoryLabel.setFont(new Font("Malgun Gothic", 14));
        satisfactoryLabel.setTextFill(Color.web("#ffcc00"));

        Label optimalLabel = new Label("Optimal: 40% - 60%");
        optimalLabel.setFont(new Font("Malgun Gothic", 14));
        optimalLabel.setTextFill(Color.web("#28a745"));

        indicatorBox.getChildren().addAll(suboptimalLabel, satisfactoryLabel, optimalLabel);

        card.getChildren().addAll(titleLabel, circle, statusLabel, measurementLabel, indicatorBox);
        return card;
    }

    // Method to get humidity - implement according to your data source
    private double getHumidity() {
        // TODO: Replace with actual implementation to get humidity data
        // For testing, you could return different values to see different states
        return 60; // Example value - optimal range
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
