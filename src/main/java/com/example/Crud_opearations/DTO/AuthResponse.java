package com.example.Crud_opearations.DTO;

public class AuthResponse {
//    private String message;
//    private boolean isAdmin;
//
//    // Constructor
//    public AuthResponse(String message, boolean isAdmin) {
//        this.message = message;
//        this.isAdmin = isAdmin;
//    }
//
//    // Getter for message
//    public String getMessage() {
//        return message;
//    }
//
//    // Setter for message
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    // Getter for isAdmin
//    public boolean isAdmin() {
//        return isAdmin;
//    }
//
//    // Setter for isAdmin
//    public void setIsAdmin(boolean isAdmin) {
//        this.isAdmin = isAdmin;
//    }




    private String message;
    private boolean isAdmin;
    private String token;

    public AuthResponse(String message, boolean isAdmin , String token) {
        this.message = message;
        this.isAdmin = isAdmin;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
