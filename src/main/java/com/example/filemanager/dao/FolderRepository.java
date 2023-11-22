package com.example.filemanager.dao;

import com.example.filemanager.domain.FmFolder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<FmFolder, String> {

    Optional<FmFolder> findById(String name);
}