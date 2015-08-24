package com.whut.util;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;



public class SelectImage {
	


	
	//上下文
	private Context context;
	//从本机选取图片
	private final int SELECT_FROM_GALLERY = 0;
	//拍照选取
	private final int SELECT_FROM_CAPTURE = 1;
	//裁剪图片
	private final int CROP_IMAGE = 2;
	//拍照图片存储路径
	//private String path = Environment.getExternalStorageDirectory().toString()+"/seller/image/temp.png";
	//图片文件
	public File imageFile ;
	//拍照图片Uri
	public Uri imageUri ;
	
	
	public SelectImage(Context context){
		this.context = context;
//		if(!imageFile.getParentFile().getParentFile().exists()){
//			imageFile.getParentFile().getParentFile().mkdir();
//		}
//		if(!imageFile.getParentFile().exists()){
//			imageFile.getParentFile().mkdir();
//		}
		imageFile =  new File(Environment.getExternalStorageDirectory(),String.valueOf(System.currentTimeMillis())+".jpg");
		try {
			if(imageFile.exists()){
				imageFile.delete();
			}
			imageFile.createNewFile();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(imageFile);
	}
	
	
	/**
	 * 选择获取图片方式
	 */
	public void selectWay(){
		String[] items = {"本机","相机"};
		new AlertDialog.Builder(context).setTitle("选择图片来源")
				.setItems(items, new OnClickListener() {
					Intent intent;
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case SELECT_FROM_GALLERY:
							intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							((Activity)context).startActivityForResult(intent, SELECT_FROM_GALLERY); //获取图片后返回本页
							break;
						case SELECT_FROM_CAPTURE:
							intent = new Intent("android.media.action.IMAGE_CAPTURE");
							intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//输出路径
							System.out.println("imageUri.toString()=" + imageUri.toString());
							//intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
							((Activity)context).startActivityForResult(intent, SELECT_FROM_CAPTURE);
							break;
							default:
								break;
						}
					}
				}).create().show();
	}
	
	
	/**
	 * 获取选择图片
	 * @param requestCode    请求码
	 * @param resultCode     结果码
	 * @param data    图片数据
	 * @param isCrop   是否裁剪
	 * @return   bitmap结果
	 */
	public Bitmap getImage(int requestCode, int resultCode, Intent data,boolean isCrop){
		Bitmap bitmap = null;
		if(resultCode==-1){//RESULT_OK = -1
			switch(requestCode){
			//本机照片
			case SELECT_FROM_GALLERY:
				Uri uri = data.getData();
				if(isCrop){
					System.out.println("需要裁剪");
					crop(uri);
				}else{
					System.out.println("不需要裁剪");
					bitmap = getBitmapFromFile(uri);
				}
				break;
			//拍照选取
			case SELECT_FROM_CAPTURE:
				
				if(isCrop){
					try{
						crop(imageUri);
					}catch (Exception e) {
						Toast.makeText(context, "保存图片失败!", Toast.LENGTH_SHORT).show();
					}
				}else{
					bitmap = getBitmapFromFile(imageUri);
				}
				break;
				
			case CROP_IMAGE:
				bitmap = getBitmapFromFile(imageUri);
		
				break;
			default:
				break;
			}
		}else{//RESULT_CANCELED = 0
			System.out.println("resultCode="+resultCode);
			Toast.makeText(context, "选择图片错误!", Toast.LENGTH_SHORT).show();
		}
		return bitmap;
	}
	
	
	/**
	 * 从文件获取图片
	 */
	private Bitmap getBitmapFromFile(Uri uri){
		System.out.println("uri.toString()=" + uri.toString());
		Bitmap bitmap = null;
		ContentResolver resolver = context.getContentResolver();
		try {
			bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
		} catch (FileNotFoundException e) {
			Toast.makeText(context, "选取图片失败!", Toast.LENGTH_SHORT).show();
		}
		return bitmap;
	}
	
	
	/**
	 * 裁剪图片
	 */
	private void crop(Uri uri){
		
		//裁剪意图
		System.out.println("uri = "+ uri.toString());
		Intent intent = new Intent("com.android.camera.action.CROP");
		//输入路径uri不能和输入路径imageUri相同！要不然会出错
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("scale", true);
		//输出路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		Intent intent = new Intent("com.android.camera.action.CROP");
//		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		//裁剪框比例，1:1
		intent.putExtra("aspectX", 3.46);
		intent.putExtra("aspectY", 2.4);
		//裁剪后输出图片尺寸大小
		intent.putExtra("outputX", 346*2);
		intent.putExtra("outputY", 240*2);
		intent.putExtra("outputFormat", "PNG");//图片格式
		intent.putExtra("noFaceDetection", true);//取消人脸识别
//		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);//取消返回
		System.out.println("裁剪中");
		((Activity)context).startActivityForResult(intent, CROP_IMAGE);
	}

}
