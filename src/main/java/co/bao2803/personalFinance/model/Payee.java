package co.bao2803.personalFinance.model;

import co.bao2803.personalFinance.annotation.AtLeastOneNotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AtLeastOneNotNull
@Table(name = "payees")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Payee implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payee_id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "payee_name")
    private String name;

    @Column(name = "payee_email")
    private String email;

    @Column(name = "payee_phone")
    private String phone;

    @OneToMany(mappedBy = "payee", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<TransactionPayee> transactionPayees;
}
