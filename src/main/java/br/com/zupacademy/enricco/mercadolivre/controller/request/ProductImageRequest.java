package br.com.zupacademy.enricco.mercadolivre.controller.request;

import br.com.zupacademy.enricco.mercadolivre.model.Image;
import br.com.zupacademy.enricco.mercadolivre.model.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductImageRequest {
    @NotNull
    @Size(min=1)
    private List<MultipartFile> images;

//    public List<Image> toModel(Product product){
//        this.images.stream().forEach(image-> new Image());
//    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ProductImageRequest{" +
                "images=" + images +
                '}';
    }
}
