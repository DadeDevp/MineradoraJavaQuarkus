package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.client.ProposalRestClient;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProposalServiceImpl implements  ProposalService{

    @Inject
    @RestClient
    ProposalRestClient proposalRestClient;

    @Override
    public ProposalDetailsDTO getProposalDetailsById(long proposalId) {
        return proposalRestClient.getProposalDetailsById(proposalId);
    }

    @Override
    public Response createNewProposal(ProposalDetailsDTO proposalDetailsDTO) {
        return proposalRestClient.createProposal(proposalDetailsDTO);
    }

    @Override
    public Response removeProposal(long id) {
        return proposalRestClient.removeProposal(id);
    }
}
