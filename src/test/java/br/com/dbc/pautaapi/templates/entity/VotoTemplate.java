package br.com.dbc.pautaapi.templates.entity;

import br.com.dbc.pautaapi.entity.Opcao;
import br.com.dbc.pautaapi.entity.Sessao;
import br.com.dbc.pautaapi.entity.Usuario;
import br.com.dbc.pautaapi.entity.Voto;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class VotoTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Voto.class).addTemplate("VALID", new Rule(){{
            add("opcao", Opcao.NAO);
            add("sessao", one(Sessao.class, "VALID"));
            add("usuario", one(Usuario.class, "VALID"));
        }});
    }
}
