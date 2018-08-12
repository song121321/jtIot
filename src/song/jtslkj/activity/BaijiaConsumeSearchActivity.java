package song.jtslkj.activity;

import song.jtslkj.app.MyApplication;
import com.jtslkj.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class BaijiaConsumeSearchActivity extends Activity {

	ImageView iv_search;
	EditText et_searcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_consume_search);
		MyApplication.getInstance().addActivity(this);
		findview();
		setclicker();
	}

	private void findview() {
		iv_search = (ImageView) findViewById(R.id.bt_et_actionbar_search_searchbt);
		et_searcontent = (EditText) findViewById(R.id.et_actionbar_search_editbox);

	}

	private void setclicker() {
		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(BaijiaConsumeSearchActivity.this,
						BaijiaConsumeSearchResultActivity.class);
				intent.putExtra("searchtext", et_searcontent.getText().toString().trim());
				startActivity(intent);
				MyApplication.getInstance().removeActivity(BaijiaConsumeSearchActivity.this);
				finish();
			}
		});

	}
}
