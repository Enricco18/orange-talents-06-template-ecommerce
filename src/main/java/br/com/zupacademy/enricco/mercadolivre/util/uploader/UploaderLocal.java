package br.com.zupacademy.enricco.mercadolivre.util.uploader;

import br.com.zupacademy.enricco.mercadolivre.controller.request.ProductImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
@Primary
public class UploaderLocal implements Uploader{
    @Autowired
    ServletContext context;

    @Override
    public List<String> send(ProductImageRequest productImageRequest) {
        productImageRequest.getImages().stream().forEach(image->{
            try {
                File url = ResourceUtils.getFile("static");
                Path path = Paths.get("./src/main/resources/static/images/" + UUID.randomUUID()+ "-" + image.getOriginalFilename());
                Files.write(path,image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return null;
    }
}
