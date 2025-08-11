package models;

public class AuthResponse {

    private final String token;
    private final int expiresIn;
    public AuthResponse(String token, int expiresIn){
        this.token = token;
        this.expiresIn = expiresIn;
    }
    public String getToken() { return token;}
    public int getExpiresIn() { return expiresIn;}
}
