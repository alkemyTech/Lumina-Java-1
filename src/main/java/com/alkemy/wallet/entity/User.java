package com.alkemy.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL", unique=true, nullable = false)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(name="CREATION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name="UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime updateDate;

    @Column(name = "SOFT_DELETE")
    private boolean softDelete = Boolean.FALSE;

}
