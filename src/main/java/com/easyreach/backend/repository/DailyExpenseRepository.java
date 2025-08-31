package com.easyreach.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyreach.backend.entity.DailyExpense;

public interface DailyExpenseRepository extends JpaRepository<DailyExpense, String> {
}
