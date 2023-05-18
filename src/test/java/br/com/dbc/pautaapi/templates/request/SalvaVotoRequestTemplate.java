package br.com.dbc.pautaapi.templates.request;

import br.com.dbc.pautaapi.dto.request.SalvaVotoRequest;
import br.com.dbc.pautaapi.entity.Opcao;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class SalvaVotoRequestTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(SalvaVotoRequest.class).addTemplate("VALID", new Rule(){{
            add("usuarioId", 1);
            add("pautaId", 1);
            add("opcao", Opcao.NAO);
        }});
    }
}
