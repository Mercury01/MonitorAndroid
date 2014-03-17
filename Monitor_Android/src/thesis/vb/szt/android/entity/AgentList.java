package thesis.vb.szt.android.entity;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="agentSet")
public class AgentList {
//	
//	@Element(name="Query")
//	private Query query;
	
	@ElementList(inline=true, entry="agent")
	private List<AgentEntity> agentSet;		//TODO change to agentlist, test with server

	public List<AgentEntity> getAgentList() {
		return agentSet;
	}

	public void setAgentList(List<AgentEntity> agentList) {
		this.agentSet = agentList;
	}
	
	
}

/**
 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 * <agentSet>
 * 		<agent>
 * 			<id>4</id>
 * 			<address>50_E5_49_4C_65_11</address>
 * 			<name>Dummy</name>
 * 		</agent>
 * 		<agent>
 * 			<id>3</id>
 * 			<address>50_E5_49_4C_65_12</address>
 * 			<name>RemoteAlma</name>
 * 		</agent>
 * </agentSet>
 */

