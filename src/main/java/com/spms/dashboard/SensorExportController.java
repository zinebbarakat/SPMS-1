package com.spms.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class SensorExportController {
    public void showExportDialog(Stage ownerStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ownerStage);
        dialog.setTitle("Export Sensor Data");

        Label limitLabel = new Label("Record Limit:");
        TextField recordLimitField = new TextField();

        Label typeLabel = new Label("Sensor Type:");
        ObservableList<String> sensorTypes = FXCollections.observableArrayList("All", "Temperature", "Light", "Soil Moisture");
        ComboBox<String> sensorTypeComboBox = new ComboBox<>(sensorTypes);
        sensorTypeComboBox.getSelectionModel().selectFirst();

        Button exportButton = new Button("Export");
        exportButton.setOnAction((ActionEvent event) -> {
            String selectedType = sensorTypeComboBox.getValue();
            String limitText = recordLimitField.getText();
            int limit;

            try {
                limit = Integer.parseInt(limitText);
            } catch (NumberFormatException e) {
                showAlert("Invalid Limit", "Please enter a valid number for the record limit.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(dialog);

            if (file != null) {
                exportSensorDataToCSV(file, selectedType, limit);
                dialog.close();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(limitLabel, 0, 0);
        grid.add(recordLimitField, 1, 0);
        grid.add(typeLabel, 0, 1);
        grid.add(sensorTypeComboBox, 1, 1);
        grid.add(exportButton, 1, 2);

        Scene scene = new Scene(grid, 350, 180);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void exportSensorDataToCSV(File file, String sensorType, int limit) {
        String url = "jdbc:mysql://localhost:3306/spms";
        String user = "root";
        String password = "";

        String sql = "SELECT * FROM measurements";
        if (!sensorType.equals("All")) {
            sql += " WHERE sensor_ID = ?";
        }
        sql += " ORDER BY timeStamp DESC LIMIT ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (!sensorType.equals("All")) {
                stmt.setString(1, sensorType);
                stmt.setInt(2, limit);
            } else {
                stmt.setInt(1, limit);
            }

            ResultSet rs = stmt.executeQuery();
            FileWriter writer = new FileWriter(file);
            writer.write("ID,Type,Value,Timestamp\n");

            while (rs.next()) {
                writer.write(rs.getInt("id") + "," +
                        rs.getString("type") + "," +
                        rs.getDouble("value") + "," +
                        rs.getTimestamp("timestamp") + "\n");
            }

            writer.close();
            showAlert("Export Successful", "Sensor data exported successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Export Failed", "An error occurred during export.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
