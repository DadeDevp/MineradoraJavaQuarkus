package org.br.mineradora.message;

import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.service.OpportunityService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaEvent {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    OpportunityService opportunityService;

    //@Incoming é um consumer do Kafka, neste caso ele está consumindo o tópico proposal
    @Incoming("proposal")
    @Transactional //É transactional pq vou salvar a oportunidade no banco de dados
    public void receiveProposal(ProposalDTO proposal){
        LOG.info("-- Recebendo Nova Proposta do tópico Kafka --");
        opportunityService.buildOpportunity(proposal);
    }

    @Incoming("quotation")
    @Blocking
    public void receiveQuotation(QuotationDTO quotation){
        LOG.info("-- Recebendo Nova Cotação do tópico Kafka --");
        opportunityService.saveQuotation(quotation);
    }

    /*
    Quando você marca um método com @Blocking, o Quarkus assegura que esse método seja executado em um pool de threads separado,
    projetado para lidar com operações bloqueantes.
    Isso libera os threads não bloqueantes para continuar processando outras tarefas que não exigem espera, mantendo a eficiência geral do sistema.
     */
}
