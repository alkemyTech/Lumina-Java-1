package com.alkemy.wallet.entity;

import com.alkemy.wallet.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "NAME",nullable = false)
    private RoleEnum name;
    @Column(name = "DESCRIPTION", nullable = true)
    private String description;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users= new HashSet<>();

    @CreationTimestamp
    @Column(name="CREATION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name="UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime updateDate;
}
