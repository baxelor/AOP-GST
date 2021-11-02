package com.axelor.app.gst.web;




import com.axelor.app.gst.db.Sequence;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;


public class SequenceController{

	
	public void SequenceGeneration(ActionRequest request, ActionResponse response) {
				Sequence sequence = request.getContext().asType(Sequence.class);
				String prefix = sequence.getPrefix();
				String suffix = sequence.getSuffix();
				Integer padding = sequence.getPadding();
				String newPadding = "";
				String newValue;
					for (int i = 0; i < padding; i++) {
						newPadding = newPadding+"0";				
					}
				if(suffix == null) {
					newValue = prefix+newPadding;
				}else {
					newValue = prefix + newPadding + suffix;
				}
				
				response.setValue("nextNumber", newValue);
			
			}
			
		}


	
