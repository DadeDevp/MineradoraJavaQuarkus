package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;

    public void getCurrencyPrice() {

        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        if (updateCurrentInfoPrice(currencyPriceInfo)) {
            kafkaEvents.sendNewKafkaEvent(QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                    .date(new Date())
                    .build());
        }
    }

    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo) {

        BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());
        boolean updatePrice = false;

        List<QuotationEntity> quotationList = quotationRepository.findAll().list();


        if (quotationList.isEmpty()) {

            saveQuotation(currencyPriceInfo);
            updatePrice = true;
        } else {

            QuotationEntity lastDollarPrice = quotationList
                    .get(quotationList.size() - 1);

            if(currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()){
                updatePrice = true;
                saveQuotation(currencyPriceInfo);
            }
        }
        return updatePrice;
    }

    private void saveQuotation(CurrencyPriceDTO currencyPriceInfo) {

        QuotationEntity quotation = new QuotationEntity();

        quotation.setDate(new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()));
        quotation.setPctChange(currencyPriceInfo.getUSDBRL().getPctChange());
        quotation.setPair("USD-BRL");

        quotationRepository.persist(quotation);
    }
}

/*
  Classe QuotationService - Serviço de Cotação de Moedas

  Esta classe é responsável pela gestão e processamento das cotações de moedas
  no sistema, atuando como um intermediário entre a obtenção de dados de cotação
  e sua persistência ou transmissão.

  Marcada com @ApplicationScoped, a classe é mantida como uma instância única
  no ciclo de vida da aplicação, garantindo consistência e eficiência na gestão
  das cotações.

  Os atributos injetados na classe incluem:
  1. 'currencyPriceClient': Cliente REST para obter informações de preço de moedas.
  2. 'quotationRepository': Repositório para persistência de dados de cotações.
  3. 'kafkaEvents': Serviço para enviar eventos de cotação para um tópico Kafka.

  O método 'getCurrencyPrice' é o ponto de entrada principal para a obtenção e
  processamento das cotações de moedas. Ele recupera informações da cotação atual
  (por exemplo, USD-BRL), verifica se há uma necessidade de atualização e, em caso
  afirmativo, envia um novo evento Kafka com os dados atualizados.

  O método 'updateCurrentInfoPrice' verifica se o preço atual difere do último preço
  registrado. Se houver uma alteração significativa, ele sinaliza para atualizar o
  preço, salvando a nova cotação e possivelmente acionando um evento Kafka.

  'saveQuotation' é um método auxiliar para persistir informações de cotação no
  repositório. Ele cria e configura uma nova entidade de cotação com os dados
  recebidos e a salva no banco de dados.

  Em suma, QuotationService é uma classe central para o gerenciamento de cotações
  de moedas, abrangendo a obtenção, processamento, persistência e comunicação
  de informações de cotação de maneira eficiente e confiável.
 */


/*
  O trecho de código abaixo executa a ação de enviar um novo evento para o Kafka,
  utilizando a instância 'kafkaEvents' da classe KafkaEvents. Este processo
  envolve a construção e envio de um objeto QuotationDTO contendo informações
  atualizadas da cotação.

  kafkaEvents.sendNewKafkaEvent(QuotationDTO
   .builder()
   .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
   .date(new Date())
   .build());

  1. QuotationDTO.builder():
     - Inicia a construção de um objeto QuotationDTO.
       O padrão Builder é utilizado aqui para uma construção eficiente e legível do objeto.

  2. .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid())):
     - Define o preço da moeda para o objeto QuotationDTO.
     - 'currencyPriceInfo.getUSDBRL().getBid()' obtém o valor da cotação atual da moeda.
     - 'new BigDecimal(...)' cria uma instância de BigDecimal com o valor da cotação,
       garantindo precisão no valor da moeda.

  3. .date(new Date()):
     - Define a data atual para o objeto QuotationDTO.
     - 'new Date()' cria uma nova instância de Date, representando o momento atual.

  4. .build():
     - Completa a construção do objeto QuotationDTO, retornando a instância finalizada.

  5. kafkaEvents.sendNewKafkaEvent(...):
     - Envia o objeto QuotationDTO construído para o tópico Kafka especificado.
     - Este método é parte da classe KafkaEvents e lida com a lógica de comunicação
       com o Kafka, garantindo que o evento de cotação seja enviado corretamente.

  Este trecho é crucial para a funcionalidade do serviço, pois permite a atualização
  e comunicação de novas informações de cotação em tempo real através do Kafka.
 */
