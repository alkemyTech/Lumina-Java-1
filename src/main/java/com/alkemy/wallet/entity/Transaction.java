package com.alkemy.wallet.entity;

import com.alkemy.wallet.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "transactions")
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TransactionTypeEnum type;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @Column(name="TRANSACTION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime transactionDate;

}
