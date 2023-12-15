package org.br.mineradora.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.br.mineradora.dto.OpportunityDTO;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream opportunitiesToCSV(List<OpportunityDTO> opportunities) {

        final CSVFormat format = CSVFormat.DEFAULT.withHeader("ID Proposta", "Cliente", "Preço por Tonelada", "Melhor cotação de Moeda");

        try (   ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            for (OpportunityDTO opps : opportunities) {
                List<String> data = Arrays.asList(
                        String.valueOf(opps.getProposalId()), opps.getCustomer(), String.valueOf(opps.getPriceTonne()),
                        String.valueOf(opps.getLastDollarQuotation()));

                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {

            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}

/*
  Classe CSVHelper.

  Esta classe é uma utilidade para converter dados de oportunidades em um formato CSV (Comma-Separated Values).
  Ela fornece um método estático para converter uma lista de DTOs (Data Transfer Objects) de oportunidades
  em um fluxo de entrada ByteArrayInputStream, que pode ser usado para gerar arquivos CSV.

  Método:
  - opportunitiesToCSV: Este método estático recebe uma lista de OpportunityDTO e converte esses objetos
    em um arquivo CSV. Ele usa a biblioteca Apache Commons CSV para criar e formatar o arquivo CSV.

    O método configura o formato CSV para incluir um cabeçalho com os títulos das colunas, que representam
    os atributos das oportunidades, como ID da proposta, cliente, preço por tonelada e a melhor cotação de
    moeda. Isso torna o arquivo CSV mais legível e organizado.

    Dentro de um bloco try-with-resources, ele inicializa um ByteArrayOutputStream e um CSVPrinter.
    O ByteArrayOutputStream é um fluxo de saída que coleta os dados em um buffer, que depois pode ser
    convertido em um array de bytes. O CSVPrinter é usado para escrever linhas no formato CSV.

    Para cada OpportunityDTO na lista fornecida, o método cria uma lista de strings representando os
    campos do DTO e usa o CSVPrinter para escrever essa linha no fluxo de saída. Após escrever todos os
    registros, o método chama csvPrinter.flush() para garantir que todos os dados sejam escritos no
    ByteArrayOutputStream.

    Finalmente, o método retorna um novo ByteArrayInputStream, que encapsula o array de bytes do
    ByteArrayOutputStream. Este ByteArrayInputStream pode ser usado para ler os dados CSV como um
    fluxo de entrada.

    Em caso de IOException durante a escrita no fluxo de saída, o método captura a exceção e lança
    uma RuntimeException. Isso sinaliza um problema durante o processo de geração do arquivo CSV.

  Em resumo, a CSVHelper é uma classe utilitária que abstrai os detalhes de criação de um arquivo CSV
  a partir de uma lista de DTOs, fornecendo um meio conveniente de exportar dados para um formato que
  pode ser facilmente compartilhado e analisado.
 */