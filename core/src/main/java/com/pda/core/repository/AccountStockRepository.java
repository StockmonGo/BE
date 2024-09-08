package com.pda.core.repository;

import com.pda.core.entity.AccountStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, Long> {

    Boolean existsAccountStockByAccountIdAndStockId(Long accountId, Long stockId);

    @Modifying
    @Query("UPDATE AccountStock acs SET acs.stockCount = acs.stockCount + :stockCount " +
            "WHERE acs.account.id = :accountId and acs.stock.id = :stockId")
    void updateStock(@Param("accountId") Long accountId, @Param("stockId") Long stockId, @Param("stockCount") Double stockCount);

}
