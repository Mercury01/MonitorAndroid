package thesis.vb.szt.android.activity;

import java.util.List;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.LoadStateTask;
import thesis.vb.szt.android.tasks.LoginTask;
import thesis.vb.szt.android.tasks.LoadStateTask.LoadStateTaskCompleteListener;
import thesis.vb.szt.android.tasks.LoginTask.LoginTaskCompleteListener;
import thesis.vb.szt.android.tasks.SaveStateTask;
import thesis.vb.szt.android.tasks.SaveStateTask.SaveStateTaskCompleteListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final int LOGIN_REQUEST = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean autoLogin = sharedPreferences.getBoolean("autoLogin", false);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        boolean credentialsSet = (username != null && !username.isEmpty()) &&
        							(password != null && !password.isEmpty());
        if(!autoLogin || !credentialsSet) {
	        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivityForResult(loginIntent, LOGIN_REQUEST);
        } else {
        	login();
        }
    }
    
    private void login() {
    	Log.i(getTag(), "Login from main activity");
		
    	
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPreferences.getString("username", "");
        String encryptedPassword = sharedPreferences.getString("password", "");
        
        Model.setUsername(username);
    	Model.setEncryptedPassword(encryptedPassword);
    	
		try {
			LoginTask loginTask = new LoginTask(new LoginTaskCompleteListener() {
				
				@Override
				public void onTaskComplete(List<AgentEntity> resultList) {
					if(resultList == null) {
						Toast t = Toast.makeText(getApplicationContext(), "Unable to login. Please try again.", Toast.LENGTH_SHORT);
						t.show();
						Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
						startActivityForResult(loginIntent, LOGIN_REQUEST);
					} else {
						Log.i(getTag(), "Login successful");
						Model.setAgentList(resultList);
						startHomeActivity();
					}
				}
			});
			
			final String url = getResources().getString(R.string.login);
			loginTask.execute(url);
		} catch (Exception e) {
			Log.e(getTag(), "Unable to log in", e);
			e.printStackTrace();
		}
	}

	@Override
    protected void onActivityResult (int requestCode, int resultCode, Intent resultIntent) {
    	super.onActivityResult(requestCode, resultCode, resultIntent);
		switch(requestCode) {
    		case LOGIN_REQUEST:
    	    	if (resultCode == RESULT_OK) { //TODO rearrange, load state
    	    		startHomeActivity();
    	    	} else {
    	    		if(Model.getAgentList() == null) {
    	    			Toast.makeText(getApplicationContext(), "Unable to contact server. Loading reports from history", Toast.LENGTH_LONG).show();
    	    			loadState();
    	    		} 
    	    	}
    			break;
			default:
				break;
		}
    }

	private void startHomeActivity() {
		if(Model.getAgentList() == null) {
			Toast.makeText(getApplicationContext(), "Unable to contact server. Loading reports from history", Toast.LENGTH_LONG).show();
			loadState();
		} else {
			saveState();
		}
		Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(homeIntent);
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
