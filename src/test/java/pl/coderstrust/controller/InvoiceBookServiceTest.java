package pl.coderstrust.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.coderstrust.generatorsfortests.InvoiceBodyGenerator;
import pl.coderstrust.generatorsfortests.InvoiceGeneratorForTests;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class InvoiceBookServiceTest {

  @InjectMocks
  private InvoiceBookService service;

  @Mock
  InvoiceBook invoiceBook;


  @Before
  public void setUp() {
    initMocks(this);

  }


  @Test
  public void shouldReturnInvoiceBook() {
    //when
    new InvoiceBookService(invoiceBook);
    //then
    assertThat(invoiceBook).isNotNull();
  }

  @Test
  public void shouldReturnSingleInvoice() throws Exception {
    //given
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    Invoice invoice = InvoiceGeneratorForTests.generateOneInvoice();
    int expectedId = invoice.getId();
    when(service.getSingleInvoice(any(Integer.class))).thenReturn(invoice);
    when(invoiceBook.getInvoiceById(any(Integer.class))).thenReturn(invoice);
    //when
    Invoice actualInvoice = service.getSingleInvoice(expectedId);
    //then
    assertEquals(invoice, actualInvoice);
    verify(invoiceBook).getInvoiceById(captor.capture());
    int actualId = captor.getValue();
    assertEquals(expectedId, actualId);
  }

  @Test
  public void shouldReturnInvoicesFromGivenMonth() throws Exception {
    //given
    ArgumentCaptor<Month> captorMonth = ArgumentCaptor.forClass(Month.class);
    ArgumentCaptor<Year> captorYear = ArgumentCaptor.forClass(Year.class);
    List<Invoice> invoices = InvoiceGeneratorForTests.generateListOfInvoicesFromApril2017();
    Month expectedMonth = Month.APRIL;
    Year expectedYear = Year.of(2017);
    when(service.getInvoicesFromGivenMonth(2017, "April")).thenReturn(invoices);
    when(invoiceBook.getInvoices(expectedYear, expectedMonth)).thenReturn(invoices);
    //when
    List<Invoice> actualInvoices = service.getInvoicesFromGivenMonth(2017, "April");
    //then
    assertEquals(invoices, actualInvoices);
    verify(invoiceBook).getInvoices(captorYear.capture(), captorMonth.capture());
    Month actualMonth = captorMonth.getValue();
    Year actualYear = captorYear.getValue();
    assertEquals(expectedMonth, actualMonth);
    assertEquals(expectedYear, actualYear);
  }

  @Test
  public void shouldReturnInvoices() throws Exception {
    //given
    List<Invoice> invoices = InvoiceGeneratorForTests.generateListOfInvoices(
        LocalDate.of(2015, 1, 1), 100);
    when(service.getInvoices()).thenReturn(invoices);
    //when
    List<Invoice> actualInvoices = service.getInvoices();
    //then
    assertEquals(invoices, actualInvoices);
  }

  @Test
  public void shouldPostInvoice() throws Exception {
    //given
    ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);
    InvoiceBody invoiceBody = InvoiceBodyGenerator.generateOneInvoiceBody(LocalDate.now(),
        "LPD", "Petrol", BigDecimal.TEN, BigDecimal.ONE);
    int expectedId = 1;
    when(invoiceBook.getNextInvoiceId()).thenReturn(expectedId);
    //when
    int actualId = service.postInvoice(invoiceBody);
    //then
    verify(invoiceBook, times(1)).addInvoice(captor.capture());
    Invoice actualInvoice = captor.getValue();
    assertEquals(expectedId, actualId);
    assertEquals(invoiceBody.getDate(), actualInvoice.getDate());
    assertEquals(invoiceBody.getCounterparty(), actualInvoice.getCounterparty());
    assertEquals(invoiceBody.getInvoiceItems().get(0).getDescription(),
        actualInvoice.getInvoiceItems().get(0).getDescription());
    assertEquals(invoiceBody.getInvoiceItems().get(0).getAmount(),
        actualInvoice.getInvoiceItems().get(0).getAmount());
    assertEquals(invoiceBody.getInvoiceItems().get(0).getVatAmount(),
        actualInvoice.getInvoiceItems().get(0).getVatAmount());
  }

  @Test
  public void shouldPutInvoice() throws Exception {
    //given
    ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);
    int expectedId = 1;
    InvoiceBody invoiceBody = invoiceBody = InvoiceBodyGenerator.generateOneInvoiceBody(
        LocalDate.now(), "LPD", "Petrol", BigDecimal.TEN, BigDecimal.ONE);

    Invoice invoice = InvoiceGeneratorForTests.generateOneInvoice(invoiceBody.getDate(),
        invoiceBody.getCounterparty().getCompanyName(),
        invoiceBody.getInvoiceItems().get(0).getDescription(),
        invoiceBody.getInvoiceItems().get(0).getAmount(),
        invoiceBody.getInvoiceItems().get(0).getVatAmount());

    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    when(invoiceBook.getInvoices()).thenReturn(invoices);
    when(invoiceBook.getNextInvoiceId()).thenReturn(1);
    //when
    service.putInvoice(expectedId, invoiceBody);
    //then
    verify(invoiceBook, times(1)).addInvoice(captor.capture());
    Invoice actualInvoice = captor.getValue();
    assertEquals(expectedId, actualInvoice.getId());
    assertEquals(invoiceBody.getDate(), actualInvoice.getDate());
    assertEquals(invoiceBody.getCounterparty(), actualInvoice.getCounterparty());
    assertEquals(invoiceBody.getInvoiceItems().get(0).getDescription(),
        actualInvoice.getInvoiceItems().get(0).getDescription());
    assertEquals(invoiceBody.getInvoiceItems().get(0).getAmount(),
        actualInvoice.getInvoiceItems().get(0).getAmount());
    assertEquals(invoiceBody.getInvoiceItems().get(0).getVatAmount(),
        actualInvoice.getInvoiceItems().get(0).getVatAmount());
  }


  @Test
  public void shouldDeleteInvoice() throws Exception {
    //given
    int id = 1;
    Invoice invoice = InvoiceGeneratorForTests.generateOneInvoice();
    List<Invoice> invoices = new ArrayList<>();
    invoices.add(invoice);
    when(invoiceBook.getInvoices()).thenReturn(invoices);
    //when
    Boolean actual = service.deleteInvoice(id);
    //then
    assertEquals(true, actual);
  }

}
