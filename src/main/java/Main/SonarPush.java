package Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class SonarPush {

	private int id;
	private Project project;
	private User user;
	private Timestamp date;
	private List<Issue> issues;
	private Double overallComplexity;
	private Double fileComplexity;
	private Double classComplexity;
	private Double functionComplexity;
	private Double technicalDebt;


	public SonarPush(){}

	public SonarPush(Double technicalDebt, int id, Project project, User user, Timestamp date, Double overallComplexity, Double fileComplexity, Double classComplexity, Double functionComplexity) {
		this.technicalDebt = technicalDebt;
		this.id = id;
		this.project = project;
		this.user = user;
		this.date = date;
		this.overallComplexity = overallComplexity;
		this.fileComplexity = fileComplexity;
		this.classComplexity = classComplexity;
		this.functionComplexity = functionComplexity;
	}

	@Override
	public String toString() {
		return "SonarPush{" +
				"id=" + id +
				", project=" + project +
				", user=" + user +
				", date=" + date +
				", issues=" + issues +
				", overallComplexity=" + overallComplexity +
				", fileComplexity=" + fileComplexity +
				", classComplexity=" + classComplexity +
				", functionComplexity=" + functionComplexity +
				", technicalDebt=" + technicalDebt +
				'}';
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "sonarPush")
	@JsonIgnoreProperties({"sonarPush"})
	public List<Issue> getIssues() {return issues;}
	public void setIssues(List<Issue> issues) {this.issues = issues;}
	public void addIssue(Issue issue){this.issues.add(issue);}

	@ManyToOne
	@JsonIgnoreProperties({"users", "sonarPushes"})
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}


	@ManyToOne
	@JsonIgnoreProperties({"projects", "gameInformation"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Double getOverallComplexity() {
		return overallComplexity;
	}

	public void setOverallComplexity(Double overallComplexity) {
		this.overallComplexity = overallComplexity;
	}

	public Double getFileComplexity() {
		return fileComplexity;
	}

	public void setFileComplexity(Double fileComplexity) {
		this.fileComplexity = fileComplexity;
	}

	public Double getClassComplexity() {
		return classComplexity;
	}

	public void setClassComplexity(Double classComplexity) {
		this.classComplexity = classComplexity;
	}

	public Double getFunctionComplexity() {
		return functionComplexity;
	}

	public void setFunctionComplexity(Double functionComplexity) {
		this.functionComplexity = functionComplexity;
	}

	public Double getTechnicalDebt() {
		return technicalDebt;
	}

	public void setTechnicalDebt(Double technicalDebt) {
		this.technicalDebt = technicalDebt;
	}
}
