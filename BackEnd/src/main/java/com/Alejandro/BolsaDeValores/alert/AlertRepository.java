package com.Alejandro.BolsaDeValores.alert;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertModel, Long> {

    List<AlertModel> findByUser_Id(Long userId);
    List<AlertModel> findByUser_IdAndActiveTrue(Long userId);

    @Query("SELECT a FROM AlertModel a WHERE a.user.id = :user_id AND a.active = TRUE")
    List<AlertModel> findActiveAlerts(@Param("user_id") Long userId);

    @Query("SELECT a FROM AlertModel a WHERE a.ticker = :ticker AND a.condition_type = :condition_type AND a.active = true")
    List<AlertModel> findByTickerAndCondition(
            @Param("ticker") String ticker,
            @Param("condition_type") AlertConditionType conditionType
    );
}