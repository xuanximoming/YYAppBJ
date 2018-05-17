package com.yy.until;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageUtilszm {
	public static LinkedHashMap<String, SoftReference<Bitmap>> imageCache = new LinkedHashMap<String, SoftReference<Bitmap>>();

	/**
	 * @category 主方法，直接调用此方法
	 * @param context
	 *            Context对象
	 * @param imgView
	 *            需要调用的ImageView控件
	 * @param url
	 *            图片网址
	 */
	public static void setBitmap(Context context, ImageView imgView, String url) {
		System.out.println("进入---setBitmap" + url);
		if (url == null || url.equals("")) {
			return;
		}
		try {
			imgView.setTag(url);
			ImageUtilszm iu = new ImageUtilszm();
			Bitmap bitmap;
			// 软引用集合中查找
			bitmap = iu.findBitmapAtCache(url);
			if (bitmap != null) {
				// Log.e("引用", "引用");
				setBitmapToImgView(imgView, bitmap);
				return;
			}
			// int index = url.lastIndexOf("/");
			// String filename = url.substring(index + 1, url.length());
			// String path = Constants.SDCARD + filename;
			// bitmap = PicUtil.convertToBitmap(path);
			// if (bitmap != null && bitmap.getWidth() > 0) {
			// // Log.e("文件", "文件");
			// setBitmapToImgView(imgView, bitmap, url, pgbar);
			// iu.putBitmapToCache(url, bitmap);
			// return;
			// }
			// 从网络获取
			iu.setBitmapFromNet(context, imgView, url);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void setBitmap(Context context, ImageView imgView, int id) {
		if (id == 0) {
			return;
		}
		try {
			imgView.setTag(id);
			ImageUtilszm iu = new ImageUtilszm();
			Bitmap bitmap;
			// 软引用集合中查找
			bitmap = iu.findBitmapAtCache(id + "");
			if (bitmap != null) {
				// Log.e("引用", "引用");
				setBitmapToImgView(imgView, bitmap);
				return;
			}
			// int index = url.lastIndexOf("/");
			// String filename = url.substring(index + 1, url.length());
			// String path = Constants.SDCARD + filename;
			// bitmap = PicUtil.convertToBitmap(path);
			// if (bitmap != null && bitmap.getWidth() > 0) {
			// // Log.e("文件", "文件");
			// setBitmapToImgView(imgView, bitmap, url, pgbar);
			// iu.putBitmapToCache(url, bitmap);
			// return;
			// }
			// 从网络获取
			iu.setBitmapFromRes(context, imgView,id);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public static void setBitmap(Context context, RelativeLayout rl, int id) {
		if (id == 0) {
			return;
		}
		try {
			rl.setTag(id);
			ImageUtilszm iu = new ImageUtilszm();
			Bitmap bitmap;
			// 软引用集合中查找
			bitmap = iu.findBitmapAtCache(id + "");
			if (bitmap != null) {
				// Log.e("引用", "引用");
				setBitmapToImgView(rl, bitmap);
				return;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 从网络获取
	 * 
	 * @param context
	 * @param imgview
	 * @param imgUrl
	 * @param tag
	 */

	private void setBitmapFromNet(final Context context,
			final ImageView imgview, final String imgUrl) {
		// TODO Auto-generated method stub
		System.out.println("进入---setBitmapFromNet--从网络获取");
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					// Log.e("网络", "网络" );

					System.out.println("进入---handleMessage");
					final Bitmap bitmap = (Bitmap) msg.obj;
					setBitmapToImgView(imgview, bitmap);
				}
			}
		};
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("进入---setBitmapFromNet--下载线程");
					URL url = new URL(imgUrl);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(3000);
					conn.setReadTimeout(3000);
					conn.connect();

					InputStream in = conn.getInputStream();
					BitmapFactory.Options options = new Options();
					options.inSampleSize = 1;// 缩放比例
					Bitmap bitmap = BitmapFactory.decodeStream(in, null,
							options);
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.sendMessage(msg);
					conn.disconnect();
					in.close();

					putBitmapToCache(imgUrl, bitmap);// 保存到软引用中

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		ThreadPoolManager.getInstance().addTask(runnable);
	}
	/**
	 * 从网络获取
	 * 
	 * @param context
	 * @param imgview
	 * @param imgUrl
	 * @param tag
	 */
	
	private void setBitmapFromRes(final Context context,
			final ImageView imgview, final int id) {
		// TODO Auto-generated method stub
		System.out.println("进入---setBitmapFromNet--从网络获取");
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					// Log.e("网络", "网络" );
					System.out.println("进入---handleMessage");
					final Bitmap bitmap = (Bitmap) msg.obj;
					setBitmapToImgView(imgview, bitmap);
				}
			}
		};
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
					System.out.println("进入---setBitmapFromRes--线程");
					Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.sendMessage(msg);
					
					putBitmapToCache(id+"", bitmap);// 保存到软引用中
					
			}
		};
		ThreadPoolManager.getInstance().addTask(runnable);
	}

	// 显示图片
	public static void setBitmapToImgView(ImageView imgView, Bitmap bitmap) {
		imgView.setImageBitmap(bitmap);
	}
	// 显示图片
	public static void setBitmapToImgView(RelativeLayout rl, Bitmap bitmap) {
		BitmapDrawable bd=new BitmapDrawable(bitmap);
		rl.setBackgroundDrawable(bd);
	}

	/**
	 * 软引用中查找
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap findBitmapAtCache(String url) {
		if (imageCache.containsKey(url)) {
			System.out.println("进入---findBitmapAtCache---软引用中查找");
			SoftReference<Bitmap> reference = imageCache.get(url);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		return null;
	}

	/**
	 * 将bitmap 加入缓存
	 * 
	 * @param url
	 * @param bitmap
	 */
	private void putBitmapToCache(String imgUrl, Bitmap bitmap) {
		// TODO Auto-generated method stub
		System.out.println("进入---putBitmapToCache--保存到软引用");
		SoftReference<Bitmap> sf = new SoftReference<Bitmap>(bitmap);
		imageCache.put(imgUrl, sf);
	}
}
