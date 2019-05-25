package com.hospitalnavigation;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "LfeVMby0Qh8nBmG1qJwADcAq-gzGzoHsz", "vmjs3gpADjiSAM7dxdqEHHWu");
    }
}
