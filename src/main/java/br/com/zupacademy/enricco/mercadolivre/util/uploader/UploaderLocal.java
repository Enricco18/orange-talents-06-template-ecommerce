package br.com.zupacademy.enricco.mercadolivre.util.uploader;

import br.com.zupacademy.enricco.mercadolivre.controller.request.ProductImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@Primary
public class UploaderLocal implements Uploader{
//    @Autowired
//    private ServerProperties serverProperties;
//
//    private String baseURL = "localhost:"+serverProperties.getPort();

    private String baseURL = "http://www.localhost:8080";
    private String imageURL = "./src/main/resources/static/images/";


    @Override
    public Set<String> send(ProductImageRequest productImageRequest) {
        String imagesPath = baseURL + "/images/";
        Set<String> urlList = new HashSet<>();

        productImageRequest.getImages().stream().forEach(image->{
            try {

                String newName =  UUID.randomUUID()+ "-" + image.getOriginalFilename();
                Path path = Paths.get(imageURL + newName);
                Files.write(path,image.getBytes());

                urlList.add(imagesPath+newName);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });

        return urlList;
    }
}
