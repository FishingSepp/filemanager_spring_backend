package com.example.filemanager.service;

import org.springframework.stereotype.Service;
import com.example.filemanager.dao.FolderRepository;

@Service
public class FolderService {
    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

}
