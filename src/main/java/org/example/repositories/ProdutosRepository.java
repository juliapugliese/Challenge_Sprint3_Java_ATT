package org.example.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.entities.ServicoModel.Plano;
import org.example.entities.ServicoModel.Produto;
import org.example.entities._BaseEntity;
import org.example.infrastructure.OracleDatabaseConfiguration;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProdutosRepository extends Starter implements _BaseRepository<Produto>, _Logger<String> {
    Gson gson = new Gson();

    public static final String TB_NAME = "PRODUTO_JAVA";
    public static final String TB_NAME_I = "ITEM_COMPRA_JAVA";

    public List<Integer> getIdProduto(Produto produto){
        var idProduto = new ArrayList<Integer>();
        try {var conn = new OracleDatabaseConfiguration().getConnection();
            var stmtGetId = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = '%s'"
                        .formatted(TB_NAME, "NOME", produto.getNomeProduto()));{
                var resultSet = stmtGetId.executeQuery();
                while (resultSet.next()) {
                    idProduto.add(resultSet.getInt("COD_PRODUTO"));
                }
            }
            conn.close();
        }catch (SQLException e) {
            logError(e);
        }
        return idProduto;
    }

    public void create(Produto produto){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME, DESCRICAO) VALUES (?,?)");
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());
            stmt.executeUpdate();
            logInfo("Produto adicionado com sucesso");

            produto.getPlanoPagamento().forEach(pln ->{
                try (var stmtPlano =  conn.prepareStatement("INSERT INTO " + PlanosRepository.TB_NAME +
                        " (NOME, DESCRICAO, RECURSOS, PRECO, COD_PRODUTO, COD_TIPO_PLANO) " +
                        "VALUES (?,?,?,?,?,?)")){
                    stmtPlano.setString(1, pln.getNomePlano());
                    stmtPlano.setString(2, pln.getDescricaoPlano());
                    stmtPlano.setString(3, pln.getRecursosPlano());
                    stmtPlano.setFloat(4, pln.getPrecoPlano());
                    stmtPlano.setInt(5, getIdProduto(produto).get(0));
                    stmtPlano.setInt(6, 1);

                    stmtPlano.executeUpdate();
                    logInfo("Dados inseridos na tabela "+ PlanosRepository.TB_NAME +" com sucesso!");

                } catch (SQLException e) {
                    logError(e);
                }}
            );

            if (produto.getSucessPlans()!=null){
                new PlanosRepository().create(produto.getSucessPlans());
            }

            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }

    public void delete(int id){
        try (var conn = new OracleDatabaseConfiguration().getConnection()) {
            try {var stmt = conn.prepareStatement("DELETE FROM " + PlanosRepository.TB_NAME + " WHERE COD_PRODUTO = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                logWarn("Plano de pagamento deletado com sucesso");
            } catch (SQLException e) {
                logError(e);
            }

            try {
                var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE COD_PRODUTO = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                logWarn("Produto deletado com sucesso");
                conn.close();
            } catch (SQLException e) {
                logError(e);
            }
        }catch (SQLException e) {
            logError(e);
        }
    }
    public List<Produto> readAll(String orderBy, String direction, int limit, int offset){
    var produtos = new ArrayList<Produto>();
    try{var conn = new OracleDatabaseConfiguration().getConnection();
        var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY COD_PRODUTO" + " ORDER BY " + orderBy + " " +
                (direction == null || direction.isEmpty() ? "ASC" : direction)
                + " OFFSET "+offset+" ROWS FETCH NEXT "+ (limit == 0 ? 10 : limit) +" ROWS ONLY");
        var rs = stmt.executeQuery();
        while(rs.next()){
            var planosPagamento = new ArrayList<Plano>();
            var planosSucesso = new ArrayList<Plano>();

            var stmtPlano = conn.prepareStatement("SELECT * FROM %s WHERE COD_PRODUTO = %s".formatted(PlanosRepository.TB_NAME, rs.getString("COD_PRODUTO")));
            var resultSetPlano = stmtPlano.executeQuery();

            while (resultSetPlano.next()) {

                var stmtTipoPlano = conn.prepareStatement("SELECT TIPO FROM " + PlanosRepository.TB_NAME_T + " WHERE COD_TIPO_PLANO IN " +
                        "(SELECT COD_TIPO_PLANO FROM " + PlanosRepository.TB_NAME + " WHERE COD_PLANO = %s)"
                        .formatted(resultSetPlano.getString("COD_PLANO")));
                var resultSetTipoPlano = stmtTipoPlano.executeQuery();
                while (resultSetTipoPlano.next()){
                    if(resultSetTipoPlano.getString(1).equalsIgnoreCase("PLANO DE PAGAMENTO")){
                        planosPagamento.add(new Plano(
                                resultSetPlano.getInt("COD_PLANO"),
                                resultSetPlano.getString("NOME"),
                                resultSetPlano.getString("DESCRICAO"),
                                resultSetPlano.getString("RECURSOS"),
                                resultSetPlano.getFloat("PRECO"),
                                resultSetTipoPlano.getString(1)
                        ));
                    }
                    else if (resultSetTipoPlano.getString(1).equalsIgnoreCase("PLANO DE SUCESSO")){
                        planosSucesso.add(new Plano(
                                resultSetPlano.getString("NOME"),
                                resultSetPlano.getString("DESCRICAO"),
                                resultSetPlano.getString("RECURSOS"),
                                resultSetPlano.getFloat("PRECO"),
                                resultSetTipoPlano.getString(1)
                        ));
                    }
                }


            }

            Produto produto = new Produto();
            produto.setId(rs.getInt("COD_PRODUTO"));
            produto.setNomeProduto(rs.getString("NOME"));
            produto.setDescricaoProduto(rs.getString("DESCRICAO"));
            if (planosPagamento.isEmpty()){
                produto.setPlanoPagamento(null);
            }
            else {produto.setPlanoPagamento(planosPagamento);}
            if (planosSucesso.isEmpty()){
                produto.setSucessPlans(null);
            }
            else {produto.setSucessPlans(planosSucesso.get(0));}

            produtos.add(produto);
        }
        conn.close();
    }
    catch (SQLException e) {
//        System.err.println("Erro ao ler produtos: " + e.getMessage());
        logError(e);

    }
    produtos.sort(Comparator.comparingInt(_BaseEntity::getId));
    logInfo("Lendo produtos: " + produtos);
    return produtos;
}

