package thesis.vb.szt.android.activity;

import java.util.ArrayList;
import java.util.List;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.fragment.AgentListFragment;
import thesis.vb.szt.android.fragment.AgentListFragment.DetailsUpdateListener;
import thesis.vb.szt.android.fragment.ChartFragment;
import thesis.vb.szt.android.fragment.ReportListFragment;
import thesis.vb.szt.android.tasks.GetReportListTask;
import thesis.vb.szt.android.tasks.GetReportListTask.GetReportListTaskCompleteListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity implements DetailsUpdateListener {
	
	private final int PAGE_NUM = 3;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // 
		
		Log.i(getTag(), "HomeActivity started");
	}
	
	/** 
	 * Called when after a list item is clicked
	 */
	@Override
	public void onDetailsUpdate(final String mac) {
		
		GetReportListTask grlt = new GetReportListTask(new GetReportListTaskCompleteListener() {
			
			@Override
			public void onTaskComplete(Boolean result) {
				if(result) {
					Log.i(getTag(), "Updating details fragment");
					pagerAdapter.notifyDataSetChanged();
				} else {
					Log.i(getTag(), "Cannot update details fragment");
				}
				
			}
		});
		final String url = getResources().getString(R.string.getAgent);
		grlt.execute(url, mac);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.actionbar, menu);
      return true;
    } 
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.action_settings:
//    	  if (Build.VERSION.SDK_INT < 11) {
    		    startActivity(new Intent(this, PreferencesSupportActivity.class));
//    		} else {
//    		    startActivity(new Intent(this, PreferencesActivity.class));
//    		}
        break;
      default:
        break;
      }
      return true;
    } 
	
	private String getTag() {
		return getClass().getName();
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		private List<Fragment> pages;
		private String[] titles = {"Agent list", "Agent reports", "Agent list"};
		
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
			pages = new ArrayList<Fragment>(PAGE_NUM);
			pages.add(0, Fragment.instantiate(getApplicationContext(), AgentListFragment.class.getName())); //TODO make main
			pages.add(1, Fragment.instantiate(getApplicationContext(), ChartFragment.class.getName())); //TODO details
			pages.add(2, Fragment.instantiate(getApplicationContext(), ReportListFragment.class.getName())); //TODO chart
		}

		@Override
		public void notifyDataSetChanged() {
//			((DetailsFragment)pages.get(1)).update();
			
		    Fragment newFragment = new ChartFragment();
		    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		    transaction.replace(R.id.details_fragment, newFragment);
		    transaction.addToBackStack(null);
		    transaction.commit();
			
			
//			pages.set(2, new DetailsFragment());
			super.notifyDataSetChanged();
		}
		
		@Override
		public Fragment getItem(int index) {
			Log.i(getTag(), "Get item: " + index);
			return pages.get(index);
		}

		@Override
		public CharSequence getPageTitle(int index) {
			return titles[index];
		}
		
		@Override
		public int getCount() {
			return PAGE_NUM;
		}
	}
}
