package com.axelor.app.gst.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.axelor.app.gst.db.Address;
import com.axelor.app.gst.db.Contact;
import com.axelor.app.gst.db.Invoice;
import com.axelor.app.gst.db.InvoiceLine;
import com.axelor.app.gst.db.Party;
import com.axelor.app.gst.db.Product;
import com.axelor.app.gst.db.State;
import com.axelor.app.gst.db.repo.ProductRepository;
import com.axelor.app.gst.web.service.InvoiceLineServiceInterface;
import com.axelor.app.gst.web.service.InvoiceServiceImp;
import com.axelor.app.gst.web.service.InvoiceServiceInterface;
import com.axelor.inject.Beans;

import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;




public class InvoiceController{
	
	@Inject
	InvoiceLineServiceInterface invoiceLineService;
	
	@Inject
	InvoiceServiceInterface invoiceService;
	
	@Inject
	ProductRepository productRepo;
	
	public void SetPartyContactPrimary(ActionRequest request, ActionResponse response) {
			try {
				Invoice invoice = request.getContext().asType(Invoice.class);		
				Party party = invoice.getParty();
				List<Contact> contactList = party.getContact();
					for(Contact c : contactList) {
						String type = c.getType();
						if(type.equalsIgnoreCase("primary"));
						response.setValue("partyContact", c);
				}			
			} catch (Exception e) {
				response.setError("Party field can't be Empty!");
			}
	}
	
	
	public void SetPartyAddress(ActionRequest request, ActionResponse response) {
			Invoice invoice = request.getContext().asType(Invoice.class);
			Party party = invoice.getParty();
			List<Address> listAddress = party.getAddress();
				for(Address a: listAddress) {
					String type = a.getType();
					if(type.equalsIgnoreCase("default")){
					response.setValue("invoiceAddress", a);
					response.setValue("shippingAddress", a);
					break;
					}
					else {
						response.setValue("invoiceAddress", a);
					
					}
				}
				
				
	}
	
	
	
	
	public void UpdateInvoiceLine(ActionRequest request, ActionResponse response) {
				try {
					Invoice invoice = request.getContext().asType(Invoice.class);
					State invoiceState = invoice.getInvoiceAddress().getStates();
					State shippingState = invoice.getShippingAddress().getStates();
					List<InvoiceLine> invoiceItemList = invoice.getInvoiceItems();
					for(InvoiceLine item: invoiceItemList) {
						BigDecimal gstValue = BigDecimal.ZERO;
						BigDecimal netAmount = item.getNetAmount();
						BigDecimal gst = item.getGstRate();
						BigDecimal updatedValue =  invoiceLineService.calculateAllGst(gst, netAmount, gstValue, invoiceState, shippingState);
						if(invoiceState.equals(shippingState)) {
							item.setCgst(updatedValue);
							item.setSgst(updatedValue);
							item.setIgst(BigDecimal.ZERO);
						}else {
							item.setIgst(updatedValue);
							item.setCgst(BigDecimal.ZERO);
							item.setSgst(BigDecimal.ZERO);
						}
						response.setValue("invoiceItems", invoiceItemList);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
								
		}
		
	
		public void setInvoiceReference(ActionRequest request, ActionResponse response) {
			Invoice invoice = request.getContext().asType(Invoice.class);
			Beans.get(InvoiceServiceInterface.class).setReference(invoice, response);
		}
		
		public void calculateNetSgst(ActionRequest request, ActionResponse response) {
			Invoice invoice = request.getContext().asType(Invoice.class);
			BigDecimal netSgst = invoiceService.netSgst(invoice);
			response.setValue("netSgst", netSgst);
			response.setValue("netCgst", netSgst);
		}
		
		public void calculateNetAmount(ActionRequest request, ActionResponse response) {
			Invoice invoice = request.getContext().asType(Invoice.class);
			BigDecimal netAmount = invoiceService.netAmount(invoice);
			response.setValue("netAmount", netAmount);		
		}
		
		public void netGrossAmount(ActionRequest request, ActionResponse response) {
			Invoice invoice = request.getContext().asType(Invoice.class);
			BigDecimal netGrossAmount = invoiceService.netGrossAmount(invoice);
			response.setValue("grossAmount", netGrossAmount);
		}
		
		public void netIgst(ActionRequest request, ActionResponse response) {
			Invoice invoice = request.getContext().asType(Invoice.class);
			BigDecimal netIgst = invoiceService.netIgst(invoice);
			response.setValue("netIgst", netIgst);
		}
		
		@SuppressWarnings("unchecked")
		public void createInvoiceLineItems(ActionRequest request, ActionResponse response) {
			List<Integer> productIdList = new ArrayList<>();
			productIdList = (List<Integer>) request.getContext().get("ids");
			try {
				List<InvoiceLine> invoiceLineList = new ArrayList<>();
				for(Integer productid:productIdList) {
					Product product = productRepo.find(productid.longValue());
					InvoiceLine invoiceline = new InvoiceLine();
					invoiceline.setProduct(product);
					invoiceline = Beans.get(InvoiceServiceImp.class).setSelectedProduct(invoiceline);
					invoiceLineList.add(invoiceline);
					response.setValue("invoiceItems", invoiceLineList);
				}
			} catch (Exception e) {
			}
			
		}
		
	
}