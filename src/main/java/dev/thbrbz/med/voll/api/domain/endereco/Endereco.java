package dev.thbrbz.med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco endereco) {
        this.cep = endereco.cep();
        this.logradouro = endereco.logradouro();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.bairro = endereco.bairro();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
    }

    public void atualizarInformacoes(DadosEndereco endereco) {
        if  (endereco.cep() != null)
            this.cep = endereco.cep();

        if (endereco.logradouro() != null)
            this.logradouro = endereco.logradouro();

        if (endereco.numero() != null)
            this.numero = endereco.numero();

        if (endereco.complemento() != null)
            this.complemento = endereco.complemento();

        if (endereco.bairro() != null)
            this.bairro = endereco.bairro();

        if (endereco.cidade() != null)
            this.cidade = endereco.cidade();

        if (endereco.uf() != null)
            this.uf = endereco.uf();
    }
}
