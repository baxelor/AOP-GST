package com.axelor.app.gst.web.module;

import com.axelor.app.AxelorModule;
import com.axelor.app.gst.db.repo.PartyRepository;
import com.axelor.app.gst.web.service.InvoiceLineServiceImpl;
import com.axelor.app.gst.web.service.InvoiceLineServiceInterface;
import com.axelor.app.gst.web.service.InvoiceServiceImp;
import com.axelor.app.gst.web.service.InvoiceServiceInterface;
import com.axelor.app.gst.web.service.PartyServiceImpl;
import com.axelor.app.gst.web.service.PartyServiceInterface;

import com.axelor.app.gst.web.service.SequenceServiceImplementation;
import com.axelor.app.gst.web.service.SequenceServiceInterface;
import com.axelor.app.gst.web.view.PartyRepo;

public class RepoModule extends AxelorModule{
		
	@Override
		protected void configure() {
			bind(PartyRepository.class).to(PartyRepo.class);
			bind(InvoiceServiceInterface.class).to(InvoiceServiceImp.class);
			bind(SequenceServiceInterface.class).to(SequenceServiceImplementation.class);
			bind(PartyServiceInterface.class).to(PartyServiceImpl.class);
			bind(InvoiceLineServiceInterface.class).to(InvoiceLineServiceImpl.class);
			}

}
