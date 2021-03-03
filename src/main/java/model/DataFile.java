package model;

public class DataFile {
    private String  filePath;
    private String  fileName;
    private String  fileExtension;
    private String  fileDelimiter;
    private String  fileLoadIndicator;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(String fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }

    public String getFileLoadIndicator() {
        return fileLoadIndicator;
    }

    public void setFileLoadIndicator(String fileLoadIndicator) {
        this.fileLoadIndicator = fileLoadIndicator;
    }

}
