module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.spms.login to javafx.fxml;
    exports com.spms.login;
}
