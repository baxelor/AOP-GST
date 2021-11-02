package com.axelor.app.gst.web;

import com.axelor.app.gst.db.Party;
import com.axelor.app.gst.web.service.PartyServiceInterface;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class PartyController {
		public void setPartyReference(ActionRequest request,ActionResponse response) {
			Party party = request.getContext().asType(Party.class);
			Beans.get(PartyServiceInterface.class).setPartyreference(party, response);
			
		}
		

}
