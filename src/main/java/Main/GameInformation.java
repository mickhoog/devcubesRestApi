package Main;

import javax.persistence.*;

@Entity
@Table(name = "gameInformation")
public class GameInformation {


    private Integer id;
    private Integer money;
    private Integer productivity;
    private Integer likeability;
    private Integer pc_level;

    public GameInformation(){}

    public GameInformation(Integer money, Integer productivity, Integer likeability, Integer pc_level){
        this.money = money;
        this.productivity = productivity;
        this.likeability = likeability;
        this.pc_level = pc_level;
    }

    @Id
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

}
