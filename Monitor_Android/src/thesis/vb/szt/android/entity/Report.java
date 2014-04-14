package thesis.vb.szt.android.entity;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import android.util.Log;

@Element(name = "report")
public class Report
{
	
	@ElementList(inline = true, entry="property")
	private List<ReportProperty> propertyList;

	public List<ReportProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<ReportProperty> propertyList) {
		this.propertyList = propertyList;
		Log.i("DELETE THIS SHIT", "setProperty");
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
			<count>8</count>
	</reportList>
*/
	
	
//	private int id;
//	
//    private AgentEntity agent;
//	
//	private int memorySizeMb;
//	
//	private int memoryFreePercent;
//	
//	private int cpuCoreNumber;
//	
//	private int cpuFrequency;
//	
//	private String cpuVendor;
//	
//	private int storageSizeGb;
//	
//	private int storageFreeGb;
//	
//	private String architecture;
//	
//	private String osName;
//	
//	private String osVersion;
//	
//	private int processCount;
//	
//	private Date timeStamp;
//
//	public int getId()
//	{
//		return id;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public AgentEntity getAgent()
//	{
//		return agent;
//	}
//
//	public void setAgent(AgentEntity agent)
//	{
//		this.agent = agent;
//	}
//
//	public int getMemorySizeMb()
//	{
//		return memorySizeMb;
//	}
//
//	public void setMemorySizeMb(int memorySizeMb)
//	{
//		this.memorySizeMb = memorySizeMb;
//	}
//
//	public int getMemoryFreePercent()
//	{
//		return memoryFreePercent;
//	}
//
//	public void setMemoryFreePercent(int memoryFreePercent)
//	{
//		this.memoryFreePercent = memoryFreePercent;
//	}
//
//	public int getCpuCoreNumber()
//	{
//		return cpuCoreNumber;
//	}
//
//	public void setCpuCoreNumber(int coreNumber)
//	{
//		this.cpuCoreNumber = coreNumber;
//	}
//
//	public int getCpuFrequency()
//	{
//		return cpuFrequency;
//	}
//
//	public void setCpuFrequency(int cpuFrequency)
//	{
//		this.cpuFrequency = cpuFrequency;
//	}
//
//	public String getCpuVendor()
//	{
//		return cpuVendor;
//	}
//
//	public void setCpuVendor(String cpuVendor)
//	{
//		this.cpuVendor = cpuVendor;
//	}
//
//	public int getStorageSizeGb()
//	{
//		return storageSizeGb;
//	}
//
//	public void setStorageSizeGb(int storageSizeGb)
//	{
//		this.storageSizeGb = storageSizeGb;
//	}
//
//	public int getStorageFreeGb()
//	{
//		return storageFreeGb;
//	}
//
//	public void setStorageFreeGb(int storageFreeGb)
//	{
//		this.storageFreeGb = storageFreeGb;
//	}
//
//	public String getArchitecture()
//	{
//		return architecture;
//	}
//
//	public void setArchitecture(String architecture)
//	{
//		this.architecture = architecture;
//	}
//
//	public String getOsName()
//	{
//		return osName;
//	}
//
//	public void setOsName(String osName)
//	{
//		this.osName = osName;
//	}
//
//	public String getOsVersion()
//	{
//		return osVersion;
//	}
//
//	public void setOsVersion(String osVersion)
//	{
//		this.osVersion = osVersion;
//	}
//
//	public int getProcessCount()
//	{
//		return processCount;
//	}
//
//	public void setProcessCount(int processCount)
//	{
//		this.processCount = processCount;
//	}
//
//	public Date getTimeStamp()
//	{
//		return timeStamp;
//	}
//
//	public void setTimeStamp(Date timeStamp)
//	{
//		this.timeStamp = timeStamp;
//	}	
	

}