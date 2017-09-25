package pl.coderstrust.model.invoiceItem;

public enum Vat {
  VAT_23(23),
  VAT_8(8),
  VAT_5(5),
  VAT_0(0);

  private final int vatCode;

  Vat(int vatCode) {
    this.vatCode = vatCode;
  }

  public int getVat() {
    return this.vatCode;
  }
}
