package entity;

public class User {

    private String username;
    private String password;
    private String id;
    private String address;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {this.id = id;}

    public void setAddress(String address) {this.address = address;}

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
