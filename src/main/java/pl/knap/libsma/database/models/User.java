package pl.knap.libsma.database.models;

public class User {
    private Integer id;
    private String login, password, type;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String type) {
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public User(String login, String password, String type, Integer id) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
