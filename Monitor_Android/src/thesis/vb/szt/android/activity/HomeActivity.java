package thesis.vb.szt.android.activity;

import java.util.ArrayList;
import java.util.List;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class HomeActivity extends FragmentActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Intent requestIntent = getIntent();
//		List<AgentEntity> agentList = (ArrayList<AgentEntity>) requestIntent.getParcelableExtra("agentList");
		List<AgentEntity> agentList = requestIntent.getParcelableArrayListExtra("agentList");
		
		
		Log.i(getTag(), "HomeActivity started");
	}
	
	private String getTag() {
		return getClass().getName();
	}
}
