package thesis.vb.szt.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.activity.HomeActivity.OnRefreshListener;
import thesis.vb.szt.android.activity.PreferencesSupportActivity;
import thesis.vb.szt.android.model.Model;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChartFragment extends Fragment implements OnRefreshListener{

	private View view;
	
	private GraphicalView chart;
	
	private XYMultipleSeriesDataset dataset;
	
	private XYMultipleSeriesRenderer renderer;
	
	private boolean initialised = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.chart_fragment, container, false); 
		Log.i(getTag(), "DetailsFragment created");
		return view;	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		update();
		
		Log.i(getTag(), "DetailsFragment resumed");
	}
	
	public void update() {
		if (!initialised && Model.getReportsList() != null) {
			LinearLayout layout = (LinearLayout) view;
			layout.removeAllViews();
	        if (chart == null) {
	            initChart();
	            updateDataset();
	            chart = ChartFactory.getCubeLineChartView(getActivity(), dataset, renderer, 0.3f);
	            layout.addView(chart);
	        } else {
	        	updateDataset();
	            chart.repaint();
	        }
	        initialised = true;
		} else {
			initialised = false;
		}
		Log.i(getTag(), "DetailsFragment updated");
	} 
	
	private void initChart() {
		dataset = new XYMultipleSeriesDataset();
        
        renderer = new XYMultipleSeriesRenderer();
        renderer.setInScroll(true);
        
        renderer.setMargins(new int[]{0,0,0,0});
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        renderer.setBackgroundColor(Color.TRANSPARENT);
    }

	private void updateDataset() {
		List<Map<String, String>> reportList = Model.getReportsList();
		if(reportList == null) {
			Log.w(getTag(), "Empty report list. Cannot update chart dataset");
			return;
		}
		
		dataset.clear();
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		int seriesCount = sharedPreferences.getInt(PreferencesSupportActivity.CHART_KEY_COUNT, -1);
		
		List<XYSeries> seriesList = new ArrayList<XYSeries>(seriesCount);
		
		for (int i = 0; i < reportList.size(); i++) {
			Map<String, String> report = reportList.get(i);

			int j = 0;
			for(Entry<String, String> property: report.entrySet()) {
				if(sharedPreferences.getBoolean(PreferencesSupportActivity.CHART_KEY_PREFIX + property.getKey(), false)) {
					if(seriesList.size() <= j) {
						seriesList.add(new XYSeries(property.getKey()));
						XYSeriesRenderer currentRenderer = new XYSeriesRenderer();
						renderer.addSeriesRenderer(currentRenderer);
					}
					try {
						seriesList.get(j).add(i, Integer.valueOf(property.getValue()));
					} catch (Exception e) {
						Log.w(getTag(), "Non numeric attribute selected for display in chart");
						seriesList.get(j).add(i, 0);
					}
					j++;
				}
			}
		}
			
		dataset.addAllSeries(seriesList);
		Log.i(getTag(), "Chart dataset updated");
	}

	@Override
	public void onRefresh() {
		Log.i(getTag(), "Refresh chart");
		if(!initialised) {
			update();
		} else {
			updateDataset();
		}
	}
}
