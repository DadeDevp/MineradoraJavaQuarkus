package org.br.mineradora.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class QuotationScheduler {
    @Inject
    QuotationService quotationService;

    private final Logger LOG = LoggerFactory.getLogger(QuotationScheduler.class);

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    void schedule() {
        LOG.info("-- Executando Scheduler --");
        quotationService.getCurrencyPrice();
    }
}

/*
  Classe QuotationScheduler - Agendador de Cotações de Moedas

  Esta classe é responsável por agendar a execução regular da tarefa
  de obtenção de preços de moedas. Ela é uma parte essencial do sistema
  de monitoramento de cotações, garantindo que as cotações sejam
  atualizadas continuamente em intervalos predefinidos.

  Anotada com @ApplicationScoped, esta classe é mantida como uma instância
  única durante o ciclo de vida da aplicação. Isso assegura que o agendamento
  é consistente e que os recursos são gerenciados de forma eficiente.

  A classe contém:
  - Uma injeção da instância 'quotationService', a qual fornece o serviço
    necessário para obter as cotações de moedas.

  O método 'schedule' é marcado com @Transactional e @Scheduled. Estas
  anotações têm as seguintes funções:
  - @Transactional: Garante que a operação realizada dentro do método
    seja gerenciada como uma transação. Isso é útil para manter a
    integridade dos dados em operações que envolvem o banco de dados.
  - @Scheduled(every = "35s", identity = "task-job"): Define que o método
    'schedule' deve ser executado automaticamente a cada 35 segundos. A
    propriedade 'identity' fornece um identificador único para a tarefa
    agendada, permitindo um gerenciamento e monitoramento mais fáceis.

  Dentro do método 'schedule', a chamada 'quotationService.getCurrencyPrice()'
  é realizada. Este método é responsável por obter as informações mais recentes
  das cotações de moedas e processá-las conforme necessário, incluindo a
  atualização do banco de dados e o envio de eventos para o Kafka.

  Em resumo, a QuotationScheduler desempenha um papel vital na automação
  do processo de coleta e processamento de cotações de moedas, assegurando
  que o sistema tenha sempre as informações mais atualizadas disponíveis.
 */
