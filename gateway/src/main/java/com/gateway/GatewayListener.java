package main.java.com.gateway;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GatewayListener {
    @RabbitListener(queues = "reserva.criacao")
    public void receberCriacaoReserva(String mensagem) {
        System.out.println("Mensagem recebida no API Gateway: " + mensagem);
    }
}
