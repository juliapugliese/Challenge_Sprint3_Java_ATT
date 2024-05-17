package org.example.repositories;

import org.example.entities.UsuarioModel.Administrador;
import org.example.entities.UsuarioModel.Cliente;
import org.example.entities.UsuarioModel.Empresa;
import org.example.entities.UsuarioModel.Usuario;
import org.example.entities._BaseEntity;
import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UsuariosRepository extends Starter implements _BaseRepository<Usuario>, _Logger<String>{

    public static final String TB_NAME_U = "USUARIO_JAVA";
    public static final String TB_NAME_C = "CLIENTE_JAVA";
    public static final String TB_NAME_CA = "CARGO_JAVA";
    public static final String TB_NAME_P = "PERFIL_JAVA";


    public UsuariosRepository() {
    }

    public List<Integer> getIdEmpresa(Usuario usuario){
        var idEmpresa = new ArrayList<Integer>();
        try (var conn = new OracleDatabaseConfiguration().getConnection();
              var stmt = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = %s"
                        .formatted(UsuariosRepository.TB_NAME_C, TB_COLUMNS.get("CNPJ"), ((Cliente) usuario).getEmpresa().getCnpj()))){
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                idEmpresa.add(resultSet.getInt(TB_COLUMNS.get("COD_CLIENTE")));
            }
            conn.close();
        }catch (SQLException e) {
            logError(e);
        }

        return idEmpresa;
    }
    public List<Integer> getIdCargo(Usuario usuario){
        var idCargo = new ArrayList<Integer>();
        try (var conn = new OracleDatabaseConfiguration().getConnection()){
             var stmtRetrieve = conn.prepareStatement("SELECT COD_CARGO FROM "+ TB_NAME_CA+" WHERE NOME_CARGO = '%s'".formatted(((Cliente) usuario).getCargo()));
            try (var rs = stmtRetrieve.executeQuery()) {
                if (rs.next()) {
                    idCargo.add(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o COD_CARGO.");
                }
            }
            conn.close();
        }catch (SQLException e) {
                logError(e);
             }
        return idCargo;
    }

    public void create(Usuario usuario) {
        try (var conn = new OracleDatabaseConfiguration().getConnection()) {

            if (usuario instanceof Cliente){
                try (var stmt = conn.prepareStatement(
                        ("INSERT INTO %s(%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)")
                                .formatted(UsuariosRepository.TB_NAME_C,
                                        TB_COLUMNS.get("NOME_EMPRESA"),
                                        TB_COLUMNS.get("CNPJ"),
                                        TB_COLUMNS.get("SEGMENTO"),
                                        TB_COLUMNS.get("TAMANHO_EMPRESA"),
                                        TB_COLUMNS.get("PAIS")))){
                    stmt.setString(1, ((Cliente) usuario).getEmpresa().getNomeEmpresa());
                    stmt.setLong(2,((Cliente) usuario).getEmpresa().getCnpj());
                    stmt.setString(3, ((Cliente) usuario).getEmpresa().getSegmento());
                    stmt.setString(4, ((Cliente) usuario).getEmpresa().getTamanhoEmpresa());
                    stmt.setString(5, ((Cliente) usuario).getEmpresa().getPais());

                    stmt.executeUpdate();
                    logInfo("Dados inseridos na tabela "+ UsuariosRepository.TB_NAME_C +"  com sucesso!");

                } catch (SQLException e) {
                    logError(e);
                }

                try (var stmtVericador = conn.prepareStatement(
                        "SELECT COUNT(*) FROM %s WHERE %s = '%s'".formatted(UsuariosRepository.TB_NAME_CA, TB_COLUMNS.get("NOME_CARGO"), ((Cliente) usuario).getCargo())
                )){
                    var rsVerifcador = stmtVericador.executeQuery();

                    while (rsVerifcador.next()) {
                        if(rsVerifcador.getInt(1) == 0){
                            try (var stmt = conn.prepareStatement(
                                    "INSERT INTO %s(%s) VALUES (?)".formatted(UsuariosRepository.TB_NAME_CA, TB_COLUMNS.get("NOME_CARGO")))) {
                                stmt.setString(1, ((Cliente) usuario).getCargo());
                                stmt.executeUpdate();

                                logInfo("Dados inseridos na tabela "+ UsuariosRepository.TB_NAME_CA +"  com sucesso!");
                            } catch (SQLException e) {
                                logError(e);
                            }
                        }
                    }
                }

            }

            try (var stmt = conn.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                            .formatted(UsuariosRepository.TB_NAME_U,
                                    TB_COLUMNS.get("NOME_USUARIO"),
                                    TB_COLUMNS.get("SENHA"),
                                    TB_COLUMNS.get("NOME_COMPLETO"),
                                    TB_COLUMNS.get("EMAIL"),
                                    TB_COLUMNS.get("CPF"),
                                    TB_COLUMNS.get("TELEFONE"),
                                    TB_COLUMNS.get("COD_CARGO"),
                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"),
                                    TB_COLUMNS.get("COD_PERFIL"),
                                    TB_COLUMNS.get("COD_CLIENTE")
                            ))) {
                stmt.setString(1, usuario.getNomeUsuario());
                stmt.setString(2, usuario.getSenha());
                stmt.setString(3, usuario.getNomeCompleto());
                stmt.setString(4, usuario.getEmail());


                if (usuario instanceof Administrador) {
                    stmt.setNull(5, Types.NUMERIC);
                    stmt.setNull(6, Types.NUMERIC);
                    stmt.setNull(7, Types.VARCHAR);
                    stmt.setNull(8, Types.VARCHAR);
                    stmt.setInt(9, 1);
                    stmt.setNull(10, Types.NUMERIC);
                    logInfo("Administrador adicionado com sucesso");

                } else if (usuario instanceof Cliente) {
                    stmt.setLong(5, ((Cliente)usuario).getCpf());
                    stmt.setString(6, ((Cliente)usuario).getTelefone());
                    stmt.setInt(7, getIdCargo(usuario).get(0));
                    stmt.setString(8, ((Cliente) usuario).getPerguntasOuComentarios());
                    stmt.setInt(9, 2);
                    stmt.setInt(10, getIdEmpresa(usuario).get(0));
                    logInfo("Cliente adicionado com sucesso");
                }
                stmt.executeUpdate();

            } catch (SQLException e) {
                logError(e);
            }
            conn.close();
            logInfo("Dados inseridos na tabela "+ UsuariosRepository.TB_NAME_U +"  com sucesso!");
        } catch (SQLException e) {
            logError(e);
        }

    }

    public void delete(int id){
        try (var conn = new OracleDatabaseConfiguration().getConnection()) {
            try {
                var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME_C + " WHERE COD_CLIENTE IN (SELECT c.COD_CLIENTE FROM " + TB_NAME_C + " c INNER JOIN "
                        + TB_NAME_U +" u ON c.COD_CLIENTE = u.COD_CLIENTE WHERE u.COD_USUARIO = ?)");

                stmt.setInt(1, id);
                stmt.executeUpdate();
                logWarn("Empresa deletado com sucesso");
            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME_U + " WHERE COD_USUARIO = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                logWarn("Usuario deletado com sucesso");
            } catch (SQLException e) {
                logError(e);
            }


            conn.close();
        }catch (SQLException e){
            logError(e);
        }
    }

    public List<Usuario> readAllADM() {
        var administradores = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME_U));
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==1) {
                    administradores.add(new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    ));
                }
            }
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
        administradores.sort(Comparator.comparingInt(_BaseEntity::getId));
        logInfo("Lendo administradores: " + administradores);
        return administradores;
    }

    public List<Usuario> readAllCLT() {
        var clientes = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME_U));
            var resultSet = stmt.executeQuery();


            while (resultSet.next()) {
                if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==2) {

                    var empresa = new ArrayList<Empresa>();
                    var cargo = new ArrayList<String>();
                    var stmt2 = conn.prepareStatement("SELECT * FROM %s WHERE COD_CLIENTE = %s".formatted(TB_NAME_C, resultSet.getString(TB_COLUMNS.get("COD_CLIENTE"))));
                    var resultSet2 = stmt2.executeQuery();
                    while (resultSet2.next()) {
                        empresa.add(new Empresa(
                                resultSet2.getInt(TB_COLUMNS.get("COD_CLIENTE")),
                                resultSet2.getString(TB_COLUMNS.get("NOME_EMPRESA")),
                                resultSet2.getLong(TB_COLUMNS.get("CNPJ")),
                                resultSet2.getString(TB_COLUMNS.get("SEGMENTO")),
                                resultSet2.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                                resultSet2.getString(TB_COLUMNS.get("PAIS"))
                        ));
                    }

                    var stmt3 = conn.prepareStatement("SELECT NOME_CARGO FROM " + TB_NAME_CA + " WHERE COD_CARGO IN " +
                            "(SELECT c.COD_CARGO FROM " + TB_NAME_CA + " c INNER JOIN " + TB_NAME_U +
                            " u ON c.COD_CARGO = u.COD_CARGO WHERE u.COD_USUARIO = %s)"
                            .formatted(resultSet.getString(TB_COLUMNS.get("COD_USUARIO"))));
                    var resultSet3 = stmt3.executeQuery();
                    while (resultSet3.next()){
                        cargo.add(resultSet3.getString(1));
                    }

                    clientes.add(new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL")),
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            cargo.get(0),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")),
                            empresa.get(0)
                    ));
                }
            }
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
        clientes.sort(Comparator.comparingInt(_BaseEntity::getId));
        logInfo("Lendo clientes: " + clientes);
        return clientes;

    }

    public List<Usuario> readAll() {
        var usuarios = new ArrayList<Usuario>();
        try {
            var conn =  new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM %s".formatted(TB_NAME_U));
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==1) {
                    usuarios.add(new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    ));
                } else {
                    var empresa = new ArrayList<Empresa>();
                    var cargo = new ArrayList<String>();
                    var stmt2 = conn.prepareStatement("SELECT * FROM %s WHERE COD_CLIENTE = %s".formatted(TB_NAME_C, resultSet.getString(TB_COLUMNS.get("COD_CLIENTE"))));
                    var resultSet2 = stmt2.executeQuery();
                    while (resultSet2.next()) {
                        empresa.add(new Empresa(
                                resultSet.getInt(TB_COLUMNS.get("COD_CLIENTE")),
                                resultSet2.getString(TB_COLUMNS.get("NOME_EMPRESA")),
                                resultSet2.getLong(TB_COLUMNS.get("CNPJ")),
                                resultSet2.getString(TB_COLUMNS.get("SEGMENTO")),
                                resultSet2.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                                resultSet2.getString(TB_COLUMNS.get("PAIS"))
                        ));
                    }

                    var stmt3 = conn.prepareStatement("SELECT NOME_CARGO FROM " + TB_NAME_CA + " WHERE COD_CARGO IN " +
                            "(SELECT c.COD_CARGO FROM " + TB_NAME_CA + " c INNER JOIN " + TB_NAME_U +
                            " u ON c.COD_CARGO = u.COD_CARGO WHERE u.COD_USUARIO = %s)"
                                    .formatted(resultSet.getString(TB_COLUMNS.get("COD_USUARIO"))));
                    var resultSet3 = stmt3.executeQuery();
                    while (resultSet3.next()){
                        cargo.add(resultSet3.getString(1));
                    }

                    usuarios.add(new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL")),
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            cargo.get(0),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")),
                            empresa.get(0)
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

    public Optional<Usuario> read(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME_U + " WHERE COD_USUARIO = ?");
            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==1) {
                    var administrador = new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    );
                    logInfo("Lendo administrador: " + administrador);
                    return Optional.of(administrador);

                } else if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==2){
                    var empresa = new ArrayList<Empresa>();
                    var cargo = new ArrayList<String>();
                    var stmt2 = conn.prepareStatement("SELECT * FROM %s WHERE COD_CLIENTE = %s".formatted(TB_NAME_C, resultSet.getString(TB_COLUMNS.get("COD_CLIENTE"))));
                    var resultSet2 = stmt2.executeQuery();
                    while (resultSet2.next()) {
                        empresa.add(new Empresa(
                                resultSet.getInt(TB_COLUMNS.get("COD_CLIENTE")),
                                resultSet2.getString(TB_COLUMNS.get("NOME_EMPRESA")),
                                resultSet2.getLong(TB_COLUMNS.get("CNPJ")),
                                resultSet2.getString(TB_COLUMNS.get("SEGMENTO")),
                                resultSet2.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                                resultSet2.getString(TB_COLUMNS.get("PAIS"))
                        ));
                    }

                    var stmt3 = conn.prepareStatement("SELECT NOME_CARGO FROM " + TB_NAME_CA + " WHERE COD_CARGO IN " +
                            "(SELECT c.COD_CARGO FROM " + TB_NAME_CA + " c INNER JOIN " + TB_NAME_U +
                            " u ON c.COD_CARGO = u.COD_CARGO WHERE u.COD_USUARIO = %s)"
                                    .formatted(resultSet.getString(TB_COLUMNS.get("COD_USUARIO"))));
                    var resultSet3 = stmt3.executeQuery();
                    while (resultSet3.next()){
                        cargo.add(resultSet3.getString(1));
                    }

                    var cliente = new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL")),
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            cargo.get(0),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")),
                            empresa.get(0)
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

    public Optional<Usuario> readADM(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME_U + " WHERE COD_USUARIO = ?");
            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==1) {
                    var administrador = new Administrador(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL"))
                    );
                    logInfo("Lendo administrador: " + administrador);
                    return Optional.of(administrador);

                }
            }
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
        return Optional.empty();
    }
    public Optional<Usuario> readCLT(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME_U + " WHERE COD_USUARIO = ?");
            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt(TB_COLUMNS.get("COD_PERFIL"))==2){
                    var empresa = new ArrayList<Empresa>();
                    var cargo = new ArrayList<String>();
                    var stmt2 = conn.prepareStatement("SELECT * FROM %s WHERE COD_CLIENTE = %s".formatted(TB_NAME_C, resultSet.getString(TB_COLUMNS.get("COD_CLIENTE"))));
                    var resultSet2 = stmt2.executeQuery();
                    while (resultSet2.next()) {
                        empresa.add(new Empresa(
                                resultSet.getInt(TB_COLUMNS.get("COD_CLIENTE")),
                                resultSet2.getString(TB_COLUMNS.get("NOME_EMPRESA")),
                                resultSet2.getLong(TB_COLUMNS.get("CNPJ")),
                                resultSet2.getString(TB_COLUMNS.get("SEGMENTO")),
                                resultSet2.getString(TB_COLUMNS.get("TAMANHO_EMPRESA")),
                                resultSet2.getString(TB_COLUMNS.get("PAIS"))
                        ));
                    }

                    var stmt3 = conn.prepareStatement("SELECT NOME_CARGO FROM " + TB_NAME_CA + " WHERE COD_CARGO IN " +
                            "(SELECT c.COD_CARGO FROM " + TB_NAME_CA + " c INNER JOIN " + TB_NAME_U +
                            " u ON c.COD_CARGO = u.COD_CARGO WHERE u.COD_USUARIO = %s)"
                                    .formatted(resultSet.getString(TB_COLUMNS.get("COD_USUARIO"))));
                    var resultSet3 = stmt3.executeQuery();
                    while (resultSet3.next()){
                        cargo.add(resultSet3.getString(1));
                    }

                    var cliente = new Cliente(
                            resultSet.getInt(TB_COLUMNS.get("COD_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("NOME_USUARIO")),
                            resultSet.getString(TB_COLUMNS.get("SENHA")),
                            resultSet.getString(TB_COLUMNS.get("NOME_COMPLETO")),
                            resultSet.getString(TB_COLUMNS.get("EMAIL")),
                            resultSet.getLong(TB_COLUMNS.get("CPF")),
                            resultSet.getString(TB_COLUMNS.get("TELEFONE")),
                            cargo.get(0),
                            resultSet.getString(TB_COLUMNS.get("PERGUNTAS_COMENTARIOS")),
                            empresa.get(0)
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

    public void update(int id, Usuario usuario) {
        try (var conn = new OracleDatabaseConfiguration().getConnection()) {
            if (usuario instanceof Cliente){
                try (var stmt = conn.prepareStatement(
                        ("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE COD_CLIENTE IN (SELECT c.COD_CLIENTE FROM "
                                + TB_NAME_C + " c INNER JOIN " + TB_NAME_U +" u ON c.COD_CLIENTE = u.COD_CLIENTE WHERE u.COD_USUARIO = ?)")
                                .formatted(UsuariosRepository.TB_NAME_C,
                                        TB_COLUMNS.get("NOME_EMPRESA"),
                                        TB_COLUMNS.get("CNPJ"),
                                        TB_COLUMNS.get("SEGMENTO"),
                                        TB_COLUMNS.get("TAMANHO_EMPRESA"),
                                        TB_COLUMNS.get("PAIS")))){
                    stmt.setString(1, ((Cliente) usuario).getEmpresa().getNomeEmpresa());
                    stmt.setLong(2,((Cliente) usuario).getEmpresa().getCnpj());
                    stmt.setString(3, ((Cliente) usuario).getEmpresa().getSegmento());
                    stmt.setString(4, ((Cliente) usuario).getEmpresa().getTamanhoEmpresa());
                    stmt.setString(5, ((Cliente) usuario).getEmpresa().getPais());
                    stmt.setInt(6, id);

                    stmt.executeUpdate();
                    logInfo("Dados atualizados na tabela "+ UsuariosRepository.TB_NAME_C +"  com sucesso!");

                } catch (SQLException e) {
                    logError(e);
                }

            }

            try (var stmt = conn.prepareStatement(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE COD_USUARIO = ?"
                            .formatted(UsuariosRepository.TB_NAME_U,
                                    TB_COLUMNS.get("NOME_USUARIO"),
                                    TB_COLUMNS.get("SENHA"),
                                    TB_COLUMNS.get("NOME_COMPLETO"),
                                    TB_COLUMNS.get("EMAIL"),
                                    TB_COLUMNS.get("CPF"),
                                    TB_COLUMNS.get("TELEFONE"),
                                    TB_COLUMNS.get("COD_CARGO"),
                                    TB_COLUMNS.get("PERGUNTAS_COMENTARIOS"),
                                    TB_COLUMNS.get("COD_PERFIL"),
                                    TB_COLUMNS.get("COD_CLIENTE")
                            ))) {
                stmt.setString(1, usuario.getNomeUsuario());
                stmt.setString(2, usuario.getSenha());
                stmt.setString(3, usuario.getNomeCompleto());
                stmt.setString(4, usuario.getEmail());
                stmt.setInt(11, id);


                if (usuario instanceof Administrador) {
                    stmt.setNull(5, Types.NUMERIC);
                    stmt.setNull(6, Types.NUMERIC);
                    stmt.setNull(7, Types.VARCHAR);
                    stmt.setNull(8, Types.VARCHAR);
                    stmt.setInt(9, 1);
                    stmt.setNull(10, Types.NUMERIC);
                    logInfo("Administrador atualizado com sucesso");

                } else if (usuario instanceof Cliente) {



                    try (var stmtVericador = conn.prepareStatement(
                            "SELECT COUNT(*) FROM %s WHERE %s = '%s'".formatted(UsuariosRepository.TB_NAME_CA, TB_COLUMNS.get("NOME_CARGO"), ((Cliente) usuario).getCargo())
                    )){
                        var rsVerifcador = stmtVericador.executeQuery();

                        while (rsVerifcador.next()) {
                            if(rsVerifcador.getInt(1) == 0){
                                try (var stmtCargo = conn.prepareStatement(
                                        "INSERT INTO %s(%s) VALUES (?)".formatted(UsuariosRepository.TB_NAME_CA, TB_COLUMNS.get("NOME_CARGO")))) {
                                    stmtCargo.setString(1, ((Cliente) usuario).getCargo());
                                    stmtCargo.executeUpdate();

                                    logInfo("Dados inseridos na tabela "+ UsuariosRepository.TB_NAME_CA +"  com sucesso!");
                                } catch (SQLException e) {
                                    logError(e);
                                }
                            }
                        }
                    }

                    stmt.setLong(5, ((Cliente)usuario).getCpf());
                    stmt.setString(6, ((Cliente)usuario).getTelefone());
                    stmt.setInt(7, getIdCargo(usuario).get(0));
                    stmt.setString(8, ((Cliente) usuario).getPerguntasOuComentarios());
                    stmt.setInt(9, 2);
                    stmt.setInt(10, getIdEmpresa(usuario).get(0));
                    logInfo("Cliente atualizado com sucesso");
                }
                stmt.executeUpdate();

            } catch (SQLException e) {
                logError(e);
            }
            conn.close();
            logInfo("Dados atualizados na tabela "+ UsuariosRepository.TB_NAME_U +"  com sucesso!");
        } catch (SQLException e) {
            logError(e);
        }

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

}
