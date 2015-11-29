package cn.fpower.financeservice.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.DialogUtils;

public class WebViewActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.webview)
    private WebView webView;

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.root)
    LinearLayout root;

    @Override
    protected int initLayout() {
        return R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        back.setOnClickListener(this);
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setLoadWithOverviewMode(true);
        String address = getIntent().getStringExtra("address");
        if (address.equals("http://www.idongri.com")) {
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setUseWideViewPort(true);
            webView.setInitialScale(1);
        }
        webView.loadUrl(address);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                DialogUtils.showProgess(WebViewActivity.this, R.string.loading);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogUtils.dismissProgessDirectly();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        root.removeView(webView);
        webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (DialogUtils.isDialogShowing()) {
                DialogUtils.dismissProgessDirectly();
            } else {
                if (webView.canGoBack()) {
                    webView.goBack();// 返回前一个页面
                    return true;
                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_stable, R.anim.out_push_left_to_right);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
