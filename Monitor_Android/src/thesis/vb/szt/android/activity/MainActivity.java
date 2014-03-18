package thesis.vb.szt.android.activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.LoadStateTask;
import thesis.vb.szt.android.tasks.LoadStateTask.LoadStateTaskCompleteListener;
import thesis.vb.szt.android.tasks.SaveStateTask;
import thesis.vb.szt.android.tasks.SaveStateTask.SaveStateTaskCompleteListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
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
		switch(requestCode) {
    		case LOGIN_REQUEST:
    	    	if (resultCode == RESULT_OK) { //TODO rearrange, load state
    	    		if(Model.getAgentList() == null) {
    	    			Toast.makeText(getApplicationContext(), "Unable to contact server. Loading reports from history", Toast.LENGTH_LONG).show();
    	    			loadState();
    	    		} else {
//    	    			try {
//    	    				FileOutputStream fos = openFileOutput("teszt", MODE_PRIVATE);
//							PrintWriter pw = new PrintWriter(fos);
//							pw.write("teszt");
//							pw.flush();
//							
//							BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("teszt")));
//							String s = br.readLine();
//							s += "";
//						} catch (FileNotFoundException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
    	    			saveState();
    	    		}
//		    			agentList = resultIntent.getParcelableArrayListExtra("resultList");
//		    			Bundle b = new Bundle();
//		    			b.putParcelableArrayList("agentsList", agentList);
	    			
    	    			
    	    			
	    			Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//		    			homeIntent.putExtra("agentList", agentList);
	    			startActivity(homeIntent);
    	    	} else {
    	    		if(Model.getAgentList() == null) {
    	    			Toast.makeText(getApplicationContext(), "Unable to contact server. Loading reports from history", Toast.LENGTH_LONG).show();
    	    			loadState();
    	    		} 
//    	    		finish();
    	    	}
	    			break;
			default:
				break;
		}
    }
    
    @Override
    protected void onDestroy() {
		saveState();
    	super.onDestroy();
    }
    
    public void saveState() {
    	if(Model.getAgentList() != null) {
	    	SaveStateTask saveStateTask = new SaveStateTask(getApplicationContext(), new SaveStateTaskCompleteListener() {
				@Override
				public void onTaskComplete() {
					Toast.makeText(getApplicationContext(), "Successfully saved application state", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onError() {
					Toast.makeText(getApplicationContext(), "An error occured while saving application state", Toast.LENGTH_SHORT).show();
				}
			});
	    	saveStateTask.execute(new Void[0]);
    	} else {
    		Log.e(getTag(), "Unable to save state. Agent list is null");
    	}
    }
    
    public void loadState() {
    	LoadStateTask loadStateTask = new LoadStateTask(getApplicationContext(), new LoadStateTaskCompleteListener() {
			
			@Override
			public void onTaskComplete() {
				Toast.makeText(getApplicationContext(), "Successfully loaded application state", Toast.LENGTH_SHORT).show();
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
			}
			
			@Override
			public void onError() {
				Toast.makeText(getApplicationContext(), "An error occured while loading application state", Toast.LENGTH_SHORT).show();
			}
		});
    	loadStateTask.execute();
    }
    
    
    private String getTag() {
		return getClass().getName();
	}
    
    
}
