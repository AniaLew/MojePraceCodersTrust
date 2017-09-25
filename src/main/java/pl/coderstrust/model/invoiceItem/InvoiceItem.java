package pl.coderstrust.model.invoiceItem;

import java.math.BigDecimal;

public class InvoiceItem {

  private String description;
  private Integer numberOfItem;
  private BigDecimal amount;
  private BigDecimal vatAmount;
  private Vat vat;

  public InvoiceItem(String description, Integer numberOfItem, BigDecimal amount,
      BigDecimal vatAmount, Vat vat) {
    this.description = description;
    this.numberOfItem = numberOfItem;
    this.amount = amount;
    this.vatAmount = vatAmount;
    this.vat = vat;
  }

  public InvoiceItem() {
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getVatAmount() {
    return vatAmount;
  }

  public Integer getNumberOfItem() {
    return numberOfItem;
  }

  public Vat getVat() {
    return vat;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setNumberOfItem(Integer numberOfItem) {
    this.numberOfItem = numberOfItem;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setVatAmount(BigDecimal vatAmount) {
    this.vatAmount = vatAmount;
  }

  public void setVat(Vat vat) {
    this.vat = vat;
  }

  @Override
  public String toString() {
    return "InvoiceItem{"
        + "description='" + description + '\''
        + ", numberOfItem=" + numberOfItem
        + ", amount=" + amount
        + ", vatAmount=" + vatAmount
        + ", VAT=" + vat
        + '}';
  }
}