package com.spms.session;

import com.spms.login.User;

public class Session {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getUserId() {
        return currentUser != null ? currentUser.getId() : -1;
    }

    public static String getUsername() {
        return currentUser != null ? currentUser.getName() : null;
    }

    public static String getRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
}
