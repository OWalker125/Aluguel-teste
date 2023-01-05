package com.aluguel.nossa_bike.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Passaport;
import com.aluguel.nossa_bike.models.Ciclista.Nacionalidade;
import com.aluguel.nossa_bike.models.Ciclista.Status;
import com.aluguel.nossa_bike.repository.CiclistaRepository;
import com.aluguel.nossa_bike.services.CiclistaService;

public class NossaBikeControllerTest {

    @InjectMocks
    NossaBikeController controller;
    
    @Mock
    CiclistaRepository dbCiclista;
    @Mock
    CiclistaService cicService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenNoErrorsThenZeroErrorsAndCreated(){
        List<String> listaVazia = new LinkedList<String>();
        when(cicService.cadastrar(any())).thenReturn(listaVazia);
        when(cicService.editAccount(any(),any())).thenReturn(listaVazia);
        ResponseEntity esperado = new ResponseEntity<>(listaVazia, HttpStatus.CREATED);
        assertEquals(esperado, controller.cadastrar(any()));
        assertEquals(esperado, controller.accountEdit(any(), any()));
    }

    @Test
    public void whenErrorThenErrorsAndBadRequest(){
        List<String> erro = new LinkedList<String>();
        erro.add("Nome Inválido");
        when(cicService.cadastrar(any())).thenReturn(erro);
        when(cicService.editAccount(any(),any())).thenReturn(erro);
        ResponseEntity esperado = new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
        assertEquals(esperado, controller.cadastrar(any()));
        assertEquals(esperado, controller.accountEdit(any(), any()));
    }

    @Test
    public void whenValidThenZeroErrorsAndOk(){
        Passaport passaport = new Passaport(1, 1, "01/12/2001", "Brasil");
        Ciclista ciclista = new Ciclista(UUID.randomUUID(), "Thiago", Status.INATIVO, "23/02/2000", "473.296.280-77", passaport,
        Nacionalidade.BRASILEIRO, "teste@teste.com", "https://teste.net");
        when(cicService.activateAccount(null)).thenReturn(ciclista);

        ResponseEntity esperado = new ResponseEntity<>(ciclista, HttpStatus.OK);
        assertEquals(esperado, controller.accountActivation(any())); 
    }

    @Test
    public void whenNullThenErrorAndNotFound(){
        when(cicService.activateAccount(null)).thenReturn(null);

        ResponseEntity esperado = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        assertEquals(esperado, controller.accountActivation(any())); 
    }

}