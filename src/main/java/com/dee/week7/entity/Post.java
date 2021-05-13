package com.dee.week7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "post_sequence"
    )
    private Long postId;

    private String deleted;
    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    private int numofLikes;

    //    @OneToMany
//    @JoinTable(name = "posts",
//            joinColumns = {@JoinColumn(name = "MY_ENTITY_A_FK")},
//            inverseJoinColumns = {@JoinColumn(name = "MY_ENTITY_B_FK")}
//    )

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(Long postID, String body) {
        this.postId = postID;
        this.body = body;
    }

    public void addComment(Comment com){
        comment.add(com);
    }
    public void increaseLikes(){
        numofLikes++;
    }
}
