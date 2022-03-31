package com.br.suetham.sp.guidebooks.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	public static String hash(String palavra) {
		//"Tempero" do hash
		String salt = "b@lich3";
		//Adicionar o tempero a palavra
		palavra = salt + palavra;
		//gera o hash
		String hash = Hashing.sha384().hashString(palavra, StandardCharsets.UTF_8).toString();
		//retorna o hash
		return hash;
	}
}