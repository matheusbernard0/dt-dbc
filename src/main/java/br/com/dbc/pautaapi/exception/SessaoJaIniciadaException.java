package br.com.dbc.pautaapi.exception;

public class SessaoJaIniciadaException extends RuntimeException {
    public SessaoJaIniciadaException(String mensagem) {
        super(mensagem);
    }
}
