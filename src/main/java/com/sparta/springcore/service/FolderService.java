package com.sparta.springcore.service;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;


    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    public List<Folder> createFolders(List<String> folderNames, User user) {
        List<Folder> folderList = new ArrayList<>();
        for(String folderName : folderNames) {
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }

        folderList = folderRepository.saveAll(folderList);
        return folderList;
    }
}
