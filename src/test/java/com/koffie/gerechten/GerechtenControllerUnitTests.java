package com.koffie.gerechten;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koffie.gerechten.model.Gerecht;
import com.koffie.gerechten.repository.GerechtRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GerechtenControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GerechtRepository gerechtRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenName_whenGetGerechtByName_returnJsonGerecht() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, false, true, true, 5, "mock.url.be");

        given(gerechtRepository.findAllByNaamContaining("Gerecht1")).willReturn(gerecht0);

        mockMvc.perform(get("/gerechten/naam/{naam}","Gerecht1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam", is("Gerecht1")))
                .andExpect(jsonPath("$.afkomst", is("Belgie")))
                .andExpect(jsonPath("$.kcal", is(100.0)))
                .andExpect(jsonPath("$.glutenvrij", is(false)))
                .andExpect(jsonPath("$.vegan", is(true)))
                .andExpect(jsonPath("$.vegetarisch", is(true)))
                .andExpect(jsonPath("$.aantalPersonen", is(5)))
                .andExpect(jsonPath("$.url", is("mock.url.be")));
    }

    @Test
    public void getGerechtByVegan_returnJsonGerechten() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, false, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(2, "Gerecht3", "Belgie", 80, false, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByIsVeganIsTrue()).willReturn(gerechten);

        mockMvc.perform(get("/gerechten/vegan"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(false)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
    }

    @Test
    public void getGerechtByIsVegetarisch_returnJsonGerechten() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, false, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(2, "Gerecht3", "Belgie", 80, false, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByIsVegetarischIsTrue()).willReturn(gerechten);


        mockMvc.perform(get("/gerechten/vegetarisch"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(false)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
    }

    @Test
    public void getGerechtByIsGlutenVrij_returnJsonGerechten() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, true, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(2, "Gerecht3", "Belgie", 80, true, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByIsGlutenvrijIsTrue()).willReturn(gerechten);

        mockMvc.perform(get("/gerechten/glutenvrij"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(true)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
    }

    @Test
    public void givenKcal_whenGetGerechtByKcal_returnJsonGerechten() throws Exception {

        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, true, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(2, "Gerecht3", "Belgie", 80, true, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByKcalIsLessThanEqual(150)).willReturn(gerechten);

        mockMvc.perform(get("/gerechten/kcal/{kcal}", 150.0))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(true)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
    }

    @Test
    public void givenAantalPersonen_whenGetGerechtByAantalPersonen_returnJsonGerechten() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, true, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(2, "Gerecht3", "Belgie", 80, true, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByAantalPersonenEquals(5)).willReturn(gerechten);

        mockMvc.perform(get("/gerechten/personen/{aantalPersonen}", 5))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(true)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
    }

    @Test
    public void givenLandAfkomst_whenGetGerechtByLandAfkomst_returnJsonGerechten() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, true, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(2, "Gerecht3", "Belgie", 80, true, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByAfkomstContaining("Belgie")).willReturn(gerechten);

        mockMvc.perform(get("/gerechten/afkomst/{afkomst}", "Belgie"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(true)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
    }

    @Test
    public void givenKoffieDrankId_whenGetGerechtByKoffieDrankId_returnJsonGerechten() throws Exception {
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, true, true, true, 5, "mock.url.be");
        Gerecht gerecht1 = new Gerecht(1, "Gerecht3", "Belgie", 80, true, true, true, 5, "mock2.url.be");

        List<Gerecht> gerechten = new ArrayList<>();
        gerechten.add(gerecht0);
        gerechten.add(gerecht1);

        given(gerechtRepository.findAllByKoffieDrankId(1)).willReturn(gerechten);


        mockMvc.perform(get("/gerechten/koffiedrank/{koffieDrankId}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].koffieDrankId", is (1)))
                .andExpect(jsonPath("$[0].naam", is("Gerecht1")))
                .andExpect(jsonPath("$[0].afkomst", is("Belgie")))
                .andExpect(jsonPath("$[1].kcal", is(80.0)))
                .andExpect(jsonPath("$[1].glutenvrij", is(true)))
                .andExpect(jsonPath("$[1].vegan", is(true)))
                .andExpect(jsonPath("$[1].vegetarisch", is(true)))
                .andExpect(jsonPath("$[0].aantalPersonen", is(5)))
                .andExpect(jsonPath("$[1].url", is("mock2.url.be")));
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
        Gerecht gerecht = new Gerecht(1, "Gerecht1", "Belgie", 130, false, false, true, 4, "mock.url.be");
        given(gerechtRepository.findByUrlIs("mock.url.be")).willReturn(gerecht);

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
        Gerecht gerecht0 = new Gerecht(1, "Gerecht1", "Belgie", 100, true, true, true, 5, "mock.url.be");

        given(gerechtRepository.findByUrlIs("mock.url.be")).willReturn(gerecht0);
        mockMvc.perform(delete("/gerechten/url/{url}", "mock.url.be")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoGerecht_whenDeleteGerecht_thenReturnStatusNotFound() throws Exception {
        given(gerechtRepository.findByUrlIs("mock123.url.be")).willReturn(null);

        mockMvc.perform(delete("/gerechten/url/{url}", "mock123.url.be")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}