package tcmp.uniquename.com.app_28_04_16;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by tcmp on 28/02/2016.
 */
public class MyWebFragment extends Fragment{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_layout, null);
        final WebView webView = (WebView) root.findViewById(R.id.webView);
        final ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://nrg.co.il");
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "Long", Toast.LENGTH_LONG).show();
                if(webView.getHitTestResult().getExtra() != null){
                    
                }
                return true;
            }
        });

        return root;
    }
}
