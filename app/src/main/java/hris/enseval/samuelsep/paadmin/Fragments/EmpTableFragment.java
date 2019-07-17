package hris.enseval.samuelsep.paadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.uttampanchasara.pdfgenerator.CreatePdf;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import hris.enseval.samuelsep.paadmin.Adapter.EmployeeAdapter;
import hris.enseval.samuelsep.paadmin.Api.ApiClient;
import hris.enseval.samuelsep.paadmin.Api.ApiInterface;
import hris.enseval.samuelsep.paadmin.Helper.RealmHelper.MailReminderRealmHelper;
import hris.enseval.samuelsep.paadmin.MainActivity;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.Model.RealmModel.MailReminderRealmModel;
import hris.enseval.samuelsep.paadmin.Model.SKModel;
import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import hris.enseval.samuelsep.paadmin.Pagination.PaginationScrollListener;
import hris.enseval.samuelsep.paadmin.Pagination.PostRecyclerAdapter;
import hris.enseval.samuelsep.paadmin.R;
import hris.enseval.samuelsep.paadmin.utils.PDFCreationUtils;
import hris.enseval.samuelsep.paadmin.utils.PdfBitmapCache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmpTableFragment extends Fragment implements EmployeeAdapter.EventListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean IS_MANY_PDF_FILE;

    View rootView;
    ImageButton btnCetakSK, btnReminder;
    RecyclerView recyclerView;
    PostRecyclerAdapter mAdapter;
    EmployeeAdapter hAdapter;
    List<EmployeeModel> lEmployee = new ArrayList<>();
    List<EmployeeModel> lEmployeeTemp = new ArrayList<>();
    MailReminderRealmHelper mailReminderRealmHelper = new MailReminderRealmHelper(getContext());
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefresh;


    public static String token = "0f00fc4be96cacc845a3f697d2aec20e5cd398948560c";
    public static String uid = "6281717170164";


    private int SECTOR = 100; // Default value for one pdf file.
    private int START;
    private int END = SECTOR;
    private int NO_OF_PDF_FILE = 1;
    private int NO_OF_FILE;
    private int LIST_SIZE;
    private ProgressDialog progressDialog;

    private List<SKModel> pdfModels;

    SKModel skModel;

    EmployeeModel employeeModel;

    final ArrayList<EmployeeModel> itemss = new ArrayList<>();



    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            for (int i = 0; i < 5; i++) {
//                    Log.d(TAG, "run: " + itemCount);
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.setNama("samuel septiano");
                employeeModel.setEmail("test@gmail.com");

                if(i%2==0) {
                    employeeModel.setNIK("18120110" + Integer.toString(i));
                }
                else{
                    employeeModel.setNIK("28120110" + Integer.toString(i));
                }
                employeeModel.setDept("HRIS");
                employeeModel.setId(Integer.toString(i));
                employeeModel.setStatus("Approved");
                employeeModel.setAtasanLangsung("Rini Anthonio");
                employeeModel.setNIKAtasanLangsung("1111111111");
                employeeModel.setEmailAtasanLangsung("samuel.septiano@enseval.com");
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
        rootView = inflater.inflate(R.layout.fragment_emp_table, container, false);
        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        btnCetakSK = rootView.findViewById(R.id.btnCetakSK);
        btnReminder = rootView.findViewById(R.id.btnReminder);
        recyclerView = rootView.findViewById(R.id.recyclerViewEmployeeList);
//        ((MainActivity) getActivity()).setDrawerEnabled(true);

        setHasOptionsMenu(true);


        if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        swipeRefresh.setOnRefreshListener(this);


        btnCetakSK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                        View inflatedFrame = inflater.inflate(R.layout.item_pdf_creation, null);
                        getBitmapFromView(inflatedFrame.findViewById(R.id.scroll));
                    }
                }, 1000);
                //cetak();
            }
        });




        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClientWA().create(ApiInterface.class);
                try{

                    if(lEmployeeTemp.size()>0){
                        for(int i=0;i<lEmployeeTemp.size();i++){
                            if(lEmployeeTemp.get(i).isChecked()) {
                                sendEmail(lEmployeeTemp.get(i).getEmailAtasanLangsung());
                                sendWhatsapp(apiService,lEmployeeTemp.get(i).getPhoneAtasanLangsung());
                            }
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        hAdapter = new EmployeeAdapter(itemss, getContext(),ConnectivityReceiver.isConnected(),getActivity(), this);
        recyclerView.setAdapter(hAdapter);

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
//                //Toast.makeText(getContext(),Integer.toString(mAdapter.getItemCount())+" "+Integer.toString(itemss.size()),Toast.LENGTH_LONG).show();
//
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



    private void cetak(){
        //requestPermission();
        try{
            if(lEmployeeTemp.size()>0){
                for(int i=0;i<lEmployeeTemp.size();i++){
//                            requestPermission();
                    if(lEmployeeTemp.get(i).isChecked()) {
//                                requestPermission();
                        skModel = new SKModel();
                        skModel.setNama(lEmployeeTemp.get(i).getNama());
                        skModel.setJoinDate(lEmployeeTemp.get(i).getJoinDate());
                        skModel.setFinalDate(lEmployeeTemp.get(i).getFinalDate());
                        skModel.setNik(lEmployeeTemp.get(i).getNIK());
                        skModel.setJobTitleName(lEmployeeTemp.get(i).getJobTitleName());
                        skModel.setDept(lEmployeeTemp.get(i).getDept());
                        skModel.setLocationName(lEmployeeTemp.get(i).getLocationName());
                        skModel.setStatus(lEmployeeTemp.get(i).getStatus());
                        skModel.setCompanyName(lEmployeeTemp.get(i).getCompanyName());
                        skModel.setSkor("n/a");

                        pdfModels = SKModel.createDummyPdfModel(skModel);

                    }
                }
            }

        }
        catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }


    }


    private void preparedListItem() {
        final ArrayList<EmployeeModel> items = new ArrayList<>();
        new Handler().postDelayed(() -> {
            for (int i = 0; i < 10; i++) {
                try{
                if(itemCount<itemss.size()) {
                    items.add(itemss.get(itemCount));
                    itemCount++;
                    //items.add(itemss.get(itemCount));
                }
                }
                catch (Exception e){
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


    protected void sendEmail(String receiverMail) {
        List<MailReminderRealmModel> mm = mailReminderRealmHelper.findAllArticle();
        final String username = mm.get(0).getSenderMail();
        final String password = mm.get(0).getSenderPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mm.get(0).getSmtpAddress());
        props.put("mail.smtp.port", mm.get(0).getSmtpPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mm.get(0).getSenderMail()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverMail));

//            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(
//                    "your@email.com"));
//            message.addRecipient(Message.RecipientType.CC, new InternetAddress(
//                    "yourOther@email.com"));

            message.setSubject(mm.get(0).getSubject());
            message.setText(mm.get(0).getMessage());

//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            Multipart multipart = new MimeMultipart();
//            messageBodyPart = new MimeBodyPart();
//            String file = "path of file to be attached";
//            String fileName = "attachmentName";
//            DataSource source = new FileDataSource(file);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(fileName);
//            multipart.addBodyPart(messageBodyPart);
//            message.setContent(multipart);

            Transport.send(message);
            Toast.makeText(getContext(),"Email Sent",Toast.LENGTH_LONG).show();
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendWhatsapp(ApiInterface apiService, String phone){
        List<MailReminderRealmModel> mm = mailReminderRealmHelper.findAllArticle();
        final String message = mm.get(0).getWhatsAppMessage();
        final String token = mm.get(0).getToken();
        final String uid = mm.get(0).getWhatsAppNumber();

        Random rand = new Random();
        int randomNum = rand.nextInt((9999 - 1000) + 1) + 1000;

        Call<String> call = apiService.postWhasappBlast(token,uid,phone,"msg-"+Integer.toString(randomNum),message);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                //Log.e(TAG, t.toString());
            }
        });

    }


    @Override
    public void getCheckedData(List<EmployeeModel> EmployeeList) {
        lEmployeeTemp = EmployeeList;
//    .makeText(getContext(),Boolean.toString(lEmployeeTemp.get(0).isChecked()),Toast.LENGTH_LONG).show();
    }

    @Override
    public void test() {
        Toast.makeText(getContext(),"hit test",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRefresh() {
//        itemCount = 0;
//        currentPage = PAGE_START;
//        isLastPage = false;
//        mAdapter.clear();
//        preparedListItem();

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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            generatePdfReport();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        } else {


            generatePdfReport();
        }
    }

    private void generatePdfReport() {


        // NO_OF_FILE : This is identify to one file or many file have to created

        LIST_SIZE = 1;
        NO_OF_FILE = LIST_SIZE / SECTOR;
        if (LIST_SIZE % SECTOR != 0) {
            NO_OF_FILE++;
        }
        if (LIST_SIZE > SECTOR) {
            IS_MANY_PDF_FILE = true;
        } else {
            END = LIST_SIZE;
        }
        createPDFFile();
    }

    private void createProgressBarForPDFCreation(int maxProgress) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(String.format(getString(R.string.msg_progress_pdf), String.valueOf(maxProgress)));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(maxProgress);
        progressDialog.show();
    }

    private void createProgressBarForMergePDF() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.msg_progress_merger_pdf));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * This function call with recursion
     * This recursion depend on number of file (NO_OF_PDF_FILE)
     */
    private void createPDFFile() {

        // Find sub list for per pdf file data
        List<SKModel> pdfDataList = pdfModels.subList(START, END);
        PdfBitmapCache.clearMemory();
        PdfBitmapCache.initBitmapCache(getContext());
        final PDFCreationUtils pdfCreationUtils = new PDFCreationUtils((MainActivity) getContext(), pdfDataList, LIST_SIZE, NO_OF_PDF_FILE);
        if (NO_OF_PDF_FILE == 1) {
            createProgressBarForPDFCreation(PDFCreationUtils.TOTAL_PROGRESS_BAR);
        }
        pdfCreationUtils.createPDF(new PDFCreationUtils.PDFCallback() {

            @Override
            public void onProgress(final int i) {
                progressDialog.setProgress(i);
            }

            @Override
            public void onCreateEveryPdfFile() {
                // Execute may pdf files and this is depend on NO_OF_FILE
                if (IS_MANY_PDF_FILE) {
                    NO_OF_PDF_FILE++;
                    if (NO_OF_FILE == NO_OF_PDF_FILE - 1) {

                        progressDialog.dismiss();
                        createProgressBarForMergePDF();
                        pdfCreationUtils.downloadAndCombinePDFs();
                    } else {

                        // This is identify to manage sub list of current pdf model list data with START and END

                        START = END;
                        if (LIST_SIZE % SECTOR != 0) {
                            if (NO_OF_FILE == NO_OF_PDF_FILE) {
                                END = (START - SECTOR) + LIST_SIZE % SECTOR;
                            }
                        }
                        END = SECTOR + END;
                        createPDFFile();
                    }

                } else {
                    // Merge one pdf file when all file is downloaded
                    progressDialog.dismiss();

                    createProgressBarForMergePDF();
                    pdfCreationUtils.downloadAndCombinePDFs();
                }

            }

            @Override
            public void onComplete(final String filePath) {
                progressDialog.dismiss();

                if (filePath != null) {
//                    btnPdfPath.setVisibility(View.VISIBLE);
//                    btnPdfPath.setText("PDF path : " + filePath);
                    Toast.makeText(getContext(), "pdf file " + filePath, Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Error  " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getBitmapFromView(View v) {
        //Define a bitmap with the same size as the view
        v.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        //return the bitmap
        createPdf(bitmap);
    }

    public void createPdf(Bitmap bitmap){
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);



        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        String targetPdf = "/sdcard/test.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
           // btn_convert.setText("Check PDF");
            //boolean_save=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }


}
