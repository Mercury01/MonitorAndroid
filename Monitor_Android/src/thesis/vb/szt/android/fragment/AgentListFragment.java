package thesis.vb.szt.android.fragment;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AgentListFragment extends ListFragment {
	
//	http://www.vogella.com/tutorials/AndroidListView/article.html#listfragments
	
	private DetailsUpdateListener listener;
	private ArrayAdapter<AgentEntity> adapter;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.home_list_fragment, container, false); 
		
		adapter = new HomeListAdapter(getActivity(), R.layout.home_row_fragment);
	    setListAdapter(adapter);
		
		return view;
	}
	
	@Override
		public void onAttach(Activity activity) {
			Log.i(getTag(), "Agent list fragment attached");
			try {
				listener = (DetailsUpdateListener) activity;
			} catch (ClassCastException e) {
				Log.e(getTag(), "Attaching activity must implement DetailsUpdateListener", e);
				Toast.makeText(getActivity(), "Unable to load details page", Toast.LENGTH_SHORT).show();
			}
			super.onAttach(activity);
		}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String mac = Model.getAgentList().get(position).getAddress();
		listener.onDetailsUpdate(mac);
	}
	  
	private class HomeListAdapter extends ArrayAdapter<AgentEntity> {

	    private LayoutInflater inflater;
	    private View view;
		
		public HomeListAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId, Model.getAgentList());
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
            view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.home_row_fragment, null);
            }
            
            AgentEntity agent = Model.getAgentList().get(position);
            ((TextView)view.findViewById(R.id.row_text_1)).setText(agent.getName() + " " + agent.getAddress());
            
            return view;
	    }
	}
	  
		public interface DetailsUpdateListener {
			public void onDetailsUpdate(String mac);
		}
}
