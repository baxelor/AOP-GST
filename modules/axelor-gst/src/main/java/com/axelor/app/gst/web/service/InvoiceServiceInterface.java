package com.axelor.app.gst.web.service;



import java.math.BigDecimal;
import com.axelor.app.gst.db.Invoice;
import com.axelor.app.gst.db.InvoiceLine;
import com.axelor.rpc.ActionResponse;

public interface InvoiceServiceInterface {
		public void setReference(Invoice invoice, ActionResponse response);
		public BigDecimal netSgst(Invoice invoice);
		public BigDecimal netIgst(Invoice invoice);
		public BigDecimal netAmount(Invoice invoice);
		public BigDecimal netGrossAmount(Invoice invoice);
		public InvoiceLine setSelectedProduct(InvoiceLine invoiceLine);
		

}
