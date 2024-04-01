package org.example.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.entities.ServicoModel.Plano;
import org.example.entities.ServicoModel.Produto;
import org.example.entities._BaseEntity;
import org.example.infrastructure.OracleDatabaseConnection;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProdutosRepository implements _BaseRepository<Produto>, _Logger<String> {
    Gson gson = new Gson();

    public static final String TB_NAME = "PRODUTO_JAVA";

    public void initialize() {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement(
                    ("CREATE TABLE " + TB_NAME + " (ID NUMBER GENERATED AS IDENTITY CONSTRAINT PRODUTOS_PK PRIMARY KEY, " +
                            "NOME VARCHAR2(60) NOT NULL, " +
                            "DESCRICAO VARCHAR2(150) NOT NULL, " +
                            "PLANO_PAGAMENTO CLOB, " +
                            "SUCESS_PLANS CLOB)" ));
            stmt.executeUpdate();
            logInfo("Tabela "+ TB_NAME +" criada com sucesso!");
            conn.close();
        } catch (SQLException e) {
            logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
        }
    }

    public void shutdown() {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("DROP TABLE %s".formatted(TB_NAME));
            stmt.executeUpdate();
            logWarn("Tabela "+ TB_NAME +" excluída com sucesso!");
            conn.close();
        } catch (SQLException e) {
            logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
        }
    }


    public void create(Produto produto){
        try{var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME, DESCRICAO, PLANO_PAGAMENTO, SUCESS_PLANS) VALUES (?,?,?,?)");
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());

            if (produto.getPlanoPagamento()==null){
                stmt.setNull(3, Types.CLOB);
            }
            //Planos de pagamento nao devem conter o mesmo nome
            else {
                produto.getPlanoPagamento().forEach(new PlanosRepository()::create);
                ArrayList<Plano> planosRepositoryAtt = new ArrayList<>();
                produto.getPlanoPagamento().forEach(pln -> {
                    Optional<Plano> planoOptional = new PlanosRepository().readByName(pln);
                    if (planoOptional.isPresent()) {
                        Plano plano = planoOptional.get();
                        planosRepositoryAtt.add(plano);
                    }
                });
                stmt.setString(3, gson.toJson(planosRepositoryAtt));

            }

            if (produto.getSucessPlans()==null){
                stmt.setNull(4, Types.CLOB);
            }
            else {
                new PlanosRepository().create(produto.getSucessPlans());
                stmt.setString(4, gson.toJson(produto.getSucessPlans()));
            }

            stmt.executeUpdate();
            logInfo("Produto adicionado com sucesso");
            conn.close();
        }
        catch (SQLException e) {
            logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
        }
    }

    public void delete(int id){
        try{var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logWarn("Produto deletado com sucesso");
            conn.close();
        }
        catch (SQLException e) {
            logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
        }
    }
    public List<Produto> readAll(){
    var produtos = new ArrayList<Produto>();
    try{var conn = new OracleDatabaseConnection().getConnection();
        var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID");
        var rs = stmt.executeQuery();
        while(rs.next()){
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
        }
        conn.close();
    }
    catch (SQLException e) {
//        System.err.println("Erro ao ler produtos: " + e.getMessage());
        logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
    }
    produtos.sort(Comparator.comparingInt(_BaseEntity::getId));
    logInfo("Lendo produtos: " + produtos);
    return produtos;
}

    public Optional<Produto> read(int id){
        try{var conn = new OracleDatabaseConnection().getConnection();
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
        catch (Exception e){
            logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
        }

        return Optional.empty();
    }


    public void update(int id, Produto produto){
        try{var conn = new OracleDatabaseConnection().getConnection();
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
            logError("%s - %s".formatted(e.getMessage(), e.getStackTrace()));
        }
    }




}
