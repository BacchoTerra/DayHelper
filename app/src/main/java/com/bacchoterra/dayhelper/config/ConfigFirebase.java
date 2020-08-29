package com.bacchoterra.dayhelper.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigFirebase {

    public static FirebaseAuth auth;


    public static FirebaseAuth getFbAuth() {

        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }

        return auth;

    }


}
