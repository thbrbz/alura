package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.AbrigoException;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureJsonTesters
@WebMvcTest(AbrigoController.class)
class AbrigoControllerTest {

    private final String url = "/abrigos";

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    CadastroAbrigoDto dto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> json;

    @Test
    @DisplayName("Sucesso ao solicitar lista de abrigos")
    public void deveriaRetornarStatus200AoSolicitarListaDeAbrigosSemErro() throws Exception {
        var response = mockMvc.perform(get(url)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Sucesso ao cadastrar abrigo")
    public void deveriaRetornarStatus200AoCadastrarAbrigoSemErro() throws Exception {
        dto = new CadastroAbrigoDto("Teste", "11900001111", "teste@teste.com", null);

        Mockito.doNothing().when(abrigoService).cadastrar(Mockito.any(CadastroAbrigoDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.write(dto).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Erro 400 caso dados informados ao cadastrar novo abrigo já existam")
    public void deveriaRetornarStatus400CasoDadosDoAbrigoJaExistam() throws Exception {
        dto = new CadastroAbrigoDto("Teste", "11900001111", "teste@teste.com", null);

        Mockito.doThrow(new AbrigoException("Dados já cadastrados para outro abrigo!"))
                .when(abrigoService).cadastrar(Mockito.any(CadastroAbrigoDto.class));

        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals("Dados já cadastrados para outro abrigo!",  response.getContentAsString());
    }

    @Test
    @DisplayName("Erro 400 ao tentar cadastrar abrigo sem informar os dados")
    public void deveriaRetornarStatus400AoCadastrarAbrigoSemInformarOsDados() throws Exception {
        var response = mockMvc.perform(
                post("/abrigos")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Sucesso ao solicitar lista de pets do abrigo")
    public void deveriaRetornarStatus200AoSolicitarListaDePetsSemErro() throws Exception {
        String idPet = "1";
        String url = String.format("/abrigos/%s/pets", idPet);

        var response = mockMvc.perform(get(url)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar status 404 se o abrigo não for encontrado")
    public void deveriaRetornarStatus404SeAbrigoNaoExistir() throws Exception {
        String idInvalido = "999";
        String url = String.format("/abrigos/%s/pets", idInvalido);

        Mockito.when(abrigoService.listarPets(idInvalido))
                .thenThrow(new AbrigoException("Abrigo não encontrado!"));

        var response = mockMvc.perform(get(url)).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
        Assertions.assertThrows(AbrigoException.class, () -> abrigoService.listarPets(idInvalido));
    }
}