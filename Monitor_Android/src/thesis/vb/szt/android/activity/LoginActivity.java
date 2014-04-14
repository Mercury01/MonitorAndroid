package thesis.vb.szt.android.activity;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.security.Keys;
import thesis.vb.szt.android.security.Security;
import thesis.vb.szt.android.tasks.LoginTask;
import thesis.vb.szt.android.tasks.LoginTask.LoginTaskCompleteListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {
	
	private Button loginButton;
	private EditText usernameText;
	private EditText passwordText;
	
	private LoginTask loginTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		loginButton = (Button) findViewById(R.id.loginLoginButton);
		loginButton.setOnClickListener(new LoginButtonListener());
		
		usernameText = (EditText) findViewById(R.id.loginUsernameTextfield);
//		usernameText.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if(!hasFocus) {
//					String username = ((EditText)v).getText().toString();
//					Model.setUsername(username);
//				}
//			}
//		});
		passwordText = (EditText) findViewById(R.id.loginPasswordTextfield);
//		passwordText.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if(!hasFocus) {
//					String password = ((EditText)v).getText().toString();
//					Model.setPassword(password);
//				}
//			}
//		});
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
							
//							Intent resultIntent = new Intent();
//							resultIntent.putExtra("resultList", resultList);
							setResult(RESULT_OK);
							finish();
						}
					}
				});
				
				
				
				
//				loginTask.execute("http://www.google.com");
//				loginTask.execute("http://192.168.1.104:8080/monitor/android/test");
				final String url = getResources().getString(R.string.login);
				loginTask.execute(url);
				
//				loginTask.execute(new URI("http", null, "192.168.1.1", 8080, "/monitor/android/test", null, null).toASCIIString());
			} catch (Exception e) {
				Log.e(getTag(), "Unable to log in", e);
				e.printStackTrace();
			}
		}
	}

//	class 
//	
//	@Override
//	public void onTaskComplete(String result) {
//		
//		if(result == null) {
//			//TODO invalid login
//		} else {
//			Log.i("", result);
//			
//			finish();
//		}
//	}
	private String getTag() {
		return getClass().getName();
	}
}

