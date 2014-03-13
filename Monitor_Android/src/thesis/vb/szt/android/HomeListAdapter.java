package thesis.vb.szt.android;

import java.util.List;

import thesis.vb.szt.android.entity.AgentEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//public class HomeListFragment extends Fragment {
//
//	private View view;
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		
//		view = inflater.inflate(R.layout.home_list_fragment, container); 
//		return view;	
//	}
//}

public class HomeListAdapter extends ArrayAdapter<AgentEntity> {

	private List<AgentEntity> agentList;
    private Context context;
    private LayoutInflater inflater;
    private View view;
	
 

	public HomeListAdapter(Context context, int textViewResourceId, List<AgentEntity> agentList) {
		super(context, textViewResourceId, agentList);
		this.agentList = agentList;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.home_row_fragment, null);
            }
            
            AgentEntity agent = agentList.get(position);
            ((TextView)view.findViewById(R.id.row_text_1)).setText(agent.getName() + " " + agent.getAddress());
            
            
            return view;
    }
}
