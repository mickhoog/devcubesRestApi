package Main;

import javax.persistence.*;

@Entity
public class Issue{

	private int id;
	private SonarPush sonarPush;
	private String severity;
	private String component;
	private String message;
	private String debt;
	
	public Issue(){}

	public Issue(String severity, String component, String message, String debt, SonarPush sonarPush) {
		this.sonarPush = sonarPush;
		this.severity = severity;
		this.component = component;
		this.message = message;
		this.debt = debt;
	}

	@Id
	@Column(columnDefinition = "INT(11) UNSIGNED")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	@ManyToOne
	public SonarPush getSonarPush() {return sonarPush;}
	public void setSonarPush(SonarPush sonarPush) {this.sonarPush = sonarPush;}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebt() {
		return debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	@Override
	public String toString() {
		return "Issue{" +
				"id=" + id +
				", sonarPush=" + sonarPush +
				", severity='" + severity + '\'' +
				", component='" + component + '\'' +
				", message='" + message + '\'' +
				", debt='" + debt + '\'' +
				'}';
	}
}
