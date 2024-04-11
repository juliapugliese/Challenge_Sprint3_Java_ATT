package org.example.services;

import org.example.entities.ServicoModel.Plano;
import org.example.repositories.PlanosRepository;

public class PlanoService {
    private final PlanosRepository planoRepository;

    public PlanoService(){
        planoRepository = new PlanosRepository();
    }

    public void create(Plano plano){
        var validation = plano.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            planoRepository.create(plano);
    }

    public void update(int id, Plano plano){
        var validation = plano.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            planoRepository.update(id, plano);
    }
    public void delete(int id){
        planoRepository.delete(id);
    }

//    public void delete(Plano plano){
//        var validation = plano.validate();
//
//        if(validation.containsKey(false))
//            throw new IllegalArgumentException(validation.get(false).toString());
//        else
//            planoRepository.delete(plano.getId());
//    }
//
//    public void delete(int id){
//        var validation = planoRepository.read(id).get().validate();
//
//        if(validation.containsKey(false))
//            throw new IllegalArgumentException(validation.get(false).toString());
//        else
//            planoRepository.delete(id);
//    }
}
