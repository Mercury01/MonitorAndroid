package thesis.vb.szt.android.entity;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="reportList")
public class ReportList
{
	@ElementList(inline = true, entry = "reportEntity")
	private List<ReportEntity> reportEntity;
	
	@Element
	private int count;

	public List<ReportEntity> getReportEntity() {
		return reportEntity;
	}

	public void setReportEntity(List<ReportEntity> reportEntity) {
		this.reportEntity = reportEntity;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
/**
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<reportList>
		<reportEntity>
			<report>
				<property value="6.1" name="osVersion"/>
				<property value="Intel" name="vendor"/>
				<property value="x64" name="architecture"/>
				<property value="3309" name="frequency"/>
				<property value="4" name="coreNumber"/>
				<property value="8175" name="sizeMb"/>
				<property value="3" name="id"/>
				<property value="2014-04-05 12:51:04.0" name="timestamp"/>
				<property value="35" name="freePercent"/>
				<property value="44" name="sizeFreeGb"/>
				<property value="1308" name="sizeGb"/>
				<property value="95" name="processCount"/>
				<property value="MacOS" name="osName"/>
			</report>
		</reportEntity>
		...
		<count>8</count>
</reportList>
*/
	
//	@Element(name="reportEntity")
//	private List<ReportMap> reportMapList;
//	
//	//number of all reports
//	private int count;
//	
//	public ReportList()
//	{
//		super();
//		reportMapList = new ArrayList<ReportMap>();
//	}
//
//	public ReportList(List<Map<String, String>> reportList)
//	{
//		super();
//		reportMapList = new ArrayList<ReportMap>();
//		for(Map<String, String> report : reportList) {
//			this.reportMapList.add(new ReportMap(report));
//		}
//	}
//
//	@Override
//	public String toString()
//	{
//		StringBuilder sb = new StringBuilder();
//		for(ReportMap reportMap : reportMapList) {
//			sb.append(reportMap.toString());
//			sb.append("\n");
//		}
//		return sb.toString();
//	}
//
//	public List<ReportMap> getReportMapList()
//	{
//		return reportMapList;
//	}
//
//	public void setReportMapList(List<ReportMap> reportMapList)
//	{
//		this.reportMapList = reportMapList;
//	}
//
//	public int getCount()
//	{
//		return count;
//	}
//
//	public void setCount(int count)
//	{
//		this.count = count;
//	}
}

