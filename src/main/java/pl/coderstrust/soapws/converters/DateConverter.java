package pl.coderstrust.soapws.converters;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class DateConverter {

  public static XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDate date)
      throws DatatypeConfigurationException {
    if (date == null) {
      return null;
    } else {
      GregorianCalendar gCal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
      XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
      return xmlCal;
    }
  }

  public static LocalDate convertToLocalDate(XMLGregorianCalendar date)
      throws DatatypeConfigurationException {
    if (date == null) {
      return null;
    }
    return date.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }

}
