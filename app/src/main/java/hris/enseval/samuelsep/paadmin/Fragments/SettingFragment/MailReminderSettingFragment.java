package hris.enseval.samuelsep.paadmin.Fragments.SettingFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import hris.enseval.samuelsep.paadmin.Helper.RealmHelper.MailReminderRealmHelper;
import hris.enseval.samuelsep.paadmin.Model.MailReminderModel;
import hris.enseval.samuelsep.paadmin.Model.RealmModel.MailReminderRealmModel;
import hris.enseval.samuelsep.paadmin.R;


public class MailReminderSettingFragment extends Fragment {

    View rootView;
    Button btnSave;
    EditText edtSenderMail,edtSenderPassword,edtSenderName, edtMessage,edtSubject,edtCC,edtBCC,edtSmtpPort,edtSmtpAddress, whatsappNumber,whatsappMessage,token;
    MailReminderRealmHelper mailReminderRealmHelper = new MailReminderRealmHelper(getContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_mail_reminder_setting, container, false);
        edtSenderMail = rootView.findViewById(R.id.edtSenderMail);
        edtBCC = rootView.findViewById(R.id.edtSenderBCC);
        edtCC = rootView.findViewById(R.id.edtCC);
        edtMessage = rootView.findViewById(R.id.edtMessage);
        edtSubject = rootView.findViewById(R.id.edtSubject);
        edtSenderName = rootView.findViewById(R.id.edtSenderMail);
        edtSenderPassword = rootView.findViewById(R.id.edtSenderPassword);
        edtSmtpAddress = rootView.findViewById(R.id.edtSmtpAddress);
        edtSmtpPort = rootView.findViewById(R.id.edtSmtpPort);
        whatsappMessage = rootView.findViewById(R.id.edtWhatsappMessage);
        whatsappNumber = rootView.findViewById(R.id.edtWhatsAppNumber);
        token = rootView.findViewById(R.id.edtToken);
        btnSave = rootView.findViewById(R.id.buttonSave);

        List<MailReminderRealmModel> mailReminderModel = mailReminderRealmHelper.findAllArticle();

        if(mailReminderRealmHelper.findAllArticle().size()>0){
            edtSenderMail.setText(mailReminderModel.get(0).getSenderMail());
            edtBCC.setText(mailReminderModel.get(0).getBcc());
            edtCC.setText(mailReminderModel.get(0).getCc());
            edtMessage.setText(mailReminderModel.get(0).getMessage());
            edtSubject.setText(mailReminderModel.get(0).getSubject());
            edtSenderName.setText(mailReminderModel.get(0).getSenderName());
            edtSenderPassword.setText(mailReminderModel.get(0).getSenderPassword());
            edtSmtpAddress.setText(mailReminderModel.get(0).getSmtpAddress());
            edtSmtpPort.setText(mailReminderModel.get(0).getSmtpPort());
            whatsappNumber.setText(mailReminderModel.get(0).getWhatsAppNumber());
            whatsappMessage.setText(mailReminderModel.get(0).getWhatsAppMessage());
            token.setText(mailReminderModel.get(0).getToken());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailReminderModel mm = new MailReminderModel();
                mm.setCc(edtCC.getText().toString());
                mm.setBcc(edtBCC.getText().toString());
                mm.setMessage(edtMessage.getText().toString());
                mm.setSenderMail(edtSenderMail.getText().toString());
                mm.setSenderPassword(edtSenderPassword.getText().toString());
                mm.setSenderName(edtSenderName.getText().toString());
                mm.setSubject(edtSubject.getText().toString());
                mm.setSmtpAddress(edtSmtpAddress.getText().toString());
                mm.setSmtpPort(edtSmtpPort.getText().toString());
                mm.setWhatsAppNumber(whatsappNumber.getText().toString());
                mm.setWhatsappMessage(whatsappMessage.getText().toString());
                mm.setToken(token.getText().toString());

                if(mailReminderRealmHelper.findAllArticle().size()>0) {
                    mailReminderRealmHelper.deleteAllData();
                    mailReminderRealmHelper.addSetting(mm);
                    Toast.makeText(getContext(),"Setting Saved",Toast.LENGTH_SHORT).show();
                }else {
                    mailReminderRealmHelper.addSetting(mm);
                    Toast.makeText(getContext(),"Setting Saved",Toast.LENGTH_SHORT).show();

                }
            }
        });


        return rootView;
    }

}
