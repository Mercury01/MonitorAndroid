package thesis.vb.szt.android.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="request")
public class ReportListRequest
{	
	@Element(name="mac", required=false)
	private String mac;
	
	//from this report
	@Element(name="from", required=false)
	private int from;
	
	//get this many reports 
	@Element(name="limit", required=false)
	private int limit;
	
	@Element(name="fromDate", required=false)
	private String fromDate;

	public ReportListRequest(String mac, int limit, String timestamp) {
		super();
		this.mac = mac;
		this.limit = limit;
		this.fromDate = timestamp;
	}
	
	public ReportListRequest(String mac, int from, int limit, String timestamp) {
		super();
		this.mac = mac;
		this.from = from;
		this.limit = limit;
		this.fromDate = timestamp;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
}