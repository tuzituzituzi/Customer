package com.whut.activity.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.activity.AddCommentActivity;
import com.whut.activity.FocusListActivity;
import com.whut.activity.SquareDetailActivity;
import com.whut.activity.map.ShowMap;
import com.whut.customer.R;
import com.whut.data.model.DynamicModel;
import com.whut.util.FileUtils;
import com.whut.util.SelectImage;

public class MallFragment extends Fragment implements OnClickListener {

	private Context context;
	private ListView listview;
	private List<DynamicModel> items;
	private MainListAdapter listAdapter;
	private LayoutInflater inflater;
	private View view;
	private SelectImage selectImage;

	private ImageView focus_img;
	private ImageView camera_img;

	private LinearLayout layout;

	private Spinner all_spinner;
	private Spinner city_spinner;
	private Spinner sort_spinner;
	private ArrayAdapter all_spinner_adapter;
	private ArrayAdapter city_spinner_adapter;
	private ArrayAdapter sort_spinner_adapter;


	private int MAX_SIZES = 500;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_mall, container, false);
		initData();
		initView();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 将可选内容与ArrayAdapter连接起来
		listview = (ListView) view.findViewById(R.id.Store_ListView);
		all_spinner = (Spinner) view.findViewById(R.id.all_spinner);
		city_spinner = (Spinner) view.findViewById(R.id.city_spinner);
		sort_spinner = (Spinner) view.findViewById(R.id.sort_spinner);

		layout = (LinearLayout) view.findViewById(R.id.mall_spinner_bar);
		layout.setVisibility(View.GONE);

		focus_img = (ImageView) view.findViewById(R.id.mall_focus_img);
		camera_img = (ImageView) view.findViewById(R.id.mall_camera_img);

		listAdapter = new MainListAdapter();
		if (listAdapter == null)
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		listview.setAdapter(listAdapter);
		enterStoreDetail();
		context = getActivity();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		all_spinner_adapter = ArrayAdapter.createFromResource(context,
				R.array.all_plantes, android.R.layout.simple_spinner_item);
		city_spinner_adapter = ArrayAdapter.createFromResource(context,
				R.array.city, android.R.layout.simple_spinner_item);
		sort_spinner_adapter = ArrayAdapter.createFromResource(context,
				R.array.sort, android.R.layout.simple_spinner_item);
		// 设置下拉列表的风格
		all_spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city_spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sort_spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter2 添加到spinner中
		all_spinner.setAdapter(all_spinner_adapter);
		city_spinner.setAdapter(city_spinner_adapter);
		sort_spinner.setAdapter(sort_spinner_adapter);

		focus_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, FocusListActivity.class));
			}
		});

		camera_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				new PostStorePopupWindow(context, view
//						.findViewById(R.id.mall_layout));
				Intent intent = new Intent(getActivity(), AddCommentActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		// 添加事件Spinner事件监听
		all_spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		city_spinner
				.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		sort_spinner
				.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

		// 设置默认值
		all_spinner.setVisibility(View.VISIBLE);
	}

	

	private void enterStoreDetail() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, SquareDetailActivity.class);
				intent.putExtra("dynamic", items.get(position));
				startActivity(intent);
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		items = new ArrayList<DynamicModel>();
		DynamicModel model;
		selectImage = new SelectImage(getActivity());

		Object data[][] = {
				{ R.drawable.dynamic_pic1, R.drawable.user_pic1, "焚心", "122",
						"0", "重庆京汉大道1299号", "巴厘岛龙虾馆",
						"虾的味道很给力 特别是油焖大虾很有独特的口感 而且服务员服务超级好 觉得以后还会再来", "122",
						"刚刚" },
				{
						R.drawable.dynamic_pic2,
						R.drawable.user_pic2,
						"じ北岛べ深巷",
						"25",
						"0",
						"天津路32号",
						"渝家小馆",
						"门口停车没人管太不方便，菜品味道在武汉的川菜来说真不错，但是荤菜里面肉太少，啤酒竟然没有雪花，好像人均有最低消费50圆，好吃不贵，推荐鱼香肉丝。",
						"22", "2分钟前" },
				{
						R.drawable.dynamic_pic3,
						R.drawable.user_pic3,
						"吢丕",
						"5",
						"0",
						"沿江大道长江二桥下",
						"天天渔港",
						"我们中午一点去的，整个大堂就没有几个顾客了，感觉服务员很生硬，不友好，全程没有笑容。食物根本很一般。和同样均价的太子湖锦比，环境服务食物都差很多。听说这个店以前生意很好，现在每况愈下，看起来真是这样啊！",
						"323", "10分钟前" },
				{ R.drawable.dynamic_pic4, R.drawable.user_pic4, "不用牵手丶我自己能走",
						"5", "0", "中山大道1618号", "四季恋餐厅",
						"去的武汉天地这家，味道不错，非常推荐外婆红烧肉，入味又不油腻，值得一试", "155", "30分钟前" },
				{ R.drawable.dynamic_pic5, R.drawable.user_pic5, "薄荷＆柠檬", "15",
						"0", "芦沟桥路12-2-3号", "公馆卡拉OK会所",
						"嫌房间小了，加钱升仓，音响效果不错，环境也很好，蜡烛免费点，很浪漫的感觉 ", "203", "1小时前" },
				{
						R.drawable.dynamic_pic6,
						R.drawable.user_pic6,
						"一只叫喵喵的狗狗",
						"23",
						"0",
						"长春街63号",
						"63号庄园咖啡",
						"这家环境不错，服务也好，夜里很安静，适合几个人来聊聊天，空间上也比较开阔，二楼是半开放式的，这种装修还是蛮有新意的，喝过一个什么茶忘了，总之就是很小资啦，价格也比较小资的  ",
						"115", "2小时前" },
				{
						R.drawable.dynamic_pic7,
						R.drawable.user_pic7,
						"先森,别耍流氓",
						"122",
						"0",
						"胜利街260号",
						"Merci Cafe",
						"三年前就来过的店子才发现竟然一直没写点评，最近又去了一次，感觉变化不大，还是浓重的日系风，光是菜单就让我爱不释手，感觉这样的小店应该甚得女生喜爱，不过夏天得时候小阳...... ",
						"103", "5小时前" },
				{
						R.drawable.dynamic_pic8,
						R.drawable.user_pic8,
						"花会开丶亦会落",
						"11",
						"0",
						"第二街区61号",
						"SPAO",
						"据说是衣恋旗下品牌！打造韩国的优衣库！！衣服价格都不贵，款式也比较年轻，今天去了碰到在打折和特价，果断入手几件～～ ",
						"332", "昨天" } };
		
		
		for (int i = 0; i < data.length; i++) {
			model = new DynamicModel();
			model.setPhotoUrl(data[i][0].toString());
			System.out.println("data[i][0].toString() = " + data[i][0].toString());
			model.setuPicUrl(data[i][1].toString());
			model.setuName(data[i][2].toString());
			model.setLikeCount(Integer.parseInt(data[i][3].toString()));
			model.setCommentCount(Integer.parseInt(data[i][4].toString()));
			model.setLocation(data[i][5].toString());
			model.setgName(data[i][6].toString());
			model.setDesc(data[i][7].toString());
			model.setVisitCount(Integer.parseInt(data[i][8].toString()));
			model.setReleaseTime(data[i][9].toString());
			items.add(model);
		}
		
		
	}

	private static final int TAKE_PICTURE = 0x000000;

