package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.entity.Order;
import jakarta.mail.MessagingException;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface IInvoiceService {

    void generateInvoice(Order order) throws FileNotFoundException, JRException, MessagingException;

}
