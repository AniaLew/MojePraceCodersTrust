package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.coderstrust.model.counterparty.Counterparty;
import pl.coderstrust.model.invoiceItem.InvoiceItem;

import java.time.LocalDate;
import java.util.List;

public class Invoice implements Comparable<Invoice> {

  private int id;
  private LocalDate date = LocalDate.now();
  private Counterparty counterparty;
  private List<InvoiceItem> invoiceItems;

  /**
   * Create instance of Invoice class.
   *
   * @param date - date of issuing of the invoice.
   * @param counterparty - says which counterparty the invoice was issues by.
   */
  @JsonCreator
  public Invoice(@JsonProperty("id") int id,
      @JsonProperty("date") LocalDate date,
      @JsonProperty("counterparty") Counterparty counterparty,
      @JsonProperty("invoiceItems") List<InvoiceItem> invoiceItems) {
    this.id = id;
    this.date = date;
    this.counterparty = counterparty;
    this.invoiceItems = invoiceItems;
  }

  public Invoice(Integer id, LocalDate date, String counterparty,
      List<InvoiceItem> itemList) {
  }

  public Invoice(Integer id, LocalDate date, Counterparty counterparty) {
    this.id = id;
    this.date = date;
    this.counterparty = counterparty;
  }

  public int getId() {
    return id;
  }

  public LocalDate getDate() {
    return date;
  }

  public Counterparty getCounterparty() {
    return counterparty;
  }

  public List<InvoiceItem> getInvoiceItems() {
    return invoiceItems;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setCounterparty(Counterparty counterparty) {
    this.counterparty = counterparty;
  }

  public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
    this.invoiceItems = invoiceItems;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Invoice)) {
      return false;
    }

    Invoice invoice = (Invoice) obj;

    if (getId() != invoice.getId()) {
      return false;
    }
    if (!getDate().equals(invoice.getDate())) {
      return false;
    }
    return getCounterparty().equals(invoice.getCounterparty());
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + getDate().hashCode();
    result = 31 * result + getCounterparty().hashCode();
    return result;
  }

  @Override
  public int compareTo(Invoice object) {
    if (object == null) {
      return -1;
    }
    if (this == null) {
      return -1;
    }
    int comparison = (this.date.getYear() - object.date.getYear());
    if (comparison == 0) {
      comparison = (this.date.getMonthValue() - object.date.getMonthValue());
      if (comparison == 0) {
        comparison = (this.date.getDayOfMonth() - object.date.getDayOfMonth());
      }
    }
    if (comparison < 0) {
      return -1;
    }
    if (comparison > 0) {
      return 1;
    }

    return 0;
  }

  @Override
  public String toString() {
    return "Invoice{"
        + "id=" + id
        + ", date=" + date
        + ", counterparty='" + counterparty + '\''
        + ", invoiceItems=" + invoiceItems
        + '}';
  }
}