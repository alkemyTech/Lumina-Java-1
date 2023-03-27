package com.alkemy.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    @Column(name = "TYPE", nullable = false)
    private TransactionType type;
    @Column(name = "DESCRIPTION")
    private String description;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;*/

    @Column(name="TRANSACTION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime transactionDate;


}
