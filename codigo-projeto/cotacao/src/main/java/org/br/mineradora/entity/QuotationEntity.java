package org.br.mineradora.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "quotation")
@Data //Gera os getters, setters, hashcode e equals
@NoArgsConstructor
public class QuotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //inidicar que cada elemento inserido no banco o id seja incrementado em 1
    private Long id;

    private Date date;

    @Column(name = "currencyPrice", scale =4) // forçar que a coluna seja nomeada em camelCase e que sejam 4 casas decimais
    private BigDecimal currencyPrice;

    //porcentagem de mudança dos preços desde a ultima busca
    @Column(name = "pctChange")
    private String pctChange;

    //moedas comparadas
    private String pair;

}
