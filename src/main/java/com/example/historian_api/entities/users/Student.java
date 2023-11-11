package com.example.historian_api.entities.users;

import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.entities.complaints.Complaint;
import com.example.historian_api.entities.courses.*;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizResult;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuizResult;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionResult;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.feedbacks.Feedback;
import com.example.historian_api.entities.posts.Bookmark;
import com.example.historian_api.entities.posts.Comment;
import com.example.historian_api.entities.posts.Like;
import com.example.historian_api.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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
    private String token;

    private String photoUrl;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "photo_id")
    private StudentImage studentImage;


    @OneToMany(
            mappedBy = "creator",
            cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Comment> comments = new HashSet<>();


    @OneToMany(
            mappedBy = "creator",
            cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(
            mappedBy = "creator",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Complaint> complaints = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "class_id")
    private StudentGrade studentGrade;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<SubscribedSemester> subscribedSemesters = new ArrayList<>();

// TODO : will remove this !!
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<EnrollmentCourse> enrollmentCourses = new ArrayList<>();


    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FinalRevisionResult> finalRevisionResults = new ArrayList<>();


    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<LessonQuizResult> quizResults = new ArrayList<>();


    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<VideoComment> videoComments = new ArrayList<>();


    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeQuizResult> gradeQuizResults = new ArrayList<>();

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Feedback> feedbacks = new ArrayList<>();


    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeQuizQuestionSolution> gradesQuestionsSolutions = new ArrayList<>();

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<LessonQuestionSolution> lessonQuestionSolutions = new ArrayList<>();

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FinalRevisionQuestionSolution> finalRevisionQuestionSolutions = new ArrayList<>();



    public Student(String name,
                   String deviceSerial,
                   String phone,
                   Role role,
                   String token,
                   String photoUrl,
                   StudentImage studentImage,
                   StudentGrade grade) {
        this.name = name;
        this.deviceSerial = deviceSerial;
        this.phone = phone;
        this.role = role;
        this.token = token;
        this.photoUrl = photoUrl;
        this.studentImage = studentImage;
        this.studentGrade = grade;
    }

    public Student(String name,
                   String deviceSerial,
                   String phone,
                   Role role,
                   String token) {
        this.name = name;
        this.deviceSerial = deviceSerial;
        this.phone = phone;
        this.role = role;
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
                "serial", getDeviceSerial()
        );
    }

    public static Student generateStudentFromRequestDto(
            RegisterStudentRequestDto dto,
            String photoUrl,
            StudentImage userImage,
            StudentGrade grade) {
        return new Student(
                dto.name(),
                dto.deviceSerial(),
                dto.phone(),
                Role.ROLE_USER,
                dto.token(),
                photoUrl,
                userImage,
                grade
        );
    }
}
