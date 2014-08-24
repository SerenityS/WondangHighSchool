package wondang.icehs.kr.whdghks913.wondanghighschool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends Activity {

	private WebView mWebView;
	private WebSettings mWebSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		mWebView = (WebView) findViewById(R.id.mWebView);

		mWebSettings = mWebView.getSettings();
		// mWebSettings.setSavePassword(false);
		// mWebSettings.setAppCacheMaxSize(10000);
		mWebSettings.setSaveFormData(false);
		mWebSettings.setSupportZoom(true);
		mWebSettings.setBuiltInZoomControls(true);
		mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

		mWebView.setWebChromeClient(new webViewChrome());
		mWebView.setWebViewClient(new webViewClient());

		mWebView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});

		Intent mIntent = getIntent();

		mWebView.loadUrl(mIntent.getStringExtra("url"));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mWebView.clearCache(true);
		mWebView.destroy();
	}

	public class webViewChrome extends WebChromeClient {

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			return super.onJsAlert(view, url, message, result);
		}

		@Override
		public boolean onJsBeforeUnload(WebView view, String url,
				String message, JsResult result) {
			return super.onJsBeforeUnload(view, url, message, result);
		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			return super.onJsConfirm(view, url, message, result);
		}

		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
	}

	public class webViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView mView, String url) {
			mView.loadUrl(url);

			return super.shouldOverrideUrlLoading(mView, url);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (mWebView.canGoBack()) {
				mWebView.goBack();
			} else {
				mWebView.clearCache(false);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
