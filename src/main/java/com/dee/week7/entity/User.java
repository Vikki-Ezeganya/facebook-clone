package com.dee.week7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "user_sequence"
    )
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    private String gender;
    //@OneToMany(cascade=ALL, mappedBy="ReleaseDateType")

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Post> posts;

    public User(String firstName, String lastName, String email, String password, String birthdate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public void addPostToList(Post post) {
        if (post != null) {
            if (posts == null) {
                posts = new ArrayList<>();
            }
            post.setUser(this);
            posts.add(post);
        }

    }
}
