/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.controllers.api;

import com.fivecafe.models.category.ProductCategoryResponse;
import com.fivecafe.providers.UrlProvider;
import javax.validation.ConstraintViolationException;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.ProductCategory.PREFIX + "")
public class ProductCategoryApiController {
    @PostMapping(value = "" + UrlProvider.ProductCategory.STORE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadSomething(@Valid @RequestBody ProductCategoryResponse reqBody, BindingResult br) throws MethodArgumentNotValidException {
        
        // Create new user instance
        Users user = new Users();
        user.setEmail(data.getEmail().trim());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setFullname("User");
        user.setAvatar(AppConstants.DEFAULT_AVATAR.toString());
        user.setEmailVerifyCode(emailVerifyCode);
        user.setEmailVerifyAt(null);
        user.setForgotPasswordCode(null);
        user.setRole(UserRoles.USER.toString());

        // Insert new user instance into DB
        try {
            usersFacade.create(user);
        } catch (ConstraintViolationException e) {
            for (Object constraintViolation : e.getConstraintViolations()) {
                System.out.println("CONSTRAINT: " + constraintViolation);
            }
        }
        return new ResponseEntity(new UserSignUpResponse(true, "Them du lieu thanh cong"), HttpStatus.OK);
    }
}
