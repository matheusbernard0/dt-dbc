package br.com.dbc.pautaapi.templates;

import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CriaPautaResponseTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(CriaPautaResponse.class).addTemplate("VALID", new Rule(){{
            add("id", 1);
            add("descricao", "criaPautaResponse description");
        }});
    }
}
