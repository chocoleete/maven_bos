package cn.itcast.bos.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User对象
 * Created by lee on 2017/2/5.
 */
@Entity
@Table(name = "t_user")
@NamedQueries({@NamedQuery(name = "findByUsernameAndpassword",query = "FROM User WHERE username=? AND password=?"),
        @NamedQuery(name = "editPassword",query = "UPDATE User SET password=? WHERE id=?")})
public class User {
    private String id;
    private String username;
    private String password;
    private Double salary;
    private Date birthday;
    private String gender;
    private String station;
    private String telephone;
    private String remark;
    private Set<Noticebill> noticebills=new HashSet<Noticebill>(0);
    private Set<Role> roles = new HashSet<Role>(0);

    //解决页面显示生日Object的问题
    @Transient
    public String getBirthdayStr() {
        if (birthday != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(birthday);
        }
        return "";
    }

    @Id
    @GeneratedValue(generator = "userId")
    @GenericGenerator(name = "userId", strategy = "uuid")//主键生成策略
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "salary")
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "station")
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (remark != null ? !remark.equals(user.remark) : user.remark != null) return false;
        if (salary != null ? !salary.equals(user.salary) : user.salary != null) return false;
        if (station != null ? !station.equals(user.station) : user.station != null) return false;
        if (telephone != null ? !telephone.equals(user.telephone) : user.telephone != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (station != null ? station.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "user")
    public Set<Noticebill> getNoticebills() {
        return noticebills;
    }

    public void setNoticebills(Set<Noticebill> noticebills) {
        this.noticebills = noticebills;
    }

    @ManyToMany/*(mappedBy = "user")*/
    @JoinTable(name = "user_role"
            ,joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}
            ,inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

/*//成员变量
    @Id
    @GeneratedValue(generator = "userId")
    @GenericGenerator(name = "userId", strategy = "uuid")//主键生成策略
    @Column
    private String id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Double salary;
    @Column
    private Date birthday;
    @Column
    private String gender;
    @Column
    private String station;
    @Column
    private String telephone;
    @Column
    private String remark;
    @Transient
    private Set<Noticebill> noticebills;

    //成员方法
    public User() {
    }

    public User(Date birthday, String gender, Set<Noticebill> noticebills, String password, String remark, Double salary, String station, String telephone, String username) {
        this.birthday = birthday;
        this.gender = gender;
        this.noticebills = noticebills;
        this.password = password;
        this.remark = remark;
        this.salary = salary;
        this.station = station;
        this.telephone = telephone;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(mappedBy = "user")
    public Set<Noticebill> getNoticebills() {
        return noticebills;
    }

    public void setNoticebills(Set<Noticebill> noticebills) {
        this.noticebills = noticebills;
    }*/
}
