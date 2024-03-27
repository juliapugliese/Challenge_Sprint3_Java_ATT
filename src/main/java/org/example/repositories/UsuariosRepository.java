package org.example.repositories;

import org.example.entities.UsuarioModel.Administrador;
import org.example.entities.UsuarioModel.Cliente;
import org.example.entities.UsuarioModel.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.of;

public class UsuariosRepository extends _BaseRepositoryImpl<Usuario> {


    public static final Map<String, String> CONNECTION_PROPERTIES = of(
            "URL", "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
            "USER", "rm553427",
            "PASSWORD", "280603"
    );

    public static final String TB_NAME = "USUARIO";

    public static final Map<String, String> TB_COLUMNS = Map.of(
            "ID", "ID",
            "NOME_USUARIO", "NOME_USUARIO",
            "SENHA", "SENHA",
            "TIPO", "TIPO",
            "NOME_ADM", "NOME_ADM",
            "EMAIL", "EMAIL",
            "PERGUNTAS_COMENTARIOS", "PERGUNTAS_COMENTARIOS"
    );
    public static final Map<String, String> TB_CLIENTE_COLUMNS = Map.of(
            "NOME_COMPLETO", "NOME_COMPLETO",
            "CPF", "CPF",
            "TELEFONE", "TELEFONE",
            "EMPRESA", "EMPRESA",
            "CNPJ", "CNPJ",
            "CARGO", "CARGO",
            "SEGMENTO", "SEGMENTO",
            "TAMANHO_EMPRESA", "TAMANHO_EMPRESA",
            "PAIS", "PAIS",
            "EMAIL_CORPORATIVO", "EMAIL_CORPORATIVO"
    );


    public UsuariosRepository() {
        Initialize();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                CONNECTION_PROPERTIES.get("URL"),
                CONNECTION_PROPERTIES.get("USER"),
                CONNECTION_PROPERTIES.get("PASSWORD"));
    }

    private void Initialize() {
        try {
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                    ("CREATE TABLE %s (" +
                            "%s NUMBER generated as identity constraint CONTA_PK PRIMARY KEY, " +
                            "%s VARCHAR2(20) NOT NULL, " +
                            "%s VARCHAR2(20) NOT NULL, " +
                            "%s VARCHAR2(3) NOT NULL, " +
                            "%s VARCHAR2(20), " +
                            "%s VARCHAR2(20), " +
                            "%s VARCHAR2(40), " +
                            "%s NUMBER(11), " +
                            "%s NUMBER(11), " +
                            "%s VARCHAR2(30), " +
                            "%s NUMBER(14), " +
                            "%s VARCHAR2(20), " +
                            "%s VARCHAR2(20), " +
                            "%s VARCHAR2(10), " +
                            "%s VARCHAR2(25), " +
                            "%s VARCHAR2(20), " +
                            "%s VARCHAR2(200), ")
                            .formatted(TB_NAME,
                                    TB_COLUMNS.get("ID"),
                                    TB_COLUMNS.get("NOME_USUARIO"),
                                    TB_COLUMNS.get("SENHA"),
                                    TB_COLUMNS.get("TIPO"),
                                    TB_COLUMNS.get("NOME_ADM"),
                                    TB_COLUMNS.get("EMAIL"),
                                    TB_CLIENTE_COLUMNS.get("NOME_COMPLETO"),
                                    TB_CLIENTE_COLUMNS.get("CPF"),
                                    TB_CLIENTE_COLUMNS.get("TELEFONE"),
                                    TB_CLIENTE_COLUMNS.get("EMPRESA"),
                                    TB_CLIENTE_COLUMNS.get("CNPJ"),
                                    TB_CLIENTE_COLUMNS.get("CARGO"),
                                    TB_CLIENTE_COLUMNS.get("SEGMENTO"),
                                    TB_CLIENTE_COLUMNS.get("TAMANHO_EMPRESA"),
                                    TB_CLIENTE_COLUMNS.get("PAIS"),
                                    TB_CLIENTE_COLUMNS.get("EMAIL_CORPORATIVO"),
                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")));
            stmt.executeUpdate();
            System.out.println("Tabela criada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            var conn = getConnection();
            var stmt = conn.prepareStatement("DROP TABLE %s".formatted(TB_NAME));
            stmt.executeUpdate();
            System.out.println("Tabela excluída com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Usuario usuario) {
        try {
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,  %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                            .formatted(TB_NAME,
                                    TB_COLUMNS.get("NOME_USUARIO"),
                                    TB_COLUMNS.get("SENHA"),
                                    TB_COLUMNS.get("TIPO"),
                                    TB_COLUMNS.get("NOME_ADM"),
                                    TB_COLUMNS.get("EMAIL"),
                                    TB_CLIENTE_COLUMNS.get("NOME_COMPLETO"),
                                    TB_CLIENTE_COLUMNS.get("CPF"),
                                    TB_CLIENTE_COLUMNS.get("TELEFONE"),
                                    TB_CLIENTE_COLUMNS.get("EMPRESA"),
                                    TB_CLIENTE_COLUMNS.get("CNPJ"),
                                    TB_CLIENTE_COLUMNS.get("CARGO"),
                                    TB_CLIENTE_COLUMNS.get("SEGMENTO"),
                                    TB_CLIENTE_COLUMNS.get("TAMANHO_EMPRESA"),
                                    TB_CLIENTE_COLUMNS.get("PAIS"),
                                    TB_CLIENTE_COLUMNS.get("EMAIL_CORPORATIVO"),
                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")));
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenha());
            if (usuario instanceof Administrador) {
                stmt.setString(3, "ADM");
                stmt.setString(4, ((Administrador) usuario).getNomeAdm());
                stmt.setString(5, ((Administrador) usuario).getEmail());
            } else if (usuario instanceof Cliente) {
                stmt.setString(3, "CLT");
                stmt.setString(6, ((Cliente) usuario).getNomeCompleto());
                stmt.setInt(7, ((Cliente) usuario).getCpf());
                stmt.setString(8, ((Cliente) usuario).getTelefone());
                stmt.setString(9, ((Cliente) usuario).getEmpresa());
                stmt.setInt(10, ((Cliente) usuario).getCnpj());
                stmt.setString(11, ((Cliente) usuario).getCargo());
                stmt.setString(12, ((Cliente) usuario).getSegmento());
                stmt.setString(13, ((Cliente) usuario).getTamanhoEmpresa());
                stmt.setString(14, ((Cliente) usuario).getPais());
                stmt.setString(15, ((Cliente) usuario).getEmailCorporativo());
                stmt.setString(16, ((Cliente) usuario).getPerguntasOuComentarios());
            }
            var result = stmt.executeUpdate();
            System.out.println("Conta criada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Usuario> readAll() {
        var usuarios = new ArrayList<Usuario>();
        try {
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME));
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(TB_COLUMNS.get("TIPO")).equals("ADM")) {
                    usuarios.add(new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_ADM")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    ));
                } else {
                    usuarios.add(new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getInt(TB_CLIENTE_COLUMNS.get("CPF")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("EMPRESA")),
                            resultSet.getInt(TB_CLIENTE_COLUMNS.get("CNPJ")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("CARGO")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("SEGMENTO")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("TAMANHO_EMPRESA")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("PAIS")),
                            resultSet.getString(TB_CLIENTE_COLUMNS.get("EMAIL_CORPORATIVO")),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

}
