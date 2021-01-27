package com.example.controllers;

import com.example.cargaUnificada.resource.request.Actividad;
import com.example.cargaUnificada.resource.request.CargaUnificadaRuleRequest;
import com.example.cargaUnificada.resource.request.Ot;
import com.example.cargaUnificada.resource.request.ServicioRuta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
	void evaluateSingleRule() throws Exception {

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


	@Test
	void evaluateMultipleRules() throws Exception {

		MockMultipartFile file
				= new MockMultipartFile(
				"file",
				"Ot-addActividad_multiple_rules.xlsx",
				MediaType.APPLICATION_FORM_URLENCODED_VALUE,
				Files.readAllBytes(Paths.get("src/test/resources/Ot-addActividad_multiple_rules.xlsx"))
		);


		mockMvc.perform(multipart("/upload/").file(file))
				.andExpect(status().isOk());

		CargaUnificadaRuleRequest cargaUnificadaRuleRequest = new CargaUnificadaRuleRequest();

		ServicioRuta servicioRuta = new ServicioRuta();

		servicioRuta.setCodProducto("1");
		servicioRuta.setCodServicio("29023URY");
		servicioRuta.setIdServicio("73cd92b8-4cda-4084-8d20-bbf2cb064e03");

		Ot ot = new Ot();
		ot.setCodTipoOT("REC");
		ot.setIdOT("a50394de-2e7c-4db6-9b49-0f20397dc156");
		ot.setCodOT("4645123131354");		ot.setProducto("Entrega Divisa Extranjera");
		ot.setEntidad("Santander");

		Actividad actividad1 = new Actividad();
		actividad1.setCodActividad("SolicitudMotivoSinRemesa");
		Actividad actividad2 = new Actividad();
		actividad2.setCodActividad("Coleta");
		Actividad actividad3 = new Actividad();
		actividad3.setCodActividad("LecturaPrecintos");
		Actividad actividad4 = new Actividad();
		actividad4.setCodActividad("LecturaRemito");

		ot.getActividades().add(actividad1);
		ot.getActividades().add(actividad2);
		ot.getActividades().add(actividad3);
		ot.getActividades().add(actividad4);

		servicioRuta.getOts().add(ot);
		cargaUnificadaRuleRequest.getServicioRutas().add(servicioRuta);

		mockMvc.perform(post("/cargaunificada/ruta/")
				.content(new ObjectMapper().writeValueAsString(cargaUnificadaRuleRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}