package org.example.testeDesabilitar;

import org.example.entities._BaseEntity;
import org.example.repositories._BaseRepository;
import org.example.repositories._Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class _BaseRepositoryImpl<T extends _BaseEntity> implements _BaseRepository<T>, _Logger<String> {
    protected List<T> entidades = new ArrayList<T>();
    static Scanner scanner = new Scanner(System.in);

    @Override
    public void create(T obj) {
        entidades.add(obj);
        logInfo( this.getClass() + " criada: " + obj);
    }

    @Override
    public List<T> readAll() {
        logInfo("Consultando a lista de " + this.getClass());
        return entidades;
    }

    @Override
    public void update(int id, T obj) {
        var index = entidades.stream().filter(e -> e.getId() == obj.getId()).findFirst();
        if(index.isPresent()){
            var entidadeAntiga = index.get();
            entidadeAntiga = obj;
            logInfo(this.getClass() + "atualizada de: "+ entidadeAntiga + " para: " + obj);
        }
        else
            logWarn("Entidade não encontrada");
    }

    @Override
    public void delete(int id) {
        entidades.removeIf(e -> e.getId() == id);
        logInfo(this.getClass() + "deletada: " + id);
    }

    public T findById(int id){
        return entidades.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}
