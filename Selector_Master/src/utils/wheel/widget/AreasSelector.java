package utils.wheel.widget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mrwujay.cascade.R;
import com.mrwujay.cascade.model.Areas;
import com.mrwujay.cascade.model.BaseEntry; 

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import utils.wheel.widget.OnWheelChangedListener;
import utils.wheel.widget.WheelView;
import utils.wheel.widget.adapters.ArrayWheelAdapter;

public class AreasSelector extends LinearLayout implements OnWheelChangedListener{
	
	private static final String TAG=AreasSelector.class.getSimpleName();
	
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private AreasListener mListener;
	private BaseEntry<ArrayList<Areas>> areas;
	
	
	
	public static  interface AreasListener{
		
		void onAreasChange(Areas province,Areas city,Areas district); 

	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public AreasSelector(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
		// TODO Auto-generated constructor stub
	}
	public AreasSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		// TODO Auto-generated constructor stub
	}
	public AreasSelector(Context context) {
		super(context);
		init(null);
		// TODO Auto-generated constructor stub
	}

	public void setAreasChangeListener(AreasListener listener){
		this.mListener=listener;
	}
	
	private void init(AttributeSet attrs){
		LayoutInflater.from(getContext()).inflate(R.layout.areas_selector, this);
		String androidnamespace="http://schemas.android.com/apk/res/android";
		 for (int index=0;index<attrs.getAttributeCount();index++ ) {
			    Log.i(TAG,attrs.getAttributeName(index)+":"+attrs.getAttributeValue(index));
			  
				if(attrs.getAttributeBooleanValue(androidnamespace, "focusable",false)){
			    	
					//getFocus();
				}
			
				 
			 
			    	 
			    	
		}
	    new getAreas().execute((Void)null);
		
	}
	
	
   private class getAreas extends AsyncTask<Void, Void, Boolean>{

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
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
     			return false;
     		}
     		return true;
     		 
     		
     		
    		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result){
			setUpViews();
			setUpListener();
			setUpData();
		}
	}
	
   }
	
	
	
	
	@Override
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		super.setVisibility(visibility);
		if(visibility==View.VISIBLE){
			toggleOnAreaChange();
		}
	}
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district); 
		
		mViewProvince.setVisibility(View.VISIBLE);
		
		 
		
	}
	
	private void setUpListener() {
    	// ï¿½ï¿½ï¿½changeï¿½Â¼ï¿½
    	mViewProvince.addChangingListener(this);
    	// ï¿½ï¿½ï¿½changeï¿½Â¼ï¿½
    	mViewCity.addChangingListener(this);
    	// ï¿½ï¿½ï¿½changeï¿½Â¼ï¿½
    	mViewDistrict.addChangingListener(this);
    	 
    }
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setUpData() {
		 
		//mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(MainActivity.this, mProvinceDatas));
		
		final int size=areas.getData().size();
		Areas[] arr=areas.getData().toArray(new Areas[size]);
		 
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<Areas>(getContext(), arr));
		// ï¿½ï¿½ï¿½Ã¿É¼ï¿½ï¿½ï¿½Ä¿ï¿½ï¿½ï¿½ï¿½
		mViewProvince.setVisibleItems(7); 
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas(); 
	}

	private void toggleOnAreaChange(){
		 
		if(this.mListener!=null){
			Areas province=areas.getData().get(mViewProvince.getCurrentItem());
			Areas city=province.getChildAreas().get(mViewCity.getCurrentItem());
			Areas district=null;
			try{
				district=city.getChildAreas().get(mViewDistrict.getCurrentItem());
			}catch(Exception e){
				
			}
			this.mListener.onAreasChange(province, city, district);
		}
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
		toggleOnAreaChange();
    }

	/**
	 * ï¿½ï¿½Ýµï¿½Ç°ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½WheelViewï¿½ï¿½ï¿½ï¿½Ï¢
	 */
	private void updateAreas() {
 
		Areas[] arr=areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().get(mViewCity.getCurrentItem()).getChildAreas().toArray(new Areas[areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().get(mViewCity.getCurrentItem()).getChildAreas().size()]);

		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Areas>(getContext(), arr));
		mViewDistrict.setCurrentItem(0);
		
		
	}

	/**
	 * ï¿½ï¿½Ýµï¿½Ç°ï¿½ï¿½Ê¡ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½WheelViewï¿½ï¿½ï¿½ï¿½Ï¢
	 */
	private void updateCities() {
 
		 
		
		Areas[] arr=areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().toArray(new Areas[areas.getData().get(mViewProvince.getCurrentItem()).getChildAreas().size()]);
		 
		mViewCity.setViewAdapter(new ArrayWheelAdapter<Areas>(getContext(), arr));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}
	 
}
