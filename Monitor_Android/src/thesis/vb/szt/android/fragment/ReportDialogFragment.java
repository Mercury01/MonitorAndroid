package thesis.vb.szt.android.fragment;

import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ReportDialogFragment extends DialogFragment {
	    Context context;
	    
	    public ReportDialogFragment() {
//	        this.context = context;
	    }
	    
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	        alertDialogBuilder.setTitle("Report details");
	        
	        Map<String, String> report = (Map<String, String>) getArguments().getSerializable("report");
	        
	        CharSequence[] items = new String[report.size()];
	        int i = 0;
	        for (Entry<String, String> attribute : report.entrySet()) {
				items[i] = attribute.getKey() + " : \n  " + attribute.getValue();
				i++;
			}
	        
	        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
	        
	        
	        alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });


	        return alertDialogBuilder.create();
	    }
	    
	    @Override
	    public void onAttach(Activity activity) {
	    context = activity;
	    super.onAttach(activity);
	    }
}
