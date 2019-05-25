package com.hospitalnavigation.fregment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hospitalnavigation.R;

public class NavigationFragment extends BaseFragment {
    @Override
    public int bindLayout() {
        return R.layout.fg_navigation;
    }

    @Override
    public void initView(View view) {
        WebView browser = (WebView) view.findViewById(R.id.Toweb);
        browser.loadUrl("http://p.brtbeacon.net/bb/hospital/navigator.html?signa=a8c116e62b714e97881282a64cd626ab");
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true); //启用javascript
        webSettings.setAppCacheEnabled(true);   //启用appCache
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);

        //设置可自由缩放网页、JS生效
        //webSettings.setSupportZoom(true);
        //webSettings.setBuiltInZoomControls(true);

        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void initData(View view) {

    }
}
