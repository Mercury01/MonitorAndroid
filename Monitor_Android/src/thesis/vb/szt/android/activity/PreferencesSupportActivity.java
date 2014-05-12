package thesis.vb.szt.android.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.model.Model;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;


/** Note:
 * 		Checkboxes are built dynamically
 * 		Preference list items are also added dynamically	
 * 
 * @author Bazint
 */
public class PreferencesSupportActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	public static final String REPORT_ROW_KEY_PREFIX = "report_row_attr_";
	public static final String CHART_KEY_PREFIX = "chart_series_";
	public static final String CHART_KEY_COUNT = CHART_KEY_PREFIX + "count";
	
	private SharedPreferences sharedPreferences;
	private CheckBox checkBox;
	private int checkboxesSelectedCount = 0;
	private Map<String, CheckBoxPreference> checkboxMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		createPreferenceHierarchy();
	}

	@Override
	  protected void onResume() {
	    super.onResume();
	    sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
	   }

	public void createPreferenceHierarchy() {
		PreferenceCategory targetCategory;

		if (Model.getReportsList() == null) {
			Toast.makeText(
					getApplicationContext(),
					"Please select an agent to be able to choose from its report attributes",
					Toast.LENGTH_LONG).show();
			
			Preference text = new Preference(getApplicationContext());
			text.setSelectable(false);
			text.setSummary(R.string.no_agent_selected);
			
			targetCategory = (PreferenceCategory) findPreference("report_details_category");
			targetCategory.addPreference(text);
			removeListPreferences(targetCategory);
			
			targetCategory = (PreferenceCategory) findPreference("chart_category");
			targetCategory.addPreference(text);
		} else {
			targetCategory = (PreferenceCategory) findPreference("report_details_category");
			addListPreferences(targetCategory);
			targetCategory = (PreferenceCategory) findPreference("chart_category");
			addCheckboxPreferences(targetCategory);
		}
	}

	private void addListPreferences(PreferenceCategory targetCategory) {
		
		ListPreference listPreference1 = (ListPreference) targetCategory.findPreference("reportlist_row_text1");//new ListPreference(getApplicationContext());
		ListPreference listPreference2 = (ListPreference) targetCategory.findPreference("reportlist_row_text2");
		
		Map<String, String> attributesMap =  Model.getReportsList().get(0);
		
		CharSequence[] entries = new String[attributesMap.size()];
		CharSequence[] entryValues = new String[attributesMap.size()];
		
		int i = 0;
		for(String key : attributesMap.keySet()) {
			entries[i] = key;
			entryValues[i] = key;
			i++;
		}
		
		listPreference1.setEntries(entries);
		listPreference1.setEntryValues(entryValues);
		
		listPreference2.setEntries(entries);
		listPreference2.setEntryValues(entryValues);
	}
	
	private void removeListPreferences(PreferenceCategory targetCategory) {
		ListPreference listPreference1 = (ListPreference) findPreference("reportlist_row_text1");
		ListPreference listPreference2 = (ListPreference) findPreference("reportlist_row_text2");
		
		targetCategory.removePreference(listPreference1);
		targetCategory.removePreference(listPreference2);
	}

	

	private void addCheckboxPreferences(PreferenceCategory targetCategory) {
		checkboxMap = new HashMap<String, CheckBoxPreference>();
		
		for (Entry<String, String> reportEntry : Model.getReportsList()
				.get(0).entrySet()) {
			final String KEY = CHART_KEY_PREFIX + reportEntry.getKey();
			final boolean IS_CHECKED = sharedPreferences.getBoolean(KEY, false);
			
			CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
			checkBoxPreference.setTitle(reportEntry.getKey());
			checkBoxPreference.setKey(KEY);
			checkBoxPreference.setChecked(IS_CHECKED);
			if(IS_CHECKED) {
				checkboxesSelectedCount++;
			}
			
			targetCategory.addPreference(checkBoxPreference);
			checkboxMap.put(KEY, checkBoxPreference);
		}
//		checkboxesSelectedCount = sharedPreferences.getInt(CHART_KEY_COUNT, -1);
	}
	  
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if(key == null || key.isEmpty()) {
			Log.e(getTag(), "Invalid key for preference: " + key);
			return;
		} else {
			if (!key.equals(CHART_KEY_COUNT) && key.startsWith(CHART_KEY_PREFIX)) {
				boolean isChecked = sharedPreferences.getBoolean(key, false);
				if (isChecked){
					checkboxesSelectedCount++;
				} else {
					checkboxesSelectedCount--;
				}
				Editor editor = sharedPreferences.edit();
				editor.putInt(CHART_KEY_COUNT, checkboxesSelectedCount);
				editor.apply();
			}
		}
	}
	//TODO check if its a numeric value
	
	
	
//		Map<String, ?> map = sharedPreferences.getAll();
//		sharedPreferences.edit().clear().apply();
		
//		} else {
//			if (key.startsWith(REPORT_ROW_KEY_PREFIX)) {
//				boolean isChecked = sharedPreferences.getBoolean(key, false);
//				
//				if (isChecked){
//					checkboxesSelectedCount++;
//				} else {
//					checkboxesSelectedCount--;
//				}
//				
//				if (isChecked && checkboxesSelectedCount > 2) {
//					Toast.makeText(
//							getApplicationContext(),
//							"Only two attributes may be selected. Please deselect one to continue",
//							Toast.LENGTH_LONG).show();
//					
//					checkboxMap.get(key).setChecked(false);
//				} 
//			}
//		}
		
		
		
		  	
//		    PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
//
//		    // category 1 created programmatically
//		    PreferenceCategory cat1 = new PreferenceCategory(this);
//		    cat1.setTitle("title");
//		    root.addPreference(cat1);
//
//		    ListPreference list1 = new ListPreference(this);
//		    list1.setTitle(getResources().getString(R.string.some_string_title));
//		    list1.setSummary(getResources().getString(R.string.some_string_text));      
//		    list1.setDialogTitle(getResources().getString(R.string.some_string_pick_title));
//		    list1.setKey("your_key");
//
//		    CharSequence[] entries  = calendars.getCalenders(); //or anything else that returns the right data
//		    list1.setEntries(entries);
//		    int length              = entries.length;
//		    CharSequence[] values   = new CharSequence[length];
//		    for (int i=0; i<length; i++){
//		        CharSequence val = ""+i+1+"";
//		        values[i] =  val;
//		    }
//		    list1.setEntryValues(values);
//
//		    cat1.addPreference(list1);
//
//		    return root;
		//end method
	  
//	private void loadPreferences() {
//		boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value",
//				false);
//		String name = sharedPreferences.getString("storedName", "YourName");
//		if (checkBoxValue) {
//			checkBox.setChecked(true);
//		} else {
//			checkBox.setChecked(false);
//		}
////		editText.setText(name);
//	}
//	  
//	private void savePreferences(String key, boolean value) {
//		Editor editor = sharedPreferences.edit();
//		editor.putBoolean(key, value);
//		editor.commit();
//	}
//
//	private void savePreferences(String key, String value) {
//		Editor editor = sharedPreferences.edit();
//		editor.putString(key, value);
//		editor.commit();
//	}
	
	private String getTag() {
		return getClass().getName();
	}

	
}