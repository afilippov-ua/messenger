package dao.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dao.contact.Contact;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy="contactUser")
    private Set<Contact> contacts;

    // default constructor
    public User(){};

    public User(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void loadValues(User sourceUser){

        setEmail(sourceUser.getEmail());
        setPassword(sourceUser.getPassword());
        setName(sourceUser.getName());
        setToken(sourceUser.getToken());

    }
}
