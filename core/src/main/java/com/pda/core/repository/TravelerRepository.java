package com.pda.core.repository;

import com.pda.core.entity.Traveler;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    Optional<Traveler> findByNickname(String nickname);
    Boolean existsByNickname(String nickname);

    @Modifying
    @Query("UPDATE Traveler t SET t.stockballCount = t.stockballCount + :ball WHERE t.nickname = :nickname")
    void addStockball(@Param("nickname") String nickname, @Param("ball") Long ball);

    @Modifying
    @Query("UPDATE Traveler t SET t.stockballCount = t.stockballCount - :ball WHERE t.id = :id")
    void minusStockball(@Param("id") Long id, @Param("ball") Long ball);

    @Query("SELECT t.stockballCount FROM Traveler t WHERE t.id = :travelerId")
    Optional<Long> findStockballCountById(@Param("travelerId") Long travelerId);

}
