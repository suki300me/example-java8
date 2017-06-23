package org.example.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.example.domain.finder.CustomerFinder;

import io.ebean.annotation.DbArray;
import io.ebean.annotation.DbComment;

/**
 * Customer entity bean.
 *
 * This example shows an entity bean without a default constructor. The name property is
 * expected to be final and non-null. Note the InsertCustomerTest.testRef() test showing
 * loading the partially loaded bean.
 */
@DbComment("Customer table general comment")
@Entity
@Table(name="be_customer")
public class Customer extends BaseModel {

  public static final CustomerFinder find = new CustomerFinder();

  boolean inactive;

  @NotNull
  @Column(length=100)
  String name;

  @DbComment("The date the customer first registered")
  LocalDate registered;

  @DbArray // Postgres ARRAY
  List<UUID> uids = new ArrayList<>();

  @Size(max = 1000)
  @Lob
  String comments;

  @ManyToOne(cascade=CascadeType.ALL)
  Address billingAddress;

  @ManyToOne(cascade=CascadeType.ALL)
  Address shippingAddress;

  @OneToMany(mappedBy="customer", cascade=CascadeType.PERSIST)
  List<Contact> contacts;

  public Customer(String name) {
    this.name = name;
  }

  public String toString() {
    return "id:" + id + " name:" + name;
  }

  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getRegistered() {
    return registered;
  }

  public void setRegistered(LocalDate registered) {
    this.registered = registered;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
  
  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  /**
   * Helper method to add a contact to the customer.
   */
  public void addContact(Contact contact) {
    if (contacts == null) {
      contacts = new ArrayList<>();
    }
    // setting the customer is automatically done when Ebean does
    // a cascade save from customer to contacts. 
    contact.setCustomer(this);
    contacts.add(contact);
  }
  
}
