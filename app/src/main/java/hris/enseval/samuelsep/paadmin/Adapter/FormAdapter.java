package hris.enseval.samuelsep.paadmin.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hris.enseval.samuelsep.paadmin.Fragments.Form.FormDetailFragment;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.Model.FormModel;
import hris.enseval.samuelsep.paadmin.R;

/**
 * Created by Samuel Septiano on 25,June,2019
 */
public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {

    private List<FormModel> homeList;
    private Context context;
    private ArrayList<FormModel> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;

    public interface EventListener {
        void getCheckedData(List<FormModel> FormList);
        void test();
    }

    EventListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFormName, tvDirektorat, tvPosisi, tvFilePath;
        LinearLayout lnEmployeeList;

        public MyViewHolder(View view) {
            super(view);
            tvFormName = (TextView) itemView.findViewById(R.id.tv_formName);
            tvDirektorat = (TextView) itemView.findViewById(R.id.tv_direktorat);
            tvPosisi = (TextView) itemView.findViewById(R.id.tv_position);
            tvFilePath = (TextView) itemView.findViewById(R.id.tv_fileName);
            lnEmployeeList = (LinearLayout) itemView.findViewById(R.id.lnEmployeeList);
        }

    }


    public FormAdapter(List<FormModel> homeList, Context context, Boolean isConnected, Activity activity, EventListener listener) {
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
                .inflate(R.layout.form_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FormModel home = homeList.get(position);

        holder.tvFilePath.setText(home.getFilePath());
        holder.tvPosisi.setText(home.getPosisi());
        holder.tvDirektorat.setText(home.getDirektorat());
        holder.tvFormName.setText(home.getFormName());

        holder.lnEmployeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = new FormDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("form", home);
            fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
//                ft.detach(fr);
//                ft.attach(fr);
                ft.addToBackStack("fragmentDetail");
                ft.commit();
            }
        });

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
                            showDialogForm(home);
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
            for (FormModel wp : homeListFilter) {
                if (wp.getFormName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    homeList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    private void showDialogForm(FormModel formModel){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.form_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText id, direktorat, posisi, formName;
        TextView filepath;
        Button btnUpload, btnDel;

        id = dialog.findViewById(R.id.edt_id);
        direktorat = dialog.findViewById(R.id.edt_direktori);
        posisi = dialog.findViewById(R.id.edt_position);
        formName = dialog.findViewById(R.id.edt_form_title);
        filepath = dialog.findViewById(R.id.tv_file_path);
        btnUpload = dialog.findViewById(R.id.btnUploadForm);
        btnDel = dialog.findViewById(R.id.btnDeleteUploadForm);

        id.setText(formModel.getId());
        direktorat.setText(formModel.getDirektorat());
        posisi.setText(formModel.getPosisi());
        formName.setText(formModel.getFormName());
        filepath.setText(formModel.getFilePath());
        if(filepath.getText().toString().length()>1){
            btnUpload.setVisibility(View.GONE);
        }
        else {
            btnDel.setVisibility(View.GONE);
        }



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
