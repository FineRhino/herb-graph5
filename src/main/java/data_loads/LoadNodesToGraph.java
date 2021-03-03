package data_loads;

import model.DataFile;
import utilities.Neo4jConnection;
import utilities.Neo4jConnectionProperties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LoadNodesToGraph {
    public void loadFiles(ArrayList<DataFile> dataNodeFiles,
                          Neo4jConnectionProperties neo4jConnectionProperties, String splitBy) {

        int i = 0;
        while (i < dataNodeFiles.size()) {
            DataFile dataFile = new DataFile();
            dataFile.setFilePath(dataNodeFiles.get(i).getFilePath().toString());
            dataFile.setFileName(dataNodeFiles.get(i).getFileName().toString());
            dataFile.setFileExtension(dataNodeFiles.get(i).getFileExtension().toString());
            dataFile.setFileLoadIndicator(dataNodeFiles.get(i).getFileLoadIndicator().toString());
            loadFileToNodes(
                    dataFile.getFileName(),
                    dataFile.getFilePath() + dataFile.getFileName() + "." + dataFile.getFileExtension(),splitBy.toString(),
                    neo4jConnectionProperties);
            i++;
        }
    }

    public void loadFileToNodes(String nodeName, String fileName, String splitBy, Neo4jConnectionProperties neo4jConnectionProperties ) {

        try {
            //Create Neo4jConnection
            Neo4jConnection neo4jConnection = new Neo4jConnection(neo4jConnectionProperties);

            //Read In DataFile
            BufferedReader br   = null;
            String line         = "";
            int lineCount       = 0;

            //Read First Line to Get Attribute Names and then Iterate over DataFile and Load Each Record to Graph
            try {
                br = new BufferedReader(new FileReader(fileName));
                ArrayList<String> fileAttributes = new ArrayList<String>();
                ArrayList<String> record         = new ArrayList<String>();

                while ((line = br.readLine()) != null) {
                    StringBuilder sb                 = new StringBuilder();
                    sb.append("CREATE (n:" + nodeName + ") ");

                    StringTokenizer tokenizer = new StringTokenizer(line, splitBy);
                    //System.out.println(lineCount);
                    Integer tokenCount      = 0;
                    Integer maxTokens       = 0;
                    while (tokenizer.hasMoreTokens()) {
                        if (lineCount == 0) {
                            fileAttributes.add(tokenCount, tokenizer.nextToken());
                            System.out.println("Header is " + fileAttributes.get(tokenCount).toString());
                        } else {
                            maxTokens = fileAttributes.size()-1;
                            record.add(tokenCount, tokenizer.nextToken());
                            //System.out.println("Record is " + record.get(tokenCount).toString());
                            if ((tokenCount == 0) && !(tokenCount.equals(maxTokens))){
                                sb.append("SET n." + fileAttributes.get(tokenCount) + "=\"" + record.get(tokenCount).toString() + "\",");
                            }
                            else if ((tokenCount == 0) && (tokenCount.equals(maxTokens))){
                                sb.append("SET n." + fileAttributes.get(tokenCount) + "=\"" + record.get(tokenCount).toString() + "\"");
                            }
                            else if ((tokenCount != 0) && (tokenCount.equals(maxTokens))) {
                                sb.append(" n." + fileAttributes.get(tokenCount) + "=\"" + record.get(tokenCount).toString() + "\"");
                            }
                            else {
                                sb.append(" n." + fileAttributes.get(tokenCount) + "=\"" + record.get(tokenCount).toString() + "\",");
                            }
                        }
                        tokenCount++;
                    }
                    if (lineCount != 0) {
                        sb.append(" RETURN 'n." + nodeName + "'");
                        String statement = sb.toString();
                        System.out.println(statement);
                        neo4jConnection.executeStatementTwo(statement);
                    }
                    lineCount++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println(lineCount);
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
