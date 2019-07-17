package hris.enseval.samuelsep.paadmin.Fragments.Employee;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.Model.FormModel;
import hris.enseval.samuelsep.paadmin.R;


public class EmployeeDetailFragment extends Fragment {

    View rootView;

    TextView name, email, nik, dept, jobtitlename, jobtitlecode, locationname, locationcode, companyname,companycode, joindate,finaldate, status, atasanlangsung, nikatasanlangsung, emailatasanlangsung, atasantaklangsung, nikatasantaklangsung,emailaasantaklangsung, phoneAtasanLangsung, phoneAtasanTaklangsung;
    ImageView img_photo;
    EmployeeModel em;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        em = new EmployeeModel();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            em = (EmployeeModel) bundle.getSerializable("employee");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_employee_detail, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//
//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        ((MainActivity) getActivity()).setDrawerEnabled(false);


        img_photo = rootView.findViewById(R.id.img_photo);;
        name = rootView.findViewById(R.id.tv_nama);
        email = rootView.findViewById(R.id.tv_email);
        nik = rootView.findViewById(R.id.tv_nik);
        dept = rootView.findViewById(R.id.tv_department);
        jobtitlecode = rootView.findViewById(R.id.tv_job_title_code);
        jobtitlename = rootView.findViewById(R.id.tv_job_title_name);
        locationcode = rootView.findViewById(R.id.tv_location_code);
        locationname = rootView.findViewById(R.id.tv_location_name);
        companycode = rootView.findViewById(R.id.tv_company_code);
        companyname = rootView.findViewById(R.id.tv_company_name);
        status = rootView.findViewById(R.id.tv_status);
        joindate = rootView.findViewById(R.id.tv_join_date);
        finaldate = rootView.findViewById(R.id.tv_final_date);
        atasanlangsung = rootView.findViewById(R.id.tv_atasan_langsung);
        nikatasanlangsung = rootView.findViewById(R.id.tv_nik_atasan_langsung);
        emailatasanlangsung = rootView.findViewById(R.id.tv_email_atasan_langsung);
        atasantaklangsung = rootView.findViewById(R.id.tv_atasan_taklangsung);
        nikatasantaklangsung = rootView.findViewById(R.id.tv_nik_atasan_taklangsung);
        emailaasantaklangsung = rootView.findViewById(R.id.tv_email_atasan_taklangsung);
        phoneAtasanTaklangsung = rootView.findViewById(R.id.tv_phone_atasan_taklangsung);
        phoneAtasanLangsung = rootView.findViewById(R.id.tv_phone_atasan_langsung);


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

        return rootView;
    }


}
