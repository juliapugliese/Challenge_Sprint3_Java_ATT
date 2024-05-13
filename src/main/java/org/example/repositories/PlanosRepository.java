package org.example.repositories;

import org.example.entities.ServicoModel.Plano;
import org.example.entities._BaseEntity;
import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PlanosRepository extends Starter implements _BaseRepository<Plano>, _Logger<String> {
    public PlanosRepository() {
    }

    public static final String TB_NAME = "PLANO_JAVA";
    public static final String TB_NAME_T = "TIPO_PLANO_JAVA";


//    public void initialize() {
//        try {
//            var conn =  new OracleDatabaseConfiguration().getConnection();
//            var stmt = conn.prepareStatement(
//                    ("CREATE TABLE " + TB_NAME + " (ID NUMBER GENERATED AS IDENTITY CONSTRAINT PLANOS_PK PRIMARY KEY, " +
//                            "NOME VARCHAR2(60) NOT NULL, " +
//                            "DESCRICAO VARCHAR2(150) NOT NULL, " +
//                            "RECURSOS VARCHAR2(150), " +
//                            "PRECO DECIMAL(9,2))" ));
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
            var stmt = conn.prepareStatement("DROP TABLE %s".formatted(TB_NAME));
            stmt.executeUpdate();
            logWarn("Tabela "+ TB_NAME +" excluída com sucesso!");
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
    }


    public List<Plano> readAll(){
        var planos = new ArrayList<Plano>();
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID");
            var rs = stmt.executeQuery();
            while(rs.next()){
                planos.add(new Plano(
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        rs.getString("RECURSOS"),
                        rs.getFloat("PRECO")));
            }

            conn.close();
        }

        catch (SQLException e) {
            logError(e);
        }
        planos.sort(Comparator.comparingInt(_BaseEntity::getId));
        logInfo("Lendo planos: " + planos);
        return planos;
    }

    public Optional<Plano> read(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?");

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                var plano = new Plano(
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        rs.getString("RECURSOS"),
                        rs.getFloat("PRECO"));
                logInfo("Lendo plano: " + plano);
                return Optional.of(plano);
            }
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
        return Optional.empty();
    }

    public Optional<Plano> getByName(Plano plano){
        try {var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE NOME = ?");

            stmt.setString(1, plano.getNomePlano());
            var rs = stmt.executeQuery();
            if(rs.next()){
                var optional = Optional.of(new Plano(
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        rs.getString("RECURSOS"),
                        rs.getFloat("PRECO")));
                logInfo("Lendo plano: " + optional.get());
                return optional;
            }
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
        return Optional.empty();
    }
    public void create(Plano plano){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME, DESCRICAO, RECURSOS, PRECO) VALUES (?,?,?,?)");
            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getDescricaoPlano());
            stmt.setString(3, plano.getRecursosPlano());
            stmt.setFloat(4, plano.getPrecoPlano());
            stmt.executeUpdate();

            logInfo("Plano adicionado com sucesso");
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }

    public void update(int id, Plano plano){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("UPDATE "+ TB_NAME + " SET NOME = ?, DESCRICAO = ?, RECURSOS = ?, PRECO = ? WHERE ID = ?");

            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getDescricaoPlano());
            stmt.setString(3, plano.getRecursosPlano());
            stmt.setFloat(4, plano.getPrecoPlano());
            stmt.setInt(5, id);
            stmt.executeUpdate();

            logWarn("Plano atualizado com sucesso!");
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }

    public void delete(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logWarn("Plano deletado com sucesso");
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }

}
