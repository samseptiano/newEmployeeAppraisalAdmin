package hris.enseval.samuelsep.paadmin.Fragments.Form;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.Model.FormModel;
import hris.enseval.samuelsep.paadmin.R;


public class FormDetailFragment extends Fragment implements MainActivity.hideBurger{
    // TODO: Rename parameter arguments, choose names that match

    View rootView;
    Context context;
    FormModel formModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        formModel = new FormModel();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            formModel = (FormModel) bundle.getSerializable("form");
            Toast.makeText(context,formModel.getPosisi(),Toast.LENGTH_LONG).show();
        }
        //((MainActivity) getActivity()).setDrawerEnabled(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_form_detail, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//
//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


       ((MainActivity) getActivity()).setDrawerEnabled(false);




        return rootView;
    }

    @Override
    public void setDrawerEnabled(boolean isEnabled) {

    }

    // TODO: Rename method, update argument and hook method into UI event

}
