package thesis.vb.szt.android.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import thesis.vb.szt.android.entity.AgentEntity;

/**
 * Does not open, or close streams. These have to be managed outside the class.
 * @author Bazint
 *
 */
public class Persistence {
	
	private PrintWriter writer;

	public Persistence(PrintWriter writer) {
		super();
		this.writer = writer;
	}

	public boolean persistAgentList() throws IOException {
		final List<AgentEntity> agentList = Model.getAgentList();
		for (AgentEntity agentEntity : agentList) {
			writer.println(getAgentLine(agentEntity));
		}
		return false;
	}
	
	private String getAgentLine(AgentEntity agentEntity) {
		return agentEntity.getId() + " "  +
				agentEntity.getName() + " " + 
				 agentEntity.getAddress();
	}

	public boolean persistReport() throws IOException {
		final List<Map<String, String>> reportsList = Model.getReportsList();
		for (Map<String, String> reportMap : reportsList) {
			for (Entry<String, String> reportEntry : reportMap.entrySet()) {
//				fileOutputStream.w //TODO
			}
		}
		return false;
	}
}
