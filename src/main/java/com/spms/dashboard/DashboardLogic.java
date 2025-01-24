package com.spms.dashboard;

import com.spms.login.Auth;
import javafx.stage.Stage;

public class DashboardLogic {

    private static Stage currentStage;

    public static void clickLightButton() {
        closeCurrentStage();
        lightUI light = new lightUI();
        Stage lightStage = new Stage();
        try {
            light.start(lightStage);
            currentStage = lightStage;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void clickTempButton() {
        closeCurrentStage();
        temperatureUI temp = new temperatureUI();
        Stage tempStage = new Stage();
        try {
            temp.start(tempStage);
            currentStage = tempStage;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void clickSoilButton() {
        if(Auth.role.equals("Premium User") | Auth.role.equals("Admin")) {
            closeCurrentStage();
            soilMoistureUI soil = new soilMoistureUI();
            Stage soilStage = new Stage();
            try {
                soil.start(soilStage);
                currentStage = soilStage;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else{
            undoneFeatureUI feature = new undoneFeatureUI();
            Stage featureStage = new Stage();
            try {
                feature.start(featureStage);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void openDashboard() {
        switch (Auth.role) {
            case "User":
                closeCurrentStage();
                userUI user = new userUI();
                Stage userStage = new Stage();
                try {
                    user.start(userStage);
                    currentStage = userStage;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Premium User":
                closeCurrentStage();
                DashboardUI premiumUser = new DashboardUI();
                Stage premiumUserStage = new Stage();
                try {
                    premiumUser.start(premiumUserStage);
                    currentStage = premiumUserStage;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Admin":
                closeCurrentStage();
                adminUI admin = new adminUI();
                Stage adminStage = new Stage();
                try {
                    admin.start(adminStage);
                    currentStage = adminStage;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid role");
                break;
        }
    }

    private static void closeCurrentStage() {
        if (currentStage != null) {
            currentStage.close(); // Close the current stage
            currentStage = null;  // Reset the reference
        }
    }

}
