package br.com.dbc.pautaapi.worker;

import br.com.dbc.pautaapi.dto.response.ResultadoPautaResponse;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.mapper.PautaMapper;
import br.com.dbc.pautaapi.pubsub.ResultadoProducer;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.SessaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class FinalizaSessaoWorker {
    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final ResultadoProducer resultadoProducer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    @SneakyThrows
    public void scheduleFixedRateWithInitialDelayTask() {
        List<Pauta> finishePautas = pautaRepository.getFinishePautas(LocalDateTime.now());
        for (Pauta pauta : finishePautas) {
            pauta.getSessao().setAberta(Boolean.FALSE);
            sessaoRepository.save(pauta.getSessao());
            ResultadoPautaResponse resultadoPautaResponse = PautaMapper.INSTANCE
                    .pautoToResultadoPautaResponse(pauta, new ResultadoPautaResponse());
            resultadoProducer.writeMessage(objectMapper.writeValueAsString(resultadoPautaResponse));
        }
    }

}
