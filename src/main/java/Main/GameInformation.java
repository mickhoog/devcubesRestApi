package Main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gameInformation")
public class GameInformation {


    private Integer id;
    private Integer money;
    private Integer productivity;
    private Integer likeability;
    private Integer pc_level;

    private User user;

    public GameInformation(){}

    public GameInformation(Integer money, Integer productivity, Integer likeability, Integer pc_level, User user){
        this.money = money;
        this.productivity = productivity;
        this.likeability = likeability;
        this.pc_level = pc_level;
        this.user = user;
    }

    @Id
    @Column(columnDefinition = "INT(11) UNSIGNED")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductivity() {
        return productivity;
    }

    public void setProductivity(Integer productivity) {
        this.productivity = productivity;
    }

    public Integer getPc_level() {
        return pc_level;
    }

    public void setPc_level(Integer pc_level) {
        this.pc_level = pc_level;
    }

    public Integer getLikeability() {
        return likeability;
    }

    public void setLikeability(Integer likeability) {
        this.likeability = likeability;
    }


    @Column(name = "money")
    public Integer getMoney() {
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }


    @OneToOne(mappedBy = "gameInformation")
    @JsonIgnoreProperties({"gameInformation", "projects", "password"})
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Integer changeProperty(Integer property, String kind, Integer amount){
        if(Objects.equals(kind, "add")){
            property += amount;
        }
        if(Objects.equals(kind, "subtract")){
            property -= amount;
        }
        return property;
    }


}
