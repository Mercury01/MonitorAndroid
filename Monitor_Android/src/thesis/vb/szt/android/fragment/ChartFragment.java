package thesis.vb.szt.android.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.activity.HomeActivity;
import thesis.vb.szt.android.activity.PreferencesSupportActivity;
import thesis.vb.szt.android.model.Model;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChartFragment extends Fragment {

	private View view;

	private GraphicalView chart;

	private XYMultipleSeriesDataset dataset;

	private XYMultipleSeriesRenderer renderer;

	private final int[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.YELLOW};
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		view = inflater.inflate(R.layout.chart_fragment, container, false);
		Log.i(getTag(), "ChartFragment created");
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter iff= new IntentFilter(HomeActivity.ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotice, iff);
        
		update();

		Log.i(getTag(), "ChartFragment resumed");
	}
	
	@Override
	public void onPause() {
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(onNotice);
		super.onPause();
	}

	public void update() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		int seriesCount = sharedPreferences.getInt(
				PreferencesSupportActivity.CHART_KEY_COUNT, -1);
		
		if(Model.getReportsList() != null) {
			LinearLayout layout = (LinearLayout) view;
			if (seriesCount == 0) {
				Toast.makeText(getActivity(), "No series added in properties", Toast.LENGTH_LONG).show();
				return;
			} else if (layout != null) {
//				if (chart == null) {
					layout.removeAllViews();
					initChart();
					updateDataset();
					chart = ChartFactory.getLineChartView(getActivity(),
							dataset, renderer);
					layout.addView(chart);
//					chart.repaint();
//				} else {
//					updateDataset();
//					chart.repaint();
//				}
			} else {
				Log.e(getTag(), "Chart layout is null");
			}
		} else {
			Log.e(getTag(), "Reportlist is null");
		}
		Log.i(getTag(), "Chartfragment updated");
	}

	private void initChart() {
		dataset = new XYMultipleSeriesDataset();

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		int seriesCount = sharedPreferences.getInt(
				PreferencesSupportActivity.CHART_KEY_COUNT, -1);
		Log.i(getTag(), "Intitialising chart with " + seriesCount + " series");
		
		renderer = new XYMultipleSeriesRenderer(seriesCount);
		renderer.setInScroll(true);
		
		renderer.setShowGrid(true);
		renderer.setYLabels(10);
		renderer.setYLabelsPadding(-20.0f);
		
		/**		Chart background	*/
		renderer.setMargins(new int[] { 0, 0, 0, 0 });
		renderer.setApplyBackgroundColor(true);
		renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		renderer.setBackgroundColor(Color.TRANSPARENT);
	}

	private void updateDataset() {
		List<Map<String, String>> reportList = Model.getReportsList();
		if (reportList == null) {
			Log.w(getTag(), "Empty report list. Cannot update chart dataset");
			return;
		}

		if(dataset == null)
		{
			initChart();
			return;
		}
		dataset.clear();

		if(getActivity() == null) {
			Log.e(getTag(), "Activity is null");
		}
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		int seriesCount = sharedPreferences.getInt(
				PreferencesSupportActivity.CHART_KEY_COUNT, -1);

//		if (seriesCount == 0) {
//			return;
//		}
		
		List<XYSeries> seriesList = new ArrayList<XYSeries>(seriesCount);
		List<Integer> usedColorList = new ArrayList<Integer>(seriesCount);

		for (int i = 0; i < reportList.size(); i++) {
			Map<String, String> report = reportList.get(i);

			int j = 0;
			for (Entry<String, String> property : report.entrySet()) {
				if (sharedPreferences.getBoolean(
						PreferencesSupportActivity.CHART_KEY_PREFIX
								+ property.getKey(), false)) {
					if (seriesList.size() <= j) {
						XYSeries s = new XYSeries(property.getKey(), j);
						seriesList.add(s);
						
						XYSeriesRenderer r = getRandomRenderer(usedColorList);
						renderer.addSeriesRenderer(j, r);
						
						renderer.setYLabelsColor(j, r.getColor());
						if(j % 2 == 0) {
							renderer.setYAxisAlign(Align.LEFT, j);
						} else {
							renderer.setYAxisAlign(Align.RIGHT, j);
						}
						
					}
					try {
						seriesList.get(j).add(i,
								Integer.valueOf(property.getValue()));
//						seriesList.get(j).add(i,
//								Integer.valueOf(property.getValue()) % 1000);
					} catch (Exception e) {
						Log.w(getTag(),
								"Non numeric attribute selected for display in chart");
						seriesList.get(j).add(i, 0);
					}
					j++;
				}
			}
		}

		//TODO
		renderer.setYAxisAlign(Align.RIGHT, 1);
		renderer.setYAxisAlign(Align.LEFT, 0);
		
		dataset.addAllSeries(seriesList);
		Log.i(getTag(), "Chart dataset updated");
	}
	
	private XYSeriesRenderer getRandomRenderer(List<Integer> colorList) {
		/** Get a random color */
		int selectedColorIndex = 0;
		if(colorList.size() >= colors.length) {
			Log.w(getTag(), "There aren't enough colors defined for this many renderers");
		} else {
			Random rand = new Random();
			do {
				selectedColorIndex = rand.nextInt(colors.length - 1);
			} while (colorList.contains(colors[selectedColorIndex]));
			colorList.add(colors[selectedColorIndex]);
		}
		
		//ff0000ff
		String selectedColorHex = Integer.toHexString(colors[selectedColorIndex]);
		
		XYSeriesRenderer currentRenderer = new XYSeriesRenderer();
		currentRenderer.setColor(hex2Rgb(selectedColorHex));
		currentRenderer.setPointStyle(PointStyle.SQUARE);
		currentRenderer.setFillPoints(true);
		return currentRenderer;
	}
	
	public static int hex2Rgb(String colorStr) {
	    return Color.argb(
	            Integer.valueOf( colorStr.substring( 0, 2 ), 16 ),
	            Integer.valueOf( colorStr.substring( 2, 4 ), 16 ),
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16 ),
	            Integer.valueOf( colorStr.substring( 6, 8 ), 16 ) );
	}

	private BroadcastReceiver onNotice = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(getTag(), "Refresh broadcast received in chart fragment");
	    	update();
	    }
	};
}
