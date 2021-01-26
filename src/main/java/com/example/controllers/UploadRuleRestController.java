package com.example.controllers;

import com.example.config.DroolsBeanFactory;
import com.example.ruleEngine.drools.resource.service.DroolsRuleService;
import com.example.utils.DrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("upload")
public class UploadRuleRestController {
   // private static final String UPLOAD_PATH = "/src/main/resources/static/upload/";

    @Autowired
    private DroolsRuleService droolsRuleService;

    @PostMapping("/")
    public void addRule(@RequestParam("file") MultipartFile file) {
        try {
            droolsRuleService.add(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  /*  private static String guardarArchivo(MultipartFile file) {
        String res;
        if (file.isEmpty()) {
            res = "Archivo no encontrado";
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(new File(".").getCanonicalPath() + UPLOAD_PATH + file.getOriginalFilename());
                Files.write(path, bytes);
                String drl = getDrl("static/upload/" + file.getOriginalFilename());
                DrlManager.insertDrl("rule1", drl);
                res = "Archivo subido correctamente" + "\n " + drl;

            } catch (IOException e) {
                res = "Error al subir archivo: " + e.getMessage();
            }

        }
        return res;
    }*/

   /* private static String getDrl(String filePath) {
        DroolsBeanFactory dbf = new DroolsBeanFactory();
        return dbf.getDrlFromExcel(filePath);

    }*/
}
