/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uk.co.senab.photoview.sample;

import java.util.ArrayList;
import java.util.HashMap;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.senab.photoview.PhotoView;
import android.R.integer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * Lock/Unlock button is added to the ActionBar.
 * Use it to temporarily disable ViewPager navigation in order to correctly interact with ImageView by gestures.
 * Lock/Unlock state of ViewPager is saved and restored on configuration changes.
 * 
 * Julia Zudikova
 */

public class ViewPagerActivity extends Activity {

	private static final String ISLOCKED_ARG = "isLocked";
	
	private ViewPager mViewPager;
	private MenuItem menuLockItem;
	private  SamplePagerAdapter adapter;
    private int mPreViewIndex=0;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mViewPager.setSaveEnabled(true); 
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setPageMargin(50);
        
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		setContentView(mViewPager);
		
		
		  if (!ImageLoader.getInstance().isInited()) {
	            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
	            ImageLoader.getInstance().init(config);
	        }
	    
		  adapter= new SamplePagerAdapter();
	    
	    mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
				if(null!= adapter.getPositionView(mPreViewIndex)){
					View view= adapter.getPositionView(mPreViewIndex);
					if(view instanceof PhotoView){
						PhotoView photoview=((PhotoView)view);
						photoview.setScale(photoview.getMinimumScale(),true);
						
						Toast.makeText(ViewPagerActivity.this, "sel index:"+mPreViewIndex, Toast.LENGTH_SHORT).show();
					}
				}
				mPreViewIndex=arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				//Toast.makeText(ViewPagerActivity.this, "sel arg0:"+arg0+"|arg1:"+arg1+"|arg2:"+arg2, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
		mViewPager.setAdapter(adapter);
		 mViewPager.setCurrentItem(1);
		if (savedInstanceState != null) {
			boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
			((HackyViewPager) mViewPager).setLocked(isLocked);
		}
	}

  
    
	static class SamplePagerAdapter extends BasePagerAdapter<String> {

		
		private static final int[] sDrawables = { R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper };
	 
		
	 
		@Override
		public int getCount() {
			return sDrawables.length;
		}

		  
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}



		@Override
		public View getView(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			PhotoView photoView = new PhotoView(container.getContext());
			//photoView.setImageResource(sDrawables[position]);

			//photoView.setImageDrawable(new BitmapDrawable(container.getContext().getResources(),BitmmapUtils.extractBitmap("/storage/emulated/0/Camera/P40918-173417.jpg", 640, 480, false)));
			 ImageLoader.getInstance().displayImage("http://epaper.yzdsb.com.cn/201201/04/yz2104_7.jpg", photoView,new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					  if (view instanceof PhotoView) {
						PhotoView photoview = (PhotoView) view;
						photoview.setZoomable(true);
					}
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
			});
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			photoView.setZoomable(false); 
			 
			
			return photoView;
		}

	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewpager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuLockItem = menu.findItem(R.id.menu_lock);
        toggleLockBtnTitle();
        menuLockItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				toggleViewPagerScrolling();
				toggleLockBtnTitle();
				return true;
			}
		});

        return super.onPrepareOptionsMenu(menu);
    }
    
    private void toggleViewPagerScrolling() {
    	if (isViewPagerActive()) {
    		((HackyViewPager) mViewPager).toggleLock();
    	}
    }
    
    private void toggleLockBtnTitle() {
    	boolean isLocked = false;
    	if (isViewPagerActive()) {
    		isLocked = ((HackyViewPager) mViewPager).isLocked();
    	}
    	String title = (isLocked) ? getString(R.string.menu_unlock) : getString(R.string.menu_lock);
    	if (menuLockItem != null) {
    		menuLockItem.setTitle(title);
    	}
    }

    private boolean isViewPagerActive() {
    	return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (isViewPagerActive()) {
			outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
    	}
		super.onSaveInstanceState(outState);
	}
    
}
