package Main;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String username;

    private GameInformation gameInformation;
    private Set<Project> projects;

    protected User() {}

    public User(String firstName, String lastName, String email, String password, String username) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String firstName, String lastName, String email, String password, String username, GameInformation gameInformation, Set<Project> projects) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.gameInformation = gameInformation;
        this.projects = projects;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() { return last_name;}

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Column(unique = true)
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String password){
        return this.password == password;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameInformation_id")
    @JsonIgnoreProperties({"user"})
    public GameInformation getGameInformation() {
        return gameInformation;
    }
    public void setGameInformation(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
    }


    @JsonIgnoreProperties({"users", "sonarPushes", "issues"})
    @ManyToMany()
    @JoinTable(name = "project_user", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    public Set<Project> getProjects() { return projects; }
    public void setProjects(Set<Project> projects) { this.projects = projects; }
    public void addProject(Project project){ this.projects.add(project); }

}