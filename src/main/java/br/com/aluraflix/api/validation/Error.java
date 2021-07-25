package br.com.aluraflix.api.validation;

public class Error {

	private String erro;
	private String campo;
	
	public Error(String erro, String campo) {
		this.erro = erro;
		this.campo = campo;
	}

	public String getErro() {
		return erro;
	}

	public String getCampo() {
		return campo;
	}
	
}
