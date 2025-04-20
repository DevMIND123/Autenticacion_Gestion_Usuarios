package com.autenticacion.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; 

    private final String TOPIC = "usuario-creado";

    public void enviarMensaje(String mensaje) {
        kafkaTemplate.send(TOPIC, mensaje);
        System.out.println("📤 Evento enviado a Kafka: " + mensaje);
    }
}
