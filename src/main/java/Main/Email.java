package Main;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Email {

    private int id;
    private int userId;
    private String description;
    private String subject;
    private Long date;
    private String sender;

    public Email(){}

    public Email(int id) {
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", subject='" + subject + '\'' +
                ", date=" + date +
                ", sender='" + sender + '\'' +
                '}';
    }
}
