package cn.itcast.bos.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 2017/2/16.
 */
@Entity
@Table(name = "auth_role", schema = "", catalog = "zjs_bos")
public class Role {
    private String id;
    private String name;
    private String code;
    private String description;
    private Set<Function> functions = new HashSet<Function>(0);
    private Set<User> users = new HashSet<User>(0);

    @Id
    @GeneratedValue(generator = "roleId")
    @GenericGenerator(name = "roleId", strategy = "uuid")
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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (code != null ? !code.equals(role.code) : role.code != null) return false;
        if (description != null ? !description.equals(role.description) : role.description != null) return false;
        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    //需要自己添加
    @ManyToMany/*(mappedBy = "role",targetEntity = Function.class)*/
    @JoinTable(name = "role_function"
            ,joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}
            ,inverseJoinColumns = {@JoinColumn(name = "function_id",referencedColumnName = "id")})
    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }

    @ManyToMany/*(mappedBy = "role",targetEntity = User.class)*/
    @JoinTable(name = "user_role"
            ,joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}
            ,inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")})
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
