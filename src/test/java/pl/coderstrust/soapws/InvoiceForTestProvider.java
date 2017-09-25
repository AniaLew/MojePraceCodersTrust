package pl.coderstrust.soapws;

import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceBuilder;
import pl.coderstrust.model.counterparty.Address;
import pl.coderstrust.model.counterparty.AddressBuilder;
import pl.coderstrust.model.counterparty.Counterparty;
import pl.coderstrust.model.counterparty.CounterpartyBuilder;
import pl.coderstrust.model.invoiceItem.InvoiceItem;
import pl.coderstrust.model.invoiceItem.InvoiceItemBuilder;
import pl.coderstrust.model.invoiceItem.Vat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceForTestProvider {

  public static Invoice provideInvoice() {
    Address address = AddressBuilder.builder()
        .withZipCode("97-420")
        .withTownName("Lodz")
        .withStreetName("Piotrkowska")
        .withHouseNumber("56")
        .build();
    Counterparty counterparty = CounterpartyBuilder.builder()
        .withCompanyName("My company")
        .withAddress(address)
        .withPhoneNumber("+48 44 634 22 70")
        .withNIP("769-19-18-016")
        .withBankName("PKO BP")
        .withBankNumber("09 2490 1044 0000 3200 9400 7370")
        .build();
    List<InvoiceItem> itemsList = new ArrayList<>();
    InvoiceItem invoiceItem = InvoiceItemBuilder.builder()
        .withNumberOfItem(1)
        .withDescription("Petrol")
        .withAmount(BigDecimal.TEN)
        .withVatAmount(BigDecimal.ONE)
        .withVat(Vat.VAT_23)
        .build();
    itemsList.add(invoiceItem);
    Invoice invoice = InvoiceBuilder.builder()
        .withId(1)
        .withDate(LocalDate.of(2017, 7, 15))
        .withCounterparty(counterparty)
        .withInvoiceItems(itemsList)
        .build();
    return invoice;
  }
}
