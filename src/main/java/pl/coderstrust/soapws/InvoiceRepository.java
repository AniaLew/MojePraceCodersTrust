package pl.coderstrust.soapws;

import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceBook;
import pl.coderstrust.soapinvoices.InvoiceBodyWS;
import pl.coderstrust.soapinvoices.InvoiceWS;
import pl.coderstrust.soapws.converters.DateConverter;
import pl.coderstrust.soapws.converters.InvoiceConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceRepository {

  private InvoiceBook invoiceBook;

  @Autowired
  public InvoiceRepository(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }


  public InvoiceWS findInvoice(Integer id) throws DatatypeConfigurationException, IOException {
    Assert.notNull(id, "The invoice's id must not be null");
    Invoice invoice = invoiceBook.getInvoiceById(id);
    if (invoice == null) {
      return null;
    }
    InvoiceWS invoiceWs = InvoiceConverter.convertToInvoiceWs(invoice);
    return invoiceWs;
  }

  public List<InvoiceWS> findInvoices() throws DatatypeConfigurationException, IOException {
    List<InvoiceWS> invoiceWsList = new ArrayList<>();
    for (Invoice invoice : invoiceBook.getInvoices()) {
      invoiceWsList.add(InvoiceConverter.convertToInvoiceWs(invoice));
    }
    return invoiceWsList;
  }

  public List<InvoiceWS> findInvoicesByYearAndMonth(int yearVar, Month monthVar) throws
      DatatypeConfigurationException, IOException {
    Assert.notNull(yearVar, "The invoice's year must not be null");
    Assert.notNull(monthVar, "The invoice's month must not be null");
    List<InvoiceWS> invoiceWsList = new ArrayList<>();
    Year year = Year.of(yearVar);
    //Month month = Month.valueOf(monthVar.toUpperCase());
    List<Invoice> invoices = invoiceBook.getInvoices(year, monthVar);
    for (Invoice invoice : invoices) {
      invoiceWsList.add(InvoiceConverter.convertToInvoiceWs(invoice));
    }
    return invoiceWsList;
  }

  public List<InvoiceWS> findInvoicesFromDateToDate(LocalDate fromDate,
      LocalDate toDate) throws DatatypeConfigurationException, IOException {
    Assert.notNull(fromDate, "The invoice's id must not be null");
    Assert.notNull(toDate, "The invoice's id must not be null");
    List<InvoiceWS> invoiceWsList = new ArrayList<>();

    List<Invoice> invoices = invoiceBook.getInvoices(fromDate, toDate);
    for (Invoice invoice : invoices) {
      invoiceWsList.add(InvoiceConverter.convertToInvoiceWs(invoice));
    }
    return invoiceWsList;
  }

  public Integer saveInvoice(InvoiceBodyWS invoiceBodyWs)
      throws DatatypeConfigurationException {
    Assert.notNull(invoiceBodyWs, "The invoice must not be null");
    Invoice invoice = InvoiceConverter.createInvoice(invoiceBodyWs, invoiceBook);
    invoiceBook.addInvoice(invoice);
    return invoice.getId();
  }

  public Integer updateInvoice(int id, InvoiceBodyWS invoiceBodyWs)
      throws DatatypeConfigurationException {
    Assert.notNull(id, "The invoice's id must not be null");
    invoiceBook.getInvoiceById(id).setDate(DateConverter.convertToLocalDate(invoiceBodyWs
        .getDate()));
    invoiceBook.getInvoiceById(id).setCounterparty(InvoiceConverter
        .convertToCounterparty(invoiceBodyWs));
    invoiceBook.getInvoiceById(id).setInvoiceItems(InvoiceConverter
        .convertToInvoiceItems(invoiceBodyWs));
    return id;
  }

  public Integer removeInvoice(int id) throws DatatypeConfigurationException, IOException {
    Assert.notNull(id, "The invoice's id must not be null");
    invoiceBook.removeInvoice(id);
    return id;
  }

}