package com.company.service;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j

public class AttachService {
    @Value("${attach.folder}")
    private String attachFolder;

    @Value("${spring.server.url}")
    private String serverUrl;
    @Autowired
    private  AttachRepository attachRepository;

    @Autowired
    @Lazy
    private ProfileService profileService;

    @Lazy
    @Autowired
    private VideoService videoService;

    @Autowired
    @Lazy
    private ChannelService channelService;



    public AttachDTO create(MultipartFile file) {
        try {
            String pathFolder = getYmDString();
            String uuid = UUID.randomUUID().toString();
            String extension = getExtension(file.getOriginalFilename());
            String fileName = uuid + "." + extension;
            File folder = new File(attachFolder+"/"+pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + "/"+pathFolder + "/" + fileName);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setPath(pathFolder);
            entity.setId(uuid);
            entity.setExtension(extension);
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            attachRepository.save(entity);

            AttachDTO dto = new AttachDTO();
            dto.setUrl(serverUrl+"/api/v1/attach/"+entity.getId());

            return dto;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getById(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);

        if (optional.isEmpty()) {
            log.error("Attach not found!");
            throw new ItemNotFoundException(
                    "Attach not found"
            );
        }

        AttachEntity entity = optional.get();

        byte[] data;
        try {
            String path = attachFolder+"/"+entity.getPath()+"/"+entity.getId()+"."+entity.getExtension();
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];

    }

    public ResponseEntity<Resource> download(String id) {
        try {
            AttachEntity entity = get(id);
            String path = getFileFullPath(entity);
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String delete(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);

        if (optional.isEmpty()) {
            log.error("File not found!");
            throw new ItemNotFoundException(
                    "File not found"
            );
        }

        if (profileService.existsPhoto(id)
            || videoService.existsPhoto(id)
            || channelService.existsPhoto(id)) {
            log.error("Can't delete an attach cuz it's been used");
            throw new BadRequestException(
                    "Can't delete an attach cuz it's been used"
            );
        }

        AttachEntity entity = optional.get();

        Path imagesPath = Paths.get(
                attachFolder+"/"+entity.getPath()+
                    "/"+entity.getId()+"."+entity.getExtension());

        try {
            Files.delete(imagesPath);

            attachRepository.deleteById(entity.getId());

            return "File "
                    + imagesPath.toAbsolutePath().toString()
                    + " successfully removed";
        } catch (IOException e) {
            return "Unable to delete "
                    + imagesPath.toAbsolutePath().toString()
                    + " due to...";
        }
    }

    public List<AttachDTO> pagination(int size, int page) {
        Sort sort = Sort.by(Sort.Direction.ASC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<AttachEntity> all = attachRepository.findAll(pageable);

        if (all == null) {
            log.error("No attaches yet!");
            throw new BadRequestException(
                    "No attaches yet!"
            );
        }

        List<AttachDTO> dtoList = new LinkedList<>();

        all.forEach(attach -> {
            AttachDTO dto = toDTO(attach);

            dtoList.add(dto);
        });

        return dtoList;
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public boolean exists(String id) {
        return attachRepository.existsById(id);
    }
    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            log.error("Image not found!");
            throw new ItemNotFoundException("Image not found");
        });
    }

    private String getFileFullPath(AttachEntity entity) {
        return attachFolder + "/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension();
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setUrl(serverUrl+"/api/v1/attach/"+entity.getId());
        dto.setId(entity.getId());
        dto.setOriginalName(entity.getOriginalName());
        dto.setSize(entity.getSize());
        dto.setExtension(entity.getExtension());

        return dto;
    }
}
