package thesis.vb.szt.android;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

	private ArrayList<AgentEntity> agentList;
    private Context context;
    private LayoutInflater inflater;
    private View view;
	
 

	public HomeListAdapter(Context context, int textViewResourceId, ArrayList<AgentEntity> agentList) {
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
            ((TextView)view.findViewById(R.id.row_text_1)).setText(agent.getText());
            
            
            return view;
            
//            PlaceItem placeItem = items.get(position);
//            if (placeItem != null) {
//                    TextView listItemName = (TextView) v.findViewById(R.id.listItemName);
//                    TextView listItemType = (TextView) v.findViewById(R.id.listItemType);
//                    TextView listItemAddress = (TextView) v.findViewById(R.id.listItemAddress);
//                    ImageView listItemIcon = (ImageView) v.findViewById(R.id.listItemIcon);
//                    if (listItemName != null) {
//                    	listItemName.setText(placeItem.getName());
//                    }
//                    if(listItemType != null){
//                    	listItemType.setText(placeItem.getType());
//                    }
//                    if(listItemAddress != null){
//                    	listItemAddress.setText(placeItem.getAddress());
//                    }
//                    System.out.println("T�pus: "+listItemType.getText());
//                    if(listItemIcon != null){
//                    	if(listItemType.equals("�tterem")) {
//                			listItemIcon.setImageResource(R.drawable.restaurant_small);                			
//                		} else if(listItemType.equals("K�v�z�")) {
//                			listItemIcon.setImageResource(R.drawable.coffee_small);  
//                		} else if(listItemType.equals("B�r/kocsma")) {
//                			listItemIcon.setImageResource(R.drawable.wine_small); 
//                		} else if(listItemType.equals("Mozi")) {
//                			listItemIcon.setImageResource(R.drawable.camera_small);                			
//                		} else if(listItemType.getText().equals("Sz�rakoz�hely")) {
//                			listItemIcon.setImageResource(R.drawable.speaker_small);		
//                		} else if(listItemType.equals("Hotel")) {
//                			listItemIcon.setImageResource(R.drawable.hotel_small);
//                		} else if(listItemType.equals("F�rd�")) {
//                			listItemIcon.setImageResource(R.drawable.water_small);
//                		} else if(listItemType.equals("�lm�nypark")) {
//                			listItemIcon.setImageResource(R.drawable.adventure_small);
//                		} else if(listItemType.equals("Bev�s�rl�k�zpont")) {
//                			listItemIcon.setImageResource(R.drawable.shopping_cart_small);
//                		} else if(listItemType.equals("ABC")) {
//                			listItemIcon.setImageResource(R.drawable.shopping_basket_small);                			
//                		} else {
//                			listItemIcon.setImageResource(R.drawable.unknown_small);
//                		}
//                    }
//            }
//            return v;
    }
}
