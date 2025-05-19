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
import com.spms.login.DatabaseHelper;

public class temperatureUI extends Application {

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
                createNavButton("Dashboard", "dashboard.png", DashboardLogic::openDashboard),
                createNavButton("Light", "sun.png", DashboardLogic::clickLightButton),
                createHighlightedNavButton("Temperature", "temperature.png"),
                createNavButton("Soil Moisture", "shovel.png", DashboardLogic::clickSoilButton),
                createNavButton("Settings", "settings.png", null)
        );

        leftVBox.getChildren().addAll(logoContainer, navContainer);

        // Center layout for main content
        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setStyle("-fx-padding: 20;");

        centerVBox.getChildren().add(createTemperatureCard());

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
        primaryStage.setTitle("Temperature Dashboard");
        primaryStage.show();
    }

    private VBox createTemperatureCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefSize(600, 400);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Title
        Label titleLabel = new Label("Temperature");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#a56336"));

        double temperature = getTemperature();

        // Circle and status indicators
        Circle circle = new Circle(30);
        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Malgun Gothic Bold", 20));

        Color statusColor;
        String statusText;

        if ((temperature >= 0 && temperature < 15) || (temperature > 30 && temperature <= 50)) {
            statusColor = Color.RED;
            statusText = "SUBOPTIMAL";
        } else if ((temperature >= 15 && temperature < 20) || (temperature >= 25 && temperature <= 30)) {
            statusColor = Color.web("#ffcc00");
            statusText = "SATISFACTORY";
        } else {
            statusColor = Color.web("#28a745");
            statusText = "OPTIMAL";
        }

        circle.setFill(statusColor);
        statusLabel.setText(statusText);
        statusLabel.setTextFill(statusColor);

        Label measurementLabel = new Label(String.format("CURRENT MEASUREMENT: %.1f °C", temperature));
        measurementLabel.setFont(new Font("Malgun Gothic Bold", 18));
        measurementLabel.setTextFill(Color.web("#666666"));

        VBox indicatorBox = new VBox(20);
        indicatorBox.setAlignment(Pos.CENTER);
        indicatorBox.setStyle("-fx-background-color: #f7f6f2; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc;");

        Label suboptimalLabel = new Label("Suboptimal: 0°C - 15°C or 30°C - 50°C");
        suboptimalLabel.setFont(new Font("Malgun Gothic", 14));
        suboptimalLabel.setTextFill(Color.RED);

        Label satisfactoryLabel = new Label("Satisfactory: 15°C - 20°C or 25°C - 30°C");
        satisfactoryLabel.setFont(new Font("Malgun Gothic", 14));
        satisfactoryLabel.setTextFill(Color.web("#ffcc00"));

        Label optimalLabel = new Label("Optimal: 20°C - 25°C");
        optimalLabel.setFont(new Font("Malgun Gothic", 14));
        optimalLabel.setTextFill(Color.web("#28a745"));

        indicatorBox.getChildren().addAll(suboptimalLabel, satisfactoryLabel, optimalLabel);

        card.getChildren().addAll(titleLabel, circle, statusLabel, measurementLabel, indicatorBox);
        return card;
    }

    private double getTemperature() {
        return DatabaseHelper.getLatestTemperatureForUser(11); // Replace 11 if needed
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
            if (onClickAction != null) onClickAction.run();
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
