package cn.itcast.bos.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 定区
 * Created by lee on 2017/2/8.
 */
@Entity
@Table(name = "bc_decidedzone", schema = "", catalog = "zjs_bos")
public class Decidedzone {
    private String id;//编号
    private String name;//定区名称
    private Staff staff;//定区对应的取派员
    private Set<Subarea> subareas = new HashSet<Subarea>(0);//定区对应的多个分区

    public Decidedzone() {
    }

    public Decidedzone(String id) {
        this.id = id;
    }

    public Decidedzone(String id, String name, Staff staff, Set<Subarea> subareas) {
        this.id = id;
        this.name = name;
        this.staff = staff;
        this.subareas = subareas;
    }

    @Id
    /*@GeneratedValue(generator = "decidedzoneId")
    @GenericGenerator(name = "decidedzoneId", strategy = "uuid")//主键生成策略*/
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Decidedzone that = (Decidedzone) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @OneToMany(mappedBy = "decidedzone",fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Subarea.class)
    public Set<Subarea> getSubareas() {
        return subareas;
    }

    public void setSubareas(Set<Subarea> subareas) {
        this.subareas = subareas;
    }
}
