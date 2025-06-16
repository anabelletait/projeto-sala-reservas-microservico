package com.reserva.application.amqp;

import com.reserva.domain.model.Reserva;
import com.reserva.infrastructure.repository.ReservaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservaCriacaoListener {
    @Autowired
    private ReservaRepository repository;

    @RabbitListener(queues = "reserva.criacao")
    public void receberCriacaoReserva(String mensagem) {
        // Espera mensagem no formato "usuarioId,salaId"
        try {
            String[] partes = mensagem.split(",");
            Long usuarioId = Long.parseLong(partes[0]);
            Long salaId = Long.parseLong(partes[1]);
            Reserva reserva = new Reserva();
            reserva.setUsuarioId(usuarioId);
            reserva.setSalaId(salaId);
            repository.save(reserva);
            System.out.println("Reserva criada via mensageria: Usuário " + usuarioId + ", Sala " + salaId);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem de criação de reserva: " + mensagem);
        }
    }
}