//	class PostStorePopupWindow extends PopupWindow {
//		public PostStorePopupWindow(Context context, View parent) {
//			final View view = View.inflate(context,
//					R.layout.post_store_popupwindow_item, null);
//			view.findViewById(R.id.post_store_popupwindow_blank_view)
//					.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							if (isShowing()) {
//								dismiss();
//							}
//						}
//					});
//			view.findViewById(R.id.item_popupwindows_camera)
//					.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							photo();
//							dismiss();
//						}
//
//						private void photo() {
//							// TODO Auto-generated method stub
//							Intent openCameraIntent = new Intent(
//									MediaStore.ACTION_IMAGE_CAPTURE);
//							File file = new File(FileUtils.SDPATH + "/", String
//									.valueOf(System.currentTimeMillis())
//									+ ".JPEG");
//							if (!file.exists()) {
//								File vDirPath = file.getParentFile(); // new
//																		// File(vFile.getParent());
//								vDirPath.mkdirs();
//							}
//							path = file.getPath();
//							Uri imageUri = Uri.fromFile(file);
//							openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//									imageUri);
//							startActivityForResult(openCameraIntent,
//									TAKE_PICTURE);
//						}
//					});
//			
//			view.findViewById(R.id.item_popupwindows_photo).setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//			
//			view.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					dismiss();
//				}
//			});
//			view.startAnimation(AnimationUtils.loadAnimation(context,
//					R.anim.push_bottom_in_2));
//			setWidth(LayoutParams.FILL_PARENT);
//			setHeight(LayoutParams.FILL_PARENT);
//			setBackgroundDrawable(new BitmapDrawable());
//			setOutsideTouchable(true);
//			setFocusable(true);
//			setContentView(view);
//			showAtLocation(parent, Gravity.TOP, 0, 0);
//			update();
//
//		}
//
//	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == -1){
				String comment = data.getStringExtra("makeCommentText");
				String photoUrl = data.getStringExtra("commentPath");
				//temp = data.getParcelableExtra("commentBitmap");
				System.out.println("comment = "+ comment);
				System.out.println("photoUrl = "+photoUrl);
				DynamicModel commentmodel = new DynamicModel();
				commentmodel.setuPicUrl(items.get(0).getuPicUrl());
				commentmodel.setuName(items.get(1).getuName());
				commentmodel.setLikeCount(items.get(2).getLikeCount());
				commentmodel.setCommentCount(items.get(3).getCommentCount());
				commentmodel.setLocation(items.get(4).getLocation());
				commentmodel.setgName(items.get(6).getgName());
				commentmodel.setVisitCount(items.get(7).getVisitCount());
				commentmodel.setReleaseTime(items.get(0).getReleaseTime());
				
				commentmodel.setDesc(comment);
				commentmodel.setPhotoUrl(photoUrl);
				items.add(0,commentmodel);
				listAdapter.notifyDataSetChanged();
			}
				
			break;

		default:
			break;
		}
		
		// TODO Auto-generated method stub
