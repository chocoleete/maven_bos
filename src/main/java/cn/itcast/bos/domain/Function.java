package cn.itcast.bos.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 2017/2/16.
 */
@Entity
@Table(name = "auth_function", schema = "", catalog = "zjs_bos")
public class Function {
    private String id;//编号
    private String name;//权限名称
    private String code;//关键字
    private String description;//描述
    private String page;//权限对应的访问url地址
    private String generatemenu = "1";//当前权限是否生成到菜单,1表示生成，0表示不生成
    private Integer zindex;//排序，保证菜单顺序
    private Function function;//当前权限的父类权限
    private Set<Function> functions=new HashSet<Function>(0);//当前权限的子类权限
    private Set<Role> roles = new HashSet<Role>(0);//当前权限对应的角色

    //加入pId属性和get方法
    private String pId;

    @Transient
    public String getpId() {
        if (function != null) {
            return function.getId();
        }
        return "0";
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

    @Basic
    @Column(name = "page")
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Basic
    @Column(name = "generatemenu")
    public String getGeneratemenu() {
        return generatemenu;
    }

    public void setGeneratemenu(String generatemenu) {
        this.generatemenu = generatemenu;
    }

    @Basic
    @Column(name = "zindex")
    public Integer getZindex() {
        return zindex;
    }

    public void setZindex(Integer zindex) {
        this.zindex = zindex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function function = (Function) o;

        if (code != null ? !code.equals(function.code) : function.code != null) return false;
        if (description != null ? !description.equals(function.description) : function.description != null)
            return false;
        if (generatemenu != null ? !generatemenu.equals(function.generatemenu) : function.generatemenu != null)
            return false;
        if (id != null ? !id.equals(function.id) : function.id != null) return false;
        if (name != null ? !name.equals(function.name) : function.name != null) return false;
        if (page != null ? !page.equals(function.page) : function.page != null) return false;
        if (zindex != null ? !zindex.equals(function.zindex) : function.zindex != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (generatemenu != null ? generatemenu.hashCode() : 0);
        result = 31 * result + (zindex != null ? zindex.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "id")
    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @OneToMany(mappedBy = "function")
    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }

    //需要自己加
    @ManyToMany/*(mappedBy = "function",targetEntity = Role.class)*/
    @JoinTable(name = "role_function"
            ,joinColumns = {@JoinColumn(name = "function_id",referencedColumnName = "id")}
            ,inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
