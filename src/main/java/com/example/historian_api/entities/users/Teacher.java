package com.example.historian_api.entities.users;

import com.example.historian_api.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "teachers")
public class Teacher implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_ADMIN;

//    @OneToMany(
//            mappedBy = "teacher",
//            cascade = CascadeType.ALL
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<Post> posts = new HashSet<>();

//    @OneToMany(
//            mappedBy = "teacher",
//            cascade = CascadeType.ALL
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Course> courses = new ArrayList<>();


    public Teacher(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    // TODO : uncomment that after creating Post and Course models
//    public Teacher(String name, String phone, Set<Post> posts, List<Course> courses) {
//        this.name = name;
//        this.phone = phone;
//        this.posts = posts;
//        this.courses = courses;
//    }

    public Teacher(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getClaims() {
        return Map.of(
                "id",id,
                "name",name,
                "phone",phone,
                "role",role.name()
        );
    }
}
