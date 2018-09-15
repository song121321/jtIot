package song.jtslkj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.List;

import song.jtslkj.bean.StaBean;

public class StaAdapter extends BaseAdapter {

    private Context context;
    private List<StaBean> staBeanList;

    public StaAdapter(Context context, List<StaBean> wellAttrBeanList) {
        this.context = context;
        this.staBeanList = wellAttrBeanList;
    }


    @Override
    public int getCount() {
        return staBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return staBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_device_sta, null);
        TextView name = (TextView) convertView
                .findViewById(R.id.tv_item_sta_title);
        TextView detail = (TextView) convertView
                .findViewById(R.id.tv_item_sta_detail);
        StaBean staBean = staBeanList.get(position);
        name.setText(staBean.getName());
        detail.setText(staBean.getDescription());
        return convertView;
    }
}