//TESTAR FUNCAO
    public List<Produto> readAllByPlano(int idPlano){
        var produtos = new ArrayList<Produto>();
        try(var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE IDPLANO = ?");){
            stmt.setInt(1, idPlano);
            var rs = stmt.executeQuery();
            while (rs.next()){

                Produto produto = new Produto();
                produto.setId(rs.getInt("ID"));
                produto.setNomeProduto(rs.getString("NOME"));
                produto.setDescricaoProduto(rs.getString("DESCRICAO"));

                String jsonPlanoPagamento = rs.getString("PLANO_PAGAMENTO");
                if (jsonPlanoPagamento != null) {
                    produto.setPlanoPagamento(gson.fromJson(jsonPlanoPagamento, new TypeToken<ArrayList<Plano>>(){}.getType()));
                }

                String jsonSuccessPlans = rs.getString("SUCESS_PLANS");
                if (jsonSuccessPlans != null) {
                    produto.setSucessPlans(gson.fromJson(jsonSuccessPlans, Plano.class));
                }
                produtos.add(produto);
                logInfo("Lendo produto: " + produto);
            }
        }
        catch (SQLException e) {
            logError(e);
        }
        return produtos;
    }



    public Optional<Produto> read(int id){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?");

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("ID"));
                produto.setNomeProduto(rs.getString("NOME"));
                produto.setDescricaoProduto(rs.getString("DESCRICAO"));

                String jsonPlanoPagamento = rs.getString("PLANO_PAGAMENTO");
                if (jsonPlanoPagamento != null) {
                    produto.setPlanoPagamento(gson.fromJson(jsonPlanoPagamento, new TypeToken<ArrayList<Plano>>(){}.getType()));
                }

                String jsonSuccessPlans = rs.getString("SUCESS_PLANS");
                if (jsonSuccessPlans != null) {
                    produto.setSucessPlans(gson.fromJson(jsonSuccessPlans, Plano.class));
                }
                logInfo("Lendo produto: " + produto);
                return Optional.of(produto);

            }
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }

        return Optional.empty();
    }


    public void update(int id, Produto produto){
        try{var conn = new OracleDatabaseConfiguration().getConnection();
            var stmt = conn.prepareStatement("UPDATE "+ TB_NAME + " SET NOME = ?, DESCRICAO = ?, PLANO_PAGAMENTO = ?, SUCESS_PLANS = ?  WHERE ID = ?");

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());

            if (produto.getPlanoPagamento()==null){
                stmt.setNull(3, Types.CLOB);
            }
            //manter o id dos planos ao atualizar o produto, caso contrario o plano não vai ser atualizado
            else {
                produto.getPlanoPagamento().forEach(pln -> new PlanosRepository().update(pln.getId(), pln));
                stmt.setString(3, gson.toJson(produto.getPlanoPagamento()));
            }

            if (produto.getSucessPlans()==null){
                stmt.setNull(4, Types.CLOB);
            }
            else {
                new PlanosRepository().update(produto.getSucessPlans().getId(), produto.getSucessPlans());
                stmt.setString(4, gson.toJson(produto.getSucessPlans()));
            }

            stmt.setInt(5, id);
            stmt.executeUpdate();
            logWarn("Produto atualizado com sucesso!");
            conn.close();
        }
        catch (SQLException e) {
            logError(e);
        }
    }



}
