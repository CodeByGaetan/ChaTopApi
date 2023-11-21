package com.chatop.api.controller;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.service.ImageService;

@Hidden
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    // @GetMapping(value = "/images/{fileName}", produces =
    // MediaType.IMAGE_PNG_VALUE)
    // public @ResponseBody InputStream getImage(@PathVariable("fileName") final
    // String fileName) throws IOException {
    // Resource img = imageService.load(fileName);
    // return img.getInputStream();
    // }

    // @GetMapping(value = "/image")
    // public void getImage(HttpServletResponse response) throws IOException {
    // ClassPathResource imgFile = new ClassPathResource("images/test.png");
    // StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    // }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("fileName") final String fileName)
            throws IOException {
        Resource img = imageService.load(fileName);
        MediaType contentType = fileName.endsWith(".png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
        return ResponseEntity
                .ok()
                .contentType(contentType)
                .body(new InputStreamResource(img.getInputStream()));
    }

}
