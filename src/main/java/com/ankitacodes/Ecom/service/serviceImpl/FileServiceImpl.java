package com.ankitacodes.Ecom.service.serviceImpl;

import com.ankitacodes.Ecom.exception.BadApiRequestException;
import com.ankitacodes.Ecom.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        //abc.png
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName:{}",originalFilename);
        String fileName= UUID.randomUUID().toString();
        //getting the last part of original image name after "." eg: '.png' from 'abc.png'
        String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=fileName+extension;
        //getting full path upto filename from input path which is upto folder
        String fullPathWithFileName= path+ File.separator+fileNameWithExtension;

    if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")
            ||extension.equalsIgnoreCase(".jpeg")){

        File folder= new File(path);

        if(!folder.exists()){
            //create folder
            folder.mkdirs();
        }
        //upload
        Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

        return fileNameWithExtension;

        }else{

            throw new BadApiRequestException("File with this"+extension+"not allowed");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath=path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
