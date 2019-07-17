package hris.enseval.samuelsep.paadmin.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hris.enseval.samuelsep.paadmin.Fragments.Employee.EmployeeDetailFragment;
import hris.enseval.samuelsep.paadmin.Fragments.Form.FormDetailFragment;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.Pagination.PostRecyclerAdapter;
import hris.enseval.samuelsep.paadmin.R;

import static hris.enseval.samuelsep.paadmin.Application.MyApplication.getContext;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {

    private List<EmployeeModel> homeList;
    private Context context;
    private ArrayList<EmployeeModel> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;

    public interface EventListener {
        void getCheckedData(List<EmployeeModel> EmployeeList);
        void test();
    }

    EventListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNIK, tvDept, tvjobtitle, tvStatus,tvStartJoin,tvLastJoin;
        LinearLayout lnEmployeeList;
        CheckBox cbEmployee;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) itemView.findViewById(R.id.tv_employeeName);
            tvNIK = (TextView) itemView.findViewById(R.id.tv_nik);
            tvDept = (TextView) itemView.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) itemView.findViewById(R.id.tv_job_title);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            lnEmployeeList = (LinearLayout) itemView.findViewById(R.id.lnEmployeeList);
            tvLastJoin = (TextView) itemView.findViewById(R.id.tv_last_join);
            tvStartJoin = (TextView) itemView.findViewById(R.id.tv_start_join);
            cbEmployee = itemView.findViewById(R.id.cbEmployee);

        }

    }


    public EmployeeAdapter(List<EmployeeModel> homeList, Context context, Boolean isConnected, Activity activity, EventListener listener) {
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
                .inflate(R.layout.employee_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EmployeeModel home = homeList.get(position);
        if(homeList.get(position).isChecked()){
            holder.cbEmployee.setChecked(true);
            //Toast.makeText(context,Boolean.toString(EmployeeList.get(position).isChecked()),Toast.LENGTH_LONG).show();
        }
        else{
            holder.cbEmployee.setChecked(false);
            //Toast.makeText(context,Boolean.toString(EmployeeList.get(position).isChecked()),Toast.LENGTH_LONG).show();
        }

        holder.tvName.setText(home.getNama());

//        if(klist.getStatus().equals("Approved")){
        holder.tvStatus.setText(home.getStatus());
//            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
//        }
//        else {
//            holder.tvStatus.setText(klist.getStatus());
//            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
//
//        }
        holder.tvStartJoin.setText("Periode: "+home.getJoinDate());
        holder.tvLastJoin.setText(home.getFinalDate());
        holder.tvjobtitle.setText(home.getJobTitleName());
        holder.tvDept.setText(home.getDept());
        holder.tvNIK.setText(home.getNIK());


        holder.lnEmployeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //employee detail
            }
        });

        holder.cbEmployee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    homeList.get(position).setChecked(true);
                }
                else{
                    homeList.get(position).setChecked(false);
                }

                try {
                    listener.getCheckedData(homeList);
                }
                catch (Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.lnEmployeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = new EmployeeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("employee", home);
                fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.detach(fr);
                ft.attach(fr);
                ft.addToBackStack("fragmentDetail");
                ft.commit();
            }
        });

        holder.lnEmployeeList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popup = new PopupMenu(context,v);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.employee_list_popup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().equals("delete")){
                            showDialogDelete(position);
                        }
                        if(item.getTitle().equals("edit")){
//                            homeList.remove(position);
//                            notifyDataSetChanged();
                            showDialogEmployee(home);
                        }
                        Toast.makeText(context,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
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
        }
        else
        {
            for (EmployeeModel wp : homeListFilter) {
                if (wp.getNama().toLowerCase(Locale.getDefault()).contains(charText)) {
                    homeList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void showDialogEmployee(EmployeeModel em){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.employee_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText id, name, email, nik, dept, jobtitlename, jobtitlecode, locationname, locationcode, companyname,companycode, joindate,finaldate, status, atasanlangsung, nikatasanlangsung, emailatasanlangsung, atasantaklangsung, nikatasantaklangsung,emailaasantaklangsung, phoneAtasanLangsung, phoneAtasanTaklangsung;
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
        phoneAtasanTaklangsung = dialog.findViewById(R.id.edt_phone_atasan_taklangsung);
        phoneAtasanLangsung = dialog.findViewById(R.id.edt_phone_atasan_langsung);

        img_final_date = dialog.findViewById(R.id.img_final_date);
        img_join_date = dialog.findViewById(R.id.img_join_date);

        tvId = dialog.findViewById(R.id.tv_id);

        id.setText(em.getId());
        name.setText(em.getNama());
        email.setText(em.getEmail());
        nik.setText(em.getNIK());
        dept.setText(em.getDept());
        jobtitlecode.setText(em.getJobTitleCode());
        jobtitlename.setText(em.getJobTitleName());
        locationcode.setText(em.getLocationCode());
        locationname.setText(em.getLocationName());
        companycode.setText(em.getCompanyCode());
        companyname.setText(em.getCompanyName());
        status.setText(em.getStatus());
        joindate.setText(em.getJoinDate());
        finaldate.setText(em.getFinalDate());
        atasanlangsung.setText(em.getAtasanLangsung());
        nikatasanlangsung.setText(em.getNIKAtasanLangsung());
        emailatasanlangsung.setText(em.getEmailAtasanLangsung());
        atasantaklangsung.setText(em.getAtasanLangsung());
        nikatasantaklangsung.setText(em.getNIKAtasanTidakLangsung());
        emailaasantaklangsung.setText(em.getEmailAtasantakLangsung());
        phoneAtasanLangsung.setText(em.getPhoneAtasanLangsung());
        phoneAtasanTaklangsung.setText(em.getPhoneAtasanTaklangsung());



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


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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