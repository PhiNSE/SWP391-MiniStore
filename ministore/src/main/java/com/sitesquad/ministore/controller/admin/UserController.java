/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.admin;


import com.sitesquad.ministore.dto.PasswordChangeDTO;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.dto.UserDTO;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.service.RoleService;
import com.sitesquad.ministore.service.UserNotificationService;
import com.sitesquad.ministore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ACER
 */

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    RequestMeta requestMeta;

    @Autowired
    UserNotificationService userNotificationService;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllUser(@RequestParam(required = false)  Integer offset){
        System.out.println(requestMeta.getName());
        if(requestMeta != null && requestMeta.getRole().trim().equalsIgnoreCase("Admin")){
            if (offset == null) {
                offset = 0;
            }
            Page<UserDTO> userList = userService.findAll(offset);

            if (!userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "User List found", userList)
                );
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "User List not found", "")
                );
            }
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(405, "Access denied", "")
            );
        }
    }
    
    @GetMapping("/details")
    public ResponseEntity<ResponseObject> getUserById(@RequestParam Long id){
        UserDTO foundUser = userService.findById(id);
        if(foundUser != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "User found", foundUser)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "User not found", "")
            );
        }
    }
    
//    @GetMapping("/search")
//    public ResponseEntity<ResponseObject> getUserByRoleName()

    @GetMapping("/profile")
    public ResponseEntity<ResponseObject> userDetails(){
        UserDTO user = userService.findUserByEmailNoPassword(requestMeta.getEmail());
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "User found", user)
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "User not found", "")
            );
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addUser(@RequestBody  User user) {
        if(user.getEmail() == null || user.getPhone()== null || user.getRoleId() == null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(405, "Email or phone or roleId must not be empty", "")
            );
        }
        boolean checkUser = userService.checkUserExist(user.getEmail(),user.getPhone());

        if(checkUser != true){

            User addUser = userService.addUser(user);
            if(addUser != null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "add success", addUser)
                );
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "add failed", addUser)
                );
            }
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(405, "user's mail or phone already exist", "")
            );
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity<ResponseObject> addUser(@RequestBody (required = false) Map<String, String> requestData){
//
//        String email = requestData.get("email");
//        String phone = requestData.get("phone");
//        String name = requestData.get("name");
//        String address = requestData.get("address");
//        String roleId = requestData.get("roleId");
//        String userImg = requestData.get("userImg");
//        String dob = requestData.get("dob");
//        String gender = requestData.get("gender");
//
//        System.out.println("Name: " + name);
//        System.out.println("Role Name: " + roleId);
//        System.out.println("Email: " + email);
//        System.out.println("Phone: " + phone);
//        System.out.println("Address: " + address);
//
//        if(requestMeta != null && requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
//            boolean checkUser = userService.checkUserExist(email, phone);
//            if (checkUser == true) {
//                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
//                        new ResponseObject(405, "User email or phone is already use", "")
//                );
//            } else {
//
//                User addUser = userService.add(name,email,phone,address,dob,gender,Long.parseLong(roleId),userImg);
//                if (addUser != null) {
//                    return ResponseEntity.status(HttpStatus.OK).body(
//                            new ResponseObject(200, "Add success", addUser)
//                    );
//                } else {
//                    return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(
//                            new ResponseObject(500, "Add failed", "")
//                    );
//                }
//            }
//        }else{
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(405, "Access denied", "")
//            );
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser (@PathVariable Long id){
        if(requestMeta != null && requestMeta.getRole().trim().equalsIgnoreCase("Admin")  ) {
            boolean userDelete = userService.delete(id);
            if (userDelete == true){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Delete success", "")
                );
            }else
                return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(
                        new ResponseObject(500, "Delete failed", "")
                );

        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(406, "Access denied", "")
            );
        }
    }

    @PutMapping()
    public ResponseEntity<ResponseObject> editUser (@RequestBody(required = false) User user) {
            if(!requestMeta.getEmail().equals(user.getEmail()) && !requestMeta.getEmail().equals("admin@gmail.com") ){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "You dont have right to edit this user", "")
                );
            }
            if(user.getEmail() == null || user.getPhone()== null || user.getRoleId() == null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(405, "Email or phone or role Id must not be empty", "")
                );
            }

            if(roleService.findById(user.getRoleId()) == null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "Role id not found", "")
                );
            }else{

                User editedUser = userService.edit(user);
                if (editedUser != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(200, "Edit success", editedUser)
                    );
                } else {
                    return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(
                            new ResponseObject(500, "Edit failed", "")
                    );
                }

            }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ResponseObject> changePassword (@RequestBody(required = false)PasswordChangeDTO passwordChangeDTO){
        String oldPassword = passwordChangeDTO.getOldPassword();
        String newPassword = passwordChangeDTO.getNewPassword();
        User currentUser = userRepository.findOneByEmailIgnoreCaseAndIsDeletedFalse(requestMeta.getEmail().trim());
        if(!passwordChangeDTO.getOldPassword().equals(currentUser.getPassword())){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(400, "Wrong password", "")
            );
        }
        if(oldPassword.equals(newPassword)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(400, "New password must be different from the old one", "")
            );
        }else{

            User userChange = userService.changePassword(currentUser,newPassword);

            if(userChange != null) {
//                List<Long> userId = new ArrayList<>();
//                userId.add(userChange.getUserId());
//                userNotificationService.customCreateUserNotification("thong bao doi mk","mk bi doi",userId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Edit success", userChange)
                );
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Edit failed", userChange)
                );
            }
        }
    }
}

