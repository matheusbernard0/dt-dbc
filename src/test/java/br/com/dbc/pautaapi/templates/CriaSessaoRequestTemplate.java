package br.com.dbc.pautaapi.templates;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CriaSessaoRequestTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(CriaSessaoRequest.class).addTemplate("VALID", new Rule(){{
            add("horas", 0);
            add("minutos", 0);
            add("segundos", 30);
        }});
    }
}
