package co.bao2803.personalFinance.repository;

import co.bao2803.personalFinance.model.Payee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, UUID> {
    @Query(
            value = """
                    SELECT *, ts_rank(text_search_vector, to_tsquery('english', COALESCE(:keyword, '%'))) AS rank
                    FROM payees
                    WHERE text_search_vector @@ to_tsquery('english', COALESCE(:keyword, '%'))
                    ORDER BY rank DESC;
                    """,
            nativeQuery = true
    )
    Stream<Payee> searchAllByKeyword(@Param("keyword") String keyword);
}
