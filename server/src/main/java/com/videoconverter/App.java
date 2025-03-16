package com.videoconverter;

import io.javalin.Javalin;
import io.javalin.http.UploadedFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start(8000);

        app.post("/", context -> {
            Map<String, Object> errors = new HashMap<>();
            UploadedFile file = context.uploadedFile("file");

            if (file == null) {
                errors.put("file", "file is required");
                context.status(422).json(errors);
            } else {
                File uploadDir = new File("uploads");
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                File targetFile = new File(uploadDir, file.filename());

                try (InputStream input = file.content()) {
                    OutputStream output = new FileOutputStream(targetFile);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }

                    context.status(201);
                }
            }
        });
    }
}
