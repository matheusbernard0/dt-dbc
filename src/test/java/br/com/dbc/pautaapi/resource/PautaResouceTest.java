package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PautaResource.class)
public class PautaResouceTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PautaRepository pautaRepository;

    @Autowired
    protected MockMvc mvc;

    @BeforeAll
    public static void before() {
        FixtureFactoryLoader.loadTemplates("br.com.dbc.pautaapi.templates");
    }

    @Test
    public void findAllMustReturnAnEmptyList() throws Exception {
        when(pautaRepository.findAll()).thenReturn(Collections.emptyList());
        mvc.perform(get("/pauta")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(pautaRepository, times(1)).findAll();
    }

    @Test
    public void findAllMustReturnAListOfCriaPautaResponse() throws Exception {
        List<Pauta> list = Fixture.from(Pauta.class).gimme(1,"VALID");
        when(pautaRepository.findAll()).thenReturn(list);

        mvc.perform(get("/pauta")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].descricao").value("criaPautaResponse description"));

        verify(pautaRepository, times(1)).findAll();
    }

    @Test
    public void createMustSaveOnDatabase() throws Exception {
        CriaPautaRequest request = Fixture.from(CriaPautaRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("VALID");
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        mvc.perform(post("/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descricao").value("criaPautaResponse description"));

        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }

    @Test
    public void getResultMustThrowPautNaoEncontradaException() throws Exception {
        when(pautaRepository.getPautaWithVotos(any(Integer.class))).thenReturn(Optional.empty());

        mvc.perform(get("/pauta/{pautaId}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("pauta nao encontrada"));

        verify(pautaRepository, times(1)).getPautaWithVotos(any(Integer.class));
    }

    @Test
    public void getResultMustThrowPautaAindaSemSessaoException() throws Exception {
        Pauta pauta = Fixture.from(Pauta.class).gimme("VALID");
        when(pautaRepository.getPautaWithVotos(any(Integer.class))).thenReturn(Optional.of(pauta));

        mvc.perform(get("/pauta/{pautaId}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("pauta ainda nao possui uma sessao"));

        verify(pautaRepository, times(1)).getPautaWithVotos(any(Integer.class));
    }

    @Test
    public void getResultMustThrowPautaAindaAbertaParaVotacaoException() throws Exception {
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_EM_VOTACAO");
        when(pautaRepository.getPautaWithVotos(any(Integer.class))).thenReturn(Optional.of(pauta));

        mvc.perform(get("/pauta/{pautaId}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("pauta ainda esta aberta para votacao"));

        verify(pautaRepository, times(1)).getPautaWithVotos(any(Integer.class));
    }

    @Test
    public void getResultReturnOneVote() throws Exception {
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_COM_UM_VOTO_NAO");
        when(pautaRepository.getPautaWithVotos(any(Integer.class))).thenReturn(Optional.of(pauta));

        mvc.perform(get("/pauta/{pautaId}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pauta_id").value(1))
                .andExpect(jsonPath("$.descricao").value("criaPautaResponse description"))
                .andExpect(jsonPath("$.votos_a_favor").value(0))
                .andExpect(jsonPath("$.votos_contra").value(1));

        verify(pautaRepository, times(1)).getPautaWithVotos(any(Integer.class));
    }
}
