package br.com.dbc.pautaapi.templates.request;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CriaUsuarioRequestTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(CriaUsuarioRequest.class).addTemplate("VALID", new Rule(){{
            add("nome", "Nome do associado");
            add("cpf", "00000000000");
        }});
    }
}
