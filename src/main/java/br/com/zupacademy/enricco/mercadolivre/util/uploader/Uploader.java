package br.com.zupacademy.enricco.mercadolivre.util.uploader;


import br.com.zupacademy.enricco.mercadolivre.controller.request.ProductImageRequest;

import java.util.Set;

public interface Uploader {
    public Set<String> send(ProductImageRequest productImageRequests);
}
