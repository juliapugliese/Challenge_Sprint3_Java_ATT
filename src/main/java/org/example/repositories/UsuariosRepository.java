package org.example.repositories;

import org.example.entities.UsuarioModel.Administrador;
import org.example.entities.UsuarioModel.Cliente;
import org.example.entities.UsuarioModel.Usuario;
import org.example.infrastructure.OracleDatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuariosRepository extends _BaseRepositoryImpl<Usuario> {


//    public static final Map<String, String> CONNECTION_PROPERTIES = of(
//            "URL", "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
//            "USER", "rm553427",
//            "PASSWORD", "280603"
//    );

    public static final String TB_NAME = "USUARIO";

    public static final Map<String, String> TB_COLUMNS = Map.ofEntries(
                    Map.entry("ID", "ID"),
                    Map.entry("NOME_USUARIO", "NOME_USUARIO"),
                    Map.entry("SENHA", "SENHA"),
                    Map.entry("TIPO", "TIPO"),
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


    public UsuariosRepository() {
        Initialize();
    }

//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(
//                CONNECTION_PROPERTIES.get("URL"),
//                CONNECTION_PROPERTIES.get("USER"),
//                CONNECTION_PROPERTIES.get("PASSWORD"));
//    }

    private void Initialize() {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
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
                                    TB_COLUMNS.get("NOME_COMPLETO"),
                                    TB_COLUMNS.get("CPF"),
                                    TB_COLUMNS.get("TELEFONE"),
                                    TB_COLUMNS.get("EMPRESA"),
                                    TB_COLUMNS.get("CNPJ"),
                                    TB_COLUMNS.get("CARGO"),
                                    TB_COLUMNS.get("SEGMENTO"),
                                    TB_COLUMNS.get("TAMANHO_EMPRESA"),
                                    TB_COLUMNS.get("PAIS"),
                                    TB_COLUMNS.get("EMAIL_CORPORATIVO"),
                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")));
            stmt.executeUpdate();
            System.out.println("Tabela criada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("DROP TABLE %s".formatted(TB_NAME));
            stmt.executeUpdate();
            System.out.println("Tabela excluída com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Usuario usuario) {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,  %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                            .formatted(TB_NAME,
                                    TB_COLUMNS.get("NOME_USUARIO"),
                                    TB_COLUMNS.get("SENHA"),
                                    TB_COLUMNS.get("TIPO"),
                                    TB_COLUMNS.get("NOME_ADM"),
                                    TB_COLUMNS.get("EMAIL"),
                                    TB_COLUMNS.get("NOME_COMPLETO"),
                                    TB_COLUMNS.get("CPF"),
                                    TB_COLUMNS.get("TELEFONE"),
                                    TB_COLUMNS.get("EMPRESA"),
                                    TB_COLUMNS.get("CNPJ"),
                                    TB_COLUMNS.get("CARGO"),
                                    TB_COLUMNS.get("SEGMENTO"),
                                    TB_COLUMNS.get("TAMANHO_EMPRESA"),
                                    TB_COLUMNS.get("PAIS"),
                                    TB_COLUMNS.get("EMAIL_CORPORATIVO"),
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
            stmt.executeUpdate();
            System.out.println("Usuário criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Usuario> readAll() {
        var usuarios = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
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
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getInt(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TB_COLUMNS.get("EMPRESA")),
                            resultSet.getInt(TB_COLUMNS.get("CNPJ")),
                            resultSet.getString(TB_COLUMNS.get("CARGO")),
                            resultSet.getString(TB_COLUMNS.get("SEGMENTO")),
                            resultSet.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                            resultSet.getString(TB_COLUMNS.get("PAIS")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL_CORPORATIVO")),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    //TESTE - READ BY ID

    public Optional<Usuario> get(int id){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ){
            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                if (resultSet.getString(TB_COLUMNS.get("TIPO")).equals("ADM")) {
                    return Optional.of(new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_ADM")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    ));
                } else {
                    return Optional.of(new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getInt(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TB_COLUMNS.get("EMPRESA")),
                            resultSet.getInt(TB_COLUMNS.get("CNPJ")),
                            resultSet.getString(TB_COLUMNS.get("CARGO")),
                            resultSet.getString(TB_COLUMNS.get("SEGMENTO")),
                            resultSet.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                            resultSet.getString(TB_COLUMNS.get("PAIS")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL_CORPORATIVO")),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"))
                    ));
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Optional.empty();
    }




    //TESTE - UPDATE
    public void update(int id, Usuario usuario) {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, WHERE ID = ?"
                            .formatted(TB_NAME,
                                    TB_COLUMNS.get("NOME_USUARIO"),
                                    TB_COLUMNS.get("SENHA"),
                                    TB_COLUMNS.get("TIPO"),
                                    TB_COLUMNS.get("NOME_ADM"),
                                    TB_COLUMNS.get("EMAIL"),
                                    TB_COLUMNS.get("NOME_COMPLETO"),
                                    TB_COLUMNS.get("CPF"),
                                    TB_COLUMNS.get("TELEFONE"),
                                    TB_COLUMNS.get("EMPRESA"),
                                    TB_COLUMNS.get("CNPJ"),
                                    TB_COLUMNS.get("CARGO"),
                                    TB_COLUMNS.get("SEGMENTO"),
                                    TB_COLUMNS.get("TAMANHO_EMPRESA"),
                                    TB_COLUMNS.get("PAIS"),
                                    TB_COLUMNS.get("EMAIL_CORPORATIVO"),
                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")));
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenha());
            stmt.setInt(17, id);
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
            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    //VERIFICAR DELETE
    public void delete(Usuario usuario){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?")){
            stmt.setInt(1, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
