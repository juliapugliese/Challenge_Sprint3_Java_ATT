package org.example.repositories;

import org.example.entities.ServicoModel.Plano;
import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class PlanosRepository extends Starter implements _BaseRepository<Plano>, _Logger<String> {
    public PlanosRepository() {
    }

    public static final String TB_NAME = "PLANO_JAVA";
    public static final String TB_NAME_T = "TIPO_PLANO_JAVA";



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

    public void create(Plano plano){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME, DESCRICAO, RECURSOS, PRECO, COD_PRODUTO, COD_TIPO_PLANO) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getDescricaoPlano());
            stmt.setString(3, plano.getRecursosPlano());
            stmt.setFloat(4, plano.getPrecoPlano());

            if(plano.getTipo().equalsIgnoreCase("SucessPlan")){
                stmt.setNull(5, Types.NULL);
                stmt.setInt(6, 2);
            }
            stmt.executeUpdate();

            logInfo("Plano adicionado com sucesso");
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }

    @Override
    public List<Plano> readAll(String orderBy, String direction, int limit, int offset) {
        return null;
    }

    @Override
    public Optional<Plano> read(int id) {
        return Optional.empty();
    }

//    public List<Plano> readAll(){
//        var planos = new ArrayList<Plano>();
//        try{var conn = new OracleDatabaseConfiguration().getConnection();
//            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID");
//            var rs = stmt.executeQuery();
//            while(rs.next()){
//                planos.add(new Plano(
//                        rs.getString("NOME"),
//                        rs.getString("DESCRICAO"),
//                        rs.getString("RECURSOS"),
//                        rs.getFloat("PRECO")));
//            }
//
//            conn.close();
//        }
//
//        catch (SQLException e) {
//            logError(e);
//        }
//        planos.sort(Comparator.comparingInt(_BaseEntity::getId));
//        logInfo("Lendo planos: " + planos);
//        return planos;
//    }
//
//    public Optional<Plano> read(int id){
//        try{var conn = new OracleDatabaseConfiguration().getConnection();
//            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?");
//
//            stmt.setInt(1, id);
//            var rs = stmt.executeQuery();
//            if(rs.next()){
//                var plano = new Plano(
//                        rs.getString("NOME"),
//                        rs.getString("DESCRICAO"),
//                        rs.getString("RECURSOS"),
//                        rs.getFloat("PRECO"));
//                logInfo("Lendo plano: " + plano);
//                return Optional.of(plano);
//            }
//            conn.close();
//        }
//        catch (SQLException e) {
//            logError(e);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<Plano> getByName(Plano plano){
//        try {var conn = new OracleDatabaseConfiguration().getConnection();
//            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE NOME = ?");
//
//            stmt.setString(1, plano.getNomePlano());
//            var rs = stmt.executeQuery();
//            if(rs.next()){
//                var optional = Optional.of(new Plano(
//                        rs.getString("NOME"),
//                        rs.getString("DESCRICAO"),
//                        rs.getString("RECURSOS"),
//                        rs.getFloat("PRECO")));
//                logInfo("Lendo plano: " + optional.get());
//                return optional;
//            }
//            conn.close();
//        }
//        catch (SQLException e) {
//            logError(e);
//        }
//        return Optional.empty();
//    }

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
