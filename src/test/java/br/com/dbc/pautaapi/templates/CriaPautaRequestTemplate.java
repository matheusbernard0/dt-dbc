package br.com.dbc.pautaapi.templates;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CriaPautaRequestTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(CriaPautaRequest.class).addTemplate("VALID", new Rule(){{
            add("descricao", "criaPautaRequest description");
        }});
    }
}
