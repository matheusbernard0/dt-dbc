package br.com.dbc.pautaapi.exception;

public class SessaoNaoExistenteException extends RuntimeException {
    public SessaoNaoExistenteException(String mensagem) {
        super(mensagem);
    }
}
