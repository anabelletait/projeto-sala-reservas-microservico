package com.sala.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sala.domain.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {}
