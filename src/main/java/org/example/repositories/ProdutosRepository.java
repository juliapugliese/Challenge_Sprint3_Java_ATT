package org.example.repositories;

import org.example.entities.ServicoModel.Plano;
import org.example.entities.ServicoModel.Produto;
import org.example.infrastructure.OracleDatabaseConnection;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutosRepository implements _BaseRepository<Produto> {

    public static final String TB_NAME = "PRODUTO_JAVA";

    public void initialize() {
        try {
            var conn =  new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement(
                    ("CREATE TABLE " + TB_NAME + " (ID NUMBER GENERATED AS IDENTITY CONSTRAINT PRODUTOS_PK PRIMARY KEY, " +
                            "NOME VARCHAR2(60) NOT NULL, " +
                            "DESCRICAO VARCHAR2(150) NOT NULL, " +
                            "PLANO_PAGAMENTO VARCHAR2(1000), " +
                            "SUCESS_PLANS VARCHAR2(1000))" ));
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



    public List<Produto> readAll(){
        var produtos = new ArrayList<Produto>();
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID")){
            var rs = stmt.executeQuery();
            while(rs.next()){
                produtos.add(new Produto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        (List<Plano>) rs.getArray("PLANO_PAGAMENTO"),
                        (Plano) rs.getObject("SUCESS_PLANS")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return produtos;
    }

    public Optional<Produto> read(int id){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ){
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Produto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        (List<Plano>) rs.getArray("PLANO_PAGAMENTO"),
                        (Plano) rs.getObject("SUCESS_PLANS")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void create(Produto produto){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME, DESCRICAO, PLANO_PAGAMENTO, SUCESS_PLANS) VALUES (?,?,?,?)")){
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());
            stmt.setArray(3, (Array) produto.getPlanoPagamento());
            stmt.setObject(4, produto.getSucessPlans());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Produto produto){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("UPDATE "+ TB_NAME + " SET NOME = ?, DESCRICAO = ?, PLANO_PAGAMENTO = ?, SUCESS_PLANS = ?,  WHERE ID = ?"))
        {
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());
            stmt.setArray(3, (Array) produto.getPlanoPagamento());
            stmt.setObject(4, produto.getSucessPlans());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?")){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
