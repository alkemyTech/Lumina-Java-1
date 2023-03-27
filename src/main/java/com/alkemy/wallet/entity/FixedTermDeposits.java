package com.alkemy.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class FixedTermDeposits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    private Long account_id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "INTEREST", nullable = false)
    private Double interest;

    @CreationTimestamp
    @Column(name="CREATION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name="UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime updateDate;

}
