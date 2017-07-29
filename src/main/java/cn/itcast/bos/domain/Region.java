package cn.itcast.bos.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 区域实体
 * Created by lee on 2017/2/8.
 */
@Entity
@Table(name = "bc_region", schema = "", catalog = "zjs_bos")
@SuppressWarnings(value = "all")
public class Region {
    private String id;//编号
    private String province;//省
    private String city;//市
    private String district;//区
    private String postcode;//编码
    private String shortcode;//简码
    private String citycode;//城市编码
    private Set<Subarea> subareas = new HashSet<Subarea>(0);//区域对应的多个分区

    public Region() {
    }

    public Region(String id) {
        this.id = id;
    }

    public Region(String id, String province, String city, String district, String postcode, String shortcode, String citycode, Set<Subarea> subareas) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.district = district;
        this.postcode = postcode;
        this.shortcode = shortcode;
        this.citycode = citycode;
        this.subareas = subareas;
    }

    public Region(String id, String province, String city, String district, String postcode) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.district = district;
        this.postcode = postcode;
    }

    /**
     * 为了能正常展示combobox，需要返回的json中包含有name字段,可以在Region实体类中
     * 添加一个getName的方法,但必须加一个@Transient注解，不然启动时会出现下面的报错
     * Context initialization failed
     * org.springframework.beans.factory.BeanCreationException:
     * Error creating bean with name 'sessionFactory' defined in class path
     * resource [applicationContext.xml]: Invocation of init method failed; nested
     * exception is org.hibernate.MappingException: Could not get constructor for
     * org.hibernate.persister.entity.SingleTableEntityPersister
     * @return
     * Transient：java语言的关键字，变量修饰符，如果用transient声明一个实例变量，当对象存储时，
     * 它的值不需要维持。换句话来说就是，用transient关键字标记的成员变量不参与序列化过程。
     */
    @Transient
    public String getName() {
        return province + city + district;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "postcode")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "shortcode")
    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @Basic
    @Column(name = "citycode")
    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        if (city != null ? !city.equals(region.city) : region.city != null) return false;
        if (citycode != null ? !citycode.equals(region.citycode) : region.citycode != null) return false;
        if (district != null ? !district.equals(region.district) : region.district != null) return false;
        if (id != null ? !id.equals(region.id) : region.id != null) return false;
        if (postcode != null ? !postcode.equals(region.postcode) : region.postcode != null) return false;
        if (province != null ? !province.equals(region.province) : region.province != null) return false;
        if (shortcode != null ? !shortcode.equals(region.shortcode) : region.shortcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (shortcode != null ? shortcode.hashCode() : 0);
        result = 31 * result + (citycode != null ? citycode.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "region", fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Subarea.class)
    public Set<Subarea> getSubareas() {
        return subareas;
    }

    public void setSubareas(Set<Subarea> subareas) {
        this.subareas = subareas;
    }
}
