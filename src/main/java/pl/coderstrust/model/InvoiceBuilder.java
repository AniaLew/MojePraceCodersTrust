package pl.coderstrust.model;

import pl.coderstrust.model.counterparty.Counterparty;
import pl.coderstrust.model.counterparty.CounterpartyBuilder;
import pl.coderstrust.model.invoiceItem.InvoiceItem;
import pl.coderstrust.model.invoiceItem.InvoiceItemBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvoiceBuilder {

  private int id;
  private LocalDate date = LocalDate.now();
  private Counterparty counterparty;
  private List<InvoiceItem> invoiceItems;

  public static InvoiceBuilder builder() {
    return new InvoiceBuilder();
  }

  public InvoiceBuilder withId(int id) {
    this.id = id;
    return this;
  }

  public InvoiceBuilder withDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public InvoiceBuilder withCounterparty(Counterparty counterparty) {
    this.counterparty = counterparty;
    return this;
  }

  public InvoiceBuilder withInvoiceItems(List<InvoiceItem> invoiceItems) {
    this.invoiceItems = invoiceItems;
    return this;
  }

  public InvoiceBuilder withInvoiceItems(InvoiceItemBuilder... invoiceItemBuilders) {
    this.invoiceItems = new ArrayList<>();
    for (InvoiceItemBuilder invoiceItemBuilder : invoiceItemBuilders) {
      this.invoiceItems.add(invoiceItemBuilder.build());
    }
    return this;
  }

  public Invoice build() {
    return new Invoice(id, date, counterparty, invoiceItems);
  }

  public static void main(String[] args) {
    Invoice invoice = InvoiceBuilder.builder()
        .withId(1)
        .withDate(LocalDate.now())
        .withCounterparty(CounterpartyBuilder.builder().withCompanyName("AniaLew").build())
        .withInvoiceItems(Arrays.asList(
            InvoiceItemBuilder
                .builder()
                .withDescription("something")
                .withAmount(BigDecimal.TEN)
                .withVatAmount(BigDecimal.ONE)
                .build()))
        .build();
    System.out.println(invoice);
  }
}

