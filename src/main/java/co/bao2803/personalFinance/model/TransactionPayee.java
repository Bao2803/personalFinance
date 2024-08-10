package co.bao2803.personalFinance.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_payee")
@IdClass(TransactionPayeeKey.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionPayee implements Serializable {
    @Id
    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", updatable = false, insertable = false)
    private Transaction transaction;

    @Id
    @Column(name = "payee_id", nullable = false)
    private UUID payeeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payee_id", updatable = false, insertable = false)
    private Payee payee;

    @Column(name = "amount", nullable = false)
    private Double amount = 0D;

    @PrePersist
    public void prePersist() {
        if (transaction != null) {
            this.transactionId = transaction.getId();
        }
    }
}
