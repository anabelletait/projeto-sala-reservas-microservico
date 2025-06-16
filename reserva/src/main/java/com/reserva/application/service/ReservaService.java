package com.reserva.application.service;
import com.reserva.application.dto.ReservaDTO;
import com.reserva.application.dto.SalaDTO;
import com.reserva.application.dto.UsuarioDTO;
import com.reserva.domain.model.Reserva;
import com.reserva.infrastructure.repository.ReservaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    @Autowired
    private RestTemplate restTemplate; 

    public List<Reserva> listar() {
        return repository.findAll();
    }

    public ReservaDTO getReservaCompleta(Long reservaId) {
        Reserva reserva = repository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        UsuarioDTO usuario = restTemplate.getForObject(
                "http://usuario:8082/usuarios/" + reserva.getUsuarioId(), UsuarioDTO.class);

        SalaDTO sala = restTemplate.getForObject(
                "http://sala:8082/salas/" + reserva.getSalaId(), SalaDTO.class);

        return new ReservaDTO(reserva, usuario, sala);
    }

    public Reserva salvar(Reserva reserva) {
        return repository.save(reserva);
    }
}