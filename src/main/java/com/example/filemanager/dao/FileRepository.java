package com.example.filemanager.dao;

import com.example.filemanager.domain.FmFile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FmFile, String> {

    Optional<FmFile> findById(String name);
}

