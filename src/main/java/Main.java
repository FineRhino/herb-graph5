import data_loads.LoadNodesToGraph;
import edge_loads.LoadEdgesToGraph;
import model.DataFile;
import utilities.EnvironmentProperties;
import utilities.FileList;
import utilities.Neo4jConnection;
import utilities.Neo4jConnectionProperties;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");

        EnvironmentProperties properties = new EnvironmentProperties();
        properties = properties.createProperties("localProperties.properties");

        //Create Neo4jConnectionProperties Instance From Properties
        Neo4jConnectionProperties neo4jConnectionProperties = new Neo4jConnectionProperties(properties);
        try {
            try (Neo4jConnection neo4jConnection = new Neo4jConnection(neo4jConnectionProperties)) {
            }
            FileList fl = new FileList();
            ArrayList<DataFile> dataNodeFiles = new ArrayList<DataFile>();
            dataNodeFiles = fl.getFilesToLoad("data/files/node-files.csv");

            ArrayList<DataFile> dataEdgeFiles = new ArrayList<DataFile>();
            dataEdgeFiles = fl.getFilesToLoad("data/files/edge-files.csv");

            LoadNodesToGraph loadNodes = new LoadNodesToGraph();
            loadNodes.loadFiles(dataNodeFiles,neo4jConnectionProperties,"|");

            LoadEdgesToGraph loadEdges = new LoadEdgesToGraph();
            loadEdges.loadFiles(dataEdgeFiles, neo4jConnectionProperties,"|");
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

