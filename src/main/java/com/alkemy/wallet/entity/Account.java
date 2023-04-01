package com.alkemy.wallet.entity;

import com.alkemy.wallet.enums.TypeCurrency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "accounts")
@SQLDelete(sql = "UPDATE accounts SET SOFT_DELETE = true WHERE id=?")
@Where(clause = "SOFT_DELETE=false")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CURRENCY", nullable = false)
    @Enumerated(value=EnumType.STRING)
    private TypeCurrency currency;
    @Column(name= "BALANCE", nullable = false)
    private Double balance;
    @Column(name = "SOFT_DELETE")
    private boolean softDelete = Boolean.FALSE;
    @Column(name = "TRANSACTION_LIMIT", nullable = false)
    private Double transactionLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name="CREATION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name="UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime updateDate;

    @JsonIgnoreProperties({"account"})
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Transaction> transactions= new ArrayList();

    @JsonIgnoreProperties({"account"})
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FixedTermDeposits> fixedTermDeposits = new ArrayList<>();
}
