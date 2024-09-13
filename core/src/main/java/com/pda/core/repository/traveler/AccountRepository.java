package com.pda.core.repository.traveler;

import com.pda.core.entity.traveler.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsById(Long id);
    Optional<Account> findByTravelerId(Long id);
    boolean existsByAccountNumber(String accountNumber);
}
