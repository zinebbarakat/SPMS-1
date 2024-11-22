package com.example.login;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class LoginUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Left side: Form container with green background
        VBox formContainer = new VBox();
        formContainer.setAlignment(Pos.CENTER_LEFT);
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #28a745;");
        formContainer.setPrefWidth(350); // Slightly smaller preferred width

        // Title and welcome message
        Label titleLabel = new Label("Smart Plant Monitoring System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label welcomeLabel = new Label("Welcome to our App");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        // Email input field
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-text-fill: white;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        // Password input field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Remember Me checkbox
        CheckBox rememberMeCheckBox = new CheckBox("Remember me");
        rememberMeCheckBox.setStyle("-fx-text-fill: white;");

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #6a4e23; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> {
            System.out.println("Login button clicked.");
        });

        // Add components to the form container
        formContainer.getChildren().addAll(titleLabel, welcomeLabel, emailLabel, emailField, passwordLabel, passwordField, rememberMeCheckBox, loginButton);

        // Right side: Image of plant
        VBox imageContainer = new VBox();
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setStyle("-fx-background-color: #f4eccc;");
        Image plantImage = new Image("file:src/main/resources/com/example/spms/plant.png"); // Adjust the image path as needed
        ImageView plantImageView = new ImageView(plantImage);
        plantImageView.setFitHeight(200);
        plantImageView.setPreserveRatio(true);
        imageContainer.getChildren().add(plantImageView);

        // Main layout: HBox to place the form and image side by side
        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(formContainer, imageContainer);

        // Proportional resizing
        HBox.setHgrow(formContainer, Priority.ALWAYS);
        HBox.setHgrow(imageContainer, Priority.ALWAYS);

        // Set the proportions of the left and right sides
        mainLayout.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            formContainer.setPrefWidth(totalWidth * 0.45); // 45% for the form container
            imageContainer.setPrefWidth(totalWidth * 0.55); // 55% for the image container
        });

        // Create the scene and set it
        Scene scene = new Scene(mainLayout, 800, 400); // Default size, but flexible
        primaryStage.setTitle("Login UI");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); // Allow resizing and maximizing
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
