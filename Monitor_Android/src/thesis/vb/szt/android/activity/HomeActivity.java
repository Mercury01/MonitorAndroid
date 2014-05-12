package thesis.vb.szt.android.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.fragment.AgentListFragment;
//import thesis.vb.szt.android.fragment.AgentListFragment.DetailsUpdateListener;
import thesis.vb.szt.android.fragment.ChartFragment;
import thesis.vb.szt.android.fragment.ReportListFragment;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.GetReportListTask;
import thesis.vb.szt.android.tasks.GetReportListTask.GetReportListTaskCompleteListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HomeActivity extends FragmentActivity {
	
	private final int PAGE_NUM = 3;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private PagerTabStrip pagerTabStrip;
	private Context context;
	
	public static String ACTION = "UPDATE";
	public static String ACTION_REPLACE = "REPLACE";
	
	private final int CHART_INDEX = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		context = this;
        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(position == CHART_INDEX) {
//					((OnRefreshListener )((ScreenSlidePagerAdapter)pagerAdapter).getItem(position)).onRefresh();
					//TODO
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab);
        
		Log.i(getTag(), "HomeActivity started");
	}
	
	public interface OnRefreshListener {
	    public void onRefresh();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter iff= new IntentFilter(ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, iff);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice);
	}
	
	/** 
	 * Called when after a list item is clicked
	 */
//	@Override
//	public void onDetailsUpdate(final String mac) {
//		
//		GetReportListTask grlt = new GetReportListTask(0, 10, Model.getMac(), new GetReportListTaskCompleteListener() {
//			
//			@Override
//			public void onTaskComplete(List<Map<String, String>> result) {
//				if(result != null) {
//					Model.setReportsList(result);
////					Log.i(getTag(), "Updating details fragment");
////					((OnRefreshListener )((ScreenSlidePagerAdapter)pagerAdapter).getItem(CHART_INDEX)).onRefresh();
////					pagerAdapter.notifyDataSetChanged();
//				} else {
//					Log.i(getTag(), "Cannot update details fragment");
//				}
//			}
//		});
//		final String url = getResources().getString(R.string.getAgent);
//		grlt.execute(url);
//		
//	}

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
			pages.add(0, Fragment.instantiate(context, AgentListFragment.class.getName()));
			pages.add(1, Fragment.instantiate(context, ChartFragment.class.getName()));
			pages.add(2, Fragment.instantiate(context, ReportListFragment.class.getName()));
		}

		@Override
		public void notifyDataSetChanged() {
//			((DetailsFragment)pages.get(1)).update();
			
//		    Fragment newFragment = new ChartFragment();
//		    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//		    transaction.replace(R.id.details_fragment, newFragment);
//		    transaction.addToBackStack(null);
//		    transaction.commit();
			
			
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
	
	private BroadcastReceiver onNotice = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
//	    	((OnRefreshListener )((ScreenSlidePagerAdapter)pagerAdapter).getItem(CHART_INDEX)).onRefresh();
//	    	pagerAdapter.notifyDataSetChanged();
	    }
	};
}
