package hris.enseval.samuelsep.paadmin.Model.TokenAuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogin {
    public UserLogin(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @SerializedName("username")
    @Expose
    private String Username;
    @SerializedName("password")
    @Expose
    private String Password;
}
