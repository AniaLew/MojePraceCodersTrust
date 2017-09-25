package pl.coderstrust.controller;

import org.springframework.stereotype.Service;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceBook;
import pl.coderstrust.model.InvoiceBuilder;
import pl.coderstrust.model.counterparty.CounterpartyBuilder;
import pl.coderstrust.model.invoiceItem.InvoiceItemBuilder;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

;

@Service
public class InvoiceBookService {

  private final InvoiceBook invoiceBook;

  public InvoiceBookService(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  public Invoice getSingleInvoice(int id) {
    return invoiceBook.getInvoiceById(id);
  }


  public List<Invoice> getInvoicesFromGivenMonth(int year, String month) {
    Month enumMonth = Month.valueOf(month.toUpperCase());
    return invoiceBook.getInvoices(Year.of(year), enumMonth);
  }

  public List<Invoice> getInvoices() throws IOException {
    return invoiceBook.getInvoices();
  }

  /**
   * @param invoiceBody - body of invoice.
   * @return - id of invoice.
   */

  public int postInvoice(InvoiceBody invoiceBody) {
    Invoice invoice = createInvoice(invoiceBody);
    invoiceBook.addInvoice(invoice);
    return invoice.getId();
  }

  /**
   * @param id - id of invoice.
   * @param invoiceBody - body of invoice.
   * @throws IOException - when no success in reading and saving invoice.
   */

  public void putInvoice(Integer id, InvoiceBody invoiceBody)
      throws IOException {
    Iterator<Invoice> iterator = invoiceBook.getInvoices().iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        Invoice invoice = createInvoice(invoiceBody);
        invoiceBook.addInvoice(invoice);
      }
    }
  }

  /**
   * @param id - id of invoice.
   * @return - the number and description of exception
   * @throws IOException - when problems deleting invoice.
   */

  public boolean deleteInvoice(Integer id) throws IOException {
    Iterator<Invoice> iterator = invoiceBook.getInvoices().iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  private Invoice createInvoice(InvoiceBody invoiceBody) {
    return InvoiceBuilder
        .builder()
        .withId(invoiceBook.getNextInvoiceId())
        .withDate(invoiceBody.getDate())
        .withCounterparty(CounterpartyBuilder
            .builder()
            .withCompanyName(invoiceBody.getCounterparty().getCompanyName())
            .build())
        .withInvoiceItems(Arrays.asList(
            InvoiceItemBuilder
                .builder()
                .withDescription(invoiceBody.getInvoiceItems().get(0).getDescription())
                .withAmount(invoiceBody.getInvoiceItems().get(0).getAmount())
                .withVatAmount(invoiceBody.getInvoiceItems().get(0).getVatAmount())
                .build()))
        .build();
  }
}
