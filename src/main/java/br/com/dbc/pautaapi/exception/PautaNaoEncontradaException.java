package br.com.dbc.pautaapi.exception;

public class PautaNaoEncontradaException extends RuntimeException {
    public PautaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
