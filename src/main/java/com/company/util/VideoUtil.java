package com.company.util;
//User :Hoshimxonov_Otabek
//Date :12.07.2022
//Time :23:10
//Project Name :YouTobe_Security

import lombok.Data;
import org.mp4parser.IsoFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Data
@Component
public class VideoUtil {

    private File convert(MultipartFile file) {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long getDuration(MultipartFile file) throws IOException {


        final IsoFile isoFile = new IsoFile(convert(file));

        long l = isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                isoFile.getMovieBox().getMovieHeaderBox().getTimescale();


//TODO add dependency to pom.xml
/*        <dependency>
            <groupId>org.mp4parser</groupId>
            <artifactId>isoparser</artifactId>
            <version>1.9.41</version>
         </dependency>
        */


        return l;
    }


}
