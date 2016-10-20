package com.example.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kasingj on 10/20/16.
 */

@Entity
public class Day {

    @Id @GeneratedValue
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCigs_smoked() {
        return cigs_smoked;
    }

    public void setCigs_smoked(Long cigs_smoked) {
        this.cigs_smoked = cigs_smoked;
    }

    public Long getCravings() {
        return cravings;
    }

    public void setCravings(Long cravings) {
        this.cravings = cravings;
    }

    public Long getCravings_resisted() {
        return cravings_resisted;
    }

    public void setCravings_resisted(Long cravings_resisted) {
        this.cravings_resisted = cravings_resisted;
    }

    @Column(name = "date")
    private Date date;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "cigs_smoked")
    private Long cigs_smoked;

    @Column(name="cravings")
    private Long cravings;

    @Column(name="cravings_resisted")
    private Long cravings_resisted;

}
