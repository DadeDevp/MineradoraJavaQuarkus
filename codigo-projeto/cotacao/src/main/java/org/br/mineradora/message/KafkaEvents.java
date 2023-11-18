package org.br.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationDTOEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation){
        LOG.info("-- Enviando Cotacao para Topico Kafka --");
        quotationDTOEmitter.send(quotation).toCompletableFuture().join();
    }
}

/*
  Classe KafkaEvents - Gestão de Eventos Kafka no Aplicativo

  Esta classe é parte vital do sistema de mensageria do aplicativo,
  especificamente focada na manipulação de eventos Kafka. Ela é
  marcada como @ApplicationScoped, significando que uma única instância
  desta classe será criada e gerenciada pelo contêiner durante o ciclo
  de vida da aplicação. Isso assegura que os eventos são tratados de
  forma consistente e eficiente.

  A classe possui um Logger privado, que é utilizado para registrar
  informações relevantes. O uso do Logger, neste caso da biblioteca
  SLF4J, permite um controle flexível sobre os níveis de log e integra-se
  facilmente com diferentes sistemas de log.

  Dentro desta classe, temos um Emitter chamado 'quotationDTOEmitter'.
  Ele é anotado com @Channel("quotation-channel"), o que indica que ele
  está ligado a um canal específico de eventos Kafka, neste caso,
  'quotation-channel'. Isso facilita a publicação de eventos relacionados
  a cotações (QuotationDTO) para este canal específico.

  O método público 'sendNewKafkaEvent' é o coração desta classe. Ele
  aceita um objeto QuotationDTO e utiliza o 'quotationDTOEmitter' para
  enviar este objeto como um evento para o tópico Kafka configurado.
  O uso do método 'toCompletableFuture().join()' garante que a operação
  de envio é concluída antes de o método retornar, proporcionando assim
  um envio síncrono e confiável.

  Em resumo, a classe KafkaEvents encapsula a lógica necessária para
  enviar eventos de cotações para um tópico Kafka, servindo como um
  ponto central de integração entre o aplicativo e o sistema de mensageria
  Kafka, garantindo uma comunicação eficiente e organizada.
 */
