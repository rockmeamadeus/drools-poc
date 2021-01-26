package com.example.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UploadRuleRestController.class, CargaUnificadaRuleRestController.class})
class CargaUnificadaRuleRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void evaluate() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "Ot-addActividad.xlsx",
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                Files.readAllBytes(Paths.get("src/test/resources/Ot-addActividad.xlsx"))
        );


        mockMvc.perform(multipart("/upload/").file(file))
                .andExpect(status().isOk());

        mockMvc.perform(post("/cargaunificada/ruta/")
                .content("{\n" +
                        "    \"servicioRutas\": [\n" +
                        "        {\n" +
                        "            \"codProducto\": \"1\",\n" +
                        "            \"codServicio\": \"29023URY\",\n" +
                        "            \"idServicio\": \"73cd92b8-4cda-4084-8d20-bbf2cb064e03\",\n" +
                        "            \"ots\": [\n" +
                        "                {\n" +
                        "                    \"actividades\": [\n" +
                        "                        {\n" +
                        "                            \"codActividad\": \"LecturaRemito\"\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"codActividad\": \"SolicitudMotivoSinRemesa\"\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"codActividad\": \"Coleta\"\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"codActividad\": \"LecturaPrecintos\"\n" +
                        "                        }\n" +
                        "                    ],\n" +
                        "                    \"codTipoOT\": \"REC\",\n" +
                        "                    \"idOT\": \"a50394de-2e7c-4db6-9b49-0f20397dc156\",\n" +
                        "                    \"codOT\": \"4645123131354\"\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}