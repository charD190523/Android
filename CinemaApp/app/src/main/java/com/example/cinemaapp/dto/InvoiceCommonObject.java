package com.example.cinemaapp.dto;

import java.io.Serializable;
import java.util.List;

public class InvoiceCommonObject implements Serializable {

    private List<InvoiceCommonDTO> invoiceCommonDTOList;

    public InvoiceCommonObject() {
    }

    public InvoiceCommonObject(List<InvoiceCommonDTO> invoiceCommonDTOList) {
        this.invoiceCommonDTOList = invoiceCommonDTOList;
    }

    public List<InvoiceCommonDTO> getInvoiceCommonDTOList() {
        return invoiceCommonDTOList;
    }

    public void setInvoiceCommonDTOList(List<InvoiceCommonDTO> invoiceCommonDTOList) {
        this.invoiceCommonDTOList = invoiceCommonDTOList;
    }
}
