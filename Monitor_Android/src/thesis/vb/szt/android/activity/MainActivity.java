package thesis.vb.szt.android.activity;

import java.util.ArrayList;
import java.util.List;

import thesis.vb.szt.android.entity.AgentEntity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	
	private final int LOGIN_REQUEST = 0;
	private ArrayList<AgentEntity> agentList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        LoginActivity loginActivity = new LoginActivity();
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
//        loginIntent.putExtra("responseXMLString", xmlString);
        //searchIntent.putExtra("responseXMLString", result);
		startActivityForResult(loginIntent, LOGIN_REQUEST);
        
		
		
		
		
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
	    			if(resultCode == RESULT_OK) {
		    			agentList = resultIntent.getParcelableArrayListExtra("resultList");
		    			Bundle b = new Bundle();
		    			b.putParcelableArrayList("agentsList", agentList);
		    			
		    			Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
		    			homeIntent.putExtra("agentList", agentList);
		    			startActivity(homeIntent);
	    			} else {
	    				Log.e(getTag(), "Unable to login. Run should not reach this point.");
	    			}
	    			break;
    			default:
    				break;
    		}
    	}
    	
    }
    
    private String getTag() {
		return getClass().getName();
	}
}
