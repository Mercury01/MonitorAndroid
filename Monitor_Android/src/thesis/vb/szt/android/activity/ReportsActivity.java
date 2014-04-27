package thesis.vb.szt.android.activity;

import java.util.List;
import java.util.Map;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.model.Model;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ReportsActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reports);
		
		List<Map<String, String>> reportList = Model.getReportsList();
		
		Log.i(getTag(), "ReportListActivity started");
	}
	
	private String getTag() {
		return getClass().getName();
	}
}
