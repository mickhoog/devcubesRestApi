package Main;

import javax.persistence.*;

@Entity
public class Issue extends SonarResult{

	private String name;
	private String description;
	private String risk;
	private SonarPush sonarpush;
	
	protected Issue() 
	{}
	
	public Issue(String name, String description, String risk, SonarPush sonarPush) {
		this.name = name;
		this.description = description;
		this.risk = risk;
		this.sonarpush = sonarPush;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}	
	@ManyToOne
	public SonarPush getSonarpush() {
		return sonarpush;
	}
	public void setSonarpush(SonarPush sonarpush) {
		this.sonarpush = sonarpush;
	}
}
