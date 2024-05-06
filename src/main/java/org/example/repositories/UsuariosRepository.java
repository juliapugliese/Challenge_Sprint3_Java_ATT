package org.example.repositories;

import org.example.entities.UsuarioModel.Administrador;
import org.example.entities.UsuarioModel.Cliente;
import org.example.entities.UsuarioModel.Usuario;
import org.example.entities._BaseEntity;
import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class UsuariosRepository extends Starter implements _BaseRepository<Usuario>, _Logger<String>{

    public static final String TB_NAME_U = "USUARIO_JAVA";
    public static final String TB_NAME_C = "CLIENTE_JAVA";
    public static final String TB_NAME_P = "PERFIL_JAVA";

//    public static final Map<String, String> TB_COLUMNS = Map.ofEntries(
//                    Map.entry("ID", "ID"),
//                    Map.entry("NOME_USUARIO", "NOME_USUARIO"),
//                    Map.entry("SENHA", "SENHA"),
//                    Map.entry("TIPO", "TIPO"),
//                    Map.entry("NOME_ADM", "NOME_ADM"),
//                    Map.entry("EMAIL", "EMAIL"),
//                    Map.entry("PERGUNTAS_COMENTARIOS", "PERGUNTAS_COMENTARIOS"),
//                    Map.entry("NOME_COMPLETO", "NOME_COMPLETO"),
//                    Map.entry("CPF", "CPF"),
//                    Map.entry("TELEFONE", "TELEFONE"),
//                    Map.entry("EMPRESA", "EMPRESA"),
//                    Map.entry("CNPJ", "CNPJ"),
//                    Map.entry("CARGO", "CARGO"),
//                    Map.entry("SEGMENTO", "SEGMENTO"),
//                    Map.entry("TAMANHO_EMPRESA", "TAMANHO_EMPRESA"),
//                    Map.entry("PAIS", "PAIS"),
//                    Map.entry("EMAIL_CORPORATIVO", "EMAIL_CORPORATIVO")
//            );
//

    public UsuariosRepository() {
    }

//    public void initialize() {
//        try {
//            var conn =  new OracleDatabaseConfiguration().getConnection();
//            var stmt = conn.prepareStatement(
//                    ("CREATE TABLE %s (" +
//                            "%s NUMBER GENERATED AS IDENTITY CONSTRAINT USER_PK PRIMARY KEY, " +
//                            "%s VARCHAR2(20) NOT NULL, " +
//                            "%s VARCHAR2(20) NOT NULL, " +
//                            "%s VARCHAR2(3) NOT NULL, " +
//                            "%s VARCHAR2(50), " +
//                            "%s VARCHAR2(60), " +
//                            "%s VARCHAR2(40), " +
//                            "%s NUMBER(11), " +
//                            "%s NUMBER(11), " +
//                            "%s VARCHAR2(70), " +
//                            "%s NUMBER(14), " +
//                            "%s VARCHAR2(50), " +
//                            "%s VARCHAR2(70), " +
//                            "%s VARCHAR2(10), " +
//                            "%s VARCHAR2(40), " +
//                            "%s VARCHAR2(60), " +
//                            "%s VARCHAR2(200))")
//                            .formatted(TB_NAME,
//                                    TB_COLUMNS.get("ID"),
//                                    TB_COLUMNS.get("NOME_USUARIO"),
//                                    TB_COLUMNS.get("SENHA"),
//                                    TB_COLUMNS.get("TIPO"),
//                                    TB_COLUMNS.get("NOME_ADM"),
//                                    TB_COLUMNS.get("EMAIL"),
//                                    TB_COLUMNS.get("NOME_COMPLETO"),
//                                    TB_COLUMNS.get("CPF"),
//                                    TB_COLUMNS.get("TELEFONE"),
//                                    TB_COLUMNS.get("EMPRESA"),
//                                    TB_COLUMNS.get("CNPJ"),
//                                    TB_COLUMNS.get("CARGO"),
//                                    TB_COLUMNS.get("SEGMENTO"),
//                                    TB_COLUMNS.get("TAMANHO_EMPRESA"),
//                                    TB_COLUMNS.get("PAIS"),
//                                    TB_COLUMNS.get("EMAIL_CORPORATIVO"),
//                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")));
//            stmt.executeUpdate();
//            logInfo("Tabela "+ TB_NAME +" criada com sucesso!");
//            conn.close();
//        } catch (SQLException e) {
//            logError(e);
//        }
//    }

    public void shutdown() {
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("DROP TABLE %s".formatted(TB_NAME_U));
            stmt.executeUpdate();
            logWarn("Tabela "+ TB_NAME_U +" excluída com sucesso!");
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
    }

    public void create(Usuario usuario) {
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                            .formatted(TB_NAME_U,
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
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.NUMERIC);
                stmt.setNull(8, Types.VARCHAR);
                stmt.setNull(9, Types.VARCHAR);
                stmt.setNull(10, Types.NUMERIC);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setNull(12, Types.VARCHAR);
                stmt.setNull(13, Types.VARCHAR);
                stmt.setNull(14, Types.VARCHAR);
                stmt.setNull(15, Types.VARCHAR);
                stmt.setNull(16, Types.VARCHAR);

            } else if (usuario instanceof Cliente) {
                stmt.setString(3, "CLT");
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setString(6, ((Cliente) usuario).getNomeCompleto());
                stmt.setLong(7, ((Cliente) usuario).getCpf());
                stmt.setString(8, ((Cliente) usuario).getTelefone());
                stmt.setString(9, ((Cliente) usuario).getEmpresa());
                stmt.setLong(10, ((Cliente) usuario).getCnpj());
                stmt.setString(11, ((Cliente) usuario).getCargo());
                stmt.setString(12, ((Cliente) usuario).getSegmento());
                stmt.setString(13, ((Cliente) usuario).getTamanhoEmpresa());
                stmt.setString(14, ((Cliente) usuario).getPais());
                stmt.setString(15, ((Cliente) usuario).getEmailCorporativo());
                stmt.setString(16, ((Cliente) usuario).getPerguntasOuComentarios());
            }
            stmt.executeUpdate();
            logInfo("Usuário adicionado com sucesso");
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
    }

    public void update(int id, Usuario usuario) {
        try {var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                            .formatted(TB_NAME_U,
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
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.NUMERIC);
                stmt.setNull(8, Types.VARCHAR);
                stmt.setNull(9, Types.VARCHAR);
                stmt.setNull(10, Types.NUMERIC);
                stmt.setNull(11, Types.VARCHAR);
                stmt.setNull(12, Types.VARCHAR);
                stmt.setNull(13, Types.VARCHAR);
                stmt.setNull(14, Types.VARCHAR);
                stmt.setNull(15, Types.VARCHAR);
                stmt.setNull(16, Types.VARCHAR);

            } else if (usuario instanceof Cliente) {
                stmt.setString(3, "CLT");
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setString(6, ((Cliente) usuario).getNomeCompleto());
                stmt.setLong(7, ((Cliente) usuario).getCpf());
                stmt.setString(8, ((Cliente) usuario).getTelefone());
                stmt.setString(9, ((Cliente) usuario).getEmpresa());
                stmt.setLong(10, ((Cliente) usuario).getCnpj());
                stmt.setString(11, ((Cliente) usuario).getCargo());
                stmt.setString(12, ((Cliente) usuario).getSegmento());
                stmt.setString(13, ((Cliente) usuario).getTamanhoEmpresa());
                stmt.setString(14, ((Cliente) usuario).getPais());
                stmt.setString(15, ((Cliente) usuario).getEmailCorporativo());
                stmt.setString(16, ((Cliente) usuario).getPerguntasOuComentarios());
            }
            stmt.executeUpdate();
            logWarn("Usuário atualizado com sucesso!");
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
    }


    public void delete(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME_U + " WHERE ID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logWarn("Usuário deletado com sucesso");
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }

    public List<Usuario> readAll() {
        var usuarios = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME_U));
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
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TB_COLUMNS.get("EMPRESA")),
                            resultSet.getLong(TB_COLUMNS.get("CNPJ")),
                            resultSet.getString(TB_COLUMNS.get("CARGO")),
                            resultSet.getString(TB_COLUMNS.get("SEGMENTO")),
                            resultSet.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                            resultSet.getString(TB_COLUMNS.get("PAIS")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL_CORPORATIVO")),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"))
                    ));
                }
            }
            conn.close();
            usuarios.sort(Comparator.comparingInt(_BaseEntity::getId));
        } catch (SQLException e) {
            logError(e);
        }
        logInfo("Lendo usuários: " + usuarios);
        return usuarios;
    }

    //testar read
    public Optional<Usuario> read(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME_U + " WHERE ID = ?");
            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                if (resultSet.getString(TB_COLUMNS.get("TIPO")).equals("ADM")) {
                    var administrador = new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_ADM")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    );
                    logInfo("Lendo administrador: " + administrador);
                    return Optional.of(administrador);
                } else {
                    var cliente = new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TB_COLUMNS.get("EMPRESA")),
                            resultSet.getLong(TB_COLUMNS.get("CNPJ")),
                            resultSet.getString(TB_COLUMNS.get("CARGO")),
                            resultSet.getString(TB_COLUMNS.get("SEGMENTO")),
                            resultSet.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                            resultSet.getString(TB_COLUMNS.get("PAIS")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL_CORPORATIVO")),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"))
                    );
                    logInfo("Lendo cliente: " + cliente);
                    return Optional.of(cliente);
                }
            }
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
        return Optional.empty();
    }


    public List<Usuario> readAllADM() {
        var administradores = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME_U));
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(TB_COLUMNS.get("TIPO")).equals("ADM")) {
                    administradores.add(new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_ADM")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    ));
                }
            }
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
        administradores.sort(Comparator.comparingInt(_BaseEntity::getId));
        logInfo("Lendo usuários: " + administradores);
        return administradores;
    }

    public List<Usuario> readAllCLT() {
        var clientes = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME_U));
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString(TB_COLUMNS.get("TIPO")).equals("CLT")) {
                    clientes.add(new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("ID")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TB_COLUMNS.get("EMPRESA")),
                            resultSet.getLong(TB_COLUMNS.get("CNPJ")),
                            resultSet.getString(TB_COLUMNS.get("CARGO")),
                            resultSet.getString(TB_COLUMNS.get("SEGMENTO")),
                            resultSet.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                            resultSet.getString(TB_COLUMNS.get("PAIS")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL_CORPORATIVO")),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"))
                    ));
                }
            }
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
        clientes.sort(Comparator.comparingInt(_BaseEntity::getId));
        logInfo("Lendo usuários: " + clientes);
        return clientes;
    }

}
