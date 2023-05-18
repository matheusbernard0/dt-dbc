package br.com.dbc.pautaapi.templates.entity;

import br.com.dbc.pautaapi.entity.Usuario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class UsuarioTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Usuario.class).addTemplate("VALID", new Rule(){{
            add("id", 1);
            add("nome", "Nome do usuario");
            add("cpf", "00000000000");
            add("dataDeCriacao", LocalDateTime.now());
        }});
    }
}
