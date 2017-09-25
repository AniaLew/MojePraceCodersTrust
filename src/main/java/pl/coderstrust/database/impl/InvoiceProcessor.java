package pl.coderstrust.database.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.coderstrust.model.Invoice;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InvoiceProcessor {

  InvoiceProcessorHelper processorHelper = new InvoiceProcessorHelper();
  ObjectMapper mapper = processorHelper.objectMapper();

  void saveToFile(List<Invoice> invoices, File filename) throws IOException {
    mapper.writeValue(filename, invoices);
  }

  List<Invoice> readFileAndConvertContentIntoListOfInvoices(String invoicePath)
      throws IOException {
    return mapper.readValue(new File(invoicePath),
        mapper.getTypeFactory().constructCollectionType(List.class, Invoice.class));
  }
}