//		System.out.println(resultCode + "resultcode");
//		switch (resultCode) {
//		case Activity.RESULT_OK:
//			DynamicModel model = items.get(0);
//			Bitmap bit = BitmapFactory.decodeFile(path);
//			Bitmap thumbnail_bit = createThumbnail(bit);
//			saveBitmap(path, thumbnail_bit);
//			model.setPhotoUrl(path);
//			items.add(0, model);
//			listAdapter.notifyDataSetChanged();
//			break;
	}

	class MainListAdapter extends BaseAdapter {
		private HolderView holderview;

		private class HolderView {
			TextView uname;
			TextView like_count;
			TextView comment_count;
			TextView location;
			TextView store;
			TextView desc;
			TextView visit_count;
			TextView release_time;
			ImageView image;
			ImageView uPic;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HolderView holder;
			if (convertView == null) {
				holder = new HolderView();
				convertView = inflater.inflate(R.layout.square_listitem, null);
				holder.image = (ImageView) convertView
						.findViewById(R.id.square_item_img);
				holder.uPic = (ImageView) convertView
						.findViewById(R.id.square_user_head);
				holder.uname = (TextView) convertView
						.findViewById(R.id.square_user_name);
				holder.like_count = (TextView) convertView
						.findViewById(R.id.square_good_count);
				holder.comment_count = (TextView) convertView
						.findViewById(R.id.square_comment_count);
				holder.location = (TextView) convertView
						.findViewById(R.id.square_store_area);
				holder.store = (TextView) convertView
						.findViewById(R.id.square_store_name);
				holder.desc = (TextView) convertView
						.findViewById(R.id.square_item_desc);
				holder.visit_count = (TextView) convertView
						.findViewById(R.id.square_msg_visit_count);
				holder.release_time = (TextView) convertView
						.findViewById(R.id.square_msg_posttime);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			/*
			 * String url = items.get(position).getPhotoUrl(); 
			 * if (url != null)
			 * { Bitmap bit = BitmapFactory.decodeFile(path);
			 * holder.photo.setImageBitmap(bit);
			 * holder.photo.setScaleType(ScaleType.CENTER); }
			 */
			System.out.println("items.get(position).getPhotoUrl() = "+items.get(position).getPhotoUrl());
			if(items.get(position).getPhotoUrl().contains("storage")){
				Bitmap bit = BitmapFactory.decodeFile(items.get(position).getPhotoUrl());
				holder.image.setImageBitmap(bit);

			}else{
				holder.image.setImageResource(Integer.parseInt(items.get(position).getPhotoUrl()));
			}
//			holder.image.setImageResource(Integer.parseInt(items.get(position).getPhotoUrl()));
			holder.uPic.setImageResource(Integer.parseInt(items.get(position)
					.getuPicUrl()));
			holder.uname.setText(items.get(position).getuName());
			holder.like_count.setText(String.valueOf(items.get(position)
					.getLikeCount()));
			holder.comment_count.setText(String.valueOf(items.get(position)
					.getCommentCount()));
			holder.location.setText(items.get(position).getLocation());
			holder.store.setText(items.get(position).getgName());
			holder.desc.setText(items.get(position).getDesc());
			holder.visit_count.setText(String.valueOf(items.get(position)
					.getVisitCount()));
			holder.release_time.setText(items.get(position).getReleaseTime());
			return convertView;
		}
	}

	public void saveBitmap(String picName, Bitmap bit) {
		File f = new File(picName);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bit.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 创建缩略图,返回缩略图文件路径 */
	public Bitmap createThumbnail(Bitmap source) {
		int oldW = source.getWidth();
		int oldH = source.getHeight();

		int w = Math.round((float) oldW / MAX_SIZES); // MAX_SIZE为缩略图最大尺寸
		int h = Math.round((float) oldH / MAX_SIZES);

		int newW = 0;
		int newH = 0;

		if (w <= 1 && h <= 1) {
			return source;
		}

		int i = w > h ? w : h; // 获取缩放比例

		newW = oldW / i;
		newH = oldH / i;

		Bitmap imgThumb = ThumbnailUtils.extractThumbnail(source, newW, newH); // 关键代码！！

		return imgThumb; // 注：saveBitmap方法为保存图片并返回路径的private方法
	}

	class SpinnerXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Toast.makeText(context,
					arg0.getItemAtPosition((int) arg3).toString(),
					Toast.LENGTH_SHORT).show();
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new ShowMap(context);
	}
	



}
