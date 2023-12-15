package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.OpportunityDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
public interface ReportService {

    //Retorna relatorio em csv
    ByteArrayInputStream generateCSVOpportunityReport();

    //Retorna relatorio em json
    List<OpportunityDTO> getOpportunitiesData();
}
