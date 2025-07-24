package com.example.meetdoc.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings;

public class CometChatHelper {

    private static final String TAG = "CometChatHelper";
    private static final String appID = "APPID"; // Your App ID
    private static final String region = "APPREGION"; // Your Region
    private static final String authKey = "APIKEY"; // Your Auth Key

    public static void initCometChat(Context context) {
        UIKitSettings uiKitSettings = new UIKitSettings.UIKitSettingsBuilder()
                .setRegion(region)
                .setAppId(appID)
                .setAuthKey(authKey)
                .subscribePresenceForAllUsers()
                .build();

        CometChatUIKit.init(context, uiKitSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String success) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Initialization successful");
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Initialization failed: " + (e != null ? e.getMessage() : "Unknown error"));
            }
        });
    }

    public static void loginUser(String uid) {
        CometChatUIKit.login(uid, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Login successful: " + user.getUid());
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Login failed: " + (e != null ? e.getMessage() : "Unknown error"));
            }
        });
    }

    public static void registerUser(String uid, String name)  {
        User userdata = new User();
        userdata.setUid(uid);
        userdata.setName(name);
        Log.d(TAG, name+uid+authKey);
        CometChat.createUser(userdata, authKey, new CometChat.CallbackListener <User> () {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, user.toString());
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }
}
