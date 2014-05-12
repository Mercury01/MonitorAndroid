package thesis.vb.szt.android.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thesis.vb.szt.android.R;
import thesis.vb.szt.android.activity.HomeActivity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.tasks.GetReportListTask;
import thesis.vb.szt.android.tasks.GetReportListTask.GetReportListTaskCompleteListener;
import thesis.vb.szt.android.utility.EndlessScrollListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ReportListFragment extends ListFragment implements GetReportListTaskCompleteListener {

	// private DetailsUpdateListener listener;
	private ArrayAdapter<Map<String, String>> adapter;
	private View view;
	private GetReportListTaskCompleteListener completeListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		view = inflater
				.inflate(R.layout.report_list_fragment, container, false);

		if (Model.getReportsList() != null) {

			((LinearLayout) view).removeViewAt(0);

			adapter = new ReportListAdapter(getActivity(),
					R.layout.report_row_fragment);
			setListAdapter(adapter);

		}

		return view;
	}

	@Override
	public void onResume() {
		completeListener = this;
		ListView listView = getListView();
		listView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int step, int totalItemsCount) {
				String timestamp = Model.getReportsList().get(Model.getReportsList().size() - 1).get("timestamp");
				new GetReportListTask(totalItemsCount, step, Model.getMac(), timestamp, completeListener).execute(getResources().getString(R.string.getAgent));
				Log.i("test", "Infinity scrolled");
			}
		});
		super.onResume();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("report",
				(HashMap<String, String>) Model.getReport(position));

		ReportDialogFragment dialog = new ReportDialogFragment();
		dialog.setArguments(bundle);

		dialog.show(getFragmentManager(), "ReportDialog");
	}

	private class ReportListAdapter extends ArrayAdapter<Map<String, String>> {

		private LayoutInflater inflater;
		private View view;

		public ReportListAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId, Model.getReportsList());
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.report_row_fragment, null);
			}
			Map<String, String> report = getItem(position);

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
			String attributeName1 = sharedPreferences.getString(
					"reportlist_row_text1", "");
			String attributeName2 = sharedPreferences.getString(
					"reportlist_row_text2", "");
			boolean attributesValid = attributeName1 != null
					&& !attributeName1.isEmpty() && attributeName2 != null
					&& !attributeName2.isEmpty();
			if (attributesValid) {
				String attributeValue1 = report.get(attributeName1);
				if (attributeValue1 != null && !attributeValue1.isEmpty()) {
					((TextView) view.findViewById(R.id.report_row_text_1))
							.setText(attributeName1 + " : " + attributeValue1);
				} else {
					Log.e(getTag(), "Property " + attributeName1
							+ " not found. Setting id as substitute");
					((TextView) view.findViewById(R.id.report_row_text_1))
							.setText("Agent id : " + report.get("id"));
				}

				String attributeValue2 = report.get(attributeName2);
				if (attributeValue2 != null && !attributeValue2.isEmpty()) {
					((TextView) view.findViewById(R.id.report_row_text_2))
							.setText(attributeName2 + " : " + attributeValue2);
				} else {
					Log.e(getTag(), "Property " + attributeName1
							+ " not found. Setting osName as substitute");
					((TextView) view.findViewById(R.id.report_row_text_2))
							.setText("Operating System : "
									+ report.get("osName"));
				}

			} else {
				Log.e(getTag(),
						"Could not find properties for custom report row fields");
				((TextView) view.findViewById(R.id.report_row_text_1))
						.setText("Agent id : " + report.get("id"));
				((TextView) view.findViewById(R.id.report_row_text_2))
						.setText("Operating System : " + report.get("osName"));
			}
			return view;
		}
	}

//	public interface DetailsUpdateListener {
//		public void onDetailsUpdate(String mac);
//	}

	@Override
	public void onTaskComplete(List<Map<String, String>> result) {
		Model.appendReportList(result);
		adapter.notifyDataSetChanged();
		Intent intent = new Intent(HomeActivity.ACTION);
		Activity activity = getActivity();
		LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
	}
}
