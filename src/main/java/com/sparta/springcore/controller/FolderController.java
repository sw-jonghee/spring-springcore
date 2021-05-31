package com.sparta.springcore.controller;

import com.sparta.springcore.dto.FolderCreateRequestDto;
import com.sparta.springcore.exception.ApiException;
import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    @Autowired
    private FolderService folderService;

    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return folderService.getFolders(userDetails.getUser());
    }

    @PostMapping("/api/folders")
    public List<Folder> addFolders(@RequestBody FolderCreateRequestDto folderCreateRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> folderNames = folderCreateRequestDto.getFolderNames();
        return folderService.createFolders(folderNames, userDetails.getUser());
    }

    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsOnFolder(@PathVariable("folderId") Long folderId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy") String sortBy,
                                             @RequestParam("isAsc") boolean isAsc,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        page = page -1;
        return folderService.getProductsOnFolder(userDetails.getUser(), page, size, sortBy, isAsc, folderId);
    }

    //공통적으로 "IllegalArgumentException" 들어오면 여기서 처리하겠다.
//    @ExceptionHandler({ IllegalArgumentException.class })
//    public ResponseEntity<Object> handle(IllegalArgumentException ex) {
//        ApiException apiException = new ApiException(
//                ex.getMessage(),
//                // HTTP 400 -> Client Error
//                HttpStatus.BAD_REQUEST
//        );
//
//        return new ResponseEntity<>(
//                apiException,   //body
//                HttpStatus.BAD_REQUEST //head
//        );
//    }
}
