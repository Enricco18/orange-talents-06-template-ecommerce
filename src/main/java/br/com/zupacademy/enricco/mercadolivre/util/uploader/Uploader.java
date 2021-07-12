package br.com.zupacademy.enricco.mercadolivre.util.uploader;


import br.com.zupacademy.enricco.mercadolivre.controller.request.ProductImageRequest;

import java.util.List;

public interface Uploader {
    public List<String> send(ProductImageRequest productImageRequests);
}
