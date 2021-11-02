package com.axelor.data;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.axelor.app.gst.db.repo.CountryRepository;
import com.axelor.app.gst.db.repo.ProductRepository;
import com.axelor.data.csv.CSVImporter;
import com.axelor.inject.Beans;
import com.axelor.test.GuiceModules;
import com.axelor.test.GuiceRunner;

@RunWith(GuiceRunner.class)
@GuiceModules(DataModule.class)

public class CSVDataTest {
	
	
//	@Test
//	public void testImoprtMethod() throws IOException{
//		System.err.println(Beans.get(CountryRepository.class).all().fetch());
//		Importer importer = new CSVImporter("data/csv-config.xml", "data/csv");
//		importer.run();
//		System.err.println(Beans.get(CountryRepository.class).all().fetch());
//		
//	}
	
	@Test
	public void productImportMethod() throws IOException{
		System.err.println(Beans.get(ProductRepository.class).all().fetch());
		Importer importer = new CSVImporter("data/csv-config.xml", "data/csv");
		importer.run();
		System.err.println(Beans.get(ProductRepository.class).all().fetch());
	}

}
