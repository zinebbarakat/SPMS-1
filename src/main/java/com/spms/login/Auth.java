package com.example.login;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Auth {
    private static final String JSON_FILE_PATH = "src/main/resources/users.json";

    private List<User> getUsersFromJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(JSON_FILE_PATH), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateUser(String email, String password) {
        List<User> users = getUsersFromJSON();
        if (users == null) {
            return false;
        }

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}