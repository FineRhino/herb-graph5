package utilities;

public class Neo4jConnectionProperties {
    private String uri;
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public Neo4jConnectionProperties (EnvironmentProperties properties){
        this.setUri(properties.getNeo4jUri());
        this.setUserName(properties.getNeo4jUserName());
        this.setPassword(properties.getNeo4jPassword());
    }
}
