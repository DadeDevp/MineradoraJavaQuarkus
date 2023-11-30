package org.br.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.ProposalDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvent {
    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);
    @Channel("proposal-channel")
    Emitter<ProposalDTO> proposalRequesteEmitter;

    public void sendNewKafkaEvent(ProposalDTO proposalDTO){

        LOG.info("-- Enviando Nova Proposta para Topico Kafka --");
        proposalRequesteEmitter.send(proposalDTO).toCompletableFuture().join();

    }
}

/*
* proposalRequesteEmitter.send(proposalDTO)

proposalRequesteEmitter é provavelmente uma instância de um Emitter, uma classe ou interface que é usada para enviar mensagens ou dados de forma assíncrona.
send(proposalDTO) é um método chamado nesse Emitter, enviando proposalDTO (que deve ser um objeto de dados, provavelmente um DTO - Data Transfer Object) para algum destino ou manipulador.
* Esta operação é normalmente não-bloqueante e pode ser realizada em uma thread separada, dependendo da implementação do Emitter.
toCompletableFuture()

Após enviar o proposalDTO, a chamada toCompletableFuture() sugere que o método send() retorna um objeto que pode ser convertido ou está associado a um CompletableFuture.
CompletableFuture é uma classe em Java que representa um futuro - um resultado de uma operação assíncrona que será concluída em algum ponto no futuro.
* Este objeto permite manipular e encadear operações assíncronas de forma mais complexa e gerenciada.
join()

O método join() é chamado no CompletableFuture. Ele bloqueia a thread atual até que a operação assíncrona seja concluída e o resultado esteja disponível.
Este é um ponto importante: enquanto CompletableFuture é usado para operações não-bloqueantes,
* o método join() é uma forma de esperar sincronamente pelo resultado, efetivamente tornando essa operação particular bloqueante.
* */