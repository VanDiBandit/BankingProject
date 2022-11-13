package com.banking.banking.service;

import com.banking.banking.ResponseHandler.ResponseHandler;
import com.banking.banking.exception.ResourceNotFoundException;
import com.banking.banking.model.Customer;
import com.banking.banking.model.Deposit;
import com.banking.banking.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DepositService {

    @Autowired
    private DepositRepository depositRepository;

    static Logger logger = LoggerFactory.getLogger(DepositService.class);



    public void createDeposit(Deposit deposit) throws ResourceNotFoundException{
        depositRepository.save(deposit);
        if (deposit.getPayee_id() == null){
            throw new ResourceNotFoundException( "Error creating Deposit");
        }
    }

    public ResponseEntity<?> getAllDeposits(){
        List<Deposit> deposits = (List<Deposit>) depositRepository.findAll();
        if(deposits.isEmpty()){
            throw new ResourceNotFoundException("error fetching deposits");
        }
        return ResponseHandler.generateResponse("Successfully retrieved customers' data!", HttpStatus.OK, deposits);
    }

    public ResponseEntity<?> getDepositById(Long depositId) throws ResourceNotFoundException {
        Deposit deposit1 = depositRepository.findById(depositId).orElse(null);
        if (deposit1 == null){
            throw new ResourceNotFoundException("error fetching deposit");
        }
        return ResponseHandler.generateResponse("Successfully retrieved deposit data!", HttpStatus.OK, deposit1);
    }

    public ResponseEntity<?> updateDeposit(Deposit deposit, Long depositId) {
        Deposit c = depositRepository.findById(depositId).orElse(null);
        if (c == null){
            throw new ResourceNotFoundException("error updating deposit");
        }else {
            depositRepository.save(deposit);
        }
       return ResponseHandler.generateResponse("Updated", HttpStatus.OK, c);
    }

    public ResponseEntity<?> deleteDepositById(Long depositId)  {
        Deposit c = depositRepository.findById(depositId).orElse(null);
        if (c == null) {
            throw new ResourceNotFoundException("error deleting deposit");
        }else {
            depositRepository.deleteById(depositId);
        }
        return ResponseHandler.generateResponseNoObj("Deposit deleted", HttpStatus.OK);
    }





}
