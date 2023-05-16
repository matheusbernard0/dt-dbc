package br.com.dbc.pautaapi;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.dto.request.VotaRequest;
import br.com.dbc.pautaapi.entity.*;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.SessaoRepository;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

@SpringBootApplication
public class PautaapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(PautaapiApplication.class, args);
	}
}
