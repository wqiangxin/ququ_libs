package uk.co.senab.photoview.sample;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.Assert;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

public class BitmmapUtils{
private static final String TAG=BitmmapUtils.class.getSimpleName();
	
	/**
	 * λͼת�ֽ�����
	 * @param bmp
	 * @param needRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * bitmapתbase64
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				//3.0����ʵ�ַ�ʽ
				//if(bitmap.getByteCount()>260*1024)
				//����2.0����
				/*
				if(bitmap.getRowBytes() * bitmap.getHeight()>260*1024)
					bitmap.compress(Bitmap.CompressFormat.JPEG, 98, baos);
				else
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					*/
				/*
				if(bitmap.getWidth()>=2500)
					bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
				else if(bitmap.getWidth()>=1500)
					bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
				else if(bitmap.getWidth()>=800)
					bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
				else 
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				*/
				Log.i("BitmmapUtils","bitmap bytcout:"+bitmap.getRowBytes() * bitmap.getHeight());
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
				Log.i("BitmmapUtils","compress bytcout:"+baos.size());

				baos.flush();
				baos.close(); 
				
				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
	/**
	 * compute Sample Size
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * compute Initial Sample Size
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		// �����޷�Χ
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	public static Bitmap scaleBitmap(Bitmap bitmap,float sx,float sy) {
		return scaleBitmap(bitmap,sx,sy,false);
	}
	/**
	 * �������������ţ����Ҫ��ԭͼ��������sx,syͳһ������float��ָ���Ŀ�ȣ�ͼƬ�Ŀ�ȣ���ʽ���С�
	 * @param bitmap
	 * @param sx
	 * @param sy
	 * @param isRecycle
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap,float sx,float sy,Boolean isRecycle) {
		 
	  Matrix matrix = new Matrix(); 
	  matrix.postScale(sx,sy); //���Ϳ�Ŵ���С�ı���
	  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
	  if(isRecycle)
		  bitmap.recycle();
	  return resizeBmp;
	}
	
	public static Bitmap extractBitmap(final String path, final int height, final int width, final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > 1920 * 1440) {
				options.inSampleSize++;
			}
			Log.d(TAG, "extractThumbNail: inSampleSize " +options.inSampleSize);
			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;
			options.inPurgeable=true;
		    options.inPreferredConfig = Bitmap.Config.RGB_565;
		    
			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}

			Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight()+"|isRecycled:"+bm.isRecycled());
			
			if(bm.getWidth()!=newWidth || bm.getHeight()!=newHeight){
				final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
				if (scale != null) {
					Log.i(TAG, "bitmap isscale!!");
					bm.recycle();
					System.gc();
					bm = scale;
					Log.i(TAG, "bitmap isRecycled:"+scale.isRecycled());
				}
			}
			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}

				bm.recycle();
				System.gc();
				bm = cropped;
				Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
			}
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}
	
	/*
	public static Bitmap scaleBitMap(Bitmap map,int width,int height){
		 BitmapFactory.Options opts = new BitmapFactory.Options(); 
		 int w = map.getWidth();
		 int h=map.getHeight();
		 float scaleWidth = 0.f, scaleHeight = 0.f; 
		 if (width >w || height > h) { 
	         // ���� 
	         scaleWidth = ((float) width) / w; 
	         scaleHeight = ((float) height) / h; 
	     } 
	    opts.inJustDecodeBounds = false; 
	      float scale = Math.max(scaleWidth, scaleHeight); 
	       opts.inSampleSize = (int)scale; 
	       BitmapFactory.
	     return Bitmap.createScaledBitmap(bt, w, h, true); 
	}
	*/
	public static Bitmap tryGetBitmap(String imgFile, int minSideLength,   
	        int maxNumOfPixels) {  
		 Log.i(TAG,"bmp: |"+imgFile);
	    if (imgFile == null || imgFile.length() == 0)   
	        return null;   
	  
	    try {   
	        //FileDescriptor fd = new FileInputStream(imgFile).getFD();   
	        BitmapFactory.Options options = new BitmapFactory.Options();   
	        options.inJustDecodeBounds = true;   
	         BitmapFactory.decodeFile(imgFile, options);   
	        //BitmapFactory.decodeFileDescriptor(fd, null, options);   
	 
	        options.inSampleSize = computeSampleSize(options, minSideLength,   
	                maxNumOfPixels);   
	        options.inPurgeable=true;
	        options.inPreferredConfig = Bitmap.Config.RGB_565;
	        Log.i(TAG,"inSampleSize: "+options.inSampleSize+"|options.outHeight:"+options.outHeight+"|options.outWidth:"+options.outWidth);
	        try {   
	            // ����һ��Ҫ�������û�false����Ϊ֮ǰ���ǽ������ó���true   
	            // ����inJustDecodeBoundsΪtrue��decodeFile��������ռ䣬����BitmapFactory���������BitmapΪNull,���ɼ����ԭʼͼƬ�ĳ��ȺͿ��   
	            options.inJustDecodeBounds = false;   
	  
	            Bitmap bmp = BitmapFactory.decodeFile(imgFile, options); 
	            Log.i(TAG,"bmp:"+bmp.getWidth() +"|"+imgFile+"|bmp size:"+bmp.getRowBytes() * bmp.getHeight());
	            
	            //fd=null; 
	            return bmp == null ? null : bmp;   
	        } catch (OutOfMemoryError err) {   
	        	Log.i(TAG,"OUT of MemoryError!!");
	            return null;   
	        }   
	    } catch (Exception e) {    
	    	Log.i(TAG,"e:"+e.getMessage());
	        return null;   
	    }   
	}  
}