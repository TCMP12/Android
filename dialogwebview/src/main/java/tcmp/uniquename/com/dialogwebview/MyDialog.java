package tcmp.uniquename.com.dialogwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by tcmp on 28/02/2016.
 */
public class MyDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.web_fragment, null);

        WebView webView = (WebView) root.findViewById(R.id.webView);
        webView.loadUrl("file:///android_res/raw/my_html_file");
//        webView.loadUrl("file:///android_asset/my_html_file.html");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "myDialog");

       // webView.loadData("javascript:", "text/javascript", "UTF-8");
        return root;
    }


    @JavascriptInterface
    public void click(){
        Toast.makeText(getContext(), "JS call me", Toast.LENGTH_LONG).show();
    }
}
