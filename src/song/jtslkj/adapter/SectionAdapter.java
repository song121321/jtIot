package song.jtslkj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.ArrayList;

public class SectionAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> itemList;
	private String selectstr;

	public SectionAdapter(Context context, ArrayList<String> item,
			String selectstr) {
		this.context = context;
		this.itemList = item;
		this.selectstr = selectstr;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataList data = null;
		if (convertView == null) {
			data = new DataList();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_popwin_section, null);
			data.mText = (TextView) convertView
					.findViewById(R.id.tv_popwin_item_name);
			data.mImage = (ImageView) convertView
					.findViewById(R.id.iv_popwin_item_checkimg);
			convertView.setTag(data);
		} else {
			data = (DataList) convertView.getTag();
		}
		data.mText.setText(itemList.get(position));
		if (selectstr.equals(itemList.get( position))) {
			data.mImage.setVisibility(View.VISIBLE);
			convertView.setBackgroundResource(R.color.border_gray);
			data.mText.setTextColor(0xff1398a7);
		} else {
			data.mImage.setVisibility(View.INVISIBLE);
			convertView.setBackgroundResource(R.drawable.white_btn_text);
			data.mText.setTextColor(Color.BLACK);
		}
		return convertView;
	}

	private class DataList {
		public TextView mText;
		public ImageView mImage;
	}

}
