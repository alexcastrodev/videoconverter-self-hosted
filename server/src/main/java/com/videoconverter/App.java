package com.videoconverter;

import io.javalin.Javalin;
import io.javalin.http.UploadedFile;

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
            }
        });
    }
}
