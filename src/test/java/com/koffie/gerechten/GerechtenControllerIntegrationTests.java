package com.koffie.gerechten;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koffie.gerechten.model.Gerecht;
import com.koffie.gerechten.repository.GerechtRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GerechtenControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GerechtRepository gerechtRepository;

    private Gerecht gerecht1 = new Gerecht(1, "Gerecht1", "Nederland", 200, true, false, true, 4, "mock1.url.be");
    private Gerecht gerecht2 = new Gerecht(1, "Gerecht30", "Belgie", 30, false, false, false, 1, "mock23.url.be");
    private Gerecht gerecht3 = new Gerecht(2, "Gerecht3", "Duitsland", 130, false, false, true, 4, "mock50.url.be");
    private Gerecht gerecht4 = new Gerecht(40,"Gerecht87", "Frankrijk", 210, false, false, true, 2, "mock12.url.be");
    private Gerecht gerecht5 = new Gerecht(32,"Gerecht100", "Nederland", 350, true, true, true, 3, "mock.url.be");

    @BeforeEach
    public void beforeAllTests(){
        gerechtRepository.deleteAll();
        gerechtRepository.save(gerecht1);
        gerechtRepository.save(gerecht2);
        gerechtRepository.save(gerecht3);
        gerechtRepository.save(gerecht4);
        gerechtRepository.save(gerecht5);
    }

    @AfterEach
    public void afterAllTests() {
        gerechtRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllGerechten_returnJsonGerechten() throws Exception{
        mockMvc.perform((get("/gerechten")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].kcal", is(200.0)))
                .andExpect(jsonPath("$[3].vegan", is(false)))
                .andExpect(jsonPath("$[2].afkomst", is("Duitsland")));
    }

    @Test
    public void givenName_whenGetGerechtByName_returnJsonGerecht() throws Exception {

        mockMvc.perform(get("/gerechten/naam/{naam}","Gerecht30"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam", is("Gerecht30")))
                .andExpect(jsonPath("$.afkomst", is("Belgie")))
                .andExpect(jsonPath("$.kcal", is(30.0)))
                .andExpect(jsonPath("$.glutenvrij", is(false)))
                .andExpect(jsonPath("$.vegan", is(false)))
                .andExpect(jsonPath("$.vegetarisch", is(false)))
                .andExpect(jsonPath("$.aantalPersonen", is(1)))
                .andExpect(jsonPath("$.url", is("mock23.url.be")));
    }

    @Test
    public void getGerechtByVegan_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/vegan"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (32)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht100")))
                .andExpect(jsonPath("$[0].afkomst", is("Nederland")))
                .andExpect(jsonPath("$[0].kcal", is(350.0)))
                .andExpect(jsonPath("$[0].glutenvrij", is(true)))
                .andExpect(jsonPath("$[0].vegan", is(true)))
                .andExpect(jsonPath("$[0].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(3)))
                .andExpect(jsonPath("$[0].url", is("mock.url.be")));
    }

    @Test
    public void getGerechtByIsVegetarisch_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/vegetarisch"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[2].naam", is("Gerecht87")))
                .andExpect(jsonPath("$[3].afkomst", is("Nederland")))
                .andExpect(jsonPath("$[0].kcal", is(200.0)))
                .andExpect(jsonPath("$[0].vegetarisch", is(true)))
                .andExpect(jsonPath("$[2].vegetarisch", is(true)));
    }

    @Test
    public void getGerechtByIsGlutenVrij_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/glutenvrij"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[1].naam", is("Gerecht100")))
                .andExpect(jsonPath("$[1].afkomst", is("Nederland")))
                .andExpect(jsonPath("$[0].kcal", is(200.0)))
                .andExpect(jsonPath("$[0].glutenvrij", is(true)))
                .andExpect(jsonPath("$[1].glutenvrij", is(true)));
    }

    @Test
    public void givenKcal_whenGetGerechtByKcal_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/kcal/{kcal}", 150.0))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[1].naam", is("Gerecht3")))
                .andExpect(jsonPath("$[1].afkomst", is("Duitsland")))
                .andExpect(jsonPath("$[0].kcal", lessThan(150.0)))
                .andExpect(jsonPath("$[1].kcal", lessThan(150.0)));
    }

    @Test
    public void givenAantalPersonen_whenGetGerechtByAantalPersonen_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/personen/{aantalPersonen}", 4))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[1].naam", is("Gerecht3")))
                .andExpect(jsonPath("$[1].afkomst", is("Duitsland")))
                .andExpect(jsonPath("$[0].aantalPersonen", is(4)))
                .andExpect(jsonPath("$[1].aantalPersonen", is(4)));
    }

    @Test
    public void givenLandAfkomst_whenGetGerechtByLandAfkomst_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/afkomst/{afkomst}", "Nederland"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[1].naam", is("Gerecht100")))
                .andExpect(jsonPath("$[0].afkomst", is("Nederland")))
                .andExpect(jsonPath("$[1].afkomst", is("Nederland")));
    }

    @Test
    public void givenKoffieDrankId_whenGetGerechtByKoffieDrankId_returnJsonGerechten() throws Exception {

        mockMvc.perform(get("/gerechten/koffiedrank/{koffieDrankId}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Nederland")))
                .andExpect(jsonPath("$[1].kcal", is(30.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(false)))
                .andExpect(jsonPath("$[1].vegan", is(false)))
                .andExpect(jsonPath("$[1].vegetarisch", is(false)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(4)))
                .andExpect(jsonPath("$[0].url", is("mock1.url.be")));
    }

    @Test
    public void whenPostGerecht_returnJsonGerecht() throws Exception{
        Gerecht newGerecht = new Gerecht(1,"TestPost", "Belgie", 100, true, false, false, 8, "mock.url.be");
        mockMvc.perform(post("/gerechten/")
                .content(mapper.writeValueAsString(newGerecht))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam", is("TestPost")))
                .andExpect(jsonPath("$.afkomst", is("Belgie")))
                .andExpect(jsonPath("$.kcal", is(100.0)))
                .andExpect(jsonPath("$.glutenvrij", is(true)))
                .andExpect(jsonPath("$.vegan", is(false)))
                .andExpect(jsonPath("$.vegetarisch", is(false)))
                .andExpect(jsonPath("$.aantalPersonen", is(8)))
                .andExpect(jsonPath("$.url", is("mock.url.be")));
    }

    @Test
    public void givenGerecht_whenPutGerecht_thenReturnJsonGerecht() throws Exception {

        Gerecht updatedGerecht = new Gerecht(5, "Gerecht31", "Nederland", 130, false, false, true, 4, "mock.url.be");

        mockMvc.perform(put("/gerechten/url/{url}", "mock.url.be")
                .content(mapper.writeValueAsString(updatedGerecht))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam", is("Gerecht31")))
                .andExpect(jsonPath("$.afkomst", is("Nederland")))
                .andExpect(jsonPath("$.kcal", is(130.0)))
                .andExpect(jsonPath("$.glutenvrij", is(false)))
                .andExpect(jsonPath("$.vegan", is(false)))
                .andExpect(jsonPath("$.vegetarisch", is(true)))
                .andExpect(jsonPath("$.aantalPersonen", is(4)))
                .andExpect(jsonPath("$.url", is("mock.url.be")));
    }

    @Test
    public void givenGerecht_whenDeleteGerecht_thenReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/gerechten/url/{url}", "mock.url.be")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoGerecht_whenDeleteGerecht_thenReturnStatusNotFound() throws Exception {
        mockMvc.perform(delete("/gerechten/url/{url}", "noMock.url.be")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
