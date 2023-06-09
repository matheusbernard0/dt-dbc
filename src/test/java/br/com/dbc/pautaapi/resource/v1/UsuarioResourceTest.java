package br.com.dbc.pautaapi.resource.v1;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.entity.Usuario;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.resource.v1.UsuarioResource;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsuarioResource.class)
public class UsuarioResourceTest {
    private static final String USUARIO_V1_BASE_PATH = "/usuario/v1";
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @Autowired
    protected MockMvc mvc;

    @BeforeAll
    public static void before() {
        FixtureFactoryLoader.loadTemplates("br.com.dbc.pautaapi.templates");
    }

    @Test
    @SneakyThrows
    public void createUserMustReturnUsuarioJaCadastradoException() {
        Usuario usuario = Fixture.from(Usuario.class).gimme("VALID");
        CriaUsuarioRequest request = Fixture.from(CriaUsuarioRequest.class).gimme("VALID");

        when(usuarioRepository.findUsuarioByCpf(anyString())).thenReturn(Optional.of(usuario));

        mvc.perform(post(USUARIO_V1_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("usuario ja cadastrado"));

        verify(usuarioRepository, times(1)).findUsuarioByCpf(request.getCpf());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    @SneakyThrows
    public void createUserMustExecuteSuccssfully() {
        CriaUsuarioRequest request = Fixture.from(CriaUsuarioRequest.class).gimme("VALID");
        Usuario usuario = Fixture.from(Usuario.class).gimme("VALID");

        when(usuarioRepository.findUsuarioByCpf(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        mvc.perform(post(USUARIO_V1_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpf").value("00000000000"))
                .andExpect(jsonPath("$.nome").value("Nome do usuario"))
                .andExpect(jsonPath("$.data_de_criacao").isNotEmpty());

        verify(usuarioRepository, times(1)).findUsuarioByCpf(request.getCpf());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @SneakyThrows
    public void findAllMustReturnUsers() {
        List<Usuario> list = Fixture.from(Usuario.class).gimme(1,"VALID");
        when(usuarioRepository.findAll()).thenReturn(list);

        mvc.perform(get(USUARIO_V1_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].cpf").value("00000000000"))
                .andExpect(jsonPath("$.[0].nome").value("Nome do usuario"));

        verify(usuarioRepository, times(1)).findAll();
    }
}
