package com.axelor.app.gst.web.service;

import com.axelor.app.gst.db.Party;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class PartyServiceImpl implements PartyServiceInterface {
	
	@Inject
	SequenceServiceImplementation sequenceService;
	
	@Transactional
	@Override
	public void setPartyreference(Party party, ActionResponse response) {
		String reference= sequenceService.getNextNumber("Party");
		if(party.getId()!=null) {
			if(party.getReference()==null) {
				response.setValue("reference", reference);
			}
		}else {
			response.setValue("reference", reference);
		}
		
	}

}
