import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class User {
    private String name,email,password;
    private String id;
    final String userType="basic";

    private FirebaseApp mAuth;
    boolean UploadAccount()
    {


        return true;
    }

    boolean LoadAccount()
    {
        return true;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

}
