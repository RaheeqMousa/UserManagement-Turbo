package com.example.TurboUserManagament.service;

import com.example.TurboUserManagament.entity.CallCenterAgent;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.repository.CallCenterRepository;

import java.util.List;

public class CallCenterService {

    private final CallCenterRepository callCenterRepository;

    public CallCenterService(CallCenterRepository callCenterRepository){
        this.callCenterRepository=callCenterRepository;
    }

    public CallCenterAgent getCallCenterAgent(Long callCenterId){
        return null;
    }

    public List<CallCenterAgent> getAgents(){
        return null;
    }

    public CallCenterAgent updateAgent(Long agentId, CallCenterAgent updatedAgent){
        CallCenterAgent existingAgent= getCallCenterAgent(agentId);
        return null;
    }

}
