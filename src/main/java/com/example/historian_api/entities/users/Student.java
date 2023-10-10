package com.example.historian_api.entities.users;

import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.enums.Gender;
import com.example.historian_api.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 150, nullable = false)
    private String deviceSerial;

    @Column(unique = true, length = 50, nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private boolean haveSimCard;

    @Column(nullable = false)
    private String token;

    private String photoUrl;

    @OneToOne()
    @JoinColumn(name = "photo_id")
    private StudentImage studentImage;

//    @OneToMany(
//            mappedBy = "creator",
//            cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<Comment> comments = new HashSet<>();


//    @OneToMany(
//            mappedBy = "creator",
//            cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<Like> likes = new HashSet<>();


//    @OneToMany(
//            cascade = CascadeType.ALL,
//            mappedBy = "student",
//            fetch = FetchType.LAZY
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<EnrollmentCourses> enrollmentCourses = new ArrayList<>();


//    @OneToMany(
//            cascade = CascadeType.ALL,
//            mappedBy = "student",
//            fetch = FetchType.LAZY
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<StudentsWithQuizSolutions> quizSolutions = new ArrayList<>();


    public Student(String name,
                   String deviceSerial,
                   String phone,
                   Role role,
                   Gender gender,
                   boolean haveSimCard,
                   String token,
                   String photoUrl,
                   StudentImage studentImage) {
        this.name = name;
        this.deviceSerial = deviceSerial;
        this.phone = phone;
        this.role = role;
        this.gender = gender;
        this.haveSimCard = haveSimCard;
        this.token = token;
        this.photoUrl = photoUrl;
        this.studentImage = studentImage;
    }

    public Student(String name,
                   String deviceSerial,
                   String phone,
                   Role role,
                   Gender gender,
                   boolean haveSimCard,
                   String token) {
        this.name = name;
        this.deviceSerial = deviceSerial;
        this.phone = phone;
        this.role = role;
        this.gender = gender;
        this.haveSimCard = haveSimCard;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return "password";
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
                "id", getId(),
                "name", getName(),
                "phone", getPhone(),
                "serial", getDeviceSerial(),
                "haveSimCard", isHaveSimCard(),
                "gender", getGender()
        );
    }

    public static Student generateStudentFromRequestDto(
            RegisterStudentRequestDto dto,
            String photoUrl,
            StudentImage userImage){
        return new Student(
                dto.name(),
                dto.deviceSerial(),
                dto.phone(),
                Role.ROLE_USER,
                dto.gender(),
                dto.haveSimCard(),
                dto.token(),
                photoUrl,
                userImage
        );
    }
}
