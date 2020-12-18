package br.com.caelum.vraptor.controller;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.dao.PessoaDao;
import br.com.caelum.vraptor.model.Pessoa;
import br.com.caelum.vraptor.util.JPAUtil;
import br.com.caelum.vraptor.validator.Validator;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@br.com.caelum.vraptor.Controller
public class AgendeadController {

    private Validator validator;
    private EntityManager em;

    private PessoaDao pessoaDao;
    private Result result;
    @Inject
    public AgendeadController(Result result, EntityManager em, PessoaDao pessoadao, Validator validator) {
        this.result = result;
        this.em = JPAUtil.criaEntityManager();
        this.pessoaDao = pessoadao;
        this.validator = validator;
    }
    public AgendeadController() {

    }

    @Path("/")
    public void inicio(){
        List<Pessoa> listaPessoas = pessoaDao.lista();
        result.include("pessoasList", listaPessoas);
    }

    @Path("/cadastrarPessoa")
    public void cadastrarPessoa(){
    }

    @Post("/editarPessoa")
    public void editarPessoa(Pessoa pessoa){
        result.include("pessoa", em.find(Pessoa.class, pessoa.getId()));
    }
    @Get("/deletaPessoa/{id}")
    public void removePessoa(String id){
        Pessoa pessoa = em.find(Pessoa.class, Long.parseLong(id));
        em.getTransaction().begin();
        em.remove(pessoa);
        em.getTransaction().commit();
        result.redirectTo("/");
    }

    @Path("/reuniao")
    public void reuniao(){

    }

    @Post("/sucessoPessoa")
    public void sucessoPessoa(@Valid Pessoa pessoa){
        validator.onErrorForwardTo(this).cadastrarPessoa();
        if(pessoa.getId() == null){
            pessoaDao.adiciona(pessoa);
        }else{
            pessoaDao.altera(pessoa);
        }
    }

}
