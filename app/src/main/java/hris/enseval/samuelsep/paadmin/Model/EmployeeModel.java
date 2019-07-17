package hris.enseval.samuelsep.paadmin.Model;

import java.io.Serializable;

/**
 * Created by Samuel Septiano on 23,May,2019
 */
public class EmployeeModel implements Serializable {
    private boolean isChecked = false;

    private String id;
    private String NIK;
    private String nama;
    private String email;
    private String jobTitleName;
    private String jobTitleCode;
    private String dept;
    private String joinDate;
    private String finalDate;
    private String companyName;
    private String companyCode;
    private String locationName;
    private String locationCode;
    private String photo; //14

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    private String Status;
    private String atasanLangsung;
    private String NIKAtasanLangsung;
    private String emailAtasanLangsung;
    private String atasanTidakLangsung;
    private String NIKAtasanTidakLangsung;
    private String EmailAtasantakLangsung;
    private String phoneAtasanLangsung;

    public String getPhoneAtasanLangsung() {
        return phoneAtasanLangsung;
    }

    public void setPhoneAtasanLangsung(String phoneAtasanLangsung) {
        this.phoneAtasanLangsung = phoneAtasanLangsung;
    }

    public String getPhoneAtasanTaklangsung() {
        return phoneAtasanTaklangsung;
    }

    public void setPhoneAtasanTaklangsung(String phoneAtasanTaklangsung) {
        this.phoneAtasanTaklangsung = phoneAtasanTaklangsung;
    }

    private String phoneAtasanTaklangsung; //7

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String jobTitleCode) {
        this.jobTitleCode = jobTitleCode;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAtasanLangsung() {
        return atasanLangsung;
    }

    public void setAtasanLangsung(String atasanLangsung) {
        this.atasanLangsung = atasanLangsung;
    }

    public String getNIKAtasanLangsung() {
        return NIKAtasanLangsung;
    }

    public void setNIKAtasanLangsung(String NIKAtasanLangsung) {
        this.NIKAtasanLangsung = NIKAtasanLangsung;
    }

    public String getEmailAtasanLangsung() {
        return emailAtasanLangsung;
    }

    public void setEmailAtasanLangsung(String emailAtasanLangsung) {
        this.emailAtasanLangsung = emailAtasanLangsung;
    }

    public String getAtasanTidakLangsung() {
        return atasanTidakLangsung;
    }

    public void setAtasanTidakLangsung(String atasanTidakLangsung) {
        this.atasanTidakLangsung = atasanTidakLangsung;
    }

    public String getNIKAtasanTidakLangsung() {
        return NIKAtasanTidakLangsung;
    }

    public void setNIKAtasanTidakLangsung(String NIKAtasanTidakLangsung) {
        this.NIKAtasanTidakLangsung = NIKAtasanTidakLangsung;
    }

    public String getEmailAtasantakLangsung() {
        return EmailAtasantakLangsung;
    }

    public void setEmailAtasantakLangsung(String emailAtasantakLangsung) {
        EmailAtasantakLangsung = emailAtasantakLangsung;
    }

    public EmployeeModel(boolean isChecked, String id, String NIK, String nama, String email, String jobTitleName, String jobTitleCode, String dept, String joinDate, String finalDate, String companyName, String companyCode, String locationName, String locationCode, String status, String atasanLangsung, String NIKAtasanLangsung, String emailAtasanLangsung, String atasanTidakLangsung, String NIKAtasanTidakLangsung, String emailAtasantakLangsung) {
        this.isChecked = isChecked;
        this.id = id;
        this.NIK = NIK;
        this.nama = nama;
        this.email = email;
        this.jobTitleName = jobTitleName;
        this.jobTitleCode = jobTitleCode;
        this.dept = dept;
        this.joinDate = joinDate;
        this.finalDate = finalDate;
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.locationName = locationName;
        this.locationCode = locationCode;
        Status = status;
        this.atasanLangsung = atasanLangsung;
        this.NIKAtasanLangsung = NIKAtasanLangsung;
        this.emailAtasanLangsung = emailAtasanLangsung;
        this.atasanTidakLangsung = atasanTidakLangsung;
        this.NIKAtasanTidakLangsung = NIKAtasanTidakLangsung;
        EmailAtasantakLangsung = emailAtasantakLangsung;
    }

    public EmployeeModel() {
    }
}
