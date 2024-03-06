package com.raghav.trademap.common.utils;

import com.raghav.trademap.modules.tradeDetails.dto.TradeDetailsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
public class FileUtils {
    public static List<String> copyFileToSystem(TradeDetailsRequest request, MultipartFile[] images, String setupName, String date) {
        List<String> paths = new ArrayList<>();

        for(int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];

            String fileType = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = "Trade-[" + setupName + "]-[" + request.getDateTime().toString().replace(":", "-").replace("T", " ") + "]-[" + (i+1) + "]." + fileType;

            Path path = Paths.get("User Data", date, "Trade Chart");

            if(!Files.exists(path)){
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try(InputStream inputStream = image.getInputStream()){
                Path filePath = path.resolve(fileName);
                Files.copy( inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                paths.add(filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return paths;
    }

    public static String copyFileFromMultipartFile(MultipartFile file){
        Path path = Paths.get("User Data", "Insight Image Contents");

        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String fileName;
        if(file.getOriginalFilename() == null)
            fileName = "Insight Image - " + String.valueOf(new Random().nextLong());
        else
            fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename().replace("&", "-");

        try(InputStream inputStream = file.getInputStream()){
            Path filePath = path.resolve(fileName);
            log.info("Copying uploaded insight content file to : " + filePath.toString());
            Files.copy( inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
