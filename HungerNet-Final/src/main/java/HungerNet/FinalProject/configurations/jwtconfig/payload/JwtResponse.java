package HungerNet.FinalProject.configurations.jwtconfig.payload;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;

    private final List<String> roles;

    public JwtResponse(String jwttoken,String username,List<String> roles) {
        this.jwttoken = jwttoken;
        this.username=username;
        this.roles=roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
