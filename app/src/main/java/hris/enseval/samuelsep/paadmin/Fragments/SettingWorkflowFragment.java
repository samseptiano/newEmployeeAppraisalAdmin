package hris.enseval.samuelsep.paadmin.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hris.enseval.samuelsep.paadmin.Adapter.FormAdapter;
import hris.enseval.samuelsep.paadmin.Adapter.WorkflowAdapter;
import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.Model.FormModel;
import hris.enseval.samuelsep.paadmin.Model.WorkFlowModel;
import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import hris.enseval.samuelsep.paadmin.R;


public class SettingWorkflowFragment extends Fragment implements WorkflowAdapter.EventListener,SwipeRefreshLayout.OnRefreshListener {

    View rootView;
    RecyclerView recyclerView;
    WorkflowAdapter workflowAdapter;
    SwipeRefreshLayout swipeRefresh;
    LinearLayoutManager mLayoutManager;
    Button btnNewWorkflow;
    List<WorkFlowModel> itemss = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 5; i++) {
            WorkFlowModel workFlowModel = new WorkFlowModel();
            workFlowModel.setId(Integer.toString(i));
            workFlowModel.setDept("example dept: "+i);
            workFlowModel.setWorkflowName("tretegdfgsfgsfgfsdg");
            workFlowModel.setPosisi("Field Sales Supervisot");
            workFlowModel.setPenilai1("sdasdsdsadasd");
            workFlowModel.setPenilai2("dasdsad");

            itemss.add(workFlowModel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_setting_workflow, container, false);

//        ((MainActivity) getActivity()).setDrawerEnabled(true);
        setHasOptionsMenu(true);
        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        recyclerView = rootView.findViewById(R.id.recyclerViewWorkflowList);
        btnNewWorkflow = rootView.findViewById(R.id.btnNewWorkflow);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        workflowAdapter = new WorkflowAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(workflowAdapter);

        swipeRefresh.setOnRefreshListener(this);


        btnNewWorkflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialogWorkflow();
            }
        });

        return rootView;
    }

    @Override
    public void onRefresh() {
        workflowAdapter = new WorkflowAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(workflowAdapter);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getCheckedData(List<WorkFlowModel> FormList) {

    }

    @Override
    public void test() {

    }

    private void showDialogWorkflow(){
        final Dialog dialog = new Dialog(getContext());
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
        id.setVisibility(View.GONE);
        tvId.setVisibility(View.GONE);

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
