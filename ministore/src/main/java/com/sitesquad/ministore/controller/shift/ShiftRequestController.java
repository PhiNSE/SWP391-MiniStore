package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.constant.ShiftConstant;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.ShiftRequest;
import com.sitesquad.ministore.repository.ShiftRequestRepository;
import com.sitesquad.ministore.service.shift.ShiftRequestService;
import com.sitesquad.ministore.service.shift.UserShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShiftRequestController {

    @Autowired
    ShiftRequestService shiftRequestService;

    @Autowired
    RequestMeta requestMeta;

    @Autowired
    UserShiftService userShiftService;

    @Autowired
    ShiftRequestRepository shiftRequestRepository;

    @GetMapping("/shiftRequest")
    public ResponseEntity<ResponseObject> getAll(){
        List<ShiftRequest> shiftRequests = shiftRequestRepository.findByType(ShiftConstant.SHIFT_REQUEST_TYPE);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Found shift requests",shiftRequests));
    }

    @PostMapping("/shiftRequest")
    public ResponseEntity<ResponseObject> add(@RequestParam(required = false) Long userShiftId){
        System.out.println(userShiftId);
        if(userShiftId == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"userShiftId parameter not found",""));
        }
        if(requestMeta.getRole().equals(RoleConstant.ADMIN_ROLE_NAME)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Admin cant request a shift",""));
        }
        String role = userShiftService.getRoleByUserShiftId(userShiftId);
        if(!requestMeta.getRole().equals(role)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Saler/Guard cant request a different shift",""));
        }
        System.out.println(requestMeta.getUserId());
        ShiftRequest shiftRequest = new ShiftRequest();
        shiftRequest.setUserId(requestMeta.getUserId());
        shiftRequest.setUserShiftId(userShiftId);
        //shift type request
        shiftRequest.setType(ShiftConstant.SHIFT_REQUEST_TYPE);
        ShiftRequest shiftRequestAdded = shiftRequestService.add(shiftRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"add shift request successfully",shiftRequestAdded));
    }

    @PutMapping("/shiftRequest")
    public ResponseEntity<ResponseObject> update(@RequestParam(required = false) ShiftRequest shiftRequest){
        if(shiftRequest == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"shiftRequest parameter not found",""));
        }
        ShiftRequest shiftRequestAdded = shiftRequestService.edit(shiftRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Update shift request successfully",shiftRequestAdded));
    }

    @DeleteMapping("/shiftRequest")
    public ResponseEntity<ResponseObject> delete(@RequestParam(required = false) Long shiftRequestId){
//        if(shiftRequestId == null){
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseObject(200,"shiftRequestId parameter not found",""));
//        }
//        if(shiftRequestRepository.findById(shiftRequestId).orElse(null) == null){
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseObject(200,"shiftRequestId doesn't exist in database",""));
//        }
        shiftRequestService.delete(shiftRequestId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Delete shift request successfully",""));
    }

    
}
