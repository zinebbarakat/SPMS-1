package com.spms.login;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.sql.SQLException;

import static com.spms.dashboard.DashboardLogic.openDashboard;

public class LoginUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox formContainer = new VBox();
        formContainer.setAlignment(Pos.CENTER_LEFT);
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #386641;");
        formContainer.setPrefWidth(350);

        Label titleLabel = new Label("Smart Plant Monitoring System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label welcomeLabel = new Label("Welcome to our App");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-text-fill: white;");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        CheckBox rememberMeCheckBox = new CheckBox("Remember me");
        rememberMeCheckBox.setStyle("-fx-text-fill: white;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #684d42; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> {
            System.out.println("Login button clicked.");
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.");
                alert.show();
                return;
            }

            Auth userDAO = new Auth();
            try {
                if (userDAO.validateUser(email, password)) {
                    openDashboard();
                    ((Stage) loginButton.getScene().getWindow()).close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password.");
                    alert.show();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink signUpLink = new Hyperlink("Sign Up");
        signUpLink.setStyle("-fx-text-fill: white; -fx-underline: true;");
        signUpLink.setOnAction(e -> {
            Stage registerStage = new Stage();
            registerStage.setTitle("Register");

            VBox registerContainer = new VBox(15);
            registerContainer.setAlignment(Pos.CENTER);
            registerContainer.setPadding(new Insets(20));

            Label roleLabel = new Label("Who are you registering as?");
            ToggleGroup roleGroup = new ToggleGroup();
            RadioButton userRadio = new RadioButton("Basic");
            userRadio.setToggleGroup(roleGroup);
            RadioButton premiumUserRadio = new RadioButton("Premium");
            premiumUserRadio.setToggleGroup(roleGroup);
            RadioButton adminRadio = new RadioButton("Admin");
            adminRadio.setToggleGroup(roleGroup);

            HBox roleSelection = new HBox(10, userRadio, premiumUserRadio, adminRadio);
            roleSelection.setAlignment(Pos.CENTER);

            // Name field (NEW)
            Label nameLabel = new Label("Name:");
            TextField nameField = new TextField();
            nameField.setPromptText("Enter your full name");

            Label newEmailLabel = new Label("Email:");
            TextField newEmailField = new TextField();
            newEmailField.setPromptText("Enter your email");

            Label newPasswordLabel = new Label("Password:");
            PasswordField newPasswordField = new PasswordField();
            newPasswordField.setPromptText("Enter your password");

            Button registerButton = new Button("Register");
            registerButton.setStyle("-fx-background-color: #684d42; -fx-text-fill: white; -fx-font-size: 14px;");
            registerButton.setOnAction(event -> {
                String selectedRole = roleGroup.getSelectedToggle() != null ? ((RadioButton) roleGroup.getSelectedToggle()).getText() : "";
                String name = nameField.getText();
                String email = newEmailField.getText();
                String password = newPasswordField.getText();

                if (selectedRole.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields and select a role.");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registered successfully as " + selectedRole + "!");
                    alert.show();

                    Auth userDAO = new Auth();
                    try {
                        // ✅ Updated to include name
                        userDAO.registerUser(email, password, selectedRole, name);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    registerStage.close();
                }
            });

            registerContainer.getChildren().addAll(
                    roleLabel, roleSelection,
                    nameLabel, nameField,
                    newEmailLabel, newEmailField,
                    newPasswordLabel, newPasswordField,
                    registerButton
            );

            Scene registerScene = new Scene(registerContainer, 400, 400);
            registerStage.setScene(registerScene);
            registerStage.show();
        });

        HBox loginSignUpContainer = new HBox(10);
        loginSignUpContainer.setAlignment(Pos.CENTER_LEFT);
        loginSignUpContainer.getChildren().addAll(loginButton, signUpLink);

        formContainer.getChildren().addAll(
                titleLabel, welcomeLabel,
                emailLabel, emailField,
                passwordLabel, passwordField,
                rememberMeCheckBox, loginSignUpContainer
        );

        VBox imageContainer = new VBox();
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setStyle("-fx-background-color: #f2e8ce;");
        Image plantImage = new Image("file:src/main/resources/com/NLS/spms/plant.png");
        ImageView plantImageView = new ImageView(plantImage);
        plantImageView.setFitHeight(200);
        plantImageView.setPreserveRatio(true);
        imageContainer.getChildren().add(plantImageView);

        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(formContainer, imageContainer);
        mainLayout.setStyle("-fx-background-color: #f2e8ce;");

        HBox.setHgrow(formContainer, Priority.ALWAYS);
        HBox.setHgrow(imageContainer, Priority.ALWAYS);

        mainLayout.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            formContainer.setPrefWidth(totalWidth * 0.45);
            imageContainer.setPrefWidth(totalWidth * 0.55);
        });

        Scene scene = new Scene(mainLayout, 800, 400);
        primaryStage.setTitle("SPMS Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(400);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
