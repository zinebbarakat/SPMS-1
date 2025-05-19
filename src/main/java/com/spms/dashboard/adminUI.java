package com.spms.dashboard;

import com.spms.login.Auth;
import com.spms.login.DatabaseHelper;
import com.spms.session.Session;
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
                createNavButton("Light", "sun.png", DashboardLogic::clickLightButton),
                createNavButton("Temperature", "temperature.png", DashboardLogic::clickTempButton),
                createNavButton("Soil Moisture", "shovel.png", DashboardLogic::clickSoilButton),
                createNavButton("Settings", "settings.png", null)
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

        // Top menu bar with welcome message
        HBox topMenu = new HBox(20);
        topMenu.setStyle("-fx-padding: 10 20; -fx-background-color: transparent;");
        topMenu.setAlignment(Pos.CENTER_RIGHT);

        Label welcomeLabel = new Label("Welcome Admin "+ Auth.loggedInUser);
        welcomeLabel.setFont(new Font("Malgun Gothic Bold", 18));
        welcomeLabel.setTextFill(Color.web("#a56336"));

        MenuButton myAccountMenu = new MenuButton("My Account");
        myAccountMenu.setFont(new Font("Malgun Gothic Bold", 18));
        myAccountMenu.setStyle("-fx-background-color: #f2e8cf; -fx-text-fill: #dda15e;");
        myAccountMenu.getItems().add(new MenuItem("Logout"));

        topMenu.getChildren().addAll(welcomeLabel, myAccountMenu);

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

        Label titleLabel = new Label("Light Status");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#e3b505"));

        int lightStatus = (int) getLight(); // Expected to return 0 or 1

        Circle circle = new Circle(30);
        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Malgun Gothic Bold", 24));

        if (lightStatus == 1) {
            circle.setFill(Color.web("#28a745")); // Green
            statusLabel.setText("GOOD LIGHT");
            statusLabel.setTextFill(Color.web("#28a745"));
        } else {
            circle.setFill(Color.RED);
            statusLabel.setText("NO LIGHT");
            statusLabel.setTextFill(Color.RED);
        }

        // Indicator box for legend
        VBox indicatorBox = new VBox(10);
        indicatorBox.setAlignment(Pos.CENTER);
        indicatorBox.setStyle("-fx-background-color: #f7f6f2; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc;");

        card.getChildren().addAll(titleLabel, circle, statusLabel);
        return card;
    }

    private double getLight() {
        // ✅ Updated: get data for the current user
        double rawLight = DatabaseHelper.getLatestLightForUser(Session.getUserId());
        return rawLight > 0 ? 1 : 0;
    }

    private VBox createSoilMoistureCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(300, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Soil Moisture");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#a56336"));

        // ✅ Now user-specific data
        double moisture = DatabaseHelper.getLatestMoistureForUser(Session.getUserId());

        Circle circle = new Circle(30);
        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Malgun Gothic Bold", 20));

        Color statusColor;
        String statusText;

        if ((moisture >= 0 && moisture < 30) || (moisture > 70 && moisture <= 100)) {
            statusColor = Color.RED;
            statusText = "SUBOPTIMAL";
        } else if ((moisture >= 30 && moisture < 40) || (moisture > 60 && moisture <= 70)) {
            statusColor = Color.web("#ffcc00");
            statusText = "SATISFACTORY";
        } else {
            statusColor = Color.web("#28a745");
            statusText = "OPTIMAL";
        }

        circle.setFill(statusColor);
        statusLabel.setText(statusText);
        statusLabel.setTextFill(statusColor);

        card.getChildren().addAll(titleLabel, circle, statusLabel);
        return card;
    }

    private VBox createTemperatureCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(300, 150);
        card.setStyle("-fx-background-color: #fef9e7; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("Temperature");
        titleLabel.setFont(new Font("Malgun Gothic Bold", 28));
        titleLabel.setTextFill(Color.web("#e3b505"));

        double temperature = getTemperature();

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

        card.getChildren().addAll(titleLabel, circle, statusLabel);
        return card;
    }

    private double getTemperature() {
        // ✅ Now retrieves data for the logged-in user
        return DatabaseHelper.getLatestTemperatureForUser(Session.getUserId());
    }

    private Button createAdminButton() {
        Button adminButton = new Button("Admin Permission");
        adminButton.setFont(new Font("Malgun Gothic Bold", 18));
        adminButton.setStyle("-fx-background-color: #dda15e; -fx-text-fill: #ffffff; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;");
        adminButton.setPrefWidth(200);
        return adminButton;
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
