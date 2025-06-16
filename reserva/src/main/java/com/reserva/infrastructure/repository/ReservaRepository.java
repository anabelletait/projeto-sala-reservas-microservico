package com.reserva.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.domain.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {}