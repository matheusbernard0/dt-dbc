package br.com.dbc.pautaapi.exception;

public class PautaAindaSemSessaoException extends RuntimeException {
    public PautaAindaSemSessaoException(String mensagem) {
        super(mensagem);
    }
}
