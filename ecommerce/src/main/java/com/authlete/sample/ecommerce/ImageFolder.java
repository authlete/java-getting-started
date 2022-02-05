package com.authlete.sample.ecommerce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ImageFolder {
  public List<String> getImagePaths() throws IOException {
    String imageFolder = Thread.currentThread().getContextClassLoader().getResource("/images").getPath();

    return Files.find(Paths.get(imageFolder),
            1,
            (path, basicFileAttributes) -> path.toFile().getName().matches(".*\\.jpeg")
        )
        .map(path -> path.getParent().getFileName().toString() + "/" + path.getFileName().toString())
        .collect(Collectors.toList());
  }
}
