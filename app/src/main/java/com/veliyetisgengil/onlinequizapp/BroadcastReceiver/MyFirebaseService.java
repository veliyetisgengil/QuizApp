package com.veliyetisgengil.onlinequizapp.BroadcastReceiver;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by veliyetisgengil on 3.04.2018.
 */

public class MyFirebaseService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshToken);
    }

    private void sendRegistrationToServer(String refreshToken) {

        Log.d("token",refreshToken);
    }
}
