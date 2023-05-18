package br.com.dbc.pautaapi.exception;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
