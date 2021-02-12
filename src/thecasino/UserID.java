package thecasino;

public class UserID {

    private String username;
    private String password;
    private int currency;
    
    public UserID(String username, String password, int currency) {
        this.username = username;
        this.password = password;
        this.currency = currency;
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
    
}
