package com.alkemy.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CURRENCY", nullable = false)
    @Enumerated(value=EnumType.STRING)
    private TypeCurrency currency;
    @Column(name= "BALANCE")
    private Double balance;
    @Column(name = "SOFT_DELETE")
    private boolean softDelete = Boolean.FALSE;
    @Column(name = "TRANSACTIONLIMIT")
    private Double transactionLimit;

  /*  @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private Users userId;

  */

    @CreationTimestamp
    @Column(name="CREATION_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name="UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime updateDate;

   /* public Accounts(Integer id, TypeCurrency currency, Double balance, boolean softDelete, Users userId) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.softDelete = softDelete;
        this.userId = userId;
    }

   */
}
