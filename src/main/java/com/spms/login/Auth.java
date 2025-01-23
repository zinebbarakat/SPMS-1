package com.spms.login;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spms.login.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public void registerUser(String email, String password, String role) {
        List<User> users = getUsersFromJSON();
        if (users == null) {
            users = new ArrayList<>();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);

        users.add(newUser);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
