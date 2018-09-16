package song.jtslkj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.List;

import song.jtslkj.bean.IraLogBean;

public class IraLogAdapter extends BaseAdapter {

    private Context context;
    private List<IraLogBean> iraLogBeanList;

    public IraLogAdapter(Context context, List<IraLogBean> iraLogBeanList) {
        this.context = context;
        this.iraLogBeanList = iraLogBeanList;
    }


    @Override
    public int getCount() {
        return iraLogBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return iraLogBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //here we reuse the layout of device_sta, not a mistake.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_device_sta, null);
        TextView checkPosition = (TextView) convertView
                .findViewById(R.id.tv_item_sta_title);
        TextView updateTime = (TextView) convertView
                .findViewById(R.id.tv_item_sta_detail);
        IraLogBean iraLogBean = iraLogBeanList.get(position);
        checkPosition.setText(iraLogBean.getPosition());
        updateTime.setText(iraLogBean.getUpdateTime());
        return convertView;
    }
}
