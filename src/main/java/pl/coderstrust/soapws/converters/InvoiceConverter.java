package pl.coderstrust.soapws.converters;

import javax.xml.datatype.DatatypeConfigurationException;
import pl.coderstrust.controller.InvoiceBody;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceBook;
import pl.coderstrust.model.InvoiceBuilder;
import pl.coderstrust.model.counterparty.Address;
import pl.coderstrust.model.counterparty.AddressBuilder;
import pl.coderstrust.model.counterparty.Counterparty;
import pl.coderstrust.model.counterparty.CounterpartyBuilder;
import pl.coderstrust.model.invoiceItem.InvoiceItem;
import pl.coderstrust.model.invoiceItem.InvoiceItemBuilder;
import pl.coderstrust.model.invoiceItem.Vat;
import pl.coderstrust.soapinvoices.AddressWS;
import pl.coderstrust.soapinvoices.CounterpartyWS;
import pl.coderstrust.soapinvoices.InvoiceBodyWS;
import pl.coderstrust.soapinvoices.InvoiceItemWS;
import pl.coderstrust.soapinvoices.InvoiceWS;
import pl.coderstrust.soapinvoices.VatWS;

import java.util.ArrayList;
import java.util.List;

public class InvoiceConverter {

  public static Counterparty convertToCounterparty(InvoiceWS invoiceWs)
      throws DatatypeConfigurationException {
    return CounterpartyBuilder.builder()
        .withCompanyName(invoiceWs.getCounterparty().getCompanyName())
        .withAddress(AddressBuilder.builder()
            .withZipCode(invoiceWs.getCounterparty().getAddress().getZipCode())
            .withTownName(invoiceWs.getCounterparty().getAddress().getTownName())
            .withStreetName(invoiceWs.getCounterparty().getAddress().getStreetName())
            .withHouseNumber(invoiceWs.getCounterparty().getAddress().getHouseNumber())
            .build())
        .withPhoneNumber(invoiceWs.getCounterparty().getPhoneNumber())
        .withNIP(invoiceWs.getCounterparty().getNip())
        .withBankName(invoiceWs.getCounterparty().getBankName())
        .withBankNumber(invoiceWs.getCounterparty().getBankNumber())
        .build();
  }

  public static Counterparty convertToCounterparty(InvoiceBodyWS invoiceBodyWs)
      throws DatatypeConfigurationException {
    return CounterpartyBuilder.builder()
        .withCompanyName(invoiceBodyWs.getCounterparty().getCompanyName())
        .withAddress(AddressBuilder.builder()
            .withZipCode(invoiceBodyWs.getCounterparty().getAddress().getZipCode())
            .withTownName(invoiceBodyWs.getCounterparty().getAddress().getTownName())
            .withStreetName(invoiceBodyWs.getCounterparty().getAddress().getStreetName())
            .withHouseNumber(invoiceBodyWs.getCounterparty().getAddress().getHouseNumber())
            .build())
        .withPhoneNumber(invoiceBodyWs.getCounterparty().getPhoneNumber())
        .withNIP(invoiceBodyWs.getCounterparty().getNip())
        .withBankName(invoiceBodyWs.getCounterparty().getBankName())
        .withBankNumber(invoiceBodyWs.getCounterparty().getBankNumber())
        .build();
  }

  public static List<InvoiceItem> convertToInvoiceItems(InvoiceWS invoiceWs) {
    InvoiceItem item;
    List<InvoiceItem> items = new ArrayList<>();
    for (InvoiceItemWS itemWs : invoiceWs.getItems()) {
      item = InvoiceItemBuilder.builder()
          .withDescription(itemWs.getDescription())
          .withNumberOfItem(Integer.valueOf(String.valueOf(itemWs.getNumberOfItem())))
          .withAmount(itemWs.getAmount())
          .withVatAmount(itemWs.getVatAmount())
          .withVat(toVat(itemWs.getVat()))
          .build();
      items.add(item);
    }
    return items;
  }

  public static List<InvoiceItem> convertToInvoiceItems(InvoiceBodyWS invoiceBodyWs) {
    InvoiceItem item;
    List<InvoiceItem> items = new ArrayList<>();
    for (InvoiceItemWS itemWs : invoiceBodyWs.getItemsList()) {
      item = InvoiceItemBuilder.builder()
          .withDescription(itemWs.getDescription())
          .withNumberOfItem(Integer.valueOf(String.valueOf(itemWs.getNumberOfItem())))
          .withAmount(itemWs.getAmount())
          .withVatAmount(itemWs.getVatAmount())
          .withVat(toVat(itemWs.getVat()))
          .build();
      items.add(item);
    }
    return items;
  }

  public static Invoice convertToInvoice(InvoiceWS invoiceWs)
      throws DatatypeConfigurationException {
    return InvoiceBuilder.builder()
        .withId(invoiceWs.getId())
        .withDate(DateConverter.convertToLocalDate(invoiceWs.getDate()))
        .withCounterparty(convertToCounterparty(invoiceWs))
        .withInvoiceItems(convertToInvoiceItems(invoiceWs))
        .build();
  }

