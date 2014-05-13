package thesis.vb.szt.android.fragment;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.tasks.GetLatestReportTask;
import thesis.vb.szt.android.tasks.GetLatestReportTask.GetLatestReportTaskCompleteListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LatestReportFragment extends Fragment implements GetLatestReportTaskCompleteListener{

private View view;
private TableLayout table;

private String mac;
//private String lastTimeStamp;

private Timer timer;
private Handler handler;

private Map<String, String> lastReport;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mac = getActivity().getIntent().getStringExtra("mac");
		
		view = inflater.inflate(R.layout.latest_report_fragment, container); 
		
		table = (TableLayout) view.findViewById(R.id.latest_report_table);
		
		TextView header = (TextView) view.findViewById(R.id.latest_report_header);
		fillTableHeader(mac, header);
		
		HandlerThread hThread = new HandlerThread("HandlerThread");
		hThread.start();
		handler = new Handler(hThread.getLooper());
		
		return view;	
	}
	
	@Override
	public void onResume() {
		timer = new Timer(this);
			
		handler.post(timer);
		super.onResume();
	}
	
	private class Timer implements Runnable {
		
		GetLatestReportTaskCompleteListener listener;
		
		public Timer(GetLatestReportTaskCompleteListener listener) {
			this.listener = listener;
		}
		
		@Override
		public void run() {
			Log.i(getTag(), "Timer tick");
//			if(lastTimeStamp != null) {
				new GetLatestReportTask(1, mac, null, listener).execute(getResources().getString(R.string.getLatestReport));
//			} else {
//				new get
//			}
			handler.postDelayed(this, 1000);
//			Thread.sleep(3000); 
			
		}
	}
	
	private void fillTableHeader(String mac, TextView header) {
		header.setText("Latest reports for " + mac);
//		header.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        header.setGravity(Gravity.CENTER);
//        header.setTypeface(Typeface.SERIF, Typeface.BOLD);
		
//        TableRow.LayoutParams params = new TableRow.LayoutParams();
//        params.span = 2;
//        
//		TableRow headerRow = new TableRow(getActivity());
//		headerRow.addView(header, params);
		
//		table.addView(headerRow);
	}
	
//	private void fillTable(String mac, String timeStamp) {
//		new GetLatestReportTask(1, mac, timeStamp, this).execute(getResources().getString(R.string.getLatestReport));
//	}

	@Override
	public void onTaskComplete(List<Map<String, String>> result) {
		Log.i(getTag(), "Received latest report");
		
		if(result == null) {
			Log.i(getTag(), "No new report");
			return;
		}
		
		Map<String, String> report = result.get(0);
		table.removeAllViews();
		
		for(Entry<String, String> property : report.entrySet()) {
			if (getActivity() == null)
				return;
			TableRow row = new TableRow(getActivity());
			TextView propertyName = new TextView(getActivity());
			TextView propertyValue = new TextView(getActivity());
			ImageView deltaImage = new ImageView(getActivity());
			
//			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			params.
			
			Bitmap bmp;
		    int width=30;
		    int height=30;
		                                                                
			
			/**
			 * Print delta indicators
			 */
			//TODO if data changes from numeric to non-numeric it could cause problems
			boolean numeric = TextUtils.isDigitsOnly(property.getValue());
			if(numeric) {
				try {
					double previousValue = 0.0;
					if(lastReport != null) {
						previousValue = Double.valueOf(lastReport.get(property.getKey()));
					}
					double currentValue = Double.valueOf(report.get(property.getKey()));
					if(currentValue == previousValue) {
//						deltaImage.setImageResource(R.drawable.ic_ab_back_holo_light_am);
						bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ic_ab_back_holo_light_am);
						
					} else if (currentValue > previousValue) {
//						deltaImage.setImageResource(R.drawable.ic_find_next_holo_light);
						bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ic_find_next_holo_light);
					} else {
//						deltaImage.setImageResource(R.drawable.ic_find_previous_holo_light);
						bmp=BitmapFactory.decodeResource(getResources(), R.drawable.ic_find_previous_holo_light);
					}
					bmp=Bitmap.createScaledBitmap(bmp, width,height, true);
				    deltaImage.setImageBitmap(bmp);
				} catch (Exception e) {
					
				}
			}
			
			propertyName.setText(property.getKey());
//			propertyName.setWidth(table.getWidth() / 3);
			if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				propertyName.setWidth(380);
			} else {
				propertyName.setWidth(800);
			}
			
			propertyValue.setText(breakValueString(property.getValue()));
			
			
//			TableLayout.LayoutParams params = new TableLayout.LayoutParams();
//	        params.width = 2;
//			TableLayout.LayoutParams textViewParam = new TableLayout.LayoutParams
//				     (TableLayout.LayoutParams.WRAP_CONTENT,
//				     TableLayout.LayoutParams.WRAP_CONTENT,1f);
//			textViewParam.setMargins(5, 0, 5,0);
//			propertyName.setLayoutParams(params);
//			propertyValue.setLayoutParams(textViewParam);
			
			
			row.addView(propertyName);
			row.addView(deltaImage);
			row.addView(propertyValue);
			
			table.addView(row);
		}
		
		lastReport = report;
	}
	
	private String breakValueString(String value) {
		String result = value;
		if(result.length() > 10) {
			result = result.substring(0, result.length() / 2) + "\n" + result.substring(result.length() / 2);
		}
			
		return result;
	}
	
	@Override
		public void onPause() {
			//TODO stop
			handler.removeCallbacks(timer);
			super.onPause();
		}
}
