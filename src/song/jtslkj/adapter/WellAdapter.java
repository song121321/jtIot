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

import song.jtslkj.bean.WellBean;

public class WellAdapter extends BaseAdapter {

    private Context context;
    private List<WellBean> wellBeanList;

    public WellAdapter(Context context, List<WellBean> wellBeanList) {
        this.context = context;
        this.wellBeanList = wellBeanList;
    }


    @Override
    public int getCount() {
        return wellBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return wellBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_device_well, null);
        TextView name = (TextView) convertView
                .findViewById(R.id.tv_item_well_name);
        TextView updateTime = (TextView) convertView
                .findViewById(R.id.tv_item_well_update_time);
        ImageView state = (ImageView) convertView
                .findViewById(R.id.iv_item_well_state);

        WellBean wellBean = wellBeanList.get(position);

        name.setText(wellBean.getWellName());
        updateTime.setText(wellBean.getUpdateTime());

        if (wellBean.getState() == 0) {
            state.setImageResource(R.drawable.blue_dot);
        } else if (wellBean.getState() == 1) {
            state.setImageResource(R.drawable.green_dot);
        } else {
            state.setImageResource(R.drawable.red_dot);
        }
        return convertView;
    }
}
