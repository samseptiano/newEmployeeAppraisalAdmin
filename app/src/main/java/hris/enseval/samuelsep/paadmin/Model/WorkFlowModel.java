package hris.enseval.samuelsep.paadmin.Model;

/**
 * Created by Samuel Septiano on 10,June,2019
 */
public class WorkFlowModel {
    private String id;
    private String posisi;
    private String dept;
    private String penilai1;
    private String penilai2;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    private String workflowName;

    public WorkFlowModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPenilai1() {
        return penilai1;
    }

    public void setPenilai1(String penilai1) {
        this.penilai1 = penilai1;
    }

    public String getPenilai2() {
        return penilai2;
    }

    public void setPenilai2(String penilai2) {
        this.penilai2 = penilai2;
    }
}
