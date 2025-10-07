package com.saveetha.hirebridge;

public class LoginResponse {
    private boolean success;
    private String message;
    private UserData user;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserData getUser() {
        return user;
    }

    public static class UserData {
        private int id;
        private String email;
        private String username;
        private String phone;
        private boolean detailsFilled;

        public int getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getPhone() {
            return phone;
        }

        public boolean isDetailsFilled() {
            return detailsFilled;
        }
    }
}
