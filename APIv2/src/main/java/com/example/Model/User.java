package com.example.Model;



import javax.persistence.*;

/**
 * Created by kasingj on 10/19/16.
 */
@Entity
@Table(name="User")
public class User {

    /*    NOTES:
    *
    *
    *
    * */

    //constructor
    public User(){

    }

    @Id @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="cigs_per_day")
    private Integer cigs_per_day;

    @Column(name="time")
    private String time;

    @Column(name="money_saved")
    private Integer money_saved;

    @Column(name="life_regained")
    private Integer life_regained;

    @Column(name="days_free")
    private Integer days_free;

    @Column(name="price_per_pack")
    private Double price_per_pack;

    @Column(name="cravings_resisted")
    private Integer cravings_resisted;

    @Column(name="current_streak")
    private Integer current_streak;

    @Column(name="longest_streak")
    private Integer longest_streak;

    @Column(name="cigs_not_smoked")
    private Integer cigs_not_smoked;

    @Column(name="buddy_id")
    private Integer buddy_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCigs_per_day() {
        return cigs_per_day;
    }

    public void setCigs_per_day(Integer cigs_per_day) {
        this.cigs_per_day = cigs_per_day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMoney_saved() {
        return money_saved;
    }

    public void setMoney_saved(Integer money_saved) {
        this.money_saved = money_saved;
    }

    public Integer getLife_regained() {
        return life_regained;
    }

    public void setLife_regained(Integer life_regained) {
        this.life_regained = life_regained;
    }

    public Integer getDays_free() {
        return days_free;
    }

    public void setDays_free(Integer days_free) {
        this.days_free = days_free;
    }

    public Double getPrice_per_pack() {
        return price_per_pack;
    }

    public void setPrice_per_pack(Double price_per_pack) {
        this.price_per_pack = price_per_pack;
    }

    public Integer getCravings_resisted() {
        return cravings_resisted;
    }

    public void setCravings_resisted(Integer cravings_resisted) {
        this.cravings_resisted = cravings_resisted;
    }

    public Integer getCurrent_streak() {
        return current_streak;
    }

    public void setCurrent_streak(Integer current_streak) {
        this.current_streak = current_streak;
    }

    public Integer getLongest_streak() {
        return longest_streak;
    }

    public void setLongest_streak(Integer longest_streak) {
        this.longest_streak = longest_streak;
    }

    public Integer getCigs_not_smoked() {
        return cigs_not_smoked;
    }

    public void setCigs_not_smoked(Integer cigs_not_smoked) {
        this.cigs_not_smoked = cigs_not_smoked;
    }

    public Integer getBuddy_id() {
        return buddy_id;
    }

    public void setBuddy_id(Integer buddy_id) {
        this.buddy_id = buddy_id;
    }
// will be a relationship *********************
//    @Column(name="buddy")
//    private Integer buddy;

//    //has a relationship *********
//    @Column(name="days")
//    private String days;


}
