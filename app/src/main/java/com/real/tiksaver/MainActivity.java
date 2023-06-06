package com.real.tiksaver;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.example.flatdialoglibrary.*;
import com.google.android.material.textfield.*;
import com.google.gson.Gson;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import com.real.tiksaver.ApiClient;
import java.lang.ref.WeakReference;
import com.example.flatdialoglibrary.dialog.FlatDialog;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double pro = 0;
	private HashMap<String, Object> getv = new HashMap<>();
	private HashMap<String, Object> Get_Videos_Jsons = new HashMap<>();
	private boolean islink = false;
	private boolean isdownload = false;
	private String Posit = "";
	private String video_size = "";
	private JSONObject Object;
	public static  WeakReference<MainActivity> weakActivity;
	private String vid_url = "";
	public static int READ_REQUEST_CODE;
	private FlatDialog CallDia;
	private String file_path = "";
	private String sharedText = "";
	private String outName = "";
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private CardView cardview1;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear2;
	private CardView editcard;
	private LinearLayout info_box;
	private CardView cardButton;
	private TextView download_int;
	private LinearLayout linear4;
	private TextInputLayout textinputlayout1;
	private ProgressBar get_progress;
	private EditText edittext1;
	private CardView image_box;
	private LinearLayout linear9;
	private ImageView thumb_img;
	private TextView title;
	private LinearLayout linear10;
	private CircleImageView userimg;
	private LinearLayout linear11;
	private TextView username;
	private TextView filesize;
	private LinearLayout buttonparent;
	private LinearLayout bottonchild;
	private TextView button_n;
	
	private TimerTask toop;
	private RequestNetwork Get_Video;
	private RequestNetwork.RequestListener _Get_Video_request_listener;
	private AlertDialog.Builder dio;
	private SharedPreferences setting;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear3 = findViewById(R.id.linear3);
		cardview1 = findViewById(R.id.cardview1);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear2 = findViewById(R.id.linear2);
		editcard = findViewById(R.id.editcard);
		info_box = findViewById(R.id.info_box);
		cardButton = findViewById(R.id.cardButton);
		download_int = findViewById(R.id.download_int);
		linear4 = findViewById(R.id.linear4);
		textinputlayout1 = findViewById(R.id.textinputlayout1);
		get_progress = findViewById(R.id.get_progress);
		edittext1 = findViewById(R.id.edittext1);
		image_box = findViewById(R.id.image_box);
		linear9 = findViewById(R.id.linear9);
		thumb_img = findViewById(R.id.thumb_img);
		title = findViewById(R.id.title);
		linear10 = findViewById(R.id.linear10);
		userimg = findViewById(R.id.userimg);
		linear11 = findViewById(R.id.linear11);
		username = findViewById(R.id.username);
		filesize = findViewById(R.id.filesize);
		buttonparent = findViewById(R.id.buttonparent);
		bottonchild = findViewById(R.id.bottonchild);
		button_n = findViewById(R.id.button_n);
		Get_Video = new RequestNetwork(this);
		dio = new AlertDialog.Builder(this);
		setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() == 0) {
					button_n.setText("Paste");
					
					Posit = "";
				}
				else {
					button_n.setText("Get Video");
					
					Posit = "get";
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		bottonchild.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_OnclickV(cardButton);
				switch(Posit) {
					default: {
						edittext1.setText(String.valueOf(((android.content.ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).getText()));
						Posit = "get";
						button_n.setText("Get Video");
						break;
					}
					case "get": {
						if (_Check_Url(edittext1.getText().toString())) {
							_Get_Videos(edittext1.getText().toString());
						}
						break;
					}
					case "download": {
						_Download_Videoz();
						break;
					}
					case "download2": {
						outName = title.getText().toString().replace(",", "").replace(".", "");
						String getUrl = vid_url;
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
						String title = outName;
						request.setTitle(title);
						request.setDescription("Tiktok Downloader");
						String cookie = CookieManager.getInstance().getCookie(getUrl);
						request.addRequestHeader("cockie",cookie);
						request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						request.setDestinationInExternalPublicDir("/Download/Tiktok Video/",title+".mp4");
						
						DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
						downloadManager.enqueue(request);
						break;
					}
				}
			}
		});
		
		_Get_Video_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try{
					Object = new JSONObject(_response);
					//Create custom Objects
					//private JSONArray Array;
					//private JSONObject Object;
					Glide.with(getApplicationContext()).load(Uri.parse(Object.getString("thumb"))).into(thumb_img);
					Glide.with(getApplicationContext()).load(Uri.parse(Object.getString("authorimg"))).into(userimg);
					username.setText(Object.getString("author"));
					title.setText(Object.getString("desc"));
					video_size = Object.getString("size");
					vid_url = Object.getString("url");
					long l = Long.valueOf(Object.getString("size")); 
					long MEGABYTE = 1024L * 1024L; 
					long b = l / MEGABYTE; 
					filesize.setText("File Size: "+ b +" Mb");
					Posit = "download";
					button_n.setText("Download Video");
					info_box.setVisibility(View.VISIBLE);
					_OnclickV(info_box);
					get_progress.setVisibility(View.GONE);
					edittext1.setEnabled(true);
					bottonchild.setEnabled(true);
				}catch(Exception e){
					FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/hoo.json"), new Gson().toJson(getv));
					get_progress.setVisibility(View.GONE);
					edittext1.setEnabled(true);
					bottonchild.setEnabled(true);
					Posit = "get";
					dio.setMessage("Message");
					dio.create().show();
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				get_progress.setVisibility(View.GONE);
				edittext1.setEnabled(true);
				bottonchild.setEnabled(true);
				dio.setMessage(_message);
				dio.create().show();
			}
		};
	}
	
	private void initializeLogic() {
		info_box.setVisibility(View.GONE);
		get_progress.setVisibility(View.GONE);
		//Then in MainActivity OnCreate()
		weakActivity = new WeakReference<>(MainActivity.this);
		edittext1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) {
				        int color = hasFocus ? Color.rgb(255, 0, 79) : Color.rgb(174, 171, 177);
				        textinputlayout1.setStartIconTintList(ColorStateList.valueOf(color));
				    }
		});
		
		//import android.view.inputmethod.InputMethodManager;
		if (!setting.contains("path")) {
			final FlatDialog CallDia = new FlatDialog(MainActivity.this);
			CallDia.setBackgroundColor(0xFFE91E63);
			CallDia.setTitle("Create TikTok Download Location");
			CallDia.setSubtitle("Create TikTok Download Location In Your Local Stroage or Extanal Sdcard");
			CallDia.setFirstButtonText("Create");
			CallDia.setFirstButtonColor(0xFF4CAF50);
			CallDia.setCancelable(false);
			CallDia.withFirstButtonListner(new View.OnClickListener() {
				@Override public void onClick(View view) {
					//public static int READ_REQUEST_CODE;
					
					Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
					startActivityForResult(intent, READ_REQUEST_CODE);
					
					CallDia.dismiss();
				} });
			CallDia.show();
		}
		sharedText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
		if (sharedText != null) {
			edittext1.setText(sharedText);
			Posit = "get";
			button_n.setText("Loading Video");
			bottonchild.performClick();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		try{
				if (_resultCode == RESULT_OK) {
							Uri treeUri = _data.getData();
						   //grant write permissions
						  file_path = treeUri.getPath(); getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
						   
						   String[] parts = file_path.split(":");
							if (parts[0].contains("primary")) {
										file_path = FileUtil.getExternalStorageDir().concat("/".concat(parts[1]));
							}
							  
						setting.edit().putString("path", file_path).commit();
						  
						  }else{
						
						  	dio.setMessage("Permission Not Chosen Try Again");
						dio.create().show();
						
						  } 
		}catch(Exception e){
				dio.setMessage(e.toString());
				dio.create().show();
		}
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _Get_Videos(final String _urls) {
		getv = new HashMap<>();
		getv.put("video-url", _urls);
		Get_Video.setParams(getv, RequestNetworkController.REQUEST_BODY);
		Get_Video.startRequestNetwork(RequestNetworkController.POST, "https://api.rav.com.ng/get-tiktok", "", _Get_Video_request_listener);
		get_progress.setVisibility(View.VISIBLE);
		button_n.setText("Loading Video");
		edittext1.setEnabled(false);
		bottonchild.setEnabled(false);
	}
	
	
	public void _intl() {
		//Then in MainActivity Create Custum ver
		//public static WeakReference<MainActivity> weakActivity;
		//Put THis In More Block
	}
	public static MainActivity getmInstanceActivity() {
		    return weakActivity.get();
		
	}
	
	
	public void _UpdateDownload(final String _Msg, final double _Progress) {
		button_n.setText("");
		download_int.setText(_Msg);
		bottonchild.setLayoutParams(new LinearLayout.LayoutParams((int) ((buttonparent.getWidth() * _Progress) / 100),(int) (-1)));
		if (_Progress == 100) {
			edittext1.setEnabled(true);
			bottonchild.setEnabled(true);
			edittext1.setText("");
			download_int.setText("");
			Glide.with(getApplicationContext()).load(Uri.parse("null")).into(thumb_img);
			info_box.setVisibility(View.GONE);
		}
	}
	
	
	public void _Download_Error(final String _msg, final String _type) {
		edittext1.setEnabled(true);
		bottonchild.setEnabled(true);
		download_int.setText(_msg);
		info_box.setVisibility(View.INVISIBLE);
		info_box.setVisibility(View.GONE);
	}
	
	
	public void _OnclickV(final View _view) {
		
		Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
		 _view.startAnimation(animation);
	}
	
	
	public void _Download_Videoz() {
		getv = new HashMap<>();
		getv.put("url", vid_url);
		outName = title.getText().toString().replace(",", "").replace(".", "").concat(".mp4");
		getv.put("name", outName);
		getv.put("format", "v");
		ApiClient apiClient = new ApiClient();
		   String url = "https://api.rav.com.ng/download-tiktok";
		   String requestBody = new Gson().toJson(getv);
		   String destinationPath = setting.getString("path", "").concat("/").concat(outName);
		   String size = video_size; apiClient.makePostRequestAndDownloadResponse(url, requestBody, destinationPath,size);
		
		bottonchild.setEnabled(false);
		edittext1.setEnabled(false);
		
	}
	
	
	public boolean _Check_Url(final String _url) {
		textinputlayout1.setErrorEnabled(false);
		if (_url.contains("www.tiktok.com/@")) {
			return (true);
		}
		else {
			if (_url.contains("www.tiktok.com/video")) {
				return (true);
			}
			else {
				if (_url.contains("vt.tiktok.com")) {
					return (true);
				}
				else {
					if (_url.contains("vm.tiktok.com")) {
						return (true);
					}
					else {
						textinputlayout1.setErrorEnabled(true);
						textinputlayout1.setError("Wrong url");
						return (false);
					}
				}
			}
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}