package cn.itcast.bos.domain;

import javax.persistence.*;

/**
 * 分区实体
 * Created by lee on 2017/2/8.
 */
@Entity
@Table(name = "bc_subarea", schema = "", catalog = "zjs_bos")
@SuppressWarnings(value = "all")
public class Subarea {
    private String id;//编号
    private String addresskey;//地址关键字
    private String startnum;//开始编号
    private String endnum;//结束编号
    private String single;//单双号
    private String position;//详细地址
    private Decidedzone decidedzone;//当前分区对应的定区
    private Region region;//当前分区对应的区域

    public Subarea() {
    }

    public Subarea(String id) {
        this.id = id;
    }

    public Subarea(String id, String addresskey, String startnum, String endnum, String single, String position, Decidedzone decidedzone, Region region) {
        this.id = id;
        this.addresskey = addresskey;
        this.startnum = startnum;
        this.endnum = endnum;
        this.single = single;
        this.position = position;
        this.decidedzone = decidedzone;
        this.region = region;
    }

    /**
     * 需要在SubareaAction中的findSubareaByAjax方法返回的json数据中包含subareaid字段,
     * 在Subarea类中提供方法getSubareaid
     * @return
     */
    @Transient
    public String getSubareaid() {
        return this.id;
    }

    @Id
    /*@GeneratedValue(generator = "subareaId")
    @GenericGenerator(name = "subareaId", strategy = "uuid")//主键生成策略*/
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "addresskey")
    public String getAddresskey() {
        return addresskey;
    }

    public void setAddresskey(String addresskey) {
        this.addresskey = addresskey;
    }

    @Basic
    @Column(name = "startnum")
    public String getStartnum() {
        return startnum;
    }

    public void setStartnum(String startnum) {
        this.startnum = startnum;
    }

    @Basic
    @Column(name = "endnum")
    public String getEndnum() {
        return endnum;
    }

    public void setEndnum(String endnum) {
        this.endnum = endnum;
    }

    @Basic
    @Column(name = "single")
    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subarea subarea = (Subarea) o;

        if (addresskey != null ? !addresskey.equals(subarea.addresskey) : subarea.addresskey != null) return false;
        if (endnum != null ? !endnum.equals(subarea.endnum) : subarea.endnum != null) return false;
        if (id != null ? !id.equals(subarea.id) : subarea.id != null) return false;
        if (position != null ? !position.equals(subarea.position) : subarea.position != null) return false;
        if (single != null ? !single.equals(subarea.single) : subarea.single != null) return false;
        if (startnum != null ? !startnum.equals(subarea.startnum) : subarea.startnum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (addresskey != null ? addresskey.hashCode() : 0);
        result = 31 * result + (startnum != null ? startnum.hashCode() : 0);
        result = 31 * result + (endnum != null ? endnum.hashCode() : 0);
        result = 31 * result + (single != null ? single.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "decidedzone_id", referencedColumnName = "id")
    public Decidedzone getDecidedzone() {
        return decidedzone;
    }

    public void setDecidedzone(Decidedzone decidedzone) {
        this.decidedzone = decidedzone;
    }

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
