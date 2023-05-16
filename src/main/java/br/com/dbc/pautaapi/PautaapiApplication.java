package br.com.dbc.pautaapi;

import br.com.dbc.pautaapi.repositories.PautaRepository;
import br.com.dbc.pautaapi.repositories.SessaoRepository;
import br.com.dbc.pautaapi.repositories.UsuarioRepository;
import br.com.dbc.pautaapi.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
@RequestMapping("/pauta")
public class PautaapiApplication {

	@Autowired
	private PautaRepository pautaRepository;
	@Autowired
	private SessaoRepository sessaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private VotoRepository votoRepository;

	public static void main(String[] args) {
		SpringApplication.run(PautaapiApplication.class, args);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta create(@RequestBody Pauta pauta) {
		return pautaRepository.save(pauta);
	}

	@PatchMapping("/{pautaId}")
	public void openSession(@RequestBody(required = false) CriaSessaoRequest criaSessaoRequest, @PathVariable Integer pautaId) {
		Pauta pauta = pautaRepository.findById(pautaId)
				.orElseThrow(() -> new RuntimeException("pauta nao encontrada"));

		if (Objects.nonNull(pauta.getSessao()))
			throw new RuntimeException("sessao ja iniciada");

		LocalDateTime inicio = LocalDateTime.now();

		LocalDateTime fim = inicio.plusHours(criaSessaoRequest.getHoras())
				.plusMinutes(criaSessaoRequest.getMinutos())
				.plusSeconds(criaSessaoRequest.getSegundos());

		Sessao sessao = Sessao.builder()
				.inicio(inicio)
				.fim(fim)
				.aberta(Boolean.TRUE)
				.pauta(pauta)
				.build();

		sessaoRepository.save(sessao);
	}

	@PostMapping("/vote")
	public void vote(@RequestBody VotaRequest votaRequest) {
		Pauta pauta = pautaRepository.findById(votaRequest.getPautaId())
				.orElseThrow(() -> new RuntimeException("Pauta nao encontrada"));

		Sessao sessao = pauta.getSessao();
		if (Objects.isNull(sessao))
			throw new RuntimeException("Nao existe sessao aberta para a pauta");

		if (!sessao.estaAbertaParaVotacao())
			throw new RuntimeException("A sessao de votacao esta encerrada");

		Usuario usuario = usuarioRepository.findById(votaRequest.getUsuarioId())
				.orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

		votoRepository.findById(new VotoId(sessao.getId(), usuario.getId()))
				.ifPresent(voto -> {
					throw new RuntimeException("Voto ja realizado para usuario e pauta informada");
				});

		Voto voto = new Voto();
		voto.setUsuario(usuario);
		voto.setSessao(sessao);
		voto.setOpcao(votaRequest.getOpcao());
		votoRepository.save(voto);
	}

	@PostMapping("/cria/usuario")
	public void createUser(@RequestBody CriaUsuarioRequest criaUsuarioRequest) {
		usuarioRepository.findUsuarioByCpf(criaUsuarioRequest.getCpf())
				.ifPresent(usuario -> {throw new RuntimeException("usuario ja cadastrado");});

		usuarioRepository.save(Usuario.builder()
						.cpf(criaUsuarioRequest.getCpf())
						.nome(criaUsuarioRequest.getNome())
				.build());
	}
}
