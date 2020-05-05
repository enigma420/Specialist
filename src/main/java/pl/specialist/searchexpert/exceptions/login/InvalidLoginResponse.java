package pl.specialist.searchexpert.exceptions.login;

public class InvalidLoginResponse {

    private String mail;
    private String password;

    public InvalidLoginResponse() {
        this.mail = "Invalid Mail";
        this.password = "Invalid Password";
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}