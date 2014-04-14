package thesis.vb.szt.android.activity;

import java.util.ArrayList;
import java.util.List;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.fragment.AgentListFragment;
import thesis.vb.szt.android.fragment.AgentListFragment.DetailsUpdateListener;
import thesis.vb.szt.android.fragment.DetailsFragment;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.GetReportListTask;
import thesis.vb.szt.android.tasks.GetReportListTask.GetReportListTaskCompleteListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
			public void onTaskComplete(String xmlString) {
//				((FragmentStatePagerAdapter)pagerAdapter).getItem(2) = new ...;
				Toast.makeText(getApplicationContext(), xmlString, Toast.LENGTH_SHORT).show();
			}
		});
		final String url = getResources().getString(R.string.getAgent);
		grlt.execute(url, mac);
		
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
			pages.add(Fragment.instantiate(getApplicationContext(), AgentListFragment.class.getName())); //TODO make main
			pages.add(Fragment.instantiate(getApplicationContext(), DetailsFragment.class.getName())); //TODO details
			pages.add(Fragment.instantiate(getApplicationContext(), AgentListFragment.class.getName())); //TODO chart
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
