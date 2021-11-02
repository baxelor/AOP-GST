package com.axelor.app.gst.web.service;

import com.axelor.app.gst.db.Sequence;
import com.axelor.app.gst.db.repo.SequenceRepository;
import com.google.inject.Inject;

public class SequenceServiceImplementation implements SequenceServiceInterface{
	
	@Inject
	SequenceRepository sequenceRepo;

	@Override
	public String getNextNumber(String modelName) {
		Sequence sequence = sequenceRepo.all().filter("self.model.name= ?",modelName).fetchOne();
		String generateNextNumber = generateNextNumber(sequence);
		return generateNextNumber;
	}
	
	public String  generateNextNumber(Sequence sequence) {
		
		String finalNextNumber="";
		if(sequence == null) {
		}else {
			String prefix = sequence.getPrefix();
			String suffix = sequence.getSuffix();
			int padding = sequence.getPadding();
			String nextNumber = sequence.getNextNumber();
			String[] splitPrefix= nextNumber.split(prefix);
			String splitPadding= splitPrefix[1];
			Integer newPadding;
			String newPaddingLength="";
			String finalP ="";
			String padding1="";
			if(suffix==null) {		
				newPadding=(Integer.parseInt(splitPadding)+1);
				newPaddingLength = Integer.toString(newPadding);
			}else {
				String[] splitSuffix = splitPadding.split(suffix);
				String suffixPadding =splitSuffix[0];
				newPadding=(Integer.parseInt(suffixPadding)+1);
				newPaddingLength = Integer.toString(newPadding);
			}
			for(int i=padding;i>newPaddingLength.length();i--) {
				padding1 = "0"+padding1;
				finalP=padding1+newPadding;
				
			}
			
			if(prefix !=null && suffix !=null) {
				finalNextNumber = prefix+finalP+suffix;
				System.out.println(finalNextNumber);
			}else {
				finalNextNumber = prefix+finalP;
				System.out.println(finalNextNumber);
			}
		}
		sequence.setNextNumber(finalNextNumber);
		sequenceRepo.save(sequence);
		return finalNextNumber;

		
	}

}
