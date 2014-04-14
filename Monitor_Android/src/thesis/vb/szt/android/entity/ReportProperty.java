package thesis.vb.szt.android.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element(name="property")
public class ReportProperty {
	
	@Attribute(name="name")
	private String name;
	
	@Attribute(name="value")
	private String value;
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