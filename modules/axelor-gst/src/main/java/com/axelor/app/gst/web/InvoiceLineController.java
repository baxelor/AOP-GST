package com.axelor.app.gst.web;

import java.math.BigDecimal;
import com.axelor.app.gst.db.Invoice;
import com.axelor.app.gst.db.InvoiceLine;

import com.axelor.app.gst.db.Product;
import com.axelor.app.gst.db.State;
import com.axelor.app.gst.web.service.InvoiceLineServiceInterface;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;





public class InvoiceLineController {
	
		@Inject
		InvoiceLineServiceInterface invoicelineService;
	
		public void SetInvoiceLineProperties(ActionRequest request, ActionResponse response) {
			try {
				InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);		
				Product product = invoiceLine.getProduct();
				String hsbn = product.getHSBN();
				BigDecimal price = product.getCostPrice();
				BigDecimal gstRate = product.getGstRate().divide(BigDecimal.valueOf(100));
				String items = product.getName();
				String code = product.getCode();
				String fullName = code.concat(" ").concat(items);
					
						response.setValue("hsbn", hsbn);
						response.setValue("gstRate", gstRate);
						response.setValue("price", price);
						response.setValue("item", fullName);
					} catch (Exception e) {
//						response.setError("Product field not be Empty!");
					}
				
		}
		
		
		public void CalculateCSandIgst(ActionRequest request, ActionResponse response) {
			try {
				InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
				Invoice invoice = invoiceLine.getInvoice();
				if(invoice == null) {
					invoice = request.getContext().getParent().asType(Invoice.class);
				}
				
				BigDecimal gst = invoiceLine.getGstRate();
				BigDecimal netAmount = invoiceLine.getNetAmount();
				State invoiceState = invoice.getInvoiceAddress().getStates();
				State shippingState = invoice.getShippingAddress().getStates();
				BigDecimal gstValue = BigDecimal.ZERO;
				BigDecimal getGstValue = invoicelineService.calculateAllGst(gst, netAmount,gstValue,invoiceState,shippingState);
				
				if(invoiceState.equals(shippingState)) {
					response.setValue("cgst", getGstValue);
					response.setValue("sgst", getGstValue);
					response.setValue("igst", BigDecimal.ZERO);
				}else {
					response.setValue("igst", getGstValue);
					response.setValue("cgst", BigDecimal.ZERO);
					response.setValue("sgst", BigDecimal.ZERO);
				}
			} catch (Exception e) {
				response.setError("Invoice field can't be empty");
			}
			
		}
		
		public void calculateGrossAmount(ActionRequest request, ActionResponse response) {
			    InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
				BigDecimal netAmount = invoiceLine.getNetAmount();
				BigDecimal cgst = invoiceLine.getCgst();
				BigDecimal igst = invoiceLine.getIgst();
				BigDecimal grossAmount = BigDecimal.ZERO;
				BigDecimal grossAmountValue =	invoicelineService.grossAmount(netAmount, cgst, igst, grossAmount);
				response.setValue("grossAmount", grossAmountValue);
			
		}
		
		public void netAmount(ActionRequest request, ActionResponse response) {
				InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
				BigDecimal price = invoiceLine.getPrice();
				Integer quantity =invoiceLine.getQty();
				BigDecimal netAmountValue = invoicelineService.netAmount(price, quantity);
				response.setValue("netAmount", netAmountValue);
		}
		
	
			
}

		