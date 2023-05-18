package br.com.dbc.pautaapi.templates.entity;

import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Sessao;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PautaTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Pauta.class).addTemplate("VALID", new Rule(){{
            add("id", 1);
            add("descricao", "criaPautaResponse description");
        }});

        Fixture.of(Pauta.class).addTemplate("PAUTA_EM_VOTACAO", new Rule(){{
            add("id", 1);
            add("descricao", "criaPautaResponse description");
            add("sessao", one(Sessao.class, "ABERTO"));
        }});

        Fixture.of(Pauta.class).addTemplate("PAUTA_VOTADA", new Rule(){{
            add("id", 1);
            add("descricao", "criaPautaResponse description");
            add("sessao", one(Sessao.class, "FECHADO"));
        }});

        Fixture.of(Pauta.class).addTemplate("PAUTA_COM_UM_VOTO_NAO", new Rule(){{
            add("id", 1);
            add("descricao", "criaPautaResponse description");
            add("sessao", one(Sessao.class, "COM_UM_VOTO_NAO"));
        }});
    }
}
