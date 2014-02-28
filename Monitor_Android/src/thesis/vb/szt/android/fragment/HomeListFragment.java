package thesis.vb.szt.android.fragment;

import java.util.ArrayList;

import thesis.vb.szt.android.HomeListAdapter;
import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeListFragment extends ListFragment {
	
//	http://www.vogella.com/tutorials/AndroidListView/article.html#listfragments
	
	
	private ArrayAdapter<AgentEntity> adapter;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		
		view = inflater.inflate(R.layout.home_list_fragment, container, false); 
		
		ArrayList<AgentEntity> list = new ArrayList<AgentEntity>();
		for(int i = 0; i < 25; i++) {
			AgentEntity entity = new AgentEntity();
			entity.setText("Number " + i);
			list.add(entity);
		}
		
		
//		setListAdapter(new ArrayAdapter<AgentEntity>(getActivity(), R.layout.home_row_fragment, list));
		adapter = new HomeListAdapter(getActivity(), R.layout.home_row_fragment, list);
	    setListAdapter(adapter);
		
		return view;
	}
	
//	 @Override
//	  public void onActivityCreated(Bundle savedInstanceState) {
//	    super.onActivityCreated(savedInstanceState);
//
//	    adapter = new HomeListAdapter(getActivity(), 0, new ArrayList<AgentEntity>());
//	    setListAdapter(adapter);
//	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    Toast.makeText(getActivity(), "List item clicked", Toast.LENGTH_LONG).show();
	  }
}
