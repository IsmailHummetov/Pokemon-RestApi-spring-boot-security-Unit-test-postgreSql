package com.example.Controller;

import com.example.controllers.PokemonController;
import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;
import com.example.dto.ReviewDto;
import com.example.models.Pokemon;
import com.example.models.Review;
import com.example.service.inter.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PokemonService pokemonService;
    @Autowired
    private ObjectMapper objectMapper;

    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonDto = PokemonDto.builder().name("Pikachu").type("Electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("title").content("content").stars(5).build();
    }

    @Test
    @DisplayName("Create Pokemon")
    public void PokemonController_CreatePokemon_ReturnCreated() throws Exception {
        given(pokemonService.createPokemon(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    @DisplayName("Get All Pokemon")
    public void PokemonController_GetAllPokemons_ReturnAllPokemons() throws Exception {
        PokemonResponse responseDto = PokemonResponse.builder().last(true).pageNo(0).pageSize(5).content(Arrays.asList(pokemonDto)).build();

        when(pokemonService.getAllPokemons(0, 5)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "0")
                .param("pageSize", "5"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    @DisplayName("Get Pokemon By Id")
    public void PokemonController_PokemonDetails_ReturnPokemon() throws Exception{
        int pokemonId=1;
        when(pokemonService.getPokemonById(pokemonId)).thenReturn(pokemonDto);

        ResultActions response = mockMvc.perform(get("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id","1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    @DisplayName("Update Pokemon")
    public void PokemonController_UpdatePokemon_ReturnPokemon() throws Exception{
        int pokemonId=1;
        when(pokemonService.updatePokemonById(pokemonDto,pokemonId)).thenReturn(pokemonDto);

        ResultActions response = mockMvc.perform(put("/api/pokemon/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto))
                .param("id","1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    @DisplayName("Delete Pokemon")
    public void PokemonController_DeletePokemon_ReturnVoid() throws Exception{
        int pokemonId=1;
        doNothing().when(pokemonService).deletePokemonById(pokemonId);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id","1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


}
