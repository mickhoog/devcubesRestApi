package Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class SonarPush {

	private int id;
	private int totalIssues;
	private Project project;
	private User user;
	private Date date;

	
	protected SonarPush()
	{}
	
	public SonarPush(int id, int totalIssues, Project project, User user, Date date) {
		this.id = id;
		this.totalIssues = totalIssues;
		this.project = project;
		this.user = user;
		this.date = date;
	}
	
	@Id 
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalIssues() {
		return totalIssues;
	}
	public void setTotalIssues(int totalIssues) {
		this.totalIssues = totalIssues;
	}

	@ManyToOne
	@JsonIgnoreProperties({"users", "sonarPushes"})
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	@JsonIgnoreProperties({"projects", "gameInformation"})
	@OneToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
