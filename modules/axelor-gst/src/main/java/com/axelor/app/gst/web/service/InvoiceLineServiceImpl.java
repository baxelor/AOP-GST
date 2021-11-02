package com.axelor.app.gst.web.service;

import java.math.BigDecimal;
import com.axelor.app.gst.db.State;

public class InvoiceLineServiceImpl implements InvoiceLineServiceInterface {
	



	@Override
	public BigDecimal calculateAllGst(BigDecimal gst, BigDecimal netAmount,BigDecimal gstValue,State invoiceState,State shippingState) {
			if(invoiceState.equals(shippingState)) {
				gstValue = netAmount.multiply(gst).divide(BigDecimal.valueOf(2));
			}else {
				gstValue = netAmount.multiply(gst);
			}	
		return gstValue;
	}

	
	
	@Override
	public BigDecimal grossAmount(BigDecimal netAmount, BigDecimal cgst, BigDecimal igst,BigDecimal grossAmount) {
		if(cgst.equals(BigDecimal.ZERO)) {
			grossAmount = igst.add(netAmount);
		}else {
			grossAmount = cgst.multiply(BigDecimal.valueOf(2)).add(netAmount);
		}
		return grossAmount;
	}


	@Override
	public BigDecimal netAmount(BigDecimal price, Integer quantity) {
		BigDecimal qty = BigDecimal.valueOf(quantity);
		BigDecimal netAmount = price.multiply(qty);
		return netAmount;
	}

	

}
