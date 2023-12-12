package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.OpportunityEntity;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.repository.OpportunityRepository;
import org.br.mineradora.repository.QuotationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OpportunityServiceImpl implements OpportunityService {

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    OpportunityRepository opportunityRepository;

    //Busca informacoes da cotacao do dolar e infos sobre a proposta e juntar essas infos para criar uma nova oportunidade
    @Override
    public void buildOpportunity(ProposalDTO proposal) {

        List<QuotationEntity> quotationEntities = quotationRepository.findAll().list();
        Collections.reverse(quotationEntities);

        OpportunityEntity opportunity = new OpportunityEntity();
        opportunity.setDate(new Date());
        opportunity.setProposalId(proposal.getProposalId());
        opportunity.setCustomer(proposal.getCustomer());
        opportunity.setPriceTonne(proposal.getPriceTonne());
        opportunity.setLastDollarQuotation(quotationEntities.get(0).getCurrencyPrice());

        opportunityRepository.persist(opportunity);

    }

    @Transactional
    @Override
    public void saveQuotation(QuotationDTO quotation) {

        QuotationEntity createQuotation = new QuotationEntity();
        createQuotation.setDate(new Date());
        createQuotation.setCurrencyPrice(quotation.getCurrencyPrice());

        quotationRepository.persist(createQuotation);

    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {

        List<OpportunityDTO> opportunities = new ArrayList<>();
        opportunityRepository
                .findAll()
                .stream()
                .forEach(item->{
                    opportunities.add(OpportunityDTO.builder()
                            .proposalId(item.getProposalId())
                            .customer(item.getCustomer())
                            .priceTonne(item.getPriceTonne())
                            .lastDollarQuotation(item.getLastDollarQuotation())
                            .build());
                });
        return opportunities;

    }
}


/*
         Método generateOpportunityData.

         Este método é responsável por gerar uma lista de DTOs (Data Transfer Objects) de oportunidades,
         que são usados para transferir dados de oportunidades de negócios dentro da aplicação. O método
         sobrescreve uma declaração de interface, indicado pela anotação @Override.

          Funcionamento:
          1. Inicializa uma lista vazia de OpportunityDTO chamada 'opportunities'.
          2. Utiliza o opportunityRepository para buscar todas as entidades de oportunidades disponíveis
             no banco de dados. O método 'findAll' do repositório retorna uma lista dessas entidades.
          3. Converte essa lista em um Stream para processamento eficiente.
          4. Para cada item (representando uma entidade de oportunidade) no Stream:
             a. Constrói um objeto OpportunityDTO usando o padrão Builder. Isso é feito para facilitar
                a criação do objeto DTO, permitindo a definição clara e flexível de seus atributos.
             b. Configura o DTO com valores retirados da entidade de oportunidade (item), como ID da proposta,
                cliente, preço por tonelada, e a última cotação do dólar.
             c. Adiciona o DTO construído à lista 'opportunities'.
          5. Retorna a lista 'opportunities' preenchida com os DTOs gerados.

          O uso do padrão Builder no processo de construção do DTO ajuda a manter o código limpo e legível,
          especialmente útil quando se lida com objetos que têm vários atributos. Além disso, a abordagem de
          streaming para processar a lista de entidades é eficiente e elegante, especialmente para grandes
          conjuntos de dados.

          Este método é fundamental para a lógica de negócios da aplicação, pois fornece uma maneira de
          transformar entidades de dados brutos em objetos DTO que podem ser facilmente manipulados e
          transmitidos dentro da aplicação ou para camadas externas, como interfaces de usuário ou APIs.
         */
