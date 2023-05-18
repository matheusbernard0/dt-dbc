package br.com.dbc.pautaapi.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ResultConsumer {
    @KafkaListener(topics = "results", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info(String.format("Recebendo resultado da votacao: %s", message));
    }
}
