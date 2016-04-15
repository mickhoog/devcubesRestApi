package Main;

import javax.persistence.*;

@Entity
public class User {
    private int id;
    private String first_name;
    private String last_name;

    private String email;

    private GameInformation gameInformation;

    protected User() {}

    public User(String firstName, String lastName) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email, GameInformation gameInformation) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.gameInformation = gameInformation;
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

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Access(AccessType.PROPERTY)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameInformation_id")
    public GameInformation getGameInformation() {
        return gameInformation;
    }

    public void setGameInformation(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s', money='%d']",
                id, first_name, last_name, gameInformation.getMoney());
    }

}