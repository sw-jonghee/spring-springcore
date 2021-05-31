package com.sparta.springcore.service;

import com.sparta.springcore.exception.ApiRequestException;
import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.FolderRepository;
import com.sparta.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

/*    public List<Folder> createFolders(List<String> folderNames, User user) {
        //1) 입력으로 들어온 폴더 이름을 기준으로, 회원이 이미 생성한 폴더들을 조회합니다.
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);  //nameList에 있는 전체 내용을 찾아온다
        List<Folder> folderList = new ArrayList<>();

        for(String folderName : folderNames) {
            //2)이미 생성한 폴더가 아닌 경우에만 폴더 생성
            if(!isExistFolderName(folderName, existFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            }
        }

        folderList = folderRepository.saveAll(folderList);
        return folderList;
    }*/

    //1) 1) 예외 발생 시, 그동안 db에 저장된 폴더들을 삭제
    /*****
    public List<Folder> createFolders(List<String> folderNameList, User user) {
        List<Folder> folderList = new ArrayList<>();

        for(String folderName : folderNameList) {
            // 1) DB에 폴더명이 FOLDER NAME인 폴더가 존재하는지?
            Folder folderInDB = folderRepository.findByName(folderName);
            if(folderInDB != null) {
                //그동안 저장된 폴더들을 모두 삭제
                for(Folder folder : folderList) {
                    folderRepository.delete(folder);
                }

                //db에 중복 폴더명 존재한다면 EXCEPTION 발생시킴
                throw new IllegalArgumentException("중복된 폴더명 (" + folderName + ")을 삭제하고 재시도 해주세요!");
            }

            //2) 폴더를 db에 저장
            Folder folder = new Folder(folderName, user);
            folder = folderRepository.save(folder);

            //3) folderList에 folder entity 객체를 추가
            folderList.add(folder);
        }

        return folderList;
    }
     *******////

    //2) @Transactional을 이용
    @Transactional(readOnly = false)
    public List<Folder> createFolders(List<String> folderNameList, User user){
        List<Folder> folderList = new ArrayList<>();

        for(String folderName : folderNameList) {
            //1) db에 폴더명이 folderName인 폴더가 존재하는지?
            Folder folderInDB = folderRepository.findByName(folderName);

            if(folderInDB != null) {
                //db에 중복 폴더명 존재한다면 exception 발생시킴
                //throw new IllegalArgumentException("중복된 폴더명 (" + folderName + ") 을 삭제하고 재시도 해주세요!");
                throw new ApiRequestException("중복된 폴더명 (" + folderName + ") 을 삭제하고 재시도 해주세요!");
            }

            // 2) 폴더를 db에 저장
            Folder folder = new Folder(folderName, user);
            folder = folderRepository.save(folder);

            //3) folderList에 folder Entity 객체를 추가

            folderList.add(folder);
        }

        return folderList;
    }

    public Page<Product> getProductsOnFolder(User user, int page, int size, String sortBy, boolean isAsc, Long folderId){
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_id(user.getId(), folderId, pageable);
    }

    public boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        // 기존 폴더 리스트에서 folder name 이 있는지?
        for (Folder existFolder : existFolderList) {
            if (existFolder.getName().equals(folderName)) {
                return true;
            }
        }

        return false;
    }
}
