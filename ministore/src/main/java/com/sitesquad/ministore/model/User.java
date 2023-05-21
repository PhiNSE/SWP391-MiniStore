package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ADMIN
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tbluser")
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @ToString.Exclude
    @ManyToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private int roleId;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        User user = (User) o;
//        return Objects.equals(id, user.id);
//    }
//
//    //hashCode
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

    @OneToMany(mappedBy = "orderUser")
    @JsonManagedReference
    @ToString.Exclude
    private Collection<Order> orders;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private Collection<Payslip> payslips;
}
