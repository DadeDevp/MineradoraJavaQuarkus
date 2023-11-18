package org.br.mineradora.client;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/last")
@RegisterRestClient /*(baseUri = "https://economia.awesomeapi.com.br") */
@ApplicationScoped
public interface CurrencyPriceClient {

    @GET
    @Path("/{pair}")
    CurrencyPriceDTO getPriceByPair(@PathParam("pair") String pair);

}



/*
Annotations de Classe:
@Path("/last"): Esta anotação define a URL base para todos os métodos desta interface.
Neste caso, qualquer método dentro desta interface que faça uma chamada REST será prefixado com /last na URL.

@RegisterRestClient: Esta anotação marca a interface como um cliente REST, que permite a integração com serviços RESTful.
No Quarkus, isso facilita o uso de microserviços e a comunicação entre eles.

@ApplicationScoped: Indica que a instância deste cliente REST será criada uma vez e reutilizada durante a vida útil da aplicação.
Isso é parte do modelo de contexto e dependência do CDI (Contexts and Dependency Injection).

Definição da Interface:
public interface CurrencyPriceClient: Define a interface CurrencyPriceClient.
Interfaces são usadas para definir um contrato que as classes implementadoras devem seguir.
---------------------------

Método da Interface:
@GET: Esta anotação indica que o método fará uma chamada GET HTTP.

@Path("/{pair}"): Define o caminho específico para o método.
O {pair} é um placeholder para uma variável que será passada no momento da chamada do método.
Assim, se o método for chamado com um pair de valor "USDBRL", a URL final será /last/USDBRL.

CurrencyPriceDTO getPriceByPair(@PathParam("pair") String pair):
Este é o método declarado na interface.
Ele retorna um objeto CurrencyPriceDTO. O @PathParam("pair") é uma anotação que mapeia a variável pair passada no método
para o placeholder {pair} na anotação @Path.

-----------------------------
Fluxo de Execução:
Quando este método é chamado, o Quarkus constrói uma requisição GET para a URL composta pela base (/last) e o parâmetro (/{pair}).
O resultado esperado é um objeto CurrencyPriceDTO, que deve ser uma classe Java que modela a resposta esperada do serviço REST.

*/
