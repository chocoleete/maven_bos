package cn.itcast.bos.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 取派员实体
 * Created by lee on 2017/2/8.
 */
@Entity
@Table(name = "bc_staff", schema = "", catalog = "zjs_bos")
public class Staff {
    private String id;//编号
    private String name;//姓名
    private String telephone;//手机号
    private String haspda = "0";//是否有pda 0为否 1为是
    private String deltag = "0";//删除标识 0为未删除 1为已删除
    private String station;//单位
    private String standard;//收派标准
    private Set<Decidedzone> decidedzones = new HashSet<Decidedzone>(0);//取派员对应的定区
    private Set<Noticebill> noticebills=new HashSet<Noticebill>(0);
    private Set<Workbill> workbills=new HashSet<Workbill>(0);

    public Staff() {
    }

    public Staff(String id) {
        this.id = id;
    }

    public Staff(Set<Decidedzone> decidedzones, String deltag, String haspda, String id, String name, Set<Noticebill> noticebills, String standard, String station, String telephone, Set<Workbill> workbills) {
        this.decidedzones = decidedzones;
        this.deltag = deltag;
        this.haspda = haspda;
        this.id = id;
        this.name = name;
        this.noticebills = noticebills;
        this.standard = standard;
        this.station = station;
        this.telephone = telephone;
        this.workbills = workbills;
    }

    @Id
    /*@GeneratedValue(generator = "staffId")
    @GenericGenerator(name = "staffId", strategy = "uuid")//主键生成策略*/
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "haspda")
    public String getHaspda() {
        return haspda;
    }

    public void setHaspda(String haspda) {
        this.haspda = haspda;
    }

    @Basic
    @Column(name = "deltag")
    public String getDeltag() {
        return deltag;
    }

    public void setDeltag(String deltag) {
        this.deltag = deltag;
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
    @Column(name = "standard")
    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Staff staff = (Staff) o;

        if (deltag != null ? !deltag.equals(staff.deltag) : staff.deltag != null) return false;
        if (haspda != null ? !haspda.equals(staff.haspda) : staff.haspda != null) return false;
        if (id != null ? !id.equals(staff.id) : staff.id != null) return false;
        if (name != null ? !name.equals(staff.name) : staff.name != null) return false;
        if (standard != null ? !standard.equals(staff.standard) : staff.standard != null) return false;
        if (station != null ? !station.equals(staff.station) : staff.station != null) return false;
        if (telephone != null ? !telephone.equals(staff.telephone) : staff.telephone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (haspda != null ? haspda.hashCode() : 0);
        result = 31 * result + (deltag != null ? deltag.hashCode() : 0);
        result = 31 * result + (station != null ? station.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        return result;
    }

    /**
     * 项目运行过程中出现
     * failed to lazily initialize a collection of role:no session or session was closed 的问题
     * 如果在web.xml中配置了延迟加载还是报错，就在OneToMany中加一个fetch = FetchType.EAGER
     *
     * @return
     */
    @OneToMany(mappedBy = "staff", fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Decidedzone.class)
    public Set<Decidedzone> getDecidedzones() {
        return decidedzones;
    }

    public void setDecidedzones(Set<Decidedzone> decidedzones) {
        this.decidedzones = decidedzones;
    }

    @OneToMany(mappedBy = "staff")
    public Set<Noticebill> getNoticebills() {
        return noticebills;
    }

    public void setNoticebills(Set<Noticebill> noticebills) {
        this.noticebills = noticebills;
    }

    @OneToMany(mappedBy = "staff")
    public Set<Workbill> getWorkbills() {
        return workbills;
    }

    public void setWorkbills(Set<Workbill> workbills) {
        this.workbills = workbills;
    }
}
