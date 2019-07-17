package hris.enseval.samuelsep.paadmin.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import hris.enseval.samuelsep.paadmin.Adapter.EmployeeAdapter;
import hris.enseval.samuelsep.paadmin.Helper.RealmHelper.MailReminderRealmHelper;
import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.Model.RealmModel.MailReminderRealmModel;
import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import hris.enseval.samuelsep.paadmin.Pagination.PaginationScrollListener;
import hris.enseval.samuelsep.paadmin.Pagination.PostRecyclerAdapter;
import hris.enseval.samuelsep.paadmin.R;

public class InputFragment extends Fragment implements EmployeeAdapter.EventListener, SwipeRefreshLayout.OnRefreshListener{

    View rootView;
    Button btnCetakSK, btnReminder;
    RecyclerView recyclerView;
    PostRecyclerAdapter mAdapter;
    EmployeeAdapter hAdapter;
    List<EmployeeModel> lEmployee = new ArrayList<>();
    List<EmployeeModel> lEmployeeTemp = new ArrayList<>();
    MailReminderRealmHelper mailReminderRealmHelper = new MailReminderRealmHelper(getContext());
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefresh;


    EmployeeModel employeeModel;

    final ArrayList<EmployeeModel> itemss = new ArrayList<>();



    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    Button newEmployee;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 40; i++) {
//                    Log.d(TAG, "run: " + itemCount);
            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setNama("samuel septiano");
            employeeModel.setNIK("181201101 " + Integer.toString(i));
            employeeModel.setDept("HRIS");
            employeeModel.setId(Integer.toString(i + 1));
            employeeModel.setStatus("Approved");
            employeeModel.setAtasanLangsung("Rini Anthonio");
            employeeModel.setNIKAtasanLangsung("1111111111");
            employeeModel.setEmailAtasanLangsung("sammuel.septiano@gmail.com");
            employeeModel.setAtasanTidakLangsung("Kristian");
            employeeModel.setNIKAtasanTidakLangsung("2222222222");
            employeeModel.setEmailAtasantakLangsung("kristian@enseval.com");
            employeeModel.setJobTitleName("Officer");
            employeeModel.setJobTitleCode("001");
            employeeModel.setCompanyName("EPM");
            employeeModel.setCompanyCode("12345");
            employeeModel.setLocationName("EPM-PUSAT");
            employeeModel.setLocationCode("12345");
            employeeModel.setJoinDate("17-12-2018");
            employeeModel.setFinalDate("17-12-2028");
            itemss.add(employeeModel);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_input, container, false);
        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        recyclerView = rootView.findViewById(R.id.recyclerViewEmployeeList);
        newEmployee = rootView.findViewById(R.id.btnNewEmp);
//        ((MainActivity) getActivity()).setDrawerEnabled(true);

        setHasOptionsMenu(true);


        if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        swipeRefresh.setOnRefreshListener(this);



        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        hAdapter = new EmployeeAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(hAdapter);

        newEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEmployee();

            }
        });



        // use a linear layout manager
//        mAdapter = new PostRecyclerAdapter(new ArrayList<EmployeeModel>(), getContext(), this);
//        recyclerView.setAdapter(mAdapter);
//        preparedListItem();
        /**
         * add scroll listener while user reach in bottom load more will call
         */
//        recyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage++;
//                preparedListItem();
//                if(mAdapter.getItemCount() == itemss.size()){
//
//                    mAdapter.removeLoadingLast();
//                }
//
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });


        return rootView;
    }


    private void preparedListItem() {
        final ArrayList<EmployeeModel> items = new ArrayList<>();
        new Handler().postDelayed(() -> {
            for (int i = 0; i < 10; i++) {
                if(itemCount<itemss.size()) {
                    try{
                        if(itemCount<itemss.size()) {
                            items.add(itemss.get(itemCount));
                            itemCount++;
                        }

                    }
                    catch (Exception e){
                    }
                }
            }
            //Toast.makeText(getContext(),Integer.toString(items.size()),Toast.LENGTH_SHORT).show();
            if (currentPage != PAGE_START) mAdapter.removeLoading();
            mAdapter.addAll(items);
            swipeRefresh.setRefreshing(false);
            if (currentPage < totalPage) mAdapter.addLoading();
            else isLastPage = true;
            isLoading = false;

        }, 2000);
    }


    @Override
    public void getCheckedData(List<EmployeeModel> EmployeeList) {
        //lEmployeeTemp = EmployeeList;
//    .makeText(getContext(),Boolean.toString(lEmployeeTemp.get(0).isChecked()),Toast.LENGTH_LONG).show();
    }

    @Override
    public void test() {
        //Toast.makeText(getContext(),"hit test",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRefresh() {
        hAdapter = new EmployeeAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(hAdapter);
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem mSearch = menu.findItem(R.id.mi_search);

        android.support.v7.widget.SearchView mSearchView = (android.support.v7.widget.SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search...");

        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String text = s.toLowerCase(Locale.getDefault());
                try {

                    //Toast.makeText(getContext(),"typing",Toast.LENGTH_SHORT).show();

                    hAdapter.filter(text);
//                    mAdapter.filter(text);

                } catch (Exception e) {

                }


                return true;

            }
        });
    }

    private void showDialogEmployee(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.employee_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText id, name, email, nik, dept, jobtitlename, jobtitlecode, locationname, locationcode, companyname,companycode, joindate,finaldate, status, atasanlangsung, nikatasanlangsung, emailatasanlangsung, atasantaklangsung, nikatasantaklangsung,emailaasantaklangsung;
        ImageButton img_join_date, img_final_date;
        TextView tvId;

        id = dialog.findViewById(R.id.edt_id);
        name = dialog.findViewById(R.id.edt_name);
        email = dialog.findViewById(R.id.edt_email);
        nik = dialog.findViewById(R.id.edt_nik);
        dept = dialog.findViewById(R.id.edt_department);
        jobtitlecode = dialog.findViewById(R.id.edt_job_title_code);
        jobtitlename = dialog.findViewById(R.id.edt_job_title_name);
        locationcode = dialog.findViewById(R.id.edt_location_code);
        locationname = dialog.findViewById(R.id.edt_location_name);
        companycode = dialog.findViewById(R.id.edt_company_code);
        companyname = dialog.findViewById(R.id.edt_company_name);
        status = dialog.findViewById(R.id.edt_status);
        joindate = dialog.findViewById(R.id.edt_join_date);
        finaldate = dialog.findViewById(R.id.edt_final_date);
        atasanlangsung = dialog.findViewById(R.id.edt_atasan_langsung);
        nikatasanlangsung = dialog.findViewById(R.id.edt_nik_atasan_langsung);
        emailatasanlangsung = dialog.findViewById(R.id.edt_email_atasan_langsung);
        atasantaklangsung = dialog.findViewById(R.id.edt_atasan_taklangsung);
        nikatasantaklangsung = dialog.findViewById(R.id.edt_nik_atasan_taklangsung);
        emailaasantaklangsung = dialog.findViewById(R.id.edt_email_atasan_taklangsung);

        img_final_date = dialog.findViewById(R.id.img_final_date);
        img_join_date = dialog.findViewById(R.id.img_join_date);

        tvId = dialog.findViewById(R.id.tv_id);


        tvId.setVisibility(View.GONE);
        id.setVisibility(View.GONE);


        img_join_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(joindate);
            }
        });

        img_final_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(finaldate);
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


    private void getDate(EditText v){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getContext(), dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_LONG).show();
                        v.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
