package com.caiocampos.cursomc.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.caiocampos.cursomc.domain.Cidade;
import com.caiocampos.cursomc.domain.Cliente;
import com.caiocampos.cursomc.domain.Endereco;
import com.caiocampos.cursomc.domain.enums.TipoCliente;
import com.caiocampos.cursomc.dto.ClienteDTO;
import com.caiocampos.cursomc.dto.ClienteNewDTO;
import com.caiocampos.cursomc.repositories.CidadeRepository;
import com.caiocampos.cursomc.repositories.ClienteRepository;
import com.caiocampos.cursomc.repositories.EnderecoRepository;
import com.caiocampos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
	   find(id);
	   try {
		   repo.delete(id);
	   }catch(DataIntegrityViolationException e){
		   throw new DataIntegrityViolationException("Não é possível excluir porque há entidades relacionadas");
	   }
	}
	
	public List<Cliente>findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente>findPage(Integer page,Integer linesPerPage, String orderBY, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBY);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
	 return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		 Cliente  cli = new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		 Cidade cid = cidadeRepository.findOne(objDto.getCidadeId());
		 Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(),objDto.getComplemento(), objDto.getBairro(),objDto.getCep(), cli, cid);
		 
		 cli.getEnderecos().add(end);
		 cli.getTelefones().add(objDto.getTelefone1());
		 if(objDto.getTelefone2() != null) {
			 cli.getTelefones().add(objDto.getTelefone2());
		 }
		 if(objDto.getTelefone3()!= null) {
			 cli.getTelefones().add(objDto.getTelefone3());
		 }
		 return cli;
		}

	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	

}
