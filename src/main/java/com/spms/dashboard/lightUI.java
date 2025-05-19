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

public class lightUI extends Application {

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
                createHighlightedNavButton("Light", "sun.png"),
                createNavButton("Temperature", "temperature.png", DashboardLogic::clickTempButton),
                createNavButton("Soil Moisture", "shovel.png", DashboardLogic::clickSoilButton),
                createNavButton("Settings", "settings.png", null)
        );

        leftVBox.getChildren().addAll(logoContainer, navContainer);

        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setStyle("-fx-padding: 20;");
        centerVBox.getChildren().add(createLightCard());

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
        primaryStage.setTitle("Light Dashboard");
        primaryStage.show();
    }

    private VBox createLightCard() {
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefSize(600, 400);
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

        Label onLabel = new Label("GOOD LIGHT (ON): Light detected");
        onLabel.setTextFill(Color.web("#28a745"));
        onLabel.setFont(new Font(14));

        Label offLabel = new Label("NO LIGHT (OFF): No light detected");
        offLabel.setTextFill(Color.RED);
        offLabel.setFont(new Font(14));

        indicatorBox.getChildren().addAll(onLabel, offLabel);

        card.getChildren().addAll(titleLabel, circle, statusLabel, indicatorBox);
        return card;
    }

    private double getLight() {
        // Expected to return 1 for ON, 0 for OFF
        double rawLight = DatabaseHelper.getLatestLightForUser(12);
        return rawLight > 0 ? 1 : 0;
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
