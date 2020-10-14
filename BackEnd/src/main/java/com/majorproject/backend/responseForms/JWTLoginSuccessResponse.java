package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.User;

public class JWTLoginSuccessResponse {
    private boolean success;
    private String token;
    private User user;

    public JWTLoginSuccessResponse(boolean success, String token, User user) {
        this.success = success;
        this.token = token;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "JWTLoginSuccessResponse{" +
                "success=" + success +
                ", user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
