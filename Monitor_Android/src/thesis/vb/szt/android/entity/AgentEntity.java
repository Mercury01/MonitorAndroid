package thesis.vb.szt.android.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class AgentEntity implements Parcelable {

	
	private String text;
	
	
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
