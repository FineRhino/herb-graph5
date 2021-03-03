package utilities;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class EnvironmentProperties {
    private String neo4jUri;
    private String neo4jUserName;
    private String neo4jPassword;
    //TODO
    // Add Graph Name. Currently this loads to the default neo4j graph instance running

    public String getNeo4jUri() {
        return neo4jUri;
    }

    public void setNeo4jUri(String neo4jUri) {
        this.neo4jUri = neo4jUri;
    }

    public String getNeo4jUserName() {
        return neo4jUserName;
    }

    public void setNeo4jUserName(String neo4jUserName) {
        this.neo4jUserName = neo4jUserName;
    }

    public String getNeo4jPassword() {
        return neo4jPassword;
    }

    public void setNeo4jPassword(String neo4jPassword) {
        this.neo4jPassword = neo4jPassword;
    }

    public EnvironmentProperties createProperties(String propertiesFile) {

        EnvironmentProperties properties = new EnvironmentProperties();
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            //String propFileName = "localProperties.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propertiesFile.toString() + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            properties.setNeo4jUri(prop.getProperty("neo4jUri"));
            properties.setNeo4jUserName(prop.getProperty("neo4jUserName"));
            properties.setNeo4jPassword(prop.getProperty("neo4jPassword"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            }
            catch (Exception ex){
                System.out.println(ex);
            }
        }
        return properties;
    }
}
