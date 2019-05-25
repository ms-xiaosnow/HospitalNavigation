package com.hospitalnavigation.fregment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by xsl on 2016/4/6.
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {
    private Bundle bundle;

    private Context mContext;
    private Toast mToast;

    protected Activity mActivity;


    public void logd(String str) {
        Log.d(getClass().getSimpleName(), str);
    }

    public void loge(String str) {
        Log.e(getClass().getSimpleName(), str);
    }

    public void logv(String str) {
        Log.v(getClass().getSimpleName(), str);
    }

    public void toast(String str) {

        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(str);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Context mGetContext() {

        if (mContext == null) {
            mContext = getActivity();
        }

        return mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        // 添加Activity到堆栈
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(bindLayout(), null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        initView(view);
        initData(view);
    }

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getActivity().getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void goActivity(Class<?> goActivity) {
        startActivity(new Intent(getActivity(), goActivity));
    }
}
