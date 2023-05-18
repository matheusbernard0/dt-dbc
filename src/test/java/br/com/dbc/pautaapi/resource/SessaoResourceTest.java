package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Sessao;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.SessaoRepository;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SessaoResource.class)
public class SessaoResourceTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PautaRepository pautaRepository;
    @MockBean
    private SessaoRepository sessaoRepository;
    @Autowired
    protected MockMvc mvc;

    @BeforeAll
    public static void before() {
        FixtureFactoryLoader.loadTemplates("br.com.dbc.pautaapi.templates");
    }

    @Test
    @SneakyThrows
    public void mustThrowPautaNaoEncontradaException() {
        CriaSessaoRequest request = Fixture.from(CriaSessaoRequest.class).gimme("VALID");
        when(pautaRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        mvc.perform(patch("/sessao/open/1" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("pauta nao encontrada"));
    }

    @Test
    @SneakyThrows
    public void mustThrowSessaoJaIniciadaException() {
        CriaSessaoRequest request = Fixture.from(CriaSessaoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_EM_VOTACAO");
        when(pautaRepository.findById(any(Integer.class))).thenReturn(Optional.of(pauta));

        mvc.perform(patch("/sessao/open/1" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("sessao ja iniciada"));
    }

    @Test
    @SneakyThrows
    public void mustCreateSession() {
        CriaSessaoRequest request = Fixture.from(CriaSessaoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("VALID");
        Sessao sessao = Fixture.from(Sessao.class).gimme("ABERTO");

        when(pautaRepository.findById(any(Integer.class))).thenReturn(Optional.of(pauta));
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

        mvc.perform(patch("/sessao/open/1" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.aberta").value(true));
    }
}
