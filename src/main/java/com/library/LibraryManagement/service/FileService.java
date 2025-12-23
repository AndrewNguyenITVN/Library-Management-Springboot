package com.library.LibraryManagement.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean saveFile(MultipartFile file);
    Resource loadFile(String filename);
}
