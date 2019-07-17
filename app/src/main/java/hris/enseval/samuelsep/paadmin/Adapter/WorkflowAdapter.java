package hris.enseval.samuelsep.paadmin.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hris.enseval.samuelsep.paadmin.Model.FormModel;
import hris.enseval.samuelsep.paadmin.Model.WorkFlowModel;
import hris.enseval.samuelsep.paadmin.R;

/**
 * Created by Samuel Septiano on 25,June,2019
 */
public class WorkflowAdapter extends RecyclerView.Adapter<WorkflowAdapter.MyViewHolder> {

    private List<WorkFlowModel> homeList;
    private Context context;
    private ArrayList<WorkFlowModel> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;

    public interface EventListener {
        void getCheckedData(List<WorkFlowModel> FormList);
        void test();
    }

    EventListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWorkflowName, tvDept, tvPosisi, tvApprover1, tvApprover2;
        LinearLayout lnEmployeeList;

        public MyViewHolder(View view) {
            super(view);
            tvWorkflowName = (TextView) itemView.findViewById(R.id.tv_workflowName);
            tvDept = (TextView) itemView.findViewById(R.id.tv_dept);
            tvPosisi = (TextView) itemView.findViewById(R.id.tv_position);
            tvApprover1 = (TextView) itemView.findViewById(R.id.tv_approver_1);
            tvApprover2 = (TextView) itemView.findViewById(R.id.tv_approver_2);
            lnEmployeeList = (LinearLayout) itemView.findViewById(R.id.lnEmployeeList);
        }
    }

    public WorkflowAdapter(List<WorkFlowModel> homeList, Context context, Boolean isConnected, Activity activity, EventListener listener) {
        this.context = context;
        this.homeList = homeList;
        this.homeListFilter.addAll(homeList);
        this.isConnected = isConnected;
        this.activity = activity;
        this.listener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workflow_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WorkFlowModel home = homeList.get(position);

        holder.tvWorkflowName.setText(home.getWorkflowName());
        holder.tvPosisi.setText(home.getPosisi());
        holder.tvDept.setText(home.getDept());
        holder.tvApprover1.setText(home.getPenilai1());
        holder.tvApprover2.setText(home.getPenilai2());

        holder.lnEmployeeList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popup = new PopupMenu(context, v);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.employee_list_popup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("delete")) {
                           showDialogDelete(position);
                        }
                        if (item.getTitle().equals("edit")) {
                            showDialogWorkflow(home);
                        }
                        Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        homeList.clear();
        if (charText.length() == 0) {
            homeList.addAll(homeListFilter);
        } else {
            for (WorkFlowModel wp : homeListFilter) {
                if (wp.getWorkflowName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    homeList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    private void showDialogWorkflow(WorkFlowModel wm){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.workflow_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText id, workflowname, posisi, dept, penilai1,penilai2;
        TextView tvId;

        id = dialog.findViewById(R.id.edt_id);
        dept = dialog.findViewById(R.id.edt_dept);
        posisi = dialog.findViewById(R.id.edt_position);
        penilai1 = dialog.findViewById(R.id.edt_approver_1);
        penilai2 = dialog.findViewById(R.id.edt_approver_2);
        workflowname = dialog.findViewById(R.id.edt_workflow_title);
        tvId = dialog.findViewById(R.id.tv_id);
//
        id.setText(wm.getId());
        dept.setText(wm.getDept());
        posisi.setText(wm.getPosisi());
        workflowname.setText(wm.getWorkflowName());
        penilai1.setText(wm.getPenilai1());
        penilai2.setText(wm.getPenilai2());


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void showDialogDelete(int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnDelete = (Button) dialog.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeList.remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
