module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires java.sql;

    opens com.spms.login to javafx.fxml;
    exports com.spms.login;
    opens com.NLS.spms to javafx.fxml;
    //opens com.spms.dashboard to javafx.fxml;
    exports com.spms.dashboard;
}
