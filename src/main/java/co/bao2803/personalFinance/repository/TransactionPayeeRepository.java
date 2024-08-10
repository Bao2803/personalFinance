package co.bao2803.personalFinance.repository;

import co.bao2803.personalFinance.model.TransactionPayee;
import co.bao2803.personalFinance.model.TransactionPayeeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPayeeRepository extends JpaRepository<TransactionPayee, TransactionPayeeKey> {
}
