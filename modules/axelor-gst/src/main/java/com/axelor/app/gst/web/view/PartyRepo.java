package com.axelor.app.gst.web.view;

import java.util.Map;

import com.axelor.app.gst.db.Party;
import com.axelor.app.gst.db.repo.PartyRepository;

public class PartyRepo extends PartyRepository{
	@Override
	  public Map<String, Object> populate(Map<String, Object> json, Map<String, Object> context) {
	    if (!context.containsKey("json-enhance")) {
	      return json;
	    }
	    try {
	      Long id = (Long) json.get("id");
	      Party party = find(id);
	      json.put("address", party.getAddress().get(0));
	    } catch (Exception e) {
	   }
	    return json;
	}
	
}
