package com.soiadmahedi.gallery;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.provider.Settings;
import android.Manifest;
import android.content.ContentUris;
import com.bumptech.glide.request.RequestOptions;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double index = 0;
	private String path = "";
	private String directory = "";
	private String videoPath = "";
	private String videoSize = "";
	private String videoHeight = "";
	private String videoWidth = "";
	private String videoDate = "";
	private String videoDuration = "";
	private String videoTitle = "";
	private double createdTimeMillisec = 0;
	private String formattedDate = "";
	private double index1 = 0;
	private double folderExist = 0;
	private HashMap<String, Object> galleryMap = new HashMap<>();
	private String galleryPath = "";
	private String galleryName = "";
	
	private ArrayList<HashMap<String, Object>> folders = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allGalleryList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> gallerys = new ArrayList<>();
	
	private LinearLayout background_layer;
	private LinearLayout folder_layer;
	private LinearLayout file_layer;
	private GridView gridview_folder;
	private GridView gridview_file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		initializeLogic();
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		background_layer = findViewById(R.id.background_layer);
		folder_layer = findViewById(R.id.folder_layer);
		file_layer = findViewById(R.id.file_layer);
		gridview_folder = findViewById(R.id.gridview_folder);
		gridview_file = findViewById(R.id.gridview_file);
		
		gridview_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				_GET_VIDEO_BY_FOLDER(folders.get((int)_position).get("directory").toString());
				folder_layer.setVisibility(View.GONE);
				file_layer.setVisibility(View.VISIBLE);
			}
		});
	}
	
	private void initializeLogic() {
		
		if (_CHECK_FILE_PERMISSION()) {
			_VIDEO_FILE_LOAD();
		}
		else {
			_REQUEST_FILE_PERMISSION();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		if (_requestCode == 2298) {
				initializeLogic();
		}
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
	@Override
	public void onBackPressed() {
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			finishAndRemoveTask();
		}
		else {
			finish();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public void _GRADIENT_DRAWABLE(final View _view, final double _radius, final double _stroke, final double _shadow, final String _color, final String _borderColor, final boolean _ripple, final boolean _clickAnim, final double _animDuration) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			if (Build.VERSION.SDK_INT >= 21){
					_view.setElevation((int)_shadow);
			}
			
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#E8F5E9")});
			android.graphics.drawable.RippleDrawable ripdrb = null;
			_view.setClickable(true);
			if (android.os.Build.VERSION.SDK_INT >= 21) {
				ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
				_view.setBackground(ripdrb);
			}
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			_view.setBackground(gd);
			if (Build.VERSION.SDK_INT >= 21){
					_view.setElevation((int)_shadow);
			}
			
		}
		if (_clickAnim) {
			_view.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
							switch (event.getAction()){
									case MotionEvent.ACTION_DOWN:{
											ObjectAnimator scaleX = new ObjectAnimator();
											scaleX.setTarget(_view);
											scaleX.setPropertyName("scaleX");
											scaleX.setFloatValues(0.9f);
											scaleX.setDuration((int)_animDuration);
											scaleX.start();
											
											ObjectAnimator scaleY = new ObjectAnimator();
											scaleY.setTarget(_view);
											scaleY.setPropertyName("scaleY");
											scaleY.setFloatValues(0.9f);
											scaleY.setDuration((int)_animDuration);
											scaleY.start();
											break;
									}
									case MotionEvent.ACTION_UP:{
											
											ObjectAnimator scaleX = new ObjectAnimator();
											scaleX.setTarget(_view);
											scaleX.setPropertyName("scaleX");
											scaleX.setFloatValues((float)1);
											scaleX.setDuration((int)_animDuration);
											scaleX.start();
											
											ObjectAnimator scaleY = new ObjectAnimator();
											scaleY.setTarget(_view);
											scaleY.setPropertyName("scaleY");
											scaleY.setFloatValues((float)1);
											scaleY.setDuration((int)_animDuration);
											scaleY.start();
											break;
									}
							}
							return false;
					}
			});
			
		}
	}
	
	
	public void _GET_VIDEO_BY_FOLDER(final String _directory) {
		
		gallerys.clear();
		index = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allGalleryList.size()); _repeat11++) {
			path = allGalleryList.get((int)index).get("galleryPath").toString();
			java.io.File file = new java.io.File(path);
			directory = file.getParent();
			if (directory.equals(_directory)) {
				if (FileUtil.isExistFile(path)) {
					try{
						galleryMap = allGalleryList.get((int)index);
						gallerys.add(galleryMap);
					} catch (Exception onlyTryAdd) { /** noException - Soiad Mahedi */ }
				}
			}
			index++;
		}
		folder_layer.setVisibility(View.GONE);
		file_layer.setVisibility(View.VISIBLE);
		gridview_file.setAdapter(new Gridview_fileAdapter(gallerys));
		gridview_file.setNumColumns((int)3);
		gridview_file.setColumnWidth(GridView.AUTO_FIT);
		gridview_file.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
	}
	
	
	public void _GET_ALL_VIDEO() {
		allGalleryList.clear();
		gallerys.clear();
		folders.clear();
		Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String orderBy = android.provider.MediaStore.Images.Media.DATE_TAKEN;
		String[] projection = {
				android.provider.MediaStore.MediaColumns.DATA,
				android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME
		};
		android.database.Cursor cursor = getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
		if (cursor != null) {
				while (cursor.moveToNext()) {
						
				galleryPath = cursor.getString(0);
				galleryName = cursor.getString(1);
				galleryMap = new HashMap<>();
				galleryMap.put("galleryPath", galleryPath);
				galleryMap.put("galleryName", galleryName);
				allGalleryList.add(galleryMap);
			}
			cursor.close();
		}
	}
	
	
	public void _GET_FOLDER_LIST() {
		folders.clear();
		index = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(allGalleryList.size()); _repeat12++) {
			path = allGalleryList.get((int)index).get("galleryPath").toString();
			java.io.File file = new java.io.File(path);
			directory = file.getParent();
			index1 = 0;
			folderExist = 0;
			for(int _repeat36 = 0; _repeat36 < (int)(folders.size()); _repeat36++) {
				if (directory.equals(folders.get((int)index1).get("directory").toString())) {
					folderExist = 1;
					folders.get((int)index1).put("items", String.valueOf((long)(Double.parseDouble(folders.get((int)index1).get("items").toString()) + 1)));
					folders.get((int)index1).put("size", String.valueOf((long)(Double.parseDouble(folders.get((int)index1).get("size").toString()) + FileUtil.getFileLength(path))));
				}
				index1++;
			}
			if (folderExist == 0) {
				if (FileUtil.isExistFile(directory)) {
					if (!String.valueOf((long)(FileUtil.getFileLength(path))).equals("0")) {
						galleryMap = new HashMap<>();
						galleryMap.put("directory", directory);
						galleryMap.put("directoryName", Uri.parse(directory).getLastPathSegment());
						galleryMap.put("lowerCaseDirectoryName", Uri.parse(directory).getLastPathSegment().toLowerCase());
						galleryMap.put("items", "1");
						galleryMap.put("size", String.valueOf((long)(FileUtil.getFileLength(path))));
						folders.add(galleryMap);
					}
				}
			}
			index++;
		}
	}
	
	
	public void _VIDEO_FILE_LOAD() {
		_GET_ALL_VIDEO();
		_GET_FOLDER_LIST();
		folder_layer.setVisibility(View.VISIBLE);
		file_layer.setVisibility(View.GONE);
		gridview_folder.setAdapter(new Gridview_folderAdapter(folders));
		gridview_folder.setNumColumns((int)3);
		gridview_folder.setColumnWidth(GridView.AUTO_FIT);
		gridview_folder.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
	}
	
	
	public boolean _CHECK_FILE_PERMISSION() {
		if (android.os.Build.VERSION.SDK_INT >= 30) {
			return Environment.isExternalStorageManager();
		}
		else {
			if (android.os.Build.VERSION.SDK_INT >= 23) {
				int writePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
				int readPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
				return writePermission == PackageManager.PERMISSION_GRANTED
						            && readPermission == PackageManager.PERMISSION_GRANTED;
			}
			else {
				return true;
			}
		}
	}
	
	
	public void _REQUEST_FILE_PERMISSION() {
		if (android.os.Build.VERSION.SDK_INT >= 30) {
			try {
				Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
				startActivityForResult(intent, 2298);
			} catch (Exception e) {
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				startActivityForResult(intent, 2298);
			}
		}
		else {
			if (android.os.Build.VERSION.SDK_INT >= 23) {
				ActivityCompat.requestPermissions(MainActivity.this, new String[] {
					android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
					android.Manifest.permission.READ_EXTERNAL_STORAGE,
					android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
				}, 1000);
			}
		}
	}
	
	
	public void _NAVIGATE_MODE_ON(final String _activityTitle) {
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			 @Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		if (_activityTitle.equals("")) {
			getSupportActionBar().setTitle(getString(R.string.app_name));
		}
		else {
			getSupportActionBar().setTitle(_activityTitle);
		}
	}
	
	public class Gridview_folderAdapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Gridview_folderAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.item_view, null);
			}
			
			final LinearLayout linear_bg = _view.findViewById(R.id.linear_bg);
			final androidx.cardview.widget.CardView cardview = _view.findViewById(R.id.cardview);
			final TextView textview_title = _view.findViewById(R.id.textview_title);
			final LinearLayout linear_fg = _view.findViewById(R.id.linear_fg);
			final ImageView imageview_thumb = _view.findViewById(R.id.imageview_thumb);
			
			imageview_thumb.setImageResource(R.drawable.ic_folder_open_black);
			textview_title.setText(_data.get((int)_position).get("directoryName").toString());
			
			return _view;
		}
	}
	
	public class Gridview_fileAdapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Gridview_fileAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.item_view, null);
			}
			
			final LinearLayout linear_bg = _view.findViewById(R.id.linear_bg);
			final androidx.cardview.widget.CardView cardview = _view.findViewById(R.id.cardview);
			final TextView textview_title = _view.findViewById(R.id.textview_title);
			final LinearLayout linear_fg = _view.findViewById(R.id.linear_fg);
			final ImageView imageview_thumb = _view.findViewById(R.id.imageview_thumb);
			
			textview_title.setVisibility(View.GONE);
			imageview_thumb.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_data.get((int)_position).get("galleryPath").toString(), 1024, 1024));
			
			return _view;
		}
	}
}