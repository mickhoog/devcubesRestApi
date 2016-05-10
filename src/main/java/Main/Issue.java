package Main;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

@Entity
public class Issue extends SonarResult{

	private String severity;
	private String component;
	private String message;
	private String debt;
	private Date date;
	private Project project;
	private User user;
	
	protected Issue() 
	{}

	public Issue(String id, String severity, String component, String message,
			String debt, Date date, Project project, User user) {
		super();
		this.id = id;
		this.severity = severity;
		this.component = component;
		this.message = message;
		this.debt = debt;
		this.date = date;
		this.project = project;
		this.user = user;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "project_id")
	@JsonIgnoreProperties({"issues"})
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
