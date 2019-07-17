package hris.enseval.samuelsep.paadmin.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Samuel Septiano on 19,June,2019
 */
public class MailReminderRealmModel extends RealmObject {

    @PrimaryKey
    private String id;
    private String message;
    private String subject;
    private String cc;
    private String bcc;
    private String senderMail;
    private String senderPassword;
    private String senderName;
    private String smtpPort;
    private String smtpAddress;
    private String whatsAppMessage;
    private String whatsAppNumber;
    private String token;

    public MailReminderRealmModel(String id, String message, String subject, String cc, String bcc, String senderMail, String senderPassword, String senderName, String smtpPort, String smtpAddress, String whatsAppMessage, String whatsAppNumber, String token) {
        this.id = id;
        this.message = message;
        this.subject = subject;
        this.cc = cc;
        this.bcc = bcc;
        this.senderMail = senderMail;
        this.senderPassword = senderPassword;
        this.senderName = senderName;
        this.smtpPort = smtpPort;
        this.smtpAddress = smtpAddress;
        this.whatsAppMessage = whatsAppMessage;
        this.whatsAppNumber = whatsAppNumber;
        this.token = token;
    }

    public String getWhatsAppMessage() {
        return whatsAppMessage;
    }

    public void setWhatsAppMessage(String whatsAppMessage) {
        this.whatsAppMessage = whatsAppMessage;
    }

    public String getWhatsAppNumber() {
        return whatsAppNumber;
    }

    public void setWhatsAppNumber(String whatsAppNumber) {
        this.whatsAppNumber = whatsAppNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public MailReminderRealmModel() {
    }

}
