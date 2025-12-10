package com.example.library_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "tbl_user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column (name = "create_at")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime update;

    //Add relationship
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BorrowEntity> borrows;


}
