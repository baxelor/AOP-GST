package com.axelor.app.gst.web.service;


import java.math.BigDecimal;
import com.axelor.app.gst.db.State;

public interface InvoiceLineServiceInterface {
	public BigDecimal calculateAllGst(BigDecimal gst, BigDecimal netAmount, BigDecimal gstValue, State invoiceState,State shippingState);
	public BigDecimal grossAmount(BigDecimal netAmount, BigDecimal cgst, BigDecimal igst, BigDecimal grossAmount);
	public BigDecimal netAmount(BigDecimal price, Integer quantity);

}
