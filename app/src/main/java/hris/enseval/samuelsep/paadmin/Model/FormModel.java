package hris.enseval.samuelsep.paadmin.Model;

import java.io.Serializable;

/**
 * Created by Samuel Septiano on 10,June,2019
 */
public class FormModel implements Serializable {
    private String id;
    private String formName;
    private String direktorat;
    private String posisi;
    private String fileString;
    private String filePath;
    private String fileExtension;


    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDirektorat() {
        return direktorat;
    }

    public void setDirektorat(String direktorat) {
        this.direktorat = direktorat;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getFileString() {
        return fileString;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FormModel() {
    }
}
