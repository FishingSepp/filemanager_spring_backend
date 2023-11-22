package com.example.filemanager.controller;

import com.example.filemanager.domain.FmFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.filemanager.dao.FileRepository;
import com.example.filemanager.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileService fileService;
    private final FileRepository fileRepository;

    public FileController(FileRepository fileRepository, FileService fileService) {
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @PutMapping("/{fileName}")
    @ApiOperation(value = "Update or Create fmFile by name", notes = "Creates or updates a fmFile with the given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File created or updated successfully"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> updateOrCreateFile(@PathVariable String fileName, @Validated @RequestBody FmFile fmFile) {
        Optional<FmFile> existingFileOptional = fileRepository.findById(fileName);

        FmFile fileToUpdate;
        if (existingFileOptional.isPresent()) {
            // File exists, update it
            fileToUpdate = existingFileOptional.get();
        } else {
            // File doesn't exist, create a new one
            fileToUpdate = new FmFile();
            fileToUpdate.setName(fileName); // Setting the name from path variable
        }

        // Update or set other file properties
        fileToUpdate.setPath(fmFile.getPath());
        fileToUpdate.setFileType(fmFile.getFileType());
        fileToUpdate.setLastConfigured(fmFile.getLastConfigured());
        fileToUpdate.setSize(fmFile.getSize());

        FmFile savedFmFile = fileRepository.save(fileToUpdate);
        log.trace("updateOrCreateFile(): file processed: name=" + fileName);
        return new ResponseEntity<>(savedFmFile, HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    @ApiOperation(value = "Gets file by name", notes = "Gets a file with the given given")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job received successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<FmFile> getFileByName(@PathVariable String fileName) {
        Optional<FmFile> fileOptional = fileRepository.findById(fileName);
        if (fileOptional.isPresent()) {
            log.trace("getFileByName(): getting file: name="+fileName);
            return new ResponseEntity<>(fileOptional.get(), HttpStatus.OK);
        }
        log.trace("getFileByName(): file not found for name="+fileName);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{fileName}")
    @ApiOperation(value = "Delete file by name", notes = "Deletes the file with the given name")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "FmFile deleted successfully"),
            @ApiResponse(code = 404, message = "FmFile not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<String> deleteJob(@PathVariable("fileName") String fileName) {
        Optional<FmFile> fileOptional = fileRepository.findById(fileName);

        if (!fileOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("deleteJob(): No file found with name: " + fileName);
        }
        fileRepository.delete(fileOptional.get());
        log.trace("deleteFile(): deleting file: name="+fileName);
        return ResponseEntity.noContent().build();
    }
}