  public static InvoiceWS convertToInvoiceWs(Invoice invoice)
      throws DatatypeConfigurationException {

    AddressWS addressWs = new AddressWS();
    addressWs.setZipCode(invoice.getCounterparty().getAddress().getZipCode());
    addressWs.setTownName(invoice.getCounterparty().getAddress().getTownName());
    addressWs.setStreetName(invoice.getCounterparty().getAddress().getStreetName());
    addressWs.setHouseNumber(invoice.getCounterparty().getAddress().getHouseNumber());

    CounterpartyWS counterpartyWs = new CounterpartyWS();
    counterpartyWs.setCompanyName(invoice.getCounterparty().getCompanyName());
    counterpartyWs.setAddress(addressWs);
    counterpartyWs.setPhoneNumber(invoice.getCounterparty().getPhoneNumber());
    counterpartyWs.setNip(invoice.getCounterparty().getNIP());
    counterpartyWs.setBankName(invoice.getCounterparty().getBankName());
    counterpartyWs.setBankNumber(invoice.getCounterparty().getBankNumber());

    InvoiceWS invoiceWs = new InvoiceWS();
    invoiceWs.setId(invoice.getId());
    invoiceWs.setDate(DateConverter.convertToXMLGregorianCalendar(invoice.getDate()));
    invoiceWs.setCounterparty(counterpartyWs);

    InvoiceItemWS itemWs = new InvoiceItemWS();
    for (InvoiceItem item : invoice.getInvoiceItems()) {
      itemWs.setDescription(item.getDescription());
      itemWs.setNumberOfItem(item.getNumberOfItem());
      itemWs.setAmount(item.getAmount());
      itemWs.setVatAmount(item.getVatAmount());
      itemWs.setVat(toVatWs(item.getVat()));
      invoiceWs.getItems().add(itemWs);
    }
    return invoiceWs;
  }

  public static InvoiceBody convertToInvoiceBody(InvoiceBodyWS invoiceBodyWs)
      throws DatatypeConfigurationException {
    return new InvoiceBody(DateConverter.convertToLocalDate(invoiceBodyWs.getDate()),
        convertToCounterparty(invoiceBodyWs), convertToInvoiceItems(invoiceBodyWs));
  }

  public static InvoiceBodyWS convertToInvoiceBodyWS(InvoiceBody invoiceBody)
      throws DatatypeConfigurationException {
    InvoiceBodyWS invoiceBodyWs = new InvoiceBodyWS();
    invoiceBodyWs.setDate(DateConverter.convertToXMLGregorianCalendar(invoiceBody.getDate()));
    invoiceBodyWs.setCounterparty(convertToCounterpartyWS(invoiceBody.getCounterparty()));
    invoiceBodyWs.getItemsList().addAll(convertToInvoiceItemWsList(invoiceBody.getInvoiceItems()));
    return invoiceBodyWs;
  }

  ;

  private static AddressWS convertToAddressWS(Address address) {
    AddressWS addressWs = new AddressWS();
    addressWs.setZipCode(address.getZipCode());
    addressWs.setTownName(address.getTownName());
    addressWs.setStreetName(address.getStreetName());
    addressWs.setHouseNumber(address.getHouseNumber());
    return addressWs;
  }

  private static CounterpartyWS convertToCounterpartyWS(Counterparty counterparty) {
    AddressWS addressWs = convertToAddressWS(counterparty.getAddress());
    CounterpartyWS counterpartyWs = new CounterpartyWS();
    counterpartyWs.setCompanyName(counterparty.getCompanyName());
    counterpartyWs.setAddress(addressWs);
    counterpartyWs.setPhoneNumber(counterparty.getPhoneNumber());
    counterpartyWs.setNip(counterparty.getNIP());
    counterpartyWs.setBankName(counterparty.getBankName());
    counterpartyWs.setBankNumber(counterparty.getBankNumber());
    return counterpartyWs;
  }

  private static List<InvoiceItemWS> convertToInvoiceItemWsList(List<InvoiceItem> list) {
    List<InvoiceItemWS> invoiceItemWSList = new ArrayList<>();
    InvoiceItemWS itemWs = new InvoiceItemWS();
    for (InvoiceItem item : list) {
      itemWs.setDescription(item.getDescription());
      itemWs.setNumberOfItem(item.getNumberOfItem());
      itemWs.setAmount(item.getAmount());
      itemWs.setVatAmount(item.getVatAmount());
      itemWs.setVat(toVatWs(item.getVat()));
      invoiceItemWSList.add(itemWs);
    }
    return invoiceItemWSList;
  }

  public static Invoice createInvoice(int id, InvoiceBodyWS invoiceBodyWs)
      throws DatatypeConfigurationException {
    return InvoiceBuilder.builder()
        .withId(id)
        .withDate(DateConverter.convertToLocalDate(invoiceBodyWs.getDate()))
        .withCounterparty(convertToCounterparty(invoiceBodyWs))
        .withInvoiceItems(convertToInvoiceItems(invoiceBodyWs))
        .build();
  }

  public static Invoice createInvoice(InvoiceBodyWS invoiceBodyWs, InvoiceBook invoiceBook)
      throws DatatypeConfigurationException {
    return InvoiceBuilder.builder()
        .withId(invoiceBook.getNextInvoiceId())
        .withDate(DateConverter.convertToLocalDate(invoiceBodyWs.getDate()))
        .withCounterparty(convertToCounterparty(invoiceBodyWs))
        .withInvoiceItems(convertToInvoiceItems(invoiceBodyWs))
        .build();
  }

  public static Vat toVat(VatWS vatWs) {
    switch (vatWs) {
      case VAT_5:
        return Vat.VAT_5;
      case VAT_8:
        return Vat.VAT_8;
      case VAT_23:
        return Vat.VAT_23;
      default:
        return Vat.VAT_0;
    }
  }

  public static VatWS toVatWs(Vat vatWs) {
    switch (vatWs) {
      case VAT_5:
        return VatWS.VAT_5;
      case VAT_8:
        return VatWS.VAT_8;
      case VAT_23:
        return VatWS.VAT_23;
      default:
        return VatWS.VAT_0;
    }
  }

}
