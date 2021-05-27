package com.mail.comm.interfaces;


public interface LifeCycleListener {

    void onCreate();

    void onStart();

    void onReStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
