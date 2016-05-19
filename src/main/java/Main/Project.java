package Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Project {
    private int id;
    private String name;
    private String description;
    private Date start_date;
    private Set<User> users;
    private List<SonarPush> sonarPushes;
    private String sonarName;

    public Project(){

    }

    public Project(String name, String description, Date start_date, String sonarName){
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.sonarName = sonarName;
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


    public String getSonarName() {
        return sonarName;
    }

    public void setSonarName(String sonarName) {
        this.sonarName = sonarName;
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
    public List<SonarPush> getSonarPushes() {
        return sonarPushes;
    }
    public void setSonarPushes(List<SonarPush> sonarPushes) {
        this.sonarPushes = sonarPushes;
    }
    

}
