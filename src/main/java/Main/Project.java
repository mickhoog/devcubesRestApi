package Main;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Project {
    private int id;
    private String name;
    private String description;
    private Date start_date;
    private Set<User> users;
    private Set<SonarPush> sonarPushes;
    private Set<Issue> issues;

    public Project(){

    }

    public Project(String name, String description, Date start_date){
        this.name = name;
        this.description = description;
        this.start_date = start_date;
    }

    public Project(String name, String description) {
    	this.name = name;
    	this.description = description;
    }
    
    public Project(String name, String description, Date start_date, Set<User> users){
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.users = users;
    }

    @Id
    @Column(columnDefinition = "INT(11) UNSIGNED")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "LONGTEXT")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    @JsonIgnoreProperties({"projects"})
    @ManyToMany(mappedBy = "projects")
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    public void addUser(User user){ this.users.add(user); }

    @JsonIgnoreProperties({"project"})
    @OneToMany(mappedBy = "project")
    public Set<SonarPush> getSonarPushes() {
        return sonarPushes;
    }
    public void setSonarPushes(Set<SonarPush> sonarPushes) {
        this.sonarPushes = sonarPushes;
    }
    
    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties({"projects","project","issues"})
    public Set<Issue> getIssues() {
    	return issues;
    }
    
    public void setIssues(Set<Issue> issues) {
    	this.issues = issues;
    }

}
