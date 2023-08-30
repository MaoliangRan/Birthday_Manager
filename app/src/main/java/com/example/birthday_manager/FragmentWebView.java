package com.example.birthday_manager;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


public class FragmentWebView extends Fragment implements OnClickListener {

    private WebView webview;
    private Button backtohome;
    private MainActivity mainActivity;
    private EditText urladress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_webview, container, false);
        View view = this.getLayoutInflater(savedInstanceState).inflate((R.layout.fragment_webview), null);

        mainActivity = (MainActivity) getActivity();
        webview = (WebView) view.findViewById(R.id.webview_layout);
        backtohome = (Button) view.findViewById(R.id.backtohome);
        urladress = (EditText) view.findViewById(R.id.urladress);
        backtohome.setOnClickListener(this);

        webview.getSettings().setJavaScriptEnabled(true);
        //加载url，但是不会显示
        webview.loadUrl(mainActivity.url);
        urladress.setText(mainActivity.url);
        //指定显示控件(class)
        webview.setWebViewClient(new myWebViewClient());

        return view;
    }

    // webView视图客户端
    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
//          return super.shouldOverrideUrlLoading(view, url);
            webview.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtohome:
                mainActivity.home();
                break;
        }
    }
}