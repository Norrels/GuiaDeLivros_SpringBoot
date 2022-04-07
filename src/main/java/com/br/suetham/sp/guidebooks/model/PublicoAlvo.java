package com.br.suetham.sp.guidebooks.model;

public enum PublicoAlvo {
INFATIL("Infatil"), JUVENIL("Juvenil"), ADULTO("Adulto");
	
	String desc;
	
	private PublicoAlvo(String desc) {
		this.desc = desc;
	
	}
	@Override
	public String toString() {
		return this.desc ;
	}
}

