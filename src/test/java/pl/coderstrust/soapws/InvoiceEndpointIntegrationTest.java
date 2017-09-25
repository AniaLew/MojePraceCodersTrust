package pl.coderstrust.soapws;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.serverOrReceiverFault;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

import javax.xml.transform.Source;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;
import pl.coderstrust.Application;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class InvoiceEndpointIntegrationTest {

  @Autowired
  private WebApplicationContext applicationContext;

  private MockWebServiceClient mockClient;
  private Resource xsdSchema = new ClassPathResource("Invoices.xsd");

  @Autowired
  private InvoiceBook invoiceBook;

  @Before
  public void init() throws IOException {
    mockClient = MockWebServiceClient.createClient(applicationContext);

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
    for (Invoice item : invoiceBook.getInvoices()) {
      invoiceBook.removeInvoice(item.getId());
    }
    invoiceBook.addInvoice(invoice);
  }

  @Test
  public void shouldValidateXsdGetInvoiceResponse() throws IOException {
    //given
    String filePathRequest = "src/test/resources/SoapXmlRequests/getInvoiceRequestId1.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/getInvoiceResponseId1.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  public void shouldValidateXsdGetInvoiceResponseForZeroId() throws IOException {
    String filePathRequest = "src/test/resources/SoapXmlRequests/getInvoiceRequestId0.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    Source requestPayload = new StringSource(getInvoiceStringRequest);
    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(serverOrReceiverFault());
  }

  @Test
  public void shouldValidateXsdGetInvoicesResponse() throws IOException {
    //given
    String filePathRequest = "src/test/resources/SoapXmlRequests/getInvoicesRequest.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/getInvoicesResponse.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  public void shouldValidateXsdGetInvoicesResponseByDate() throws IOException {
    //given
    String filePathRequest = "src/test/resources/SoapXmlRequests/getInvoicesRequestByDate.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/getInvoicesResponseByDate.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  public void shouldValidateXsdGetInvoicesResponseByYearAndMonth() throws IOException {
    //given
    String filePathRequest = "src/test/resources/SoapXmlRequests/getInvoicesRequestByYearAndMonth.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/getInvoicesResponseByYearAndMonth.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  public void shouldValidateXsdSetInvoicesResponseAddInvoice() throws IOException {
    //given
    invoiceBook.getNextInvoiceId();
    String filePathRequest = "src/test/resources/SoapXmlRequests/setInvoiceRequestAdd.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/setInvoiceResponseAdd.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  public void shouldValidateXsdSetInvoicesResponseUpdateInvoice() throws IOException {
    //given
    String filePathRequest = "src/test/resources/SoapXmlRequests/setInvoiceRequestUpdate.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/setInvoiceResponseUpdate.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  public void shouldValidateXsdSetInvoicesZzzResponseRemoveInvoice() throws IOException {
    //given
    String filePathRequest = "src/test/resources/SoapXmlRequests/setInvoiceRequestDelete.xml";
    String filePathResponse = "src/test/resources/SoapXmlRequests/setInvoiceResponseDelete.xml";
    XmlFileReader fileReader = new XmlFileReader();
    String getInvoiceStringRequest = fileReader.readFromFile(filePathRequest);
    String getInvoiceStringResponse = fileReader.readFromFile(filePathResponse);

    Source requestPayload = new StringSource(getInvoiceStringRequest);
    Source responsePayload = new StringSource(getInvoiceStringResponse);
    //when
    mockClient
        .sendRequest(withPayload(requestPayload))
        //then
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }
}