package main;

import java.util.ArrayList;

public class Repositorio {
	
	private int id;
	private static ArrayList<Pessoa> pessoas;
	
	public Repositorio() {
		pessoas = new ArrayList<>();
		this.id = 1;
	}
	
	public int getId() {
		return this.id;
	}
	
	
	public void adicionarPessoa(Pessoa p) {
		p.setIdPessoa(id);
		pessoas.add(p);
		this.id++;
	}
	
	public void removerPessoa(Pessoa pessoa) {
		pessoas.remove(pessoa);
	}
	
	public ArrayList<Pessoa> getPessoas(){
		return pessoas;
	}
	
	public void atualizarPessoa(Pessoa pessoaAtualizada) {
		for(int i = 0; i < pessoas.size(); i++) {
			if(pessoas.get(i).getIdPessoa() == pessoaAtualizada.getIdPessoa()) {
				pessoas.set(i, pessoaAtualizada);
				break;
			}
		}
	}
}
