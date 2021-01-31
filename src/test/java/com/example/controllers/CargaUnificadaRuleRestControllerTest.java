package com.example.controllers;

import com.example.cargaUnificada.resource.request.Actividad;
import com.example.cargaUnificada.resource.request.CargaUnificadaRuleRequest;
import com.example.cargaUnificada.resource.request.Ot;
import com.example.cargaUnificada.resource.request.ServicioRuta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
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
import java.util.Arrays;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UploadRuleRestController.class, CargaUnificadaRuleRestController.class})
class CargaUnificadaRuleRestControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@Test
	void given_servicio_ruta_ENT_then_actividades_are_modified_and_valores_maximos_y_minimos_are_set() throws Exception {

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
		ot.setCodTipoOT("ENT");
		ot.setIdOT("1234");
		ot.setCodOT("12345");
		ot.setProducto("Entrega Divisa Extranjera");
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
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.servicioRutas", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas").isArray())
				.andExpect(jsonPath("$.servicioRutas", hasSize(1)))
				.andExpect(jsonPath("$.servicioRutas[0].idServicio", is(equalTo("73cd92b8-4cda-4084-8d20-bbf2cb064e03"))))
				.andExpect(jsonPath("$.servicioRutas[0].codServicio", is(equalTo("29023URY"))))
				.andExpect(jsonPath("$.servicioRutas[0].codProducto", is(equalTo("1"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots", hasSize(1)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", hasSize(4)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].idOT", is(equalTo("1234"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].codOT", is(equalTo("12345"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].producto", is(equalTo("Entrega Divisa Extranjera"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].entidad", is(equalTo("Santander"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[0].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[1].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[2].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[3].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))));
	}
}