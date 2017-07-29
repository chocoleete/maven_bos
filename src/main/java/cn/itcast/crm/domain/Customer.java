package cn.itcast.crm.domain;

import javax.persistence.*;

/**
 * Created by lee on 2017/2/13.
 */
@Entity
@Table(name = "t_customer", schema = "", catalog = "crm")
public class Customer {
    private int id;
    private String name;
    private String station;
    private String telephone;
    private String address;
    private String decidedzoneId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "decidedzone_id")
    public String getDecidedzoneId() {
        return decidedzoneId;
    }

    public void setDecidedzoneId(String decidedzoneId) {
        this.decidedzoneId = decidedzoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (address != null ? !address.equals(customer.address) : customer.address != null) return false;
        if (decidedzoneId != null ? !decidedzoneId.equals(customer.decidedzoneId) : customer.decidedzoneId != null)
            return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (station != null ? !station.equals(customer.station) : customer.station != null) return false;
        if (telephone != null ? !telephone.equals(customer.telephone) : customer.telephone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (station != null ? station.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (decidedzoneId != null ? decidedzoneId.hashCode() : 0);
        return result;
    }
}
