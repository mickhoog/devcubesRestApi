package Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {
    private int id;
    private String name;
    private Set<User> users;

    public Project(){

    }

    public Project(String name){
        this.name = name;
    }

    public Project(String name, Set<User> users){
        this.name = name;
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
