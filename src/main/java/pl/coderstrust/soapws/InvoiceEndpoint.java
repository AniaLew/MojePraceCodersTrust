package pl.coderstrust.soapws;

import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.soapinvoices.GetInvoiceRequest;
import pl.coderstrust.soapinvoices.GetInvoiceResponse;
import pl.coderstrust.soapinvoices.GetInvoicesRequest;
import pl.coderstrust.soapinvoices.GetInvoicesResponse;
import pl.coderstrust.soapinvoices.InvoiceWS;
import pl.coderstrust.soapinvoices.SetInvoiceRequest;
import pl.coderstrust.soapinvoices.SetInvoiceResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://coderstrust.pl/soapinvoices";
  private InvoiceRepository invoiceRepository;

  @Autowired
  public InvoiceEndpoint(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getInvoice(@RequestPayload GetInvoiceRequest request)
      throws DatatypeConfigurationException, IOException {
    if (request.getId() == 0) {
      throw new RuntimeException("id cannot be 0");
    }
    GetInvoiceResponse response = new GetInvoiceResponse();
    response.setInvoice(invoiceRepository.findInvoice(request.getId()));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesRequest")
  @ResponsePayload
  public GetInvoicesResponse getInvoices(@RequestPayload GetInvoicesRequest request)
      throws DatatypeConfigurationException, IOException {
    List<InvoiceWS> invoiceWsList = new ArrayList<>();
    if (!isNotNull(request.getSelectByDate())
        && !isNotNull(request.getSelectByYearAndMonth())) {
      invoiceWsList = invoiceRepository.findInvoices();
    }
    if (isNotNull(request.getSelectByDate())) {
      request.getSelectByDate().getFromDate();
      LocalDate fromDate = LocalDate.parse(request.getSelectByDate().getFromDate());
      LocalDate toDate = LocalDate.parse(request.getSelectByDate().getToDate());
      invoiceWsList = invoiceRepository.findInvoicesFromDateToDate(fromDate, toDate);
    }
    if (isNotNull(request.getSelectByYearAndMonth())) {
      invoiceWsList = invoiceRepository.findInvoicesByYearAndMonth(
          request.getSelectByYearAndMonth().getYear(),
          Month.valueOf(request.getSelectByYearAndMonth().getMonth().toUpperCase()));
    }
    GetInvoicesResponse response = new GetInvoicesResponse();
    response.getInvoices().addAll(invoiceWsList);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setInvoiceRequest")
  @ResponsePayload
  public SetInvoiceResponse setInvoice(@RequestPayload SetInvoiceRequest request)
      throws DatatypeConfigurationException, IOException {

    SetInvoiceResponse response = new SetInvoiceResponse();
    if (isNotNull(request.getInvoiceAttributes())) {
      if (!isNotNull(request.getInvoiceAttributes().getInvoiceBodyWs())) {
        response.setId(invoiceRepository.removeInvoice(request.getInvoiceAttributes().getId()));
      }
      if (!isNotNull(request.getInvoiceAttributes().getId())) {
        response.setId(invoiceRepository.saveInvoice(request
            .getInvoiceAttributes().getInvoiceBodyWs()));
      }
      if (isNotNull(request.getInvoiceAttributes().getId())
          && isNotNull(request.getInvoiceAttributes().getInvoiceBodyWs())) {
        response.setId(invoiceRepository.updateInvoice(request.getInvoiceAttributes().getId(),
            request.getInvoiceAttributes().getInvoiceBodyWs()));
      }
    } else {
      response.setId(-1);
    }
    return response;
  }

  private static boolean isNotNull(Object element) {
    Optional<Object> optional = Optional.ofNullable(element);
    return optional.isPresent();
  }
}