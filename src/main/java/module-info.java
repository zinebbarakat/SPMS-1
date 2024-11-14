module com.example.spms {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spms to javafx.fxml;
    exports com.example.spms;
}