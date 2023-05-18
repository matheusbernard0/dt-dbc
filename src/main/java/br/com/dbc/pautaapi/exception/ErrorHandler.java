package br.com.dbc.pautaapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(value = {PautaNaoEncontradaException.class, UsuarioNaoEncontradoException.class})
    public ResponseEntity notFound(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PautaAindaAbertaParaVotacaoException.class, PautaAindaSemSessaoException.class,
    SessaoJaIniciadaException.class, UsuarioJaCadastradoException.class, SessaoNaoExistenteException.class,
    SessaoEncerradaException.class, VotoJaRealizadoException.class})
    public ResponseEntity badRequest(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity internalServerError(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
