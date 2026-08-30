package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.CallCenterAgent;
import com.example.TurboUserManagament.exception.UserNotFoundException;
import com.example.TurboUserManagament.repository.CallCenterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CallCenterService {

    private final CallCenterRepository callCenterRepository;
    private final UserService userService;

    public CallCenterService(CallCenterRepository callCenterRepository,
                             UserService userService){
        this.callCenterRepository=callCenterRepository;
        this.userService=userService;
    }

    public CallCenterAgent getCallCenterAgent(Long callCenterId){
        CallCenterAgent agent = callCenterRepository.findByID(callCenterId);

        if (agent == null) {
            throw new UserNotFoundException("Call center agent not found");
        }

        return agent;
    }

    public CallCenterAgent updateAgent(Long agentId, CallCenterAgent updatedAgent){
        CallCenterAgent existingAgent= getCallCenterAgent(agentId);
        return null;
    }

}
