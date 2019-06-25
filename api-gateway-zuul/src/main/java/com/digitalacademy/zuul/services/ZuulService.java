package com.digitalacademy.zuul.services;

import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.exceptions.ZuulServiceException;
import com.digitalacademy.zuul.models.Zuul;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ZuulService {

    // Further references are provided in `customerservice` and `loanservice` project.
    // This is just a rough example of how to handle the request from UserController.
    // *** You SHOULD, obviously, modify this method (and also `Zuul` model) to suit your project ***
    public Zuul getZuulById(Long id) throws Exception {
        Zuul zuul = new Zuul();
        if (id.equals(1L)) {
//            zuul.setId(1L);
//            zuul.setStatus("OK");
//            zuul.setAccountPayable("102-403-9696");
//            zuul.setAccountReceivable("105-232-5672");
//            zuul.setPrincipalAmount(350000.00);
//            zuul.setOutstandingBalance(320000.00);
//            zuul.setInterest(4.20);
//            zuul.setInstallmentAmount(10000.00);
//            zuul.setAmountDue(10000.00);
//            zuul.setOverDueAmount(0.00);
        } else if (id.equals(2L)) {
            throw new ZuulServiceException(
                    StatusResponse.GET_NOT_FOUND_ERROR_EXCEPTION, HttpStatus.BAD_REQUEST
            );
        } else {
            throw new Exception("Test Throw new exception");
        }
        return zuul;
    }
}
