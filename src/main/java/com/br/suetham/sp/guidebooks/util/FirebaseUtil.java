package com.br.suetham.sp.guidebooks.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;



public class FirebaseUtil {
	//variavel para guarda as credenciais de acesso
	private Credentials credentials;
	//variavel para acessar e manipular o storage
	private Storage storage;
	//Constante para o nome do bucket
	private final String BUCKET_NAME = "guidebook-suehtam.appspot.com";
	//Constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME+"/o/";
	//Constante para o sufixo da URL
	private final String SUFFIX = "?alt=media";
	//Constante para a URL
	private final String DOWLOAD_URL = PREFIX + "%S" + SUFFIX;
	
	public FirebaseUtil() {
		//acessar o arquivo Json com a chave privada
		Resource resource = new ClassPathResource("chaveFireBase.json");
		//gera uma credencial no firebase
		try {
			credentials = GoogleCredentials.fromStream(resource.getInputStream());
			storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//metedo para extrar a extensão do arquivo
	private String getExtensao(String nomeArquivo) {
	//extrai o trecho do arquivo onde está a extensão
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	//metedo que faz o upload
	public String upload(MultipartFile arquivo) {
		//gera um nome aleatorio para o arquivo
		String nomeString = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		return "";
	}
			
			
	
	
}

