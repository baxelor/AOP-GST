package com.axelor.app.gst.web.service;

import java.math.BigDecimal;
import java.util.List;

import com.axelor.app.gst.db.Invoice;
import com.axelor.app.gst.db.InvoiceLine;
import com.axelor.app.gst.db.Product;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class InvoiceServiceImp implements InvoiceServiceInterface {
	@Inject
	SequenceServiceImplementation sequenceService;
	@Inject
	InvoiceLineServiceImpl invoiceLineServ;
	
	@Transactional
	@Override
	public void setReference(Invoice invoice, ActionResponse response) {
		try {
			String reference= sequenceService.getNextNumber("Invoice");
			if(invoice.getId()!=null) {
				if(invoice.getReference()==null) {
					response.setValue("reference", reference);
				}
			}else {
				response.setValue("reference", reference);
			}
		} catch (Exception e) {
			response.setError("No Sequence Found");
		}
	}


	@Override
	public BigDecimal netSgst(Invoice invoice) {
		List<InvoiceLine> invoiceLineList= invoice.getInvoiceItems();
		BigDecimal netSgst = BigDecimal.ZERO;
		for(InvoiceLine item : invoiceLineList) {
			BigDecimal sgst = item.getSgst();
			netSgst = netSgst.add(sgst);
		}
		
		return netSgst;
	}


	@Override
	public BigDecimal netIgst(Invoice invoice) {
		List<InvoiceLine> invoiceLineList = invoice.getInvoiceItems();
		BigDecimal netIgst = BigDecimal.ZERO;
		for(InvoiceLine items: invoiceLineList) {
			BigDecimal igst = items.getIgst();
			netIgst = igst.add(netIgst);
		}
		return netIgst;
	}


	@Override
	public BigDecimal netAmount(Invoice invoice) {
		List<InvoiceLine> invoiceLineList = invoice.getInvoiceItems();
		BigDecimal totalNetAmount = BigDecimal.ZERO;
		for(InvoiceLine items: invoiceLineList) {
			BigDecimal netAmount = items.getNetAmount();
			totalNetAmount = netAmount.add(totalNetAmount);
		}
		return totalNetAmount;
	}


	@Override
	public BigDecimal netGrossAmount(Invoice invoice) {
		List<InvoiceLine> invoiceLineList = invoice.getInvoiceItems();
		BigDecimal totalGrossAmount = BigDecimal.ZERO;
		for(InvoiceLine items: invoiceLineList) {
			BigDecimal grossAmount = items.getGrossAmount();
			totalGrossAmount = grossAmount.add(totalGrossAmount);
		}
		return totalGrossAmount;
	}


	@Override
	public InvoiceLine setSelectedProduct(InvoiceLine invoiceLine) {
		Product product = invoiceLine.getProduct();
		
		if(product.getGstRate() != BigDecimal.ZERO) {
			invoiceLine.setGstRate(product.getGstRate());
		}
		if(product.getHSBN() != null) {
			invoiceLine.setHsbn(product.getHSBN());
		}
		if(invoiceLine.getQty() == 0) {
			invoiceLine.setQty(1);
		}
		
		if(invoiceLine.getPrice() !=null) {
			invoiceLine.setPrice(product.getCostPrice());
		}
		
		if(invoiceLine.getNetAmount() != null) {
			BigDecimal amount = invoiceLineServ.netAmount(invoiceLine.getPrice(), invoiceLine.getQty());
			invoiceLine.setNetAmount(amount);
		}
		invoiceLine.setItem(product.getCode()+ " " + product.getName() );
		
		return invoiceLine;
	}






	

}
