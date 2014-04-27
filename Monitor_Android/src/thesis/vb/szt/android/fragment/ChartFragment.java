package thesis.vb.szt.android.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.Inflater;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.model.Model;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ChartFragment extends Fragment {

	private View view;
	
	private GraphicalView chart;
	
	private XYMultipleSeriesDataset dataset;
	
	private XYMultipleSeriesRenderer renderer;
	
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
	
	//TODO vmiért csak elforgatáskor mûködik
	public void update() {
		LinearLayout layout = (LinearLayout) view;//.findViewById(R.id.details_fragment);
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
//        chart.invalidate();
		Log.i(getTag(), "DetailsFragment updated");
	} 
	
	private void initChart() {
		dataset = new XYMultipleSeriesDataset();
        
        renderer = new XYMultipleSeriesRenderer();
        renderer.setInScroll(true);
    }

	//TODO valami nincs felszabadítva
	private void updateDataset() {
		List<Map<String, String>> reportList = Model.getReportsList();
		if(reportList == null) {
			Log.w(getTag(), "Empty report list. Cannot update chart dataset");
			return;
		}
		
		//TODO lehet nem is kell
		dataset.clear();
		
		for (int i = 0; i < reportList.size(); i++) {
			Map<String, String> report = reportList.get(i);
			
			Map<String, Integer> measured = new HashMap<String, Integer>();
			measured.put("freePercent", 0);
			measured.put("frequency", 1);
			
			for(Entry<String, Integer> property: measured.entrySet()) {
				Log.i(getTag(), "Adding " + property.getKey() + " to chart");
				XYSeries series;
				if(property.getValue() >= dataset.getSeriesCount()) {
					series = new XYSeries(property.getKey());
					dataset.addSeries(series);
					
					XYSeriesRenderer currentRenderer = new XYSeriesRenderer();
					renderer.addSeriesRenderer(currentRenderer);
				} else {
					series = dataset.getSeriesAt(property.getValue());
				}
				try {
					
					double x = i;
					double y = Double.parseDouble(report.get(property.getKey()));
					series.add(x, y);
				} catch (NumberFormatException e) {
					Log.e(getTag(), "Unable to set coordinates in chart", e);
				}
			}

		}
		Log.i(getTag(), "Chart dataset updated");
	}
	
//    private void addSampleData() {
//    
//    	XYSeries currentSeries = dataset.getSeriesAt(0);
//        currentSeries.add(1, 2);
//        currentSeries.add(2, 3);
//        currentSeries.add(3, 2);
//        currentSeries.add(4, 5);
//        currentSeries.add(5, 4);
//    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

//    protected void onResume() {
//        super.onResume();
////        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
//        LinearLayout layout = (LinearLayout) findViewById(R.id.details_fragment);
//        if (mChart == null) {
//            initChart();
//            addSampleData();
//            mChart = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0.3f);
//            layout.addView(mChart);
//        } else {
//            mChart.repaint();
//        }
//    }
}
