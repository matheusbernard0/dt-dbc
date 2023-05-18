package br.com.dbc.pautaapi.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResultProducer {

    public final String TOPIC = "results";
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void writeMessage(String message) {
        log.info("enviando resultado para o topico de resultados");
        this.kafkaTemplate.send(TOPIC, message);
    }
}
