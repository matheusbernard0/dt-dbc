package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.SalvaVotoRequest;
import br.com.dbc.pautaapi.dto.response.SalvaVotoResponse;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Usuario;
import br.com.dbc.pautaapi.entity.Voto;
import br.com.dbc.pautaapi.entity.VotoId;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.repository.VotoRepository;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VotoResource.class)
public class VotoResourceTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PautaRepository pautaRepository;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private VotoRepository votoRepository;
    @Autowired
    protected MockMvc mvc;

    @BeforeAll
    public static void before() {
        FixtureFactoryLoader.loadTemplates("br.com.dbc.pautaapi.templates");
    }

    @Test
    @SneakyThrows
    public void voteMustThrowPautaNaoEncontradaException() {
        SalvaVotoRequest request = Fixture.from(SalvaVotoRequest.class).gimme("VALID");
        when(pautaRepository.findByPautaId(any(Integer.class))).thenReturn(Optional.empty());

        mvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Pauta nao encontrada"));
    }

    @Test
    @SneakyThrows
    public void voteMustThrowSessaoNaoExistenteException() {
        SalvaVotoRequest request = Fixture.from(SalvaVotoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("VALID");
        when(pautaRepository.findByPautaId(any(Integer.class))).thenReturn(Optional.of(pauta));

        mvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Nao existe sessao aberta para a pauta"));
    }

    @Test
    @SneakyThrows
    public void voteMustThrowSessaoEncerradaException() {
        SalvaVotoRequest request = Fixture.from(SalvaVotoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_VOTADA");
        when(pautaRepository.findByPautaId(any(Integer.class))).thenReturn(Optional.of(pauta));

        mvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("A sessao de votacao esta encerrada"));
    }

    @Test
    @SneakyThrows
    public void voteMustThrowUsuarioNaoEncontradoException() {
        SalvaVotoRequest request = Fixture.from(SalvaVotoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_EM_VOTACAO");

        when(pautaRepository.findByPautaId(any(Integer.class))).thenReturn(Optional.of(pauta));
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        mvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Usuario nao encontrado"));
    }

    @Test
    @SneakyThrows
    public void voteMustThrowVotoJaRealizadoException() {
        SalvaVotoRequest request = Fixture.from(SalvaVotoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_EM_VOTACAO");
        Usuario usuario = Fixture.from(Usuario.class).gimme("VALID");
        Voto voto = Fixture.from(Voto.class).gimme("VALID");

        when(pautaRepository.findByPautaId(any(Integer.class))).thenReturn(Optional.of(pauta));
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.of(usuario));
        when(votoRepository.findById(any(VotoId.class))).thenReturn(Optional.of(voto));

        mvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Voto ja realizado para usuario e pauta informada"));
    }

    @Test
    @SneakyThrows
    public void voteMustExecuteSuccessfully() {
        SalvaVotoRequest request = Fixture.from(SalvaVotoRequest.class).gimme("VALID");
        Pauta pauta = Fixture.from(Pauta.class).gimme("PAUTA_EM_VOTACAO");
        Usuario usuario = Fixture.from(Usuario.class).gimme("VALID");
        Voto voto = Fixture.from(Voto.class).gimme("VALID");

        when(pautaRepository.findByPautaId(any(Integer.class))).thenReturn(Optional.of(pauta));
        when(usuarioRepository.findById(any(Integer.class))).thenReturn(Optional.of(usuario));
        when(votoRepository.findById(any(VotoId.class))).thenReturn(Optional.empty());
        when(votoRepository.save(any(Voto.class))).thenReturn(voto);

        mvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.opcao").value("NAO"))
                .andExpect(jsonPath("$.usuario_id").value(1))
                .andExpect(jsonPath("$.pauta_id").value("1"));
    }
}
