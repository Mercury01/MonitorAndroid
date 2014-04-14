package thesis.vb.szt.android.fragment;

import thesis.vb.szt.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsFragment extends Fragment {

private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.details_fragment, container, false); 
		return view;	
	}
	
	@Override
	public void onResume() {
		Log.i(getTag(), "DetailsFragment resumed");
		super.onResume();
	}
	
}
