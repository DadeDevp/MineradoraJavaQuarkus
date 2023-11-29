package org.br.mineradora.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.entity.ProposalEntity;

import java.util.Optional;

@ApplicationScoped

public class ProposalRepository implements PanacheRepository<ProposalEntity> {

    //Como se fosse:
    //SELECT * FROM proposal WHERE customer = "nome"
    public Optional<ProposalEntity> findByCustomer(String customer){
        return Optional.of(find("customer", customer).firstResult());
    }
}
/*
  Classe ProposalRepository.

  Esta classe é um repositório no contexto do framework Quarkus, utilizando o Panache ORM,
  que é uma camada de abstração sobre o JPA (Java Persistence API). A classe está marcada
  com a anotação @ApplicationScoped, indicando que ela é gerenciada pelo contêiner CDI
  (Contexts and Dependency Injection) do Quarkus e que haverá uma única instância dessa
  classe no ciclo de vida da aplicação (scope de aplicação).

  Implementa a interface PanacheRepository<ProposalEntity>, o que significa que esta classe
  fornecerá operações de banco de dados para entidades do tipo ProposalEntity. A interface
  PanacheRepository fornece vários métodos úteis para persistência e consulta de dados,
  simplificando a interação com o banco de dados.

  Método findByCustomer:
  Este método é uma adição específica ao repositório que permite a busca de uma proposta
  (ProposalEntity) com base no nome do cliente. A assinatura do método indica que ele aceita
  uma string 'customer' como parâmetro e retorna um Optional<ProposalEntity>. O uso de Optional
  aqui é uma boa prática, pois permite lidar de forma mais elegante com situações onde a
  proposta não é encontrada (evitando NullPointerException).

  A implementação do método findByCustomer utiliza o método 'find' herdado de PanacheRepository.
  A string "customer" usada como primeiro argumento em 'find' refere-se ao nome do campo na
  entidade ProposalEntity que se deseja filtrar. O segundo argumento, 'customer', é o valor
  passado para o método, que será utilizado na consulta.

  O método 'firstResult()' é chamado após 'find' para retornar apenas o primeiro resultado
  da consulta. O método 'firstResult()' em si retorna um objeto ProposalEntity, que é então
  encapsulado em um Optional usando 'Optional.of()'. Se nenhum resultado for encontrado,
  'firstResult()' retornará null, o que pode causar uma exceção ao usar 'Optional.of()'.
  Portanto, pode ser mais seguro usar 'firstResultOptional()' para lidar com a possibilidade
  de não encontrar resultados.

  Em resumo, esta classe é um componente central para a manipulação de dados relacionados
  a entidades ProposalEntity, utilizando os recursos do Quarkus e Panache para simplificar
  o código de persistência de dados.
 */

