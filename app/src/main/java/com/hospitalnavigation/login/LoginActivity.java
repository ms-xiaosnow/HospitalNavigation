package com.hospitalnavigation.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hospitalnavigation.MainActivity;
import com.hospitalnavigation.R;
import com.hospitalnavigation.utils.SPUtils;
import com.hospitalnavigation.utils.ToastUtils;

import java.util.Random;


/**
 * Created by xsl on 2019/3/20.
 */

public class LoginActivity extends AppCompatActivity {

    LoginButton btn_login;
    RelativeLayout login_content;
    ImageView img_login_bg;
    View color_view;
    EditText ev_userName, ev_pwd;
    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
            R.drawable.meinv
    };
    private static long DOUBLE_CLICK_TIME = 0L;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initData();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initData() {
        btn_login = findViewById(R.id.btn_login);
        login_content = findViewById(R.id.login_content);
        img_login_bg = findViewById(R.id.img_login_bg);
        color_view = findViewById(R.id.color_view);
        ev_userName = findViewById(R.id.ev_userName);
        ev_pwd = findViewById(R.id.ev_pwd);

        Random r = new Random(SystemClock.elapsedRealtime());
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),
                SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        img_login_bg.setImageBitmap(RenderScriptHelper.rsBlur(this, myBitmap, 14));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ev_userName.getText().toString())) {
                    ToastUtils.show(LoginActivity.this, "请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(ev_pwd.getText().toString())) {
                    ToastUtils.show(LoginActivity.this, "请输入密码");
                    return;
                }

                if (ev_userName.getText().toString().equals("xsl") && ev_pwd.getText().toString().equals("123")) {
                    switch (view.getId()) {
                        case R.id.btn_login:
                            if (btn_login.isRuning()) {
                                btn_login.setState(LoginButton.STATUS_ERROR);
                            } else {
                                btn_login.startOk();
                                new Handler().postDelayed(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    public void run() {
                                        startAnimate();

                                    }

                                }, 1000);
                            }
                            break;
                    }
                } else {
                    ToastUtils.show(LoginActivity.this, "账号或密码错误");
                }


            }
        });

        findViewById(R.id.tv_rg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startAnimate() {
        int[] location = new int[2];
        btn_login.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(color_view,
                x + btn_login.getMeasuredWidth() / 2,
                y + btn_login.getMeasuredHeight() / 2,
                btn_login.getWidth() / 2, login_content.getHeight());
        mAnimator.setDuration(400);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                SPUtils.put(LoginActivity.this, "loginState", true);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                color_view.setVisibility(View.VISIBLE);
            }
        });
        mAnimator.start();
    }

    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                Toast.makeText(LoginActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
