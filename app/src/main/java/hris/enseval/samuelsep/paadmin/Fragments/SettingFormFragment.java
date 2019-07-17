package hris.enseval.samuelsep.paadmin.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hris.enseval.samuelsep.paadmin.Adapter.EmployeeAdapter;
import hris.enseval.samuelsep.paadmin.Adapter.FormAdapter;
import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.Model.FormModel;
import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import hris.enseval.samuelsep.paadmin.R;
import hris.enseval.samuelsep.paadmin.utils.FilePath;
import io.realm.Realm;

public class SettingFormFragment extends Fragment implements FormAdapter.EventListener,SwipeRefreshLayout.OnRefreshListener {

    View rootView;
    RecyclerView recyclerView;
    FormAdapter formAdapter;
    SwipeRefreshLayout swipeRefresh;
    LinearLayoutManager mLayoutManager;
    Button btnNewForm;
    List<FormModel> itemss = new ArrayList<>();
    FormModel formModel = new FormModel();
    private static final int PICK_FILE_REQUEST = 1;
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 5; i++) {
            FormModel formModel = new FormModel();
            formModel.setId(Integer.toString(i));
            formModel.setFormName("example form");
            formModel.setDirektorat("CHD");
            formModel.setPosisi("Field Sales Supervisot");
            formModel.setFilePath("/test/test.pdf");
            formModel.setFileString("dasdsad");

            itemss.add(formModel);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_setting_form, container, false);
        setHasOptionsMenu(true);
//        ((MainActivity) getActivity()).setDrawerEnabled(true);

        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        recyclerView = rootView.findViewById(R.id.recyclerViewFormList);
        btnNewForm = rootView.findViewById(R.id.btnNewForm);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        swipeRefresh.setOnRefreshListener(this);

        // use a linear layout manager

        formAdapter = new FormAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(formAdapter);

        btnNewForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForm();
            }
        });

        return rootView;
    }

    @Override
    public void onRefresh() {
        formAdapter = new FormAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(formAdapter);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getCheckedData(List<FormModel> FormList) {

    }

    @Override
    public void test() {

    }

    private void showDialogForm(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.form_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText id, direktorat, posisi, formName;
        TextView filepath, tvId;
        Button btnUpload, btnDel;

        id = dialog.findViewById(R.id.edt_id);
        direktorat = dialog.findViewById(R.id.edt_direktori);
        posisi = dialog.findViewById(R.id.edt_position);
        formName = dialog.findViewById(R.id.edt_form_title);
        filepath = dialog.findViewById(R.id.tv_file_path);
        tvId = dialog.findViewById(R.id.tv_id);
        btnUpload = dialog.findViewById(R.id.btnUploadForm);
        btnDel = dialog.findViewById(R.id.btnDeleteUploadForm);


        id.setVisibility(View.GONE);
        tvId.setVisibility(View.GONE);

        direktorat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(! hasFocus){
                    formModel.setDirektorat(direktorat.getText().toString());
                }
            }
        });

        formName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(! hasFocus){
                    formModel.setFormName(formName.getText().toString());
                }
            }
        });

        posisi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(! hasFocus){
                    formModel.setPosisi(posisi.getText().toString());
                }
            }
        });

        try{
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
        }
        catch (Exception e){}

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direktorat.setFocusable(false);
                posisi.setFocusable(false);
                formName.setFocusable(false);
                showPictureDialog();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
            }
        });

        dialog.show();
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Pilih Aksi");
        String[] pictureDialogItems = {
                "Pilih dokumen dari media penyimpanan",
                "Ambil gambar dari kamera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showFileChooser();
                                break;
                            case 1:
                                try {
                                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(photoCaptureIntent, 20);
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("*/*"); // intent.setType("video/*"); to select videos to upload
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FILE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK)
        {
            try {


//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
//
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap photo = BitmapFactory.decodeStream(imageStream);


                //============================================================================
                //Convert Bitmap to String
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String temp = Base64.encodeToString(b, Base64.DEFAULT);
                //============================================================================
                formModel.setFileExtension(imgDecodableString.substring(imgDecodableString.lastIndexOf(".")));
                formModel.setFileString(temp);
                formModel.setFilePath(imgDecodableString);

                dialog.dismiss();
                showDialogForm();



                //cara buat imgCaptureList setiap beda recycler view rest jadi null
            }
            catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

            }
        }

        else if(requestCode == PICK_FILE_REQUEST){
            String temp ="";
            try {


                Uri selectedImageUri = data.getData();
                String imagepath = FilePath.getPath(getContext(), selectedImageUri);
                String extension = imagepath.substring(imagepath.lastIndexOf("."));
                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger images
                // options.inSampleSize = 10;


                if(extension.equals(".pdf")){
                    File originalFile = new File(imagepath);
                    temp = null;
                    try {
                        FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                        byte[] bytes = new byte[(int)originalFile.length()];
                        fileInputStreamReader.read(bytes);
                        temp = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(extension.equals(".jpg") ||extension.equals(".png") ||extension.equals(".jpeg") || extension.equals(".bmp") || extension.equals(".gif")){
                    //============ image =======================
                    final Bitmap bitmap = BitmapFactory.decodeFile(imagepath, options);
                    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
                    byte [] b=baos.toByteArray();
                    temp=Base64.encodeToString(b, Base64.DEFAULT);
                    //===========================================
                }

                formModel.setFileString(temp);
                formModel.setFileExtension(extension);
                formModel.setFilePath(imagepath);

                dialog.dismiss();
                showDialogForm();



            }catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
