package pl.coderstrust.soapws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import pl.coderstrust.controller.InvoiceBody;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceBook;
import pl.coderstrust.soapinvoices.InvoiceBodyWS;
import pl.coderstrust.soapinvoices.InvoiceWS;
import pl.coderstrust.soapws.converters.InvoiceConverter;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepositoryTest {

  @Test
  public void shouldReturnInvoiceRepository() {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    //when
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    //then
    assertThat(invoiceRepository).isNotNull();
    assertThat(invoiceBook).isNotNull();
  }

  @Test
  public void shouldFindInvoice() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    int id = 1;
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    InvoiceWS invoiceWSExpected = InvoiceConverter.convertToInvoiceWs(invoice);
    when(invoiceBook.getInvoiceById(id)).thenReturn(invoice);
    //when
    InvoiceWS invoiceWSActual = invoiceRepository.findInvoice(id);
    //then
    assertEquals(invoiceWSExpected.getId(), invoiceWSActual.getId());
    assertEquals(invoiceWSExpected.getDate(), invoiceWSActual.getDate());
    assertEquals(invoiceWSExpected.getCounterparty().getCompanyName(),
        invoiceWSActual.getCounterparty().getCompanyName());
    assertEquals(invoiceWSExpected.getCounterparty().getAddress().getZipCode(),
        invoiceWSActual.getCounterparty().getAddress().getZipCode());
    assertEquals(invoiceWSExpected.getCounterparty().getAddress().getHouseNumber(),
        invoiceWSActual.getCounterparty().getAddress().getHouseNumber());
    assertEquals(invoiceWSExpected.getCounterparty().getAddress().getStreetName(),
        invoiceWSActual.getCounterparty().getAddress().getStreetName());
    assertEquals(invoiceWSExpected.getCounterparty().getAddress().getTownName(),
        invoiceWSActual.getCounterparty().getAddress().getTownName());
    assertEquals(invoiceWSExpected.getCounterparty().getPhoneNumber(),
        invoiceWSActual.getCounterparty().getPhoneNumber());
    assertEquals(invoiceWSExpected.getCounterparty().getNip(),
        invoiceWSActual.getCounterparty().getNip());
    assertEquals(invoiceWSExpected.getCounterparty().getBankName(),
        invoiceWSActual.getCounterparty().getBankName());
    assertEquals(invoiceWSExpected.getCounterparty().getBankNumber(),
        invoiceWSExpected.getCounterparty().getBankNumber());
    assertEquals(invoiceWSExpected.getItems().get(0).getDescription(),
        invoiceWSActual.getItems().get(0).getDescription());
    assertEquals(invoiceWSExpected.getItems().get(0).getNumberOfItem(),
        invoiceWSActual.getItems().get(0).getNumberOfItem());
    assertEquals(invoiceWSExpected.getItems().get(0).getAmount(),
        invoiceWSActual.getItems().get(0).getAmount());
    assertEquals(invoiceWSExpected.getItems().get(0).getVatAmount(),
        invoiceWSActual.getItems().get(0).getVatAmount());
    assertEquals(invoiceWSExpected.getItems().get(0).getVat(),
        invoiceWSActual.getItems().get(0).getVat());
  }

  @Test
  public void shouldFindInvoices() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    int id = 1;
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    InvoiceWS invoiceWS = InvoiceConverter.convertToInvoiceWs(invoice);
    List<InvoiceWS> expectedList = new ArrayList<>();
    expectedList.add(invoiceWS);
    when(invoiceBook.getInvoices()).thenReturn(invoices);
    //when
    List<InvoiceWS> actualList = invoiceRepository.findInvoices();
    //then
    assertEquals(expectedList.get(0).getId(), actualList.get(0).getId());
    assertEquals(expectedList.get(0).getDate(), actualList.get(0).getDate());
    assertEquals(expectedList.get(0).getCounterparty().getCompanyName(),
        actualList.get(0).getCounterparty().getCompanyName());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getZipCode(),
        actualList.get(0).getCounterparty().getAddress().getZipCode());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getHouseNumber(),
        actualList.get(0).getCounterparty().getAddress().getHouseNumber());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getStreetName(),
        actualList.get(0).getCounterparty().getAddress().getStreetName());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getTownName(),
        actualList.get(0).getCounterparty().getAddress().getTownName());
    assertEquals(expectedList.get(0).getCounterparty().getPhoneNumber(),
        actualList.get(0).getCounterparty().getPhoneNumber());
    assertEquals(expectedList.get(0).getCounterparty().getNip(),
        actualList.get(0).getCounterparty().getNip());
    assertEquals(expectedList.get(0).getCounterparty().getBankName(),
        actualList.get(0).getCounterparty().getBankName());
    assertEquals(expectedList.get(0).getCounterparty().getBankNumber(),
        actualList.get(0).getCounterparty().getBankNumber());
    assertEquals(expectedList.get(0).getItems().get(0).getDescription(),
        actualList.get(0).getItems().get(0).getDescription());
    assertEquals(expectedList.get(0).getItems().get(0).getNumberOfItem(),
        actualList.get(0).getItems().get(0).getNumberOfItem());
    assertEquals(expectedList.get(0).getItems().get(0).getAmount(),
        actualList.get(0).getItems().get(0).getAmount());
    assertEquals(expectedList.get(0).getItems().get(0).getVatAmount(),
        actualList.get(0).getItems().get(0).getVatAmount());
    assertEquals(expectedList.get(0).getItems().get(0).getVat(),
        actualList.get(0).getItems().get(0).getVat());
  }

  @Test
  public void shouldFindInvoicesByYearAndMonth() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    int id = 1;
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    InvoiceWS invoiceWS = InvoiceConverter.convertToInvoiceWs(invoice);
    List<InvoiceWS> expectedList = new ArrayList<>();
    expectedList.add(invoiceWS);
    when(invoiceBook.getInvoices(Year.of(2017), Month.JULY)).thenReturn(invoices);
    //when
    List<InvoiceWS> actualList = invoiceRepository
        .findInvoicesByYearAndMonth(2017, Month.JULY);
    //then
    assertEquals(expectedList.get(0).getId(), actualList.get(0).getId());
    assertEquals(expectedList.get(0).getDate(), actualList.get(0).getDate());
    assertEquals(expectedList.get(0).getCounterparty().getCompanyName(),
        actualList.get(0).getCounterparty().getCompanyName());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getZipCode(),
        actualList.get(0).getCounterparty().getAddress().getZipCode());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getHouseNumber(),
        actualList.get(0).getCounterparty().getAddress().getHouseNumber());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getStreetName(),
        actualList.get(0).getCounterparty().getAddress().getStreetName());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getTownName(),
        actualList.get(0).getCounterparty().getAddress().getTownName());
    assertEquals(expectedList.get(0).getCounterparty().getPhoneNumber(),
        actualList.get(0).getCounterparty().getPhoneNumber());
    assertEquals(expectedList.get(0).getCounterparty().getNip(),
        actualList.get(0).getCounterparty().getNip());
    assertEquals(expectedList.get(0).getCounterparty().getBankName(),
        actualList.get(0).getCounterparty().getBankName());
    assertEquals(expectedList.get(0).getCounterparty().getBankNumber(),
        actualList.get(0).getCounterparty().getBankNumber());
    assertEquals(expectedList.get(0).getItems().get(0).getDescription(),
        actualList.get(0).getItems().get(0).getDescription());
    assertEquals(expectedList.get(0).getItems().get(0).getNumberOfItem(),
        actualList.get(0).getItems().get(0).getNumberOfItem());
    assertEquals(expectedList.get(0).getItems().get(0).getAmount(),
        actualList.get(0).getItems().get(0).getAmount());
    assertEquals(expectedList.get(0).getItems().get(0).getVatAmount(),
        actualList.get(0).getItems().get(0).getVatAmount());
    assertEquals(expectedList.get(0).getItems().get(0).getVat(),
        actualList.get(0).getItems().get(0).getVat());
  }

  @Test
  public void shouldFindInvoicesFromDateToDate() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    int id = 1;
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    InvoiceWS invoiceWS = InvoiceConverter.convertToInvoiceWs(invoice);
    List<InvoiceWS> expectedList = new ArrayList<>();
    expectedList.add(invoiceWS);
    when(invoiceBook.getInvoices(LocalDate
        .of(2017, 6, 1), LocalDate.of(2017, 8, 1)))
        .thenReturn(invoices);
    //when
    List<InvoiceWS> actualList = invoiceRepository.findInvoicesFromDateToDate(LocalDate
        .of(2017, 6, 1), LocalDate.of(2017, 8, 1));
    //then
    assertEquals(expectedList.get(0).getId(), actualList.get(0).getId());
    assertEquals(expectedList.get(0).getDate(), actualList.get(0).getDate());
    assertEquals(expectedList.get(0).getCounterparty().getCompanyName(),
        actualList.get(0).getCounterparty().getCompanyName());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getZipCode(),
        actualList.get(0).getCounterparty().getAddress().getZipCode());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getHouseNumber(),
        actualList.get(0).getCounterparty().getAddress().getHouseNumber());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getStreetName(),
        actualList.get(0).getCounterparty().getAddress().getStreetName());
    assertEquals(expectedList.get(0).getCounterparty().getAddress().getTownName(),
        actualList.get(0).getCounterparty().getAddress().getTownName());
    assertEquals(expectedList.get(0).getCounterparty().getPhoneNumber(),
        actualList.get(0).getCounterparty().getPhoneNumber());
    assertEquals(expectedList.get(0).getCounterparty().getNip(),
        actualList.get(0).getCounterparty().getNip());
    assertEquals(expectedList.get(0).getCounterparty().getBankName(),
        actualList.get(0).getCounterparty().getBankName());
    assertEquals(expectedList.get(0).getCounterparty().getBankNumber(),
        actualList.get(0).getCounterparty().getBankNumber());
    assertEquals(expectedList.get(0).getItems().get(0).getDescription(),
        actualList.get(0).getItems().get(0).getDescription());
    assertEquals(expectedList.get(0).getItems().get(0).getNumberOfItem(),
        actualList.get(0).getItems().get(0).getNumberOfItem());
    assertEquals(expectedList.get(0).getItems().get(0).getAmount(),
        actualList.get(0).getItems().get(0).getAmount());
    assertEquals(expectedList.get(0).getItems().get(0).getVatAmount(),
        actualList.get(0).getItems().get(0).getVatAmount());
    assertEquals(expectedList.get(0).getItems().get(0).getVat(),
        actualList.get(0).getItems().get(0).getVat());
  }

  @Test
  public void shouldSaveInvoice() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    InvoiceBody invoiceBody = new InvoiceBody(invoice.getDate(), invoice.getCounterparty(),
        invoice.getInvoiceItems());
    InvoiceBodyWS invoiceBodyWS = InvoiceConverter.convertToInvoiceBodyWS(invoiceBody);
    when(invoiceBook.getNextInvoiceId()).thenReturn(1);
    //when
    invoiceRepository.saveInvoice(invoiceBodyWS);
    doThrow(new RuntimeException()).when(invoiceBook).addInvoice(any(Invoice.class));
    //then
    verify(invoiceBook).addInvoice(captor.capture());
    verify(invoiceBook, timeout(1)).addInvoice(any(Invoice.class));
    assertEquals(invoice, captor.getValue());
  }

  @Test
  public void shouldUpdateInvoice() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    InvoiceBody invoiceBody = new InvoiceBody(invoice.getDate(), invoice.getCounterparty(),
        invoice.getInvoiceItems());
    InvoiceBodyWS invoiceBodyWS = InvoiceConverter.convertToInvoiceBodyWS(invoiceBody);
    int expectedId = 1;
    when(invoiceBook.getInvoiceById(expectedId)).thenReturn(invoice);
    //when
    int actualId = invoiceRepository.updateInvoice(expectedId, invoiceBodyWS);
    //then
    assertEquals(expectedId, actualId);
  }

  @Test
  public void shouldRemoveInvoice() throws Exception {
    //given
    final InvoiceBook invoiceBook = mock(InvoiceBook.class);
    InvoiceRepository invoiceRepository = new InvoiceRepository(invoiceBook);
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    Invoice invoice = InvoiceForTestProvider.provideInvoice();
    InvoiceBody invoiceBody = new InvoiceBody(invoice.getDate(), invoice.getCounterparty(),
        invoice.getInvoiceItems());
    InvoiceBodyWS invoiceBodyWS = InvoiceConverter.convertToInvoiceBodyWS(invoiceBody);
    Integer expectedId = 1;

    //when
    Integer actualId = invoiceRepository.removeInvoice(expectedId);
    doThrow(new RuntimeException()).when(invoiceBook).removeInvoice(any(Integer.class));
    //then
    verify(invoiceBook).removeInvoice(captor.capture());
    assertEquals(expectedId, captor.getValue());
    assertEquals(expectedId, actualId);
  }

}