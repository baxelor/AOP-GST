package com.axelor.app.gst.web.service;

import com.axelor.app.gst.db.Party;
import com.axelor.rpc.ActionResponse;

public interface PartyServiceInterface {
	public void setPartyreference(Party party, ActionResponse response);
}
