package br.com.dbc.pautaapi.templates;

import br.com.dbc.pautaapi.entity.Sessao;
import br.com.dbc.pautaapi.entity.Voto;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class SessaoTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Sessao.class).addTemplate("ABERTO", new Rule(){{
            add("id", 1);
            add("inicio", LocalDateTime.now().minusDays(1));
            add("fim", LocalDateTime.now().plusDays(1));
            add("aberta", Boolean.TRUE);
        }});

        Fixture.of(Sessao.class).addTemplate("COM_UM_VOTO_NAO", new Rule(){{
            add("id", 1);
            add("inicio", LocalDateTime.now().minusDays(1));
            add("fim", LocalDateTime.now().minusHours(1));
            add("aberta", Boolean.TRUE);
            add("votos", has(1).of(Voto.class, "VALID"));
        }});
    }
}
