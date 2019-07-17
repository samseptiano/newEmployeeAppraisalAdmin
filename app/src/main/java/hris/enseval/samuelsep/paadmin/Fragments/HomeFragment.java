package hris.enseval.samuelsep.paadmin.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import hris.enseval.samuelsep.paadmin.R;

import static hris.enseval.samuelsep.paadmin.Application.MyApplication.getContext;

public class HomeFragment extends Fragment {
   View rootView;
    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
//        ((MainActivity) getActivity()).setDrawerEnabled(true);


        fr = new EmpTableFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            fr.setArguments(bundle);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getContext(), "Home Area", Toast.LENGTH_SHORT).show();

        return rootView;


    }
}
