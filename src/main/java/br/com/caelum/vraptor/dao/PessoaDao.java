package br.com.caelum.vraptor.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.model.Pessoa;


public class PessoaDao {

    private final EntityManager em;
    @Inject
    public PessoaDao(EntityManager em) {
        this.em = em;
    }
    public PessoaDao(){
        this(null);
    }

    public void adiciona(Pessoa pessoa) {
        em.getTransaction().begin();
        em.persist(pessoa);
        em.getTransaction().commit();
    }

    public void altera(Pessoa pessoa) {
//        em.getTransaction().begin();
        Pessoa novo = em.find(Pessoa.class, pessoa.getId());
        em.merge(novo);
        novo.setNome(pessoa.getNome());
        novo.setNascimento(pessoa.getNascimento());
        novo.setCpf(pessoa.getCpf());
//        em.getTransaction().commit();
    }
    
    public void remove(Pessoa pessoa) {
        em.remove(busca(pessoa));
    }

    public Pessoa busca(Pessoa pessoa) {
        return em.find(Pessoa.class, pessoa.getId());
    }

    @SuppressWarnings("unchecked")
    public List<Pessoa> lista() {
//        em.getTransaction().begin();
        return em.createQuery("select p from Pessoa p").getResultList();
    }
}