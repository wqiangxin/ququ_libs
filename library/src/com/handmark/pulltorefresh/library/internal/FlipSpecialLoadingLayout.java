package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
/**
 * 特殊的Flip模式，没有加载中的菊花动画。
 * @author WqxWork
 *
 */
public class FlipSpecialLoadingLayout extends FlipLoadingLayout{

	public FlipSpecialLoadingLayout(Context context, Mode mode,
			Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void refreshingImpl() {
		mHeaderImage.clearAnimation();
		mHeaderImage.setVisibility(View.INVISIBLE);
		//mHeaderProgress.setVisibility(View.VISIBLE);
	}
	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.indicator_arrow;
	}
	@Override
	protected void pullToRefreshImpl() {
		// TODO Auto-generated method stub
		//mHeaderImage.setVisibility(View.INVISIBLE);
		Log.i(LOG_TAG,"goAnimation!");
		super.pullToRefreshImpl();
	}

}
