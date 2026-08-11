package dev.thbrbz.screenmatch.service;

public interface Conversor {
    <T> T obterDados(String json, Class<T> classe);

    <T> Object obterLista(String json, Class<T> classe);
}
