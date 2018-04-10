package com.veliyetisgengil.onlinequizapp.BroadcastReceiver;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.veliyetisgengil.onlinequizapp.Common.Common;

/**
 * Created by veliyetisgengil on 3.04.2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
      handleNotification(remoteMessage.getNotification().getBody());


    }

    private void handleNotification(String body) {

        Intent pushNotification =new Intent(Common.STR_PUSH);
        pushNotification.putExtra("message",body);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }
}
