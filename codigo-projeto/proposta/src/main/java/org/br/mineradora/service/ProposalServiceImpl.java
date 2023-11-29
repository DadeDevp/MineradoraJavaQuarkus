package org.br.mineradora.service;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.br.mineradora.entity.ProposalEntity;
import org.br.mineradora.message.KafkaEvent;
import org.br.mineradora.repository.ProposalRepository;

import java.util.Date;

public class ProposalServiceImpl implements ProposalService {

    @Inject
    ProposalRepository proposalRepository;

    @Inject
    KafkaEvent kafkaMessages;

    @Override
    public ProposalDetailsDTO findFullProposal(long id) {

        ProposalEntity proposal = proposalRepository.findById(id);

        //Método Builder da notaçao @builder para facilitar a construcao de um objeto do tipo ProposalDetailsDTO
        return ProposalDetailsDTO.builder()
                .proposalId(proposal.getId())
                .customer(proposal.getCustomer())
                .priceTonne(proposal.getPriceTonne())
                .tonnes(proposal.getTonnes())
                .country(proposal.getCountry())
                .proposalValidityDays(proposal.getProposalValidityDays())
                .build();
    }

    @Override
    @Transactional //Devo usar essa notacao pq irei mudar o estado do banco de dados, vou inserir um dado
    public void createNewProposal(ProposalDetailsDTO proposalDetailsDTO) {

        //Metodo que salva no banco de dados e manda um topico no kafka
        ProposalDTO proposal = buildAndSaveNewProposal(proposalDetailsDTO);
        kafkaMessages.sendNewKafkaEvent(proposal);

    }

    @Override
    @Transactional
    public void removeProposal(long id) {
        proposalRepository.deleteById(id);
    }

    @Transactional
    private ProposalDTO buildAndSaveNewProposal(ProposalDetailsDTO proposalDetailsDTO) {

        try {

            ProposalEntity proposal = new ProposalEntity();

            proposal.setCreated(new Date());
            proposal.setProposalValidityDays(proposalDetailsDTO.getProposalValidityDays());
            proposal.setCountry(proposalDetailsDTO.getCountry());
            proposal.setCustomer(proposalDetailsDTO.getCustomer());
            proposal.setPriceTonne(proposalDetailsDTO.getPriceTonne());
            proposal.setTonnes(proposalDetailsDTO.getTonnes());

            proposalRepository.persist(proposal);

            return ProposalDTO.builder()
                    .proposalId(proposalRepository.findByCustomer(proposal.getCustomer()).get().getId())
                    .priceTonne(proposal.getPriceTonne())
                    .customer(proposal.getCustomer())
                    .build();

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

/*
  Classe ProposalServiceImpl.

  Esta classe é uma implementação do serviço ProposalService, responsável pela lógica de negócios
  relacionada a propostas no sistema. Ela é uma parte crucial do padrão arquitetural de serviço,
  atuando como intermediária entre a camada de persistência (repositórios) e a camada de apresentação
  ou controladores da aplicação.

  A classe utiliza injeção de dependências (via @Inject) para obter instâncias de ProposalRepository,
  que lida com operações de banco de dados, e KafkaEvent, que é usado para enviar eventos para um
  sistema de mensagens Kafka. Estas dependências permitem que ProposalServiceImpl interaja com
  o banco de dados e sistemas externos de forma desacoplada.

  Métodos:
  - findFullProposal: Este método recebe um ID de proposta e usa o ProposalRepository para recuperar
    a proposta correspondente do banco de dados. Em seguida, ele constrói e retorna um DTO
    (Data Transfer Object) contendo detalhes da proposta. Este método é apenas de leitura e não
    modifica o estado do banco de dados.

  - createNewProposal: Este método é responsável por criar uma nova proposta. Ele recebe um DTO
    com detalhes da proposta, constrói uma entidade de proposta, salva essa entidade no banco de dados
    e, em seguida, envia uma mensagem para o Kafka. Este método é marcado como @Transactional, o que
    significa que qualquer alteração no banco de dados será parte de uma transação gerenciada pelo
    JTA (Java Transaction API).

  - removeProposal: Este método remove uma proposta do banco de dados com base em seu ID. Também é
    marcado como @Transactional, garantindo que a operação de exclusão seja parte de uma transação.

  - buildAndSaveNewProposal: Este é um método auxiliar privado usado para construir uma entidade de
    proposta a partir de um DTO e salvar essa entidade no banco de dados. Ele também constrói e retorna
    um DTO de resposta contendo informações relevantes da proposta recém-criada. Este método lida com
    exceções, garantindo que erros sejam registrados e que uma RuntimeException seja lançada em caso
    de falha.

  Em resumo, a ProposalServiceImpl é uma classe central na lógica de negócios do sistema, responsável
  por gerenciar propostas, interagindo com o banco de dados e integrando-se com sistemas externos
  como o Kafka.
 */
