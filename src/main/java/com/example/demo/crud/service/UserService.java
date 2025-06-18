package com.example.demo.crud.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.demo.crud.model.UserDetailsModel;
import com.example.demo.crud.model.entity.UserDetails;
import com.example.demo.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${base.upload.path}")
    private String baseUploadDir;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GCSAccess gcsAccess;

    public List<UserDetails> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public UserDetails getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public UserDetails createUserDetails(UserDetailsModel user) {
        logger.info("Creating new user: {}", user.getName());

        UserDetails userDetails = new UserDetails();
        userDetails.setName(user.getName());
        userDetails.setAge(user.getAge());
        userDetails.setAddress(user.getAddress());
        userDetails.setEmail(user.getEmail());

        String imagePath = gcsAccess.GCSSaveFile("image", user.getImage());
        String filePath = gcsAccess.GCSSaveFile("file", user.getFile());

        userDetails.setImageLocation(imagePath);
        userDetails.setResumeLocation(filePath);

        UserDetails savedUser = userRepository.save(userDetails);
        return savedUser;
    }

    public UserDetails updateUserDetails(Long id, UserDetails userDetails) {
        logger.info("Updating user with ID: {}", id);
        UserDetails existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            return userRepository.save(existingUser);
        }
        logger.warn("User not found with ID: {}", id);
        return null;
    }

    public void deleteUserDetails(Long id) {
        logger.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
    }

//    private String saveFile(String type, MultipartFile multipartFile) {
//        try {
//			if (multipartFile == null) {
//                logger.warn("No file uploaded for type: {}", type);
//                return null;
//            }
//
//            File baseDir = new File(baseUploadDir);
//            if (!baseDir.exists() && !baseDir.mkdirs()) {
//                logger.error("Failed to create base upload directory: {}", baseUploadDir);
//                return null;
//            }
//
//            String subDirName = type.equalsIgnoreCase("image") ? "images" : "files";
//            File subDir = new File(baseDir, subDirName);
//            if (!subDir.exists() && !subDir.mkdirs()) {
//                logger.error("Failed to create subdirectory: {}", subDir.getAbsolutePath());
//                return null;
//            }
//
//            String originalFilename = multipartFile.getOriginalFilename();
//            if (originalFilename == null || originalFilename.trim().isEmpty()) {
//                logger.warn("Uploaded file has no name.");
//                return null;
//            }
//
//            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
//            File fileToSave = new File(subDir, uniqueFilename);
//
//            multipartFile.transferTo(fileToSave);
//            logger.info("Saved {} to {}", type, fileToSave.getAbsolutePath());
//
//            return fileToSave.getAbsolutePath();
//
//        } catch (Exception e) {
//            logger.error("Error saving {}: {}", type, e.getMessage(), e);
//            return null;
//        }
//    }
}
