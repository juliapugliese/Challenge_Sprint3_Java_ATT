package org.example.repositories;

import org.example.entities.ServicoModel.Plano;
import org.example.infrastructure.OracleDatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanosRepository extends _BaseRepositoryImpl<Plano>{

    public static final String TB_NAME = "PLANO";

    public List<Plano> getAll(){
        var planos = new ArrayList<Plano>();
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID")){
            var rs = stmt.executeQuery();
            while(rs.next()){
                planos.add(new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        rs.getString("RECURSOS"),
                        rs.getFloat("PRECO")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return planos;
    }

    public Optional<Plano> get(int id){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ){
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getString("DESCRICAO"),
                        rs.getString("RECURSOS"),
                        rs.getFloat("PRECO")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void create(Plano plano){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (NOME, DESCRICAO, RECURSOS, PRECO) VALUES (?,?,?,?)")){
            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getDescricaoPlano());
            stmt.setString(3, plano.getRecursosPlano());
            stmt.setFloat(4, plano.getPrecoPlano());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Plano plano){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("UPDATE "+ TB_NAME + " SET NOME = ?, DESCRICAO = ?, RECURSOS = ?, PRECO = ? WHERE ID = ?");)
        {
            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getDescricaoPlano());
            stmt.setString(3, plano.getRecursosPlano());
            stmt.setFloat(4, plano.getPrecoPlano());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Plano plano){
        try(var conn = new OracleDatabaseConnection().getConnection();
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?")){
            stmt.setInt(1, plano.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
