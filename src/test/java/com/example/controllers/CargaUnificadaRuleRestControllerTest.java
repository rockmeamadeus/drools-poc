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

import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.equalTo;
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

		servicioRuta.setCodProducto("Entrega de Otros");
		servicioRuta.setCodServicio("29023URY");
		servicioRuta.setIdServicio("73cd92b8-4cda-4084-8d20-bbf2cb064e03");

		Ot ot = new Ot();
		ot.setCodTipoOT("ENT");
		ot.setIdOT("xxxx");
		ot.setCodOT("12345");
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
				.andExpect(jsonPath("$.servicioRutas[0].codProducto", is(equalTo("Entrega de Otros"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots", hasSize(1)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", hasSize(4)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].idOT", is(equalTo("xxxx"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].codOT", is(equalTo("12345"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].entidad", is(equalTo("Santander"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMinimo", is(equalTo(101.12))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMaximo", is(equalTo(751.123))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[0].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[1].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[2].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[3].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(is(equalTo("SAFEBAG"))).or(equalTo("LecturaRemito"))));
	}

	@Test
	void given_servicio_ruta_then_rule2_should_be_skipped() throws Exception {

		MockMultipartFile file
				= new MockMultipartFile(
				"file",
				"Ot-addActividad_multiple_rules_skip_rule2.xlsx",
				MediaType.APPLICATION_FORM_URLENCODED_VALUE,
				Files.readAllBytes(Paths.get("src/test/resources/Ot-addActividad_multiple_rules_skip_rule2.xlsx"))
		);


		mockMvc.perform(multipart("/upload/").file(file))
				.andExpect(status().isOk());

		CargaUnificadaRuleRequest cargaUnificadaRuleRequest = new CargaUnificadaRuleRequest();

		ServicioRuta servicioRuta = new ServicioRuta();

		servicioRuta.setCodProducto("Entrega de Otros");
		servicioRuta.setCodServicio("29023URY");
		servicioRuta.setIdServicio("73cd92b8-4cda-4084-8d20-bbf2cb064e03");

		Ot ot = new Ot();
		ot.setCodTipoOT("ENT");
		ot.setIdOT("a50394de-2e7c-4db6-9b49-0f20397dc156");
		ot.setCodOT("12345");
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
				.andExpect(jsonPath("$.servicioRutas[0].codProducto", is(equalTo("Entrega de Otros"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots", hasSize(1)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", hasSize(4)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].idOT", is(equalTo("a50394de-2e7c-4db6-9b49-0f20397dc156"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].codOT", is(equalTo("12345"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].entidad", is(equalTo("Santander"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMinimo", is(equalTo(101.12))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMaximo", is(equalTo(751.123))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[0].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[1].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[2].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[3].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))));
	}

	@Test
	void given_servicio_ruta_then_rule2_should_be_skipped_due_of_second_value_in_array() throws Exception {

		MockMultipartFile file
				= new MockMultipartFile(
				"file",
				"Ot-addActividad_multiple_rules_skip_rule2.xlsx",
				MediaType.APPLICATION_FORM_URLENCODED_VALUE,
				Files.readAllBytes(Paths.get("src/test/resources/Ot-addActividad_multiple_rules_skip_rule2_2.xlsx"))
		);


		mockMvc.perform(multipart("/upload/").file(file))
				.andExpect(status().isOk());

		CargaUnificadaRuleRequest cargaUnificadaRuleRequest = new CargaUnificadaRuleRequest();

		ServicioRuta servicioRuta = new ServicioRuta();

		servicioRuta.setCodProducto("Entrega de Otros");
		servicioRuta.setCodServicio("29023URY");
		servicioRuta.setIdServicio("73cd92b8-4cda-4084-8d20-bbf2cb064e03");

		Ot ot = new Ot();
		ot.setCodTipoOT("ENT");
		ot.setIdOT("a50394de-2e7c-4db6-9b49-0f20397dc157");
		ot.setCodOT("12345");
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
				.andExpect(jsonPath("$.servicioRutas[0].codProducto", is(equalTo("Entrega de Otros"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots", hasSize(1)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", hasSize(4)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].idOT", is(equalTo("a50394de-2e7c-4db6-9b49-0f20397dc157"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].codOT", is(equalTo("12345"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].entidad", is(equalTo("Santander"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMinimo", is(equalTo(101.12))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMaximo", is(equalTo(751.123))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[0].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[1].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[2].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[3].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))));
	}

	@Test
	void given_servicio_ruta_then_defaults_montos_values_are_set() throws Exception {

		MockMultipartFile file
				= new MockMultipartFile(
				"file",
				"Ot-addActividad_multiple_rules_default_montos_values.xlsx",
				MediaType.APPLICATION_FORM_URLENCODED_VALUE,
				Files.readAllBytes(Paths.get("src/test/resources/Ot-addActividad_multiple_rules_default_montos_values.xlsx"))
		);

		mockMvc.perform(multipart("/upload/").file(file))
				.andExpect(status().isOk());

		CargaUnificadaRuleRequest cargaUnificadaRuleRequest = new CargaUnificadaRuleRequest();

		ServicioRuta servicioRuta = new ServicioRuta();

		servicioRuta.setCodProducto("xxxx");
		servicioRuta.setCodServicio("29023URY");
		servicioRuta.setIdServicio("73cd92b8-4cda-4084-8d20-bbf2cb064e03");

		Ot ot = new Ot();
		ot.setCodTipoOT("ENT");
		ot.setIdOT("a50394de-2e7c-4db6-9b49-0f20397dc157");
		ot.setCodOT("12345");
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
				.andExpect(jsonPath("$.servicioRutas[0].codProducto", is(equalTo("xxxx"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots", hasSize(1)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", is(notNullValue())))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades").isArray())
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades", hasSize(4)))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].idOT", is(equalTo("a50394de-2e7c-4db6-9b49-0f20397dc157"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].codOT", is(equalTo("12345"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].entidad", is(equalTo("Santander"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMinimo", is(equalTo(0.0))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].valorMaximo", is(equalTo(0.0))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[0].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[1].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[2].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))))
				.andExpect(jsonPath("$.servicioRutas[0].ots[0].actividades[3].codActividad",
						either(is(equalTo("Coleta"))).or(is(equalTo("LecturaPrecintos"))).or(equalTo("LecturaRemito")).or(equalTo("SolicitudMotivoSinRemesa"))));
	}
}