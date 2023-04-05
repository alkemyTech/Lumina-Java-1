package com.alkemy.wallet.entity;

import com.alkemy.wallet.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Enumerated(EnumType.STRING)
    @Column(name = "NAME",nullable = false)
    private RoleEnum name;
    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @CreationTimestamp
    @Column(name="CREATION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name="UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime updateDate;
}
