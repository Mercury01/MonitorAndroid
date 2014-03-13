package thesis.vb.szt.android.entity;

import org.simpleframework.xml.Element;

@Element(name="agent")
public class AgentEntity {

	@Element(name="id", required=false)
	private int id;
	
	@Element(name="address", required=false)
	private String address;
	
	@Element(name="name", required=false)
	private String name;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

/**
 * 		<agent>
 * 			<id>4</id>
 * 			<address>50_E5_49_4C_65_11</address>
 * 			<name>Dummy</name>
 * 		</agent>
*/