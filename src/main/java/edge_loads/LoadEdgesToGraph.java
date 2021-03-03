package edge_loads;

import model.DataFile;
import utilities.Neo4jConnection;
import utilities.Neo4jConnectionProperties;

import java.util.ArrayList;

public class LoadEdgesToGraph {
    public void loadFiles(ArrayList<DataFile> dataEdgeFiles,
                          Neo4jConnectionProperties neo4jConnectionProperties, String splitBy) {
        System.out.println("About to load edges for " + dataEdgeFiles.size() + " files");
        try {
            int i=0;
            while (i < dataEdgeFiles.size()){
                DataFile dataFile = new DataFile();
                dataFile.setFilePath(dataEdgeFiles.get(i).getFilePath().toString());
                dataFile.setFileName(dataEdgeFiles.get(i).getFileName().toString());
                dataFile.setFileExtension(dataEdgeFiles.get(i).getFileExtension().toString());
                dataFile.setFileLoadIndicator(dataEdgeFiles.get(i).getFileLoadIndicator().toString());
                if(dataEdgeFiles.get(i).getFileLoadIndicator().equalsIgnoreCase("Y")){
                    System.out.println("About to load " + dataFile.getFileName());
                    loadFileToEdges(
                            dataFile.getFilePath(),
                            dataFile.getFileName(),
                            dataFile.getFileExtension(),
                            splitBy.toString(),
                            neo4jConnectionProperties);
                }
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void loadFileToEdges(String filePath,
                                String fileName,
                                String fileExtension,
                                String splitBy,
                                Neo4jConnectionProperties neo4jConnectionProperties ) {

        try {
            //Create Neo4jConnection
            Neo4jConnection neo4jConnection = new Neo4jConnection(neo4jConnectionProperties);

            //Read In DataFile
            java.io.BufferedReader br   = null;
            String line             = "";
            int lineCount           = 0;
            Integer tokenCount      = 0;
            String nodeOneKey       = "";
            String nodeTwoKey       = "";
            String edgeType         = fileName;
            String nodeOneKeyValue  = "";
            String nodeTwoKeyValue  = "";
            String fName            = filePath + fileName + "." + fileExtension;
            System.out.println("Edge filename is " + fName);
            ArrayList<String> nodeNames = parseEdge(fileName);
            System.out.println("Node 1 is " + nodeNames.get(0) + " and Node 2 is " + nodeNames.get(1));
            String nodeNameOne      = nodeNames.get(0).toString().trim();
            String nodeNameTwo      = nodeNames.get(1).toString().trim();
            //Read First Line to Get Attribute Names and then Iterate over DataFile and Load Each Record to Graph
            try {
                br = new java.io.BufferedReader(new java.io.FileReader(fName));
                ArrayList<String> fileAttributes = new ArrayList<String>();
                ArrayList<String> record         = new ArrayList<String>();

                while ((line = br.readLine()) != null) {
                    System.out.println("Line Count is " + lineCount);
                    if (lineCount == 0){
                        StringBuilder sb = new StringBuilder();
                        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(line, splitBy);
                        while (tokenizer.hasMoreTokens()) {
                            fileAttributes.add(tokenCount, tokenizer.nextToken());
                            /*
                            //TODO - Add logic for adding edge properties
                            if (tokenCount == 2) {
                                System.out.println("Token Count is " + tokenCount);
                                System.out.println("Header is " +
                                    fileAttributes.get(0).toString() + " " +
                                    fileAttributes.get(1).toString());
                                System.out.println("Token Count is " + tokenCount);
                                System.out.println("Line Count is " + lineCount);
                            }
                            */
                            tokenCount++;
                        }
                        nodeOneKey = fileAttributes.get(0);
                        nodeTwoKey = fileAttributes.get(1);
                    }
                    if (lineCount > 0){
                        StringBuilder sb = new StringBuilder();
                        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(line, splitBy);
                        tokenCount = 0;
                        while (tokenizer.hasMoreTokens()){
                            record.add(tokenCount, tokenizer.nextToken());
                            if (tokenCount == 1){
                                nodeOneKeyValue = record.get(0);
                                nodeTwoKeyValue = record.get(1);
                                System.out.println("Record is " +
                                        record.get(0).toString() + " " +
                                        record.get(1).toString());
                                System.out.println("Token Count is " + tokenCount);
                                System.out.println("Line count is " + lineCount);

                                sb.append("MATCH (n1:" + nodeNameOne + "), (n2:" + nodeNameTwo + ") ");
                                sb.append("WHERE n1." + nodeOneKey + "=\"" + nodeOneKeyValue + "\" AND n2." + nodeTwoKey + "=\"" + nodeTwoKeyValue + "\" ");
                                sb.append("CREATE (n1)-[r:" + edgeType + "]->(n2) RETURN type(r)");
                                String statement = sb.toString();
                                System.out.println("Statement is " + statement);
                                neo4jConnection.executeStatementTwo(statement);
                            }
                            tokenCount++;
                        }
                    }
                    lineCount++;
                }
            } catch (java.io.FileNotFoundException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Line count is " + lineCount);
                if (br != null) {
                    try {
                        br.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    private ArrayList<String> parseEdge(String fName){
        ArrayList<String> edgeName = new ArrayList<String>();
        ArrayList<String> nodeNames = new ArrayList<String>();
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(fName, "_");
        int tokenCount = 0;
        while (tokenizer.hasMoreTokens()) {
            edgeName.add(tokenCount, tokenizer.nextToken());
            System.out.println("Token Count is " + tokenCount);
            System.out.println(edgeName.get(tokenCount));
            tokenCount++;
        }
        nodeNames.add(0,edgeName.get(0).toString());
        nodeNames.add(1,edgeName.get(tokenCount-1).toString());
        return nodeNames;
    }
}
