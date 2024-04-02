package org.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.example package
        final ResourceConfig rc = new ResourceConfig().packages("org.example");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //TESTE
//        new UsuariosRepository().initialize();
//        var adm2 = new Administrador("553427", "fiap2", "Julia Ribeiro", "rm553427@fiap.com.br");
//        var cli = new Cliente("juliana", "juliana789", "juliana G. P. Ribeiro", 14451445751L, "11456699887", "fiap", 14563201478965L, "Aluno", "Analise e Desenvolvimento de Sistemas", "Medio", "Brasil", "rm553427@fiap.com.br");
//        var administradores = new UsuariosRepository();
//        var clientes = new UsuariosRepository();
//        administradores.read(2);
//        clientes.create(cli);
//        administradores.create(adm2);
//        clientes.readAll();
//        clientes.read(1);
//        clientes.readAllCLT();
//        administradores.readAllADM();
//        administradores.readAll();
//        administradores.create(adm2);
//        administradores.readAll();
//        administradores.update(7, adm2);
//        administradores.readAll();
//        administradores.delete(2);
//        administradores.readAll();
//        System.out.println(administradores.read(2));
//        new UsuariosRepository().shutdown();
//
//        new PlanosRepository().initialize();
//        var planscss = new Plano("Premium", "Explore recursos autoguiados, como aprendizado online, demonstrações e conselhos da comunidade. Incluído em todas as licenças", "Trailhead, Portal de Ajuda, Comunidade de Trailblazers, Success Center, Suporte Técnico");
//        var planscss1 = new Plano("PP", "Explore recursos autoguiados, como aprendizado online, demonstrações e conselhos da comunidade. Incluído em todas as licenças", "Trailhead, Portal de Ajuda, Comunidade de Trailblazers, Success Center, Suporte Técnico");
//        var repopl = new PlanosRepository();
//        repopl.create(planscss);
//        repopl.readAll();
//        repopl.delete(4);
//        repopl.read(6);
//        repopl.readByName(planscss);
//        repopl.readAll();
//        repopl.update(7, planscss1);
//        repopl.readAll();
//        new PlanosRepository().shutdown();
//
//        new ProdutosRepository().initialize();
//        var produto = new Produto("Sales Cloudy",  "Venda mais rápido e com mais inteligência com qualquer uma das nossas edições de CRM totalmente personalizáveis.", new ArrayList<>(List.of(
//                new Plano("Starter", "Ferramentas de vendas e atendimento ao cliente em um app", 25),
//                new Plano("Sales Professional", "Solução de vendas completa para equipes de qualquer tamanho",  80),
//                new Plano("Enterprise", "CRM de vendas altamente personalizável para o seu negócio",  165),
//                new Plano("Unlimited", "A plataforma definitiva para seu crescimento",  330)
//        )));
//
//        var produto1 = new Produto("MuleSoft Automation",  "Combine o poder do MuleSoft RPA, MuleSoft Composer e Anypoint Platform para ajudar as equipes a automatizar.", new ArrayList<>(List.of(
//                new Plano("MuleSoft Automation", "Capacite suas equipes para fazer mais, com menos.",  4750)
//        )));
////
//        var prodrepo = new ProdutosRepository();
//        prodrepo.create(produto);
//        prodrepo.delete(2);
//        prodrepo.readAll();
//        prodrepo.update(2, produto1);
//        prodrepo.readAll();
//        repopl.readAll();
//        new ProdutosRepository().shutdown();



        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();


    }
}

