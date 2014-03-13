package thesis.vb.szt.android.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.model.Persistence;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	private final int LOGIN_REQUEST = 0;
	private ArrayList<AgentEntity> agentList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TODO undo comment
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivityForResult(loginIntent, LOGIN_REQUEST);
        
		

//		Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//		homeIntent.putExtra("agentList", agentList);
//		startActivity(homeIntent);
		
		
		
//        Static
//        Fragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.loginFragment);
        
//        Dynamic
//        Fragment loginFragment = new LoginFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(CONTENT_VIEW_ID, loginFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent resultIntent) {
    	super.onActivityResult(requestCode, resultCode, resultIntent);
    	if (resultCode == RESULT_OK) {
    		switch(requestCode) {
	    		case LOGIN_REQUEST:
//		    			agentList = resultIntent.getParcelableArrayListExtra("resultList");
//		    			Bundle b = new Bundle();
//		    			b.putParcelableArrayList("agentsList", agentList);
		    			
		    			Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//		    			homeIntent.putExtra("agentList", agentList);
		    			startActivity(homeIntent);
	    			break;
    			default:
    				break;
    		}
    	} else {
    		Toast.makeText(getApplicationContext(), "Something went wrong. Please retry.", Toast.LENGTH_LONG).show();
    	}
    }
    
    public boolean saveState() {
    	

		PrintWriter pw = null;
		Persistence persistence;
		try {
			String agentListFilename = "agentlist_for_" + Model.getUsername();
			pw = new PrintWriter(openFileOutput(agentListFilename, Context.MODE_PRIVATE));
			persistence = new Persistence(pw);
			persistence.persistAgentList();
			return true;
		} catch (IOException e) {
			Log.e(getTag(), "Unable to save application state", e);
			return false;
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
    }
    
    private String getTag() {
		return getClass().getName();
	}
}
