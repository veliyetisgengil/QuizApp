package com.veliyetisgengil.onlinequizapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.veliyetisgengil.onlinequizapp.BroadcastReceiver.AlarmReceiver;
import com.veliyetisgengil.onlinequizapp.Common.Common;
import com.veliyetisgengil.onlinequizapp.Model.User;

import java.io.IOError;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtNewUser, edtNewPassword, edtNewEmail; //for signup
    MaterialEditText edtUser, edtPassword, edtEmail;//for sigin

    Button btnSignUp, btnSignIn;

    FirebaseDatabase database;

    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mSharedPrefs = getSharedPreferences("xmlFile",MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        int mCounter = mSharedPrefs.getInt("counter", 0);
        if(mCounter==0){
            try{             Intent HomeScreenShortCut= new Intent(getApplicationContext(),SplashScreenActivity.class);
                HomeScreenShortCut.setAction(Intent.ACTION_MAIN);
                HomeScreenShortCut.putExtra("duplicate", false);
                Intent addIntent = new Intent();
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, HomeScreenShortCut);
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "SoruÇöz");
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                        Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.mipmap.ic_launcher));
                addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                getApplicationContext().sendBroadcast(addIntent);
            }catch(IOError e){             }         }
            mPrefsEditor.putInt("counter", ++mCounter);
        mPrefsEditor.commit();

        registerAlarm();

//firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtUser = (MaterialEditText) findViewById(R.id.edtUserName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin(edtUser.getText().toString(),edtPassword.getText().toString());
            }
        });
    }

    private void registerAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,38);
        calendar.set(Calendar.SECOND,0);


        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);




    }

    private void signin(final String user, final String password) {
users.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.child(user).exists()){

            if(!user.isEmpty()){
                User login = dataSnapshot.child(user).getValue(User.class);
                if (login.getPassword().equals(password)){
                    Toast.makeText(MainActivity.this, "Giriş Yapıldı. Hoşgeldiniz.", Toast.LENGTH_SHORT).show();
                    Common.currentUser = login;
                    Intent homeActivity = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(homeActivity);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Giriş Yapılamadı.Lütfen bilgilerinizi kontrol ediniz.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MainActivity.this, "Kullanıcı Adı Girmelisiniz.", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(MainActivity.this, "Kullanıcı Bulunamadı.Lütfen önce kayıt olunuz.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Kayıt OL");
        alertDialog.setMessage("Lütfen Bilgilerinizi Eksiksiz Doldurun.");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up, null);

        edtNewUser = (MaterialEditText) sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = (MaterialEditText) sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.edtNewEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(edtNewUser.getText().toString(), edtNewPassword.getText().toString(), edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUserName()).exists()) {
                            Toast.makeText(MainActivity.this, "Kullanıcı Adı Kullanılıyor.", Toast.LENGTH_SHORT).show();
                        } else {
                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(MainActivity.this, "Kullanıcı Kayıt Edildi.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}
