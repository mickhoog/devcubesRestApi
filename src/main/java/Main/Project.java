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

    public Project(){

    }

    public Project(String name, String description, Date start_date){
        this.name = name;
        this.description = description;
        this.start_date = start_date;
    }

    public Project(String name, String description, Date start_date, Set<User> users){
        this.name = name;
        this.description = description;
        this.start_date = start_date;
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
}
