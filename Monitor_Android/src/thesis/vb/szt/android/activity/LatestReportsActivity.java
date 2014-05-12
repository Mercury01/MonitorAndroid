package thesis.vb.szt.android.activity;

import thesis.vb.szt.android.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class LatestReportsActivity extends FragmentActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.latest_report);
	}
	
	private String getTag() {
		return getClass().getName();
	}
}

