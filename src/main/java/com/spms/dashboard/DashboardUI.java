package com.spms.dashboard;

import com.spms.login.Auth;
import com.spms.login.DatabaseHelper;
import com.spms.session.Session;
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

public class DashboardUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(920, 620);

        BorderPane borderPane = new BorderPane();
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
                createHighlightedNavButton("Dashboard", "dashboard.png"),
                createNavButton("Light", "sun.png", DashboardLogic::clickLightButton),
                createNavButton("Temperature", "temperature.png", DashboardLogic::clickTempButton),
                createNavButton("Soil Moisture", "shovel.png", DashboardLogic::clickSoilButton)
        );

        leftVBox.getChildren().addAll(logoContainer, navContainer);

        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setStyle("-fx-padding: 20;");
        centerVBox.getChildren().addAll(
                createLightCard(),
                createSoilCard(),
                createTemperatureCard()
        );

        HBox topMenu = new HBox();
        topMenu.setStyle("-fx-padding: 10 20; -fx-background-color: transparent;");
        topMenu.setAlignment(Pos.CENTER_RIGHT);

        Label welcomeLabel = new Label("Welcome Premium User " + Auth.loggedInUser);
        welcomeLabel.setFont(new Font("Malgun Gothic Bold", 18));
        welcomeLabel.setTextFill(Color.web("#a56336"));

        MenuButton myAccountMenu = new MenuButton("My Account");
        myAccountMenu.setFont(new Font("Malgun Gothic Bold", 18));
        myAccountMenu.setStyle("-fx-background-color: #f2e8cf; -fx-text-fill: #dda15e;");
        myAccountMenu.getItems().add(new MenuItem("Logout"));

        topMenu.getChildren().addAll(welcomeLabel, myAccountMenu);

        borderPane.setLeft(leftVBox);
        borderPane.setCenter(centerVBox);
        borderPane.setTop(topMenu);
        anchorPane.getChildren().add(borderPane);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }

    private VBox createLightCard() {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(400, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Light Level");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 18));
        titleLabel.setTextFill(Color.web("#a56336"));

        // Get light value from your data source
        double light = getLight();

        Circle circle = new Circle(20);
        Label lightLabel = new Label();
        lightLabel.setFont(new Font("Malgun Gothic Bold", 20));

        // Set color and text based on light value
        Color statusColor;
        String statusText;

        if (light == 1) {
            statusColor = Color.web("#28a745"); // Green
            statusText = "OPTIMAL";
        } else { // When light == 0
            statusColor = Color.RED;
            statusText = "SUBOPTIMAL";
        }

        circle.setFill(statusColor);
        lightLabel.setText(statusText);
        lightLabel.setTextFill(statusColor);

        // Add current light level status
        Label actualLightLabel = new Label("CURRENT MEASUREMENT: " + (light == 1 ? "ON" : "OFF"));
        actualLightLabel.setFont(new Font("Malgun Gothic", 14));
        actualLightLabel.setTextFill(Color.web("#666666"));

        card.getChildren().addAll(titleLabel, circle, lightLabel, actualLightLabel);
        return card;
    }
    // Method to get light level - implement according to your data source
    private double getLight() {
        // ✅ Updated: get data for the current user
        double rawLight = DatabaseHelper.getLatestLightForUser(Session.getUserId());
        return rawLight > 0 ? 1 : 0;
    }

    private VBox createSoilCard() {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(400, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Soil Moisture");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 18));
        titleLabel.setTextFill(Color.web("#a56336"));

        // Get humidity value from your data source
        double humidity = DatabaseHelper.getLatestMoistureForUser(Session.getUserId());

        Circle circle = new Circle(20);
        Label humidityLabel = new Label();
        humidityLabel.setFont(new Font("Malgun Gothic Bold", 20));

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
        humidityLabel.setText(statusText);
        humidityLabel.setTextFill(statusColor);

        // Add actual humidity value display
        Label actualHumidityLabel = new Label(String.format("CURRENT MEASUREMENT: %.1f %%", humidity));
        actualHumidityLabel.setFont(new Font("Malgun Gothic", 14));
        actualHumidityLabel.setTextFill(Color.web("#666666"));

        card.getChildren().addAll(titleLabel, circle, humidityLabel, actualHumidityLabel);
        return card;
    }

    private VBox createTemperatureCard() {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(400, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Temperature");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 18));
        titleLabel.setTextFill(Color.web("#a56336"));

        // Get temperature value from your data source
        double temperature = getTemperature(); // Implement this method to fetch temperature data

        Circle circle = new Circle(20);
        Label temperatureLabel = new Label();
        temperatureLabel.setFont(new Font("Malgun Gothic Bold", 20));

        // Set color and text based on temperature range
        Color statusColor;
        String statusText;

        if ((temperature >= 0 && temperature < 15) || (temperature > 30 && temperature <= 50)) {
            statusColor = Color.RED;
            statusText = "SUBOPTIMAL";
        } else if ((temperature >= 15 && temperature < 20) || (temperature >= 25 && temperature <= 30)) {
            statusColor = Color.web("#ffcc00"); // Yellow
            statusText = "SATISFACTORY";
        } else { // Between 20 and 25
            statusColor = Color.web("#28a745"); // Green
            statusText = "OPTIMAL";
        }

        circle.setFill(statusColor);
        temperatureLabel.setText(statusText);
        temperatureLabel.setTextFill(statusColor);

        // Add actual temperature value display
        Label actualTempLabel = new Label(String.format("CURRENT MEASUREMENT: %.1f °C", temperature));
        actualTempLabel.setFont(new Font("Malgun Gothic", 14));
        actualTempLabel.setTextFill(Color.web("#666666"));

        card.getChildren().addAll(titleLabel, circle, temperatureLabel, actualTempLabel);
        return card;
    }
    // Method to get temperature - implement according to your data source
    private double getTemperature() {
        return DatabaseHelper.getLatestTemperatureForUser(Session.getUserId());
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
