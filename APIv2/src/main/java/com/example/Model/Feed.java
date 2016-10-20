package com.example.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by kasingj on 10/20/16.
 */
@Entity
public class Feed {

    @Id @GeneratedValue
    private Long Id;

    @Column(name="posted_by")
    private Long posted_by;

    @Column(name="description")
    private String description;

    @Column(name="date")
    private Date date;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "likes")
    private Long dislikes;
    
}
