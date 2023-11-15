package org.br.mineradora.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.entity.QuotationEntity;

//@ApplicationScoped: Quando o quarkus fizer o start da app, ela sabe que essa classe administrada pelo framework
@ApplicationScoped

//PanacheRepository: É implementacao de um classe onde terá metodos para trabalhar com o banco de dados
public class QuotationRepository implements PanacheRepository <QuotationEntity> {

}
