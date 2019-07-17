package hris.enseval.samuelsep.paadmin.Application;

import android.content.Context;

import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;


public class MyApplication extends android.app.Application {
    private static MyApplication mInstance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();

        //kode konfigurasi Realm
//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder(). deleteRealmIfMigrationNeeded().build();
//        Realm.setDefaultConfiguration(config);

    }

    public static Context getContext(){
        return mContext;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            //Mengambil schema
            RealmSchema schema = realm.getSchema();
            RealmSchema schema2 = realm.getSchema();


            //membuat schema baru jika versi 0
            if (oldVersion == 0) {
                schema.create("User")
                        .addField("id", String.class)
                        .addField("userID", String.class)
                        .addField("username", String.class)
                        .addField("empEmail", String.class)
                        .addField("password", String.class)
                        .addField("empNIK", String.class)
                        .addField("lastChangePassword", String.class)
                        .addField("lastUpdate", String.class)
                        .addField("lastUpdateBy", String.class)
                        .addField("fgActiveYN", String.class)
                        .addField("token", String.class)
                        .addField("userQR", String.class);

                schema2.create("EmailReminder")
                        .addField("id", String.class)
                        .addField("message", String.class)
                        .addField("subject", String.class)
                        .addField("cc", String.class)
                        .addField("bcc", String.class)
                        .addField("senderMail", String.class)
                        .addField("senderPassword", String.class)
                        .addField("senderName", String.class)
                        .addField("smtpPort", String.class)
                        .addField("smtpAddress", String.class)
                        .addField("whatsAppMessage", String.class)
                        .addField("whatsAppNumber", String.class)
                        .addField("token", String.class);

                oldVersion++;
            }

        }
    }
}
