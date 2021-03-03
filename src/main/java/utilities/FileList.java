package utilities;

import model.DataFile;

import java.util.ArrayList;

public class FileList {
    public static ArrayList<DataFile> getFilesToLoad(String inFile) {
        //Read In Files DataFile
        int lineCount = 0;
        java.io.BufferedReader br = null;
        ArrayList<DataFile> dataFiles = new ArrayList<DataFile>();
        try{

            String line = "";

            br = new java.io.BufferedReader(new java.io.FileReader(inFile));

            while ((line = br.readLine()) != null) {
                java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(line, "|");
                DataFile dataFile = new DataFile();
                dataFile.setFilePath(tokenizer.nextToken().toString());
                dataFile.setFileName(tokenizer.nextToken().toString());
                dataFile.setFileExtension(tokenizer.nextToken().toString());
                dataFile.setFileLoadIndicator(tokenizer.nextToken().toString());
                dataFiles.add(dataFile);
                lineCount++;
            }
            System.out.println("Data Files Count for " + inFile.toString() + " is " + dataFiles.size());
        }
        catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(lineCount);
            if (br != null) {
                try {
                    br.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataFiles;
    }
}
