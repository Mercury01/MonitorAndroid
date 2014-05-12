package thesis.vb.szt.android.fragment;

import java.util.List;
import java.util.Map;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.activity.HomeActivity;
import thesis.vb.szt.android.activity.LatestReportsActivity;
import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.GetReportListTask;
import thesis.vb.szt.android.tasks.GetReportListTask.GetReportListTaskCompleteListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AgentListFragment extends ListFragment implements GetReportListTaskCompleteListener, OnItemLongClickListener {
	
//	http://www.vogella.com/tutorials/AndroidListView/article.html#listfragments
	
//	private DetailsUpdateListener listener;
	private ArrayAdapter<AgentEntity> adapter;
	private View view;
	
	private View previouslySelectedItem;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.agent_list_fragment, container, false); 
//		view.setOnLongClickListener(this);
		
		adapter = new HomeListAdapter(getActivity(), R.layout.agent_row_fragment);
	    setListAdapter(adapter);
	    
		return view;
	}
	
	@Override
		public void onAttach(Activity activity) {
			Log.i(getTag(), "Agent list fragment attached");
			super.onAttach(activity);
		}
	
	@Override
		public void onResume() {
			getListView().setOnItemLongClickListener(this);
			super.onResume();
		}
	
	@Override
	public void onListItemClick(ListView l, View selectedItem, int position, long id) {
		String mac = Model.getAgentList().get(position).getAddress();
		Model.setMac(mac);
//		listener.onDetailsUpdate(mac);
		
		new GetReportListTask(0, 10, Model.getMac(), null, this).execute(getResources().getString(R.string.getAgent));
		
		selectedItem.setBackgroundColor(Color.argb(20, 120, 120, 140));
		
		if(previouslySelectedItem != null && previouslySelectedItem != selectedItem) {
			previouslySelectedItem.setBackgroundColor(Color.WHITE);
		}
		
		previouslySelectedItem = selectedItem;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View selectedItem, int position, long id) {
		String mac = Model.getAgentList().get(position).getAddress();
		
		Intent latestReportIntent = new Intent(getActivity(), LatestReportsActivity.class);
		latestReportIntent.putExtra("mac", mac);
		startActivity(latestReportIntent);
		return false;
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
                view = inflater.inflate(R.layout.agent_row_fragment, null);
            }
            
            AgentEntity agent = Model.getAgentList().get(position);
            ((TextView)view.findViewById(R.id.row_text_1)).setText(agent.getName() + " " + agent.getAddress());
            
            return view;
	    }
	}
	  
//		public interface DetailsUpdateListener {
//			public void onDetailsUpdate(String mac);
//		}

	@Override
	public void onTaskComplete(List<Map<String, String>> result) {
		Model.setReportsList(result);
//			adapter.notifyDataSetChanged();
		//TODO
		Intent intent = new Intent(HomeActivity.ACTION);
//			Intent intent = new Intent(HomeActivity.ACTION_REPLACE);
		Activity activity = getActivity();
		LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
	}
}
