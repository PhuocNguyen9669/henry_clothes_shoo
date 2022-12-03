package com.cg.model;

import com.cg.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id", nullable = false)
    private Role role;

//    private String phone;
//
//
//    @Column(name = "is_active",columnDefinition = "boolean default false")
//    private boolean isActive;
//
//    @Column(name = "url_image",nullable = false)
//    private String urlImage;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    @OneToOne
//    @JoinColumn(name = "location_region_id", nullable = false)
//    private LocationRegion locationRegion;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Order> orders;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Cart> carts;

    public UserDTO toUserDTO() {
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRoleDTO());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
