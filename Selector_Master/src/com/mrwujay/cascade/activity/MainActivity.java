package com.mrwujay.cascade.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mrwujay.cascade.R;
import com.mrwujay.cascade.R.id;
import com.mrwujay.cascade.R.layout;
import com.mrwujay.cascade.model.Areas;
import com.mrwujay.cascade.model.BaseEntry;

import utils.wheel.widget.OnWheelChangedListener;
import utils.wheel.widget.WheelView;
import utils.wheel.widget.adapters.ArrayWheelAdapter;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener, OnWheelChangedListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	private EditText edittext;
	private BaseEntry<ArrayList<Areas>> areas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		try {
			InputStream is=getResources().getAssets().open("areas.txt");
			InputStreamReader read = new InputStreamReader(
					is,"utf-8");//¿¼ÂÇµ½±àÂë¸ñÊ½
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    StringBuffer sb=new StringBuffer();
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	sb.append(lineTxt);
                    }
            read.close();
            
             
            System.out.println("areas:"+ sb.toString());
            
            Type type = new TypeToken<BaseEntry<ArrayList<Areas>>>() {
     		}.getType(); 
     		 
    		//BaseEntry<MineTopObj> minetop= new Gson().fromJson(PrefUtils.getMineMenu(getActivity()), type);
     	   areas= new Gson().fromJson(sb.toString(), type);
     		
     		if(areas.getData()==null || areas.getData().size()==0){
     			return;
     		}
     		for (Areas area : areas.getData()) {
     			System.out.println("Ê¡:"+area.getArea());
     			for (Areas city : area.getChildAreas()) {
     				System.out.println("ÊÐ:"+city.getArea());
     				 
				}
			}
     		
     		
    		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setUpViews();
		setUpListener();
		setUpData();
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		
		mViewProvince.setVisibility(View.GONE);
		mViewCity.setVisibility(View.GONE);
		mViewDistrict.setVisibility(View.GONE);
		
		edittext=(EditText) findViewById(R.id.edittext);
		
		edittext.setInputType(EditorInfo.TYPE_NULL);
		edittext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 
			}
		});
		
		edittext.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					mViewProvince.setVisibility(View.GONE);
					mViewCity.setVisibility(View.GONE);
					mViewDistrict.setVisibility(View.GONE);
				}else{
					 InputMethodManager inputManager =

			                    (InputMethodManager)MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

					 inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					 
			                //inputManager.hideSoftInputFromInputMethod(null, InputMethodManager.HIDE_IMPLICIT_ONLY);
					mViewProvince.setVisibility(View.VISIBLE);
					mViewCity.setVisibility(View.VISIBLE);
					mViewDistrict.setVisibility(View.VISIBLE);
				
				 
				}
			}
		});
		
	}
	
	private void setUpListener() {
    	// ï¿½ï¿½ï¿½changeï¿½Â¼ï¿½
    	mViewProvince.addChangingListener(this);
    	// ï¿½ï¿½ï¿½changeï¿½Â¼ï¿½
    	mViewCity.addChangingListener(this);
    	// ï¿½ï¿½ï¿½changeï¿½Â¼ï¿½
    	mViewDistrict.addChangingListener(this);
    	// ï¿½ï¿½ï¿½onclickï¿½Â¼ï¿½
    	mBtnConfirm.setOnClickListener(this);
    }
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setUpData() {
		initProvinceDatas();
		//mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(MainActivity.this, mProvinceDatas));
		
		final int size=areas.getData().size();
		Areas[] arr=areas.getData().toArray(new Areas[size]);
		 
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<Areas>(MainActivity.this, arr));
		// ï¿½ï¿½ï¿½Ã¿É¼ï¿½ï¿½ï¿½Ä¿ï¿½ï¿½ï¿½ï¿½
		mViewProvince.setVisibleItems(7);
		 
		mViewProvince.setHorizontalFadingEdgeEnabled(false);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			
			//mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			//mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
		edittext.setText(areas.getData().get(mViewProvince.getCurrentItem()).getArea()+","+areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().get(mViewCity.getCurrentItem()).getArea()+",");
	}

	/**
	 * ï¿½ï¿½Ýµï¿½Ç°ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½WheelViewï¿½ï¿½ï¿½ï¿½Ï¢
	 */
	private void updateAreas() {
//		int pCurrent = mViewCity.getCurrentItem();
//		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
//		String[] areas = mDistrictDatasMap.get(mCurrentCityName);
//
//		if (areas == null) {
//			areas = new String[] { "" };
//		}
		Areas[] arr=areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().get(mViewCity.getCurrentItem()).getChildAreas().toArray(new Areas[areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().get(mViewCity.getCurrentItem()).getChildAreas().size()]);

		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Areas>(this, arr));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * ï¿½ï¿½Ýµï¿½Ç°ï¿½ï¿½Ê¡ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½WheelViewï¿½ï¿½ï¿½ï¿½Ï¢
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
//		mCurrentProviceName = mProvinceDatas[pCurrent];
//		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
//		if (cities == null) {
//			cities = new String[] { "" };
//		}
//		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		//final int size=areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().size();
		 
		
		Areas[] arr=areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().toArray(new Areas[areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().size()]);
		 
		mViewCity.setViewAdapter(new ArrayWheelAdapter<Areas>(this, arr));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_confirm:
//			showSelectedResult();
//			break;
//		default:
//			break;
//		}
	}

	private void showSelectedResult() {
		Toast.makeText(MainActivity.this, "åœ°ç‚¹:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}
}
