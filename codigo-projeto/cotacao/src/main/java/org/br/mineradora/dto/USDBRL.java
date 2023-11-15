package org.br.mineradora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

//@Jacksonized Permite que a classe seja lida como um objeto json
@Jacksonized
@Data
//@Builder Consigo instanciar objetos e inserir apenas atribuir valores em alguns dos atributos sem precisar criar um construtor pra isso
@Builder
@AllArgsConstructor
public class USDBRL {

    public String code;
    public String codein;
    public String name;
    public String high;
    public String low;
    public String varBid;
    public String pctChange;
    public String bid;
    public String ask;
    public String timestamp;
    public String create_date;
}
