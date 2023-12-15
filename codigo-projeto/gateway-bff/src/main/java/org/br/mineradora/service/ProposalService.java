package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.ProposalDetailsDTO;

@ApplicationScoped
public interface ProposalService {

    ProposalDetailsDTO getProposalDetailsById(@PathParam("id") long proposalId);

    Response createNewProposal(ProposalDetailsDTO proposalDetailsDTO);

    Response removeProposal(long id);
}
