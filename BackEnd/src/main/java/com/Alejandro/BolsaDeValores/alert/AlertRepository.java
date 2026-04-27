package com.Alejandro.BolsaDeValores.alert;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertModel, Long> {
    
    List<AlertModel> findByUserId(Long userId);
    
    Page<AlertModel> findByUserId(Long userId, Pageable pageable);
    
    @Query("SELECT a FROM AlertModel a WHERE a.user.id = :userId AND a.active = true")
    List<AlertModel> findActiveByUserId(@Param("userId") Long userId);
    
    @Query("SELECT a FROM AlertModel a WHERE a.active = true")
    List<AlertModel> findAllActive();
    
    @Query("SELECT a FROM AlertModel a WHERE a.ticker = :ticker AND a.condition_type = :conditionType AND a.active = true")
    List<AlertModel> findByTickerAndCondition(
            @Param("ticker") String ticker,
            @Param("conditionType") AlertConditionType conditionType
    );
    
    @Query("SELECT DISTINCT a.ticker FROM AlertModel a WHERE a.active = true")
    List<String> findAllActiveTickers();
    }


