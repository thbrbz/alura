package com.thbrbz.screensound.main;

import com.thbrbz.screensound.model.Artista;
import com.thbrbz.screensound.model.Musica;
import com.thbrbz.screensound.model.enums.TipoArtista;
import com.thbrbz.screensound.repository.ArtistaRepository;
import com.thbrbz.screensound.service.ConsultaIA;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final ArtistaRepository artistaRepository;
    private final Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 9) {
            var menu = """

                    *** Screen Sound Músicas ***
                    
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }

    private void pesquisarDadosDoArtista() {
        System.out.println("\nPesquisar dados sobre qual artista?");
        var nome = leitura.nextLine();

        var resposta = ConsultaIA.obterInformacao(nome);
        System.out.println(resposta.trim());
    }

    private void buscarMusicasPorArtista() {
        System.out.println("\nBuscar músicas de que artista?");
        var nome = leitura.nextLine();

        List<Musica> musicas = artistaRepository.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = artistaRepository.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarMusicas() {
        System.out.println("\nCadastrar música de que artista? ");
        var nome = leitura.nextLine();

        Optional<Artista> artista = artistaRepository.findByNomeContainingIgnoreCase(nome);

        if (artista.isEmpty()) {
            System.out.println("Artista não encontrado");
            return;
        }

        System.out.println("\nInforme o título da música: ");
        var nomeMusica = leitura.nextLine();

        Musica musica = new Musica(nomeMusica, artista.get());
        artista.get().getMusicas().add(musica);

        artistaRepository.save(artista.get());
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("\nInforme o nome desse artista: ");
            var nome = leitura.nextLine();

            System.out.println("\nInforme o tipo desse artista: (solo, dupla ou banda)");
            var tipo = leitura.nextLine();

            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);

            artistaRepository.save(artista);

            System.out.println("\nCadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }
}
