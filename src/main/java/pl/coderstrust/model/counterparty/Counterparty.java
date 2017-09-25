package pl.coderstrust.model.counterparty;

public class Counterparty {

  private String companyName;
  private Address address;
  private String phoneNumber;
  private String nip;
  private String bankName;
  private String bankNumber;

  public Counterparty(String companyName, Address address, String phoneNumber, String nip,
      String bankName,
      String bankNumber) {
    this.companyName = companyName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.nip = nip;
    this.bankName = bankName;
    this.bankNumber = bankNumber;
  }

  public Counterparty() {

  }

  public String getCompanyName() {
    return companyName;
  }

  public Address getAddress() {
    return address;
  }

  public String getNIP() {
    return nip;
  }

  public String getBankName() {
    return bankName;
  }

  public String getBankNumber() {
    return bankNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setNip(String nip) {
    this.nip = nip;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public void setBankNumber(String bankNumber) {
    this.bankNumber = bankNumber;
  }

  @Override
  public String toString() {
    return "Counterparty{"
        + "companyName='" + companyName + '\''
        + ", address=" + address
        + ", phoneNumber='" + phoneNumber + '\''
        + ", NIP='" + nip + '\''
        + ", bankName='" + bankName + '\''
        + ", bankNumber='" + bankNumber + '\''
        + '}';
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Counterparty)) {
      return false;
    }
    Counterparty that = (Counterparty) object;
    if (!getCompanyName().equals(that.getCompanyName())) {
      return false;
    }
    if (!getAddress().equals(that.getAddress())) {
      return false;
    }
    return getNIP() != null ? getNIP().equals(that.getNIP()) : that.getNIP() == null;
  }

  @Override
  public int hashCode() {
    int result = getCompanyName().hashCode();
    result = 31 * result + getAddress().hashCode();
    result = 31 * result + nip.hashCode();
    return result;
  }
}
