package co.bao2803.personalFinance.repository;

import co.bao2803.personalFinance.model.Payee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface PayeeRepository extends JpaRepository<Payee, UUID> {
    Stream<Payee> findAllByEmailLikeIgnoreCaseAndPhoneLikeIgnoreCaseAndNameLikeIgnoreCase(
            final String email,
            final String phone,
            final String name
    );
}
