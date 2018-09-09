package song.jtslkj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.List;

import song.jtslkj.bean.WellAttrBean;
import song.jtslkj.bean.WellBean;

public class WellAttrAdapter extends BaseAdapter {

    private Context context;
    private List<WellAttrBean> wellAttrBeanList;

    public WellAttrAdapter(Context context, List<WellAttrBean> wellAttrBeanList) {
        this.context = context;
        this.wellAttrBeanList = wellAttrBeanList;
    }


    @Override
    public int getCount() {
        return wellAttrBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return wellAttrBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_device_well_detail, null);
        TextView name = (TextView) convertView
                .findViewById(R.id.tv_item_device_well_detail_name);
        TextView value = (TextView) convertView
                .findViewById(R.id.tv_item_device_well_detail_value);
        WellAttrBean wellAttrBean = wellAttrBeanList.get(position);
        name.setText(wellAttrBean.getName());
        value.setText(wellAttrBean.getValue());
        return convertView;
    }
}
