package org.example.repositories;

import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.util.Map;

public class Starter implements _Logger<String>{


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

            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + PlanosRepository.TB_NAME + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + ProdutosRepository.TB_NAME + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + UsuariosRepository.TB_NAME_U + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + UsuariosRepository.TB_NAME_C + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + UsuariosRepository.TB_NAME_P + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + ProdutosRepository.TB_NAME_I + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }




            try (var stmt = conn.prepareStatement(
                            ("CREATE TABLE %s (" +
                                    "%s NUMBER GENERATED AS IDENTITY CONSTRAINT USER_PK PRIMARY KEY, " +
                                    "%s VARCHAR2(20) NOT NULL, " +
                                    "%s VARCHAR2(20) NOT NULL, " +
                                    "%s VARCHAR2(60), " +
                                    "%s VARCHAR2(60), " +
                                    "%s NUMBER(11), " +
                                    "%s NUMBER(11), " +
                                    "%s VARCHAR2(200), " +
                                    "%s NUMBER, " +
                                    "%s NUMBER)")
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
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement(
                    ("CREATE TABLE %s (" +
                            "%s NUMBER GENERATED AS IDENTITY CONSTRAINT CLIENTE_JAVA_PK PRIMARY KEY, " +
                            "%s VARCHAR2(70), " +
                            "%s NUMBER(14), " +
                            "%s VARCHAR2(50), " +
                            "%s VARCHAR2(70), " +
                            "%s VARCHAR2(10), " +
                            "%s VARCHAR2(40), " +
                            "%s VARCHAR2(60))")
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

            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + ProdutosRepository.TB_NAME + " (COD_PRODUTO NUMBER GENERATED AS IDENTITY CONSTRAINT PRODUTOS_JAVA_PK PRIMARY KEY, " +
                                "NOME VARCHAR2(60) NOT NULL, " +
                                "DESCRICAO VARCHAR2(150) NOT NULL, " +
                                "PLANO_PAGAMENTO NUMBER, " +
                                "PLANO_SUCESSO NUMBER)" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ ProdutosRepository.TB_NAME +" criada com sucesso!");
            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + UsuariosRepository.TB_NAME_P + " (COD_PERFIL NUMBER GENERATED AS IDENTITY CONSTRAINT PERFIL_JAVA_PK PRIMARY KEY, " +
                                "TIPO VARCHAR2(60) NOT NULL)" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ UsuariosRepository.TB_NAME_P +" criada com sucesso!");

            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement(
                        ("CREATE TABLE " + PlanosRepository.TB_NAME + " (COD_PLANO NUMBER GENERATED AS IDENTITY CONSTRAINT PLANOS_JAVA_PK PRIMARY KEY, " +
                                "NOME VARCHAR2(60) NOT NULL, " +
                                "DESCRICAO VARCHAR2(150) NOT NULL, " +
                                "RECURSOS VARCHAR2(150), " +
                                "PRECO DECIMAL(9,2))" ));
                stmt.executeUpdate();
                logInfo("Tabela "+ PlanosRepository.TB_NAME +" criada com sucesso!");

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

            } catch (SQLException e) {
                logError(e);
            }






            try (var stmt = conn.prepareStatement("ALTER TABLE "+ UsuariosRepository.TB_NAME_U +" ADD CONSTRAINT USUARIO_PERFIL_FK FOREIGN KEY(COD_PERFIL) REFERENCES "+ UsuariosRepository.TB_NAME_P +"(COD_PERFIL)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("ALTER TABLE "+ UsuariosRepository.TB_NAME_U +" ADD CONSTRAINT USUARIO_CLIENTE_FK FOREIGN KEY(COD_CLIENTE) REFERENCES "+ UsuariosRepository.TB_NAME_C +"(COD_CLIENTE)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("ALTER TABLE "+ ProdutosRepository.TB_NAME +" ADD CONSTRAINT PRODUTO_PLANO_PAGAMENTO_FK FOREIGN KEY(PLANO_PAGAMENTO) REFERENCES "+ PlanosRepository.TB_NAME +"(COD_PLANO)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("ALTER TABLE "+ ProdutosRepository.TB_NAME +" ADD CONSTRAINT PRODUTO_PLANO_SUCESSO_FK FOREIGN KEY(PLANO_SUCESSO) REFERENCES "+ PlanosRepository.TB_NAME +"(COD_PLANO)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("ALTER TABLE "+ ProdutosRepository.TB_NAME_I +" ADD CONSTRAINT ITEM_COMPRA_PRODUTO_FK FOREIGN KEY(COD_PRODUTO) REFERENCES "+ ProdutosRepository.TB_NAME +"(COD_PRODUTO)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }
            try (var stmt = conn.prepareStatement("ALTER TABLE "+ ProdutosRepository.TB_NAME_I +" ADD CONSTRAINT ITEM_COMPRA_CLIENTE_FK FOREIGN KEY(COD_CLIENTE) REFERENCES "+ UsuariosRepository.TB_NAME_C +"(COD_CLIENTE)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                logError(e);
            }

            conn.close();
        }catch (SQLException e) {
            logError(e);
        }



    }
//    public void shutdown() {
//        try {
//            var conn =  new OracleDatabaseConfiguration().getConnection();
//            var stmt = conn.prepareStatement("DROP TABLE %s".formatted(TB_NAME));
//            stmt.executeUpdate();
//            logWarn("Tabela "+ TB_NAME +" excluída com sucesso!");
//            conn.close();
//        } catch (SQLException e) {
//            logError(e);
//        }
//    }
}
