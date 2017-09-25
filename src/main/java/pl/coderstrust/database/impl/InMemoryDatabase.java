package pl.coderstrust.database.impl;


import org.springframework.stereotype.Service;
import pl.coderstrust.database.Database;
import pl.coderstrust.model.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class InMemoryDatabase implements Database {

  private final List<Invoice> invoices = new ArrayList<>();

  private AtomicInteger atomicInteger = new AtomicInteger(1);

  @Override
  public Integer getNextInvoiceId() {
    return atomicInteger.getAndIncrement();
  }

  @Override
  public void saveInvoice(Invoice invoice) {
    invoices.add(invoice);
  }

  @Override
  public List<Invoice> getInvoices() {
    return invoices.stream().sorted().collect(Collectors.toList());
  }

  @Override
  public void removeInvoice(Invoice invoice) {
    Invoice foundInvoice = invoices
        .stream()
        .filter(invoice1 -> invoice1.equals(invoice))
        .findFirst().orElse(null);
    invoices.remove(invoices.indexOf(foundInvoice));
  }
}