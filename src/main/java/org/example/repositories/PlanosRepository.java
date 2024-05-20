package org.example.repositories;

import org.example.entities.ServicoModel.Plano;
import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanosRepository extends Starter implements _BaseRepository<Plano>, _Logger<String> {
    public PlanosRepository() {
    }

    public static final String TB_NAME = "PLANO_JAVA";
    public static final String TB_NAME_T = "TIPO_PLANO_JAVA";


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

    public void delete(int id){
        try (var conn = new OracleDatabaseConfiguration().getConnection()) {
            var stmt = conn.prepareStatement("DELETE FROM " + PlanosRepository.TB_NAME + " WHERE COD_PLANO = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logWarn("Plano de pagamento deletado com sucesso");
        } catch (SQLException e) {
            logError(e);
        }

    }


    @Override
    public List<Plano> readAll(String orderBy, String direction, int limit, int offset) {
        var planos = new ArrayList<Plano>();
        try {
            var conn = new OracleDatabaseConfiguration().getConnection();
            var stmtPlano = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY " + orderBy + " " +
                    (direction == null || direction.isEmpty() ? "ASC" : direction)
                    + " OFFSET " + offset + " ROWS FETCH NEXT " + (limit == 0 ? 10 : limit) + " ROWS ONLY");
            var resultSetPlano = stmtPlano.executeQuery();

            while (resultSetPlano.next()) {

                var stmtTipoPlano = conn.prepareStatement("SELECT TIPO FROM " + PlanosRepository.TB_NAME_T + " WHERE COD_TIPO_PLANO IN " +
                        "(SELECT COD_TIPO_PLANO FROM " + PlanosRepository.TB_NAME + " WHERE COD_PLANO = %s)"
                        .formatted(resultSetPlano.getString("COD_PLANO")));
                var resultSetTipoPlano = stmtTipoPlano.executeQuery();
                while (resultSetTipoPlano.next()){
                    if(resultSetTipoPlano.getString(1).equalsIgnoreCase("PLANO DE PAGAMENTO")){
                        planos.add(new Plano(
                                resultSetPlano.getInt("COD_PLANO"),
                                resultSetPlano.getString("NOME"),
                                resultSetPlano.getString("DESCRICAO"),
                                resultSetPlano.getString("RECURSOS"),
                                resultSetPlano.getFloat("PRECO"),
                                resultSetTipoPlano.getString(1)
                        ));
                    }
                    else if (resultSetTipoPlano.getString(1).equalsIgnoreCase("PLANO DE SUCESSO")){
                        planos.add(new Plano(
                                resultSetPlano.getInt("COD_PLANO"),
                                resultSetPlano.getString("NOME"),
                                resultSetPlano.getString("DESCRICAO"),
                                resultSetPlano.getString("RECURSOS"),
                                resultSetPlano.getFloat("PRECO"),
                                resultSetTipoPlano.getString(1)
                        ));
                    }

                }
            }

        } catch(SQLException e){
            logError(e);
        }
        return planos;
    }

    @Override
    public Optional<Plano> read(int id) {
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE COD_PLANO = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()){

                var stmtTipoPlano = conn.prepareStatement("SELECT TIPO FROM " + PlanosRepository.TB_NAME_T + " WHERE COD_TIPO_PLANO IN " +
                        "(SELECT COD_TIPO_PLANO FROM " + PlanosRepository.TB_NAME + " WHERE COD_PLANO = %s)"
                        .formatted(rs.getString("COD_PLANO")));
                var resultSetTipoPlano = stmtTipoPlano.executeQuery();
                while (resultSetTipoPlano.next()){
                    if(resultSetTipoPlano.getString(1).equalsIgnoreCase("PLANO DE PAGAMENTO")){
                        var planoPagamento = new Plano(
                                rs.getInt("COD_PLANO"),
                                rs.getString("NOME"),
                                rs.getString("DESCRICAO"),
                                rs.getString("RECURSOS"),
                                rs.getFloat("PRECO"),
                                resultSetTipoPlano.getString(1)
                        );
                        return Optional.of(planoPagamento);
                    }
                    else if (resultSetTipoPlano.getString(1).equalsIgnoreCase("PLANO DE SUCESSO")){
                        var planoSucesso = new Plano(
                                rs.getInt("COD_PLANO"),
                                rs.getString("NOME"),
                                rs.getString("DESCRICAO"),
                                rs.getString("RECURSOS"),
                                rs.getFloat("PRECO"),
                                resultSetTipoPlano.getString(1)
                        );
                        return Optional.of(planoSucesso);
                    }

                }
            }
        }
        catch (SQLException e) {
            logError(e);
        }

        return Optional.empty();
    }

    public void update(int id, Plano plano){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("UPDATE "+ TB_NAME + " SET NOME = ?, DESCRICAO = ?, RECURSOS = ?, PRECO = ? WHERE COD_PLANO = ?");

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


}
