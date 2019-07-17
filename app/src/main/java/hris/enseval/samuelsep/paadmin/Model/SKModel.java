package hris.enseval.samuelsep.paadmin.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hris.enseval.samuelsep.paadmin.utils.PDFCreationUtils;

/**
 * Created by Samuel Septiano on 28,June,2019
 */
public class SKModel {
    String nama, nik, joinDate,finalDate, status, dept, jobTitleName, companyName, locationName, skor;

    public SKModel() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getSkor() {
        return skor;
    }

    public void setSkor(String skor) {
        this.skor = skor;
    }


    public static List<SKModel> createDummyPdfModel(SKModel skModel) {
        PDFCreationUtils.filePath.clear();
        PDFCreationUtils.progressCount = 1;

        boolean isFirstReceivedItem = false;
        List<SKModel> pdfModels = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Random rand = new Random();
            int price = rand.nextInt((1000 - 200) + 1) + 200;

            SKModel model = new SKModel();

            model.setNama(skModel.getNama());
            model.setNik(skModel.getNik());
            model.setCompanyName(skModel.getCompanyName());
            model.setLocationName(skModel.getLocationName());
            model.setDept(skModel.getDept());
            model.setJobTitleName(skModel.getJobTitleName());
            model.setStatus(skModel.getStatus());
            model.setJoinDate(skModel.getJoinDate());
            model.setFinalDate(skModel.getFinalDate());
            model.setSkor(skModel.getSkor());

            pdfModels.add(model);
        }

        return pdfModels;
    }


}
