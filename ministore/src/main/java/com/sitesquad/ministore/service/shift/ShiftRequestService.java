package com.sitesquad.ministore.service.shift;

import com.sitesquad.ministore.model.ShiftRequest;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.ShiftRequestRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftRequestService {

    @Autowired
    ShiftRequestRepository shiftRequestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserShiftRepository userShiftRepository;

    public List<User> getRequestUsersByUserShiftId(Long userShiftId){
        List<User> users = new ArrayList<>();
        List<ShiftRequest> shiftRequests = getByUserIdAndUserShiftId(userShiftId);
        if(shiftRequests == null || shiftRequests.isEmpty()){
            return null;
        }
        for(ShiftRequest shiftRequest: shiftRequests){
            if(shiftRequest.getType() == false){
                users.add(userRepository.findByUserIdAndIsDeletedFalse(shiftRequest.getUserId()));
            }
        }
        return users;
    }

    public List<ShiftRequest> getByUserIdAndUserShiftId(Long userShiftId){
        return shiftRequestRepository.findByUserShiftId(userShiftId);
    }

    public List<ShiftRequest> getAll(){
        return shiftRequestRepository.findAll();
    }

    public ShiftRequest add(ShiftRequest shiftRequest){
        System.out.println(shiftRequest);
        shiftRequest.setUser(userRepository.findByUserIdAndIsDeletedFalse(shiftRequest.getUserId()));
        shiftRequest.setUserShift(userShiftRepository.findByUserShiftId(shiftRequest.getUserShiftId()));
        return shiftRequestRepository.save(shiftRequest);
    }

    public ShiftRequest edit(ShiftRequest shiftRequest){
        if(shiftRequestRepository.findById(shiftRequest.getShiftRequestId())==null){
            return null;
        } else{
            return add(shiftRequest);
        }
    }

    public void delete(Long shiftRequestId){
        System.out.println(shiftRequestId);
        shiftRequestRepository.deleteById(shiftRequestId);
    }

    public List<ShiftRequest> findByUserId(Long userId){
        return shiftRequestRepository.findByUserId(userId);
    }
}
