package thesis.vb.szt.android.activity;

import java.util.List;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.LoginTask;
import thesis.vb.szt.android.tasks.LoginTask.LoginTaskCompleteListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {
	
	private Button loginButton;
	private EditText usernameText;
	private EditText passwordText;
	private CheckBox autoLoginCheckbox;
	
	private LoginTask loginTask;
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.login);
		
		
		loginButton = (Button) findViewById(R.id.loginLoginButton);
		loginButton.setOnClickListener(new LoginButtonListener());
		
		usernameText = (EditText) findViewById(R.id.loginUsernameTextfield);
		passwordText = (EditText) findViewById(R.id.loginPasswordTextfield);

		boolean isAutoLoginActivated = sharedPreferences.getBoolean("autoLogin", false);
		
		autoLoginCheckbox = (CheckBox) findViewById(R.id.loginAutoCheckbox);
		autoLoginCheckbox.setChecked(isAutoLoginActivated);
	}
	
	@Override
	protected void onPause() {
		String username = usernameText.getText().toString().trim();
		Model.setUsername(username);
		
		String password = passwordText.getText().toString();
		Model.setPassword(password);
		
		super.onStop();
	}
	
	class LoginButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Log.i(getTag(), "Login clicked");
			Model.setUsername(usernameText.getText().toString().trim());
			Model.setPassword(passwordText.getText().toString());
			
			boolean autoLogin = autoLoginCheckbox.isChecked();
			
			try {
				loginTask = new LoginTask(new LoginTaskCompleteListener() {
					
					@Override
					public void onTaskComplete(List<AgentEntity> resultList) {
						if(resultList == null) {
							Toast t = Toast.makeText(getApplicationContext(), "Unable to login. Your credentials might be invalid.", Toast.LENGTH_SHORT);
							t.show();
						} else {
							Log.i(getTag(), "Login successful");
							
							Model.setAgentList(resultList);
							setResult(RESULT_OK);
							finish();
						}
					}
				});
				
				final String url = getResources().getString(R.string.login);
				loginTask.execute(url);
				
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("username", Model.getUsername());
				editor.putString("password", Model.getPasswordHash());
				editor.putBoolean("autoLogin", autoLogin);
				editor.apply();	
			} catch (Exception e) {
				Log.e(getTag(), "Unable to log in", e);
				e.printStackTrace();
			}
		}
	}
	private String getTag() {
		return getClass().getName();
	}
}

