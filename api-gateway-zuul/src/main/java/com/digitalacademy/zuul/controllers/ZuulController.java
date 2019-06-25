package com.digitalacademy.zuul.controllers;


import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.exceptions.ZuulServiceException;
import com.digitalacademy.zuul.models.ResponseModel;
import com.digitalacademy.zuul.models.StatusModel;
import com.digitalacademy.zuul.models.Zuul;
import com.digitalacademy.zuul.services.ZuulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/user")
public class ZuulController {

    // Further references are provided in `loanservice` project.
    private ZuulService zuulService;

    @Autowired
    public ZuulController(ZuulService zuulService) {
        this.zuulService = zuulService;
    }

    // This is just a rough example of how to handle the request from outside.
    // *** You SHOULD, obviously, modify this method (and also `Zuul` model) to suit your project ***
    @GetMapping("/info/{id}")
    public HttpEntity<ResponseModel> getUserById(@PathVariable Long id) {
        try {
            Zuul zuul = zuulService.getZuulById(id);
            StatusModel status = new StatusModel(
                    StatusResponse.GET_SUCCESS_RESPONSE.getCode(), StatusResponse.GET_SUCCESS_RESPONSE.getMessage()
            );

            return ResponseEntity.ok(new ResponseModel(status, zuul));
        } catch (ZuulServiceException e) {
            StatusResponse statusResponse = e.getStatusResponse();

            return ResponseEntity.ok(new ResponseModel(
                    new StatusModel(statusResponse.getCode(), statusResponse.getMessage()),
                    "Error caused by user service"
            ));
        } catch (Exception e) {
            StatusResponse statusResponse = StatusResponse.GET_TECHNICAL_ERROR_EXCEPTION;

            return new ResponseModel(
                    new StatusModel(statusResponse.getCode(), statusResponse.getMessage()),
                    "Error caused by unknown incident"
            ).build(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
