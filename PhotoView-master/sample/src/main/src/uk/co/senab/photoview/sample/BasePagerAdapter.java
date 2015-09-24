package uk.co.senab.photoview.sample;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasePagerAdapter<T> extends PagerAdapter{
	private HashMap<Integer, View> mViewMaps=null;
	private HashMap<Integer, T> mListMaps=null;
	
	public BasePagerAdapter(){
		mViewMaps=new HashMap<Integer, View>();
		mListMaps=new HashMap<Integer, T>();
	};
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mViewMaps!=null)
			return mViewMaps.size();
		return 0;
	}
	public void setList(ArrayList<T> list){
		mListMaps.clear();
		for (int i=0; i<list.size();i++) {
			mListMaps.put(i, list.get(i));
		}
	}
	
	public T getPositionItem(int position){
		
		if(null!=mListMaps && mListMaps.containsKey(position))
			return mListMaps.get(position);
		return null;
	}
	
	public View getPositionView(int position){
		if(null!=mViewMaps && mViewMaps.containsKey(position)){
			return mViewMaps.get(position);
		}
		return null;
	}
	@Override
	public View instantiateItem(ViewGroup container, int position) {
		
		View view =getView(container,position);
		
		if(!mViewMaps.containsKey(position)){
			mViewMaps.put(position, view);
		}
		
		return view;
	}
	
	public abstract View getView(ViewGroup container, int position);
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		if(mViewMaps.containsKey(position))
			mViewMaps.remove(position);
	}
}
