package thesis.vb.szt.android.activity;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.fragment.PreferencesFragment;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

@Deprecated() 	//Does not work somehow
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PreferencesActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.preferences);
//		FragmentManager manager = getFragmentManager();
//		FragmentTransaction transaction = manager.beginTransaction();
//		transaction.add("pref", new PreferencesFragment());
//		transaction.commit();
//		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
//		getFragmentManager().beginTransaction()
//			.replace(R.id.preferencesFragment, new PreferencesFragment()).commit();
		
		//TODO read prefs
		
		
	}
	
	@Override
	protected void onPause() {
		// TODO store prefs
		super.onPause();
	}
	
	
	
//	@Override
//	public void onBuildHeaders(List<Header> target) {
//		loadHeadersFromResource(R.xml.preference_headers, target);
//	}

}