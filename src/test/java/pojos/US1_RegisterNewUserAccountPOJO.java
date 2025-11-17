package pojos;

public class US1_RegisterNewUserAccountPOJO {

    private String email;
    private String name;
    private String password;
    private String password_confirmation;

    public US1_RegisterNewUserAccountPOJO() {
    }

    public US1_RegisterNewUserAccountPOJO(String email,String name,String password,String confirmPassword) {
        this.email = email;
        this.name=name;
        this.password=password;
        this.password_confirmation =password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "US1_RegisterNewUserAccountPOJO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + password_confirmation + '\'' +
                '}';
    }
}
