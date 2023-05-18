package br.com.dbc.pautaapi.exception;

public class SessaoEncerradaException extends RuntimeException {
    public SessaoEncerradaException(String mensagem) {
        super(mensagem);
    }
}
