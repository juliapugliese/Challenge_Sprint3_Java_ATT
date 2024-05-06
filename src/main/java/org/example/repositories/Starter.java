package org.example.repositories;

import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.util.Map;

public class Starter implements _Logger<String>{


//    ITEM COMPRA
//    id cliente
//    id produto
//
//
//    USUARIO (fisico)
//    id cliente
//    id perfil
//
//    CLIENTE (juridico)
//
//
//    PERFIS
//    id perfil
//    nome perfil (adm,visualisador)

    public static final Map<String, String> TB_COLUMNS = Map.ofEntries(
            Map.entry("COD_USUARIO", "COD_USUARIO"),
            Map.entry("COD_CLIENTE", "COD_CLIENTE"),
            Map.entry("COD_PERFIL", "COD_PERFIL"),
            Map.entry("NOME_USUARIO", "NOME_USUARIO"),
            Map.entry("SENHA", "SENHA"),
            Map.entry("NOME_ADM", "NOME_ADM"),
            Map.entry("EMAIL", "EMAIL"),
            Map.entry("PERGUNTAS_COMENTARIOS", "PERGUNTAS_COMENTARIOS"),
            Map.entry("NOME_COMPLETO", "NOME_COMPLETO"),
            Map.entry("CPF", "CPF"),
            Map.entry("TELEFONE", "TELEFONE"),
            Map.entry("EMPRESA", "EMPRESA"),
            Map.entry("CNPJ", "CNPJ"),
            Map.entry("CARGO", "CARGO"),
            Map.entry("SEGMENTO", "SEGMENTO"),
            Map.entry("TAMANHO_EMPRESA", "TAMANHO_EMPRESA"),
            Map.entry("PAIS", "PAIS"),
            Map.entry("EMAIL_CORPORATIVO", "EMAIL_CORPORATIVO")
    );

    public void initialize() {
        try (var conn = new OracleDatabaseConfiguration().getConnection()) {

            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + PlanosRepository.TB_NAME + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + ProdutosRepository.TB_NAME + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + UsuariosRepository.TB_NAME_U + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + UsuariosRepository.TB_NAME_C + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + UsuariosRepository.TB_NAME_P + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + ProdutosRepository.TB_NAME_I + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }




            try (var stmt = conn.prepareStatement(
                            ("CREATE TABLE %s (" +
                                    "%s NUMBER GENERATED AS IDENTITY CONSTRAINT USER_PK PRIMARY KEY, " +
                                    "%s VARCHAR2(20) NOT NULL, " +
                                    "%s VARCHAR2(20) NOT NULL, " +
                                    "%s VARCHAR2(3) NOT NULL, " +
                                    "%s VARCHAR2(50), " +
                                    "%s VARCHAR2(60), " +
                                    "%s VARCHAR2(40), " +
                                    "%s NUMBER(11), " +
                                    "%s NUMBER(11), " +
                                    "%s VARCHAR2(200))")
                                    .formatted(UsuariosRepository.TB_NAME_U,
                                            TB_COLUMNS.get("COD_USUARIO"),
                                            TB_COLUMNS.get("NOME_USUARIO"),
                                            TB_COLUMNS.get("SENHA"),
                                            TB_COLUMNS.get("EMAIL"),
                                            TB_COLUMNS.get("NOME_COMPLETO"),
                                            TB_COLUMNS.get("CPF"),
                                            TB_COLUMNS.get("TELEFONE"),
                                            TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"),
                                            TB_COLUMNS.get("COD_PERFIL"),
                                            TB_COLUMNS.get("COD_CLIENTE")
                                            ))){
                stmt.executeUpdate();
                logInfo("Tabela "+ UsuariosRepository.TB_NAME_U +" criada com sucesso!");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement(
                    ("CREATE TABLE %s (" +
                            "%s NUMBER GENERATED AS IDENTITY CONSTRAINT USER_PK PRIMARY KEY, " +
                            "%s VARCHAR2(70), " +
                            "%s NUMBER(14), " +
                            "%s VARCHAR2(50), " +
                            "%s VARCHAR2(70), " +
                            "%s VARCHAR2(10), " +
                            "%s VARCHAR2(40), " +
                            "%s VARCHAR2(60)")
                            .formatted(UsuariosRepository.TB_NAME_C,
                                    TB_COLUMNS.get("COD_CLIENTE"),
                                    TB_COLUMNS.get("EMPRESA"),
                                    TB_COLUMNS.get("CNPJ"),
                                    TB_COLUMNS.get("CARGO"),
                                    TB_COLUMNS.get("SEGMENTO"),
                                    TB_COLUMNS.get("TAMANHO_EMPRESA"),
                                    TB_COLUMNS.get("PAIS"),
                                    TB_COLUMNS.get("EMAIL_CORPORATIVO")))){
                stmt.executeUpdate();
                logInfo("Tabela "+ UsuariosRepository.TB_NAME_C +" criada com sucesso!");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + ProdutosRepository.TB_NAME + " (COD_PRODUTO NUMBER GENERATED AS IDENTITY CONSTRAINT PRODUTOS_PK PRIMARY KEY, " +
                                "NOME VARCHAR2(60) NOT NULL, " +
                                "DESCRICAO VARCHAR2(150) NOT NULL, " +
                                "PLANO_PAGAMENTO NUMBER, " +
                                "SUCESS_PLANS NUMBER)" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ ProdutosRepository.TB_NAME +" criada com sucesso!");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }


            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + UsuariosRepository.TB_NAME_P + " (COD_PERFIL NUMBER GENERATED AS IDENTITY CONSTRAINT PRODUTOS_PK PRIMARY KEY, " +
                                "TIPO VARCHAR2(60) NOT NULL)" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ UsuariosRepository.TB_NAME_P +" criada com sucesso!");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }


            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + PlanosRepository.TB_NAME + " (COD_PLANO NUMBER GENERATED AS IDENTITY CONSTRAINT PLANOS_PK PRIMARY KEY, " +
                                "NOME VARCHAR2(60) NOT NULL, " +
                                "DESCRICAO VARCHAR2(150) NOT NULL, " +
                                "RECURSOS VARCHAR2(150), " +
                                "PRECO DECIMAL(9,2))" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ PlanosRepository.TB_NAME +" criada com sucesso!");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + ProdutosRepository.TB_NAME_I + " (COD_PRODUTO NUMBER, " +
                                "COD_CLIENTE NUMBER, " +
                                "PRECO DECIMAL(9,2))" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ ProdutosRepository.TB_NAME_I +" criada com sucesso!");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }




        }catch (SQLException e) {
            logError(e);
        }



    }
}
