package com.axelor.app.gst.web;

import java.math.BigDecimal;
import com.axelor.app.gst.db.Product;
import com.axelor.app.gst.db.ProductCategory;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;


public class ProductController {
		
	public void SetGstRate(ActionRequest request ,ActionResponse response) {
		
		try {
			
		Product product = request.getContext().asType(Product.class);
		ProductCategory productCategory = product.getProductcategories();
		BigDecimal gst = productCategory.getGstRate();
			
			if(gst.equals(gst));
			BigDecimal gstRate1 = gst;
			response.setValue("gstRate", gstRate1);
		} catch (Exception e) {
			response.setError("Category field not be Empty!");
		}
	}
	

}
