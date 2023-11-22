package com.example.filemanager.controller;

import com.example.filemanager.domain.FmFolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.filemanager.dao.FolderRepository;
import com.example.filemanager.service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private static final Logger log = LoggerFactory.getLogger(FolderService.class);

    private final FolderService folderService;
    private final FolderRepository folderRepository;

    public FolderController(FolderRepository folderRepository, FolderService folderService) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
    }

    @PutMapping("/{folderName}")
    @ApiOperation(value = "Update or Create fmFolder by name", notes = "Creates or updates a fmFolder with the given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FmFolder created or updated successfully"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> updateOrCreateFolder(@PathVariable String folderName, @Validated @RequestBody FmFolder fmFolder) {
        Optional<FmFolder> existingFolderOptional = folderRepository.findById(folderName);

        FmFolder folderToUpdate;
        if (existingFolderOptional.isPresent()) {
            // FmFolder exists, update it
            folderToUpdate = existingFolderOptional.get();
        } else {
            // FmFolder doesn't exist, create a new one
            folderToUpdate = new FmFolder();
            folderToUpdate.setName(folderName); // Setting the name from path variable
        }

        // Update or set other fmFolder properties
        folderToUpdate.setPath(fmFolder.getPath());

        FmFolder savedFmFolder = folderRepository.save(folderToUpdate);
        log.trace("updateOrCreateFolder(): fmFolder processed: name=" + folderName);
        return new ResponseEntity<>(savedFmFolder, HttpStatus.OK);
    }

    @GetMapping("/{folderName}")
    @ApiOperation(value = "Gets folder by name", notes = "Gets a folder with the given given")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job received successfully"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<FmFolder> getFolderByName(@PathVariable String folderName) {
        Optional<FmFolder> folderOptional = folderRepository.findById(folderName);
        if (folderOptional.isPresent()) {
            log.trace("getFolderByName(): getting folder: name="+folderName);
            return new ResponseEntity<>(folderOptional.get(), HttpStatus.OK);
        }
        log.trace("getFolderByName(): folder not found for name="+folderName);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{folderName}")
    @ApiOperation(value = "Delete folder by name", notes = "Deletes the folder with the given name")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "FmFolder deleted successfully"),
            @ApiResponse(code = 404, message = "FmFolder not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<String> deleteJob(@PathVariable("folderName") String folderName) {
        Optional<FmFolder> folderOptional = folderRepository.findById(folderName);

        if (!folderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("deleteJob(): No folder found with name: " + folderName);
        }
        folderRepository.delete(folderOptional.get());
        log.trace("deleteFolder(): deleting folder: name="+folderName);
        return ResponseEntity.noContent().build();
    }
}