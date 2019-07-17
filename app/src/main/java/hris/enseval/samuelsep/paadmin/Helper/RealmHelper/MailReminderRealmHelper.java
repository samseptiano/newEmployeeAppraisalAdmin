package hris.enseval.samuelsep.paadmin.Helper.RealmHelper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import hris.enseval.samuelsep.paadmin.Model.MailReminderModel;
import hris.enseval.samuelsep.paadmin.Model.RealmModel.MailReminderRealmModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Samuel Septiano on 19,June,2019
 */
public class MailReminderRealmHelper {
    private static final String TAG = "MailReminderRealmHelper";


    private Realm realm;
    private RealmResults<MailReminderRealmModel> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public MailReminderRealmHelper(Context context) {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        this.context = context;
    }


    /**
     * menambah data
     *
     * @param //title
     * @param //description
     */
    public void addSetting(MailReminderModel mailReminderModel) {
        MailReminderRealmModel mailReminderRealmModel = new MailReminderRealmModel();

        mailReminderRealmModel.setId(UUID.randomUUID().toString());
        mailReminderRealmModel.setBcc(mailReminderModel.getBcc());
        mailReminderRealmModel.setCc(mailReminderModel.getCc());
        mailReminderRealmModel.setMessage(mailReminderModel.getMessage());
        mailReminderRealmModel.setSenderMail(mailReminderModel.getSenderMail());
        mailReminderRealmModel.setSenderName(mailReminderModel.getSenderName());
        mailReminderRealmModel.setSenderPassword(mailReminderModel.getSenderPassword());
        mailReminderRealmModel.setSmtpAddress(mailReminderModel.getSmtpAddress());
        mailReminderRealmModel.setSmtpPort(mailReminderModel.getSmtpPort());
        mailReminderRealmModel.setSubject(mailReminderModel.getSubject());
        mailReminderRealmModel.setWhatsAppMessage(mailReminderModel.getWhatsappMessage());
        mailReminderRealmModel.setWhatsAppNumber(mailReminderModel.getWhatsAppNumber());
        mailReminderRealmModel.setToken(mailReminderModel.getToken());


        realm.beginTransaction();
        realm.copyToRealm(mailReminderRealmModel);
        realm.commitTransaction();

        showLog("Added ; " + mailReminderModel.getId());

//        showToast(mailReminderModel.getWhatsAppNumber() + " berhasil disimpan.");
    }


    /**
     * method mencari semua article
     */
    public ArrayList<MailReminderRealmModel> findAllArticle() {
        ArrayList<MailReminderRealmModel> data = new ArrayList<>();


        realmResult = realm.where(MailReminderRealmModel.class).findAll();
        realmResult.sort("id", Sort.DESCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            //Toast.makeText(context,"fdsfsdfsd",Toast.LENGTH_SHORT).show();

            for (int i = 0; i < realmResult.size(); i++) {
                String id;
                String message,subject,cc,bcc,senderMail,senderPassword,senderName,smtpPort,smtpAddress, whatsappMessage,whatsappNumber,token;
                id = realmResult.get(i).getId();
                message = realmResult.get(i).getMessage();
                subject = realmResult.get(i).getSubject();
                cc = realmResult.get(i).getCc();
                bcc = realmResult.get(i).getBcc();
                senderMail = realmResult.get(i).getSenderMail();
                senderName = realmResult.get(i).getSenderName();
                senderPassword = realmResult.get(i).getSenderPassword();
                smtpPort = realmResult.get(i).getSmtpPort();
                smtpAddress = realmResult.get(i).getSmtpAddress();
                whatsappMessage = realmResult.get(i).getWhatsAppMessage();
                whatsappNumber = realmResult.get(i).getWhatsAppNumber();
                token = realmResult.get(i).getToken();

                data.add(new MailReminderRealmModel( id,  message,  subject, cc,  bcc, senderMail,  senderPassword, senderName, smtpPort, smtpAddress, whatsappMessage, whatsappNumber, token));

            }


        } else {
            showLog("Size : 0");
            //showToast("Database Kosong!");
        }

        return data;
    }


    /**
     * method update article
     *

     * @param //title
     * @param //description
     */
    public void updateSetting(MailReminderModel mailReminderModel) {
        realm.beginTransaction();
        MailReminderRealmModel mailReminderRealmModel = realm.where(MailReminderRealmModel.class).equalTo("senderMail", mailReminderModel.getSenderMail()).findFirst();

        mailReminderRealmModel.setBcc(mailReminderModel.getBcc());
        mailReminderRealmModel.setCc(mailReminderModel.getCc());
        mailReminderRealmModel.setMessage(mailReminderModel.getMessage());
        mailReminderRealmModel.setSenderMail(mailReminderModel.getSenderMail());
        mailReminderRealmModel.setSenderName(mailReminderModel.getSenderName());
        mailReminderRealmModel.setSenderPassword(mailReminderModel.getSenderPassword());
        mailReminderRealmModel.setSmtpAddress(mailReminderModel.getSmtpAddress());
        mailReminderRealmModel.setSmtpPort(mailReminderModel.getSmtpPort());
        mailReminderRealmModel.setSubject(mailReminderModel.getSubject());
        mailReminderRealmModel.setWhatsAppMessage(mailReminderModel.getWhatsappMessage());
        mailReminderRealmModel.setWhatsAppNumber(mailReminderModel.getWhatsAppNumber());
        mailReminderRealmModel.setToken(mailReminderModel.getToken());

        realm.commitTransaction();
        showLog("Updated : " + mailReminderModel.getId());

        //showToast(usr.getUserId() + " berhasil diupdate.");
    }


    /**
     * method menghapus article berdasarkan id
     *
     * @param id
     */
    public void deleteData(String id) {
        RealmResults<MailReminderRealmModel> dataDesults = realm.where(MailReminderRealmModel.class).equalTo("senderMail", id).findAll();
        realm.beginTransaction();
        //dataDesults.remove(0);
        //dataDesults.removeLast();
        //dataDesults.clear();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus data berhasil.");
    }


    public void deleteAllData() {
        RealmResults<MailReminderRealmModel> dataDesults = realm.where(MailReminderRealmModel.class).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();

        //showToast("Hapus Semua data berhasil.");
    }


    /**
     * membuat log
     *
     * @param s
     */
    private void showLog(String s) {
        Log.d(TAG, s);

    }

    /**
     * Membuat Toast Informasi
     */
    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
