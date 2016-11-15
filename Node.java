public class Node {
	Node esquerda;
	Node direita;
	Node pai;
	String key;

	// Vermelho é false e preto é true
	boolean cor;

	public Node(String key) {
		this.esquerda = null;
		this.direita = null;
		this.pai = null;
		this.key = key;
		this.cor = false;
	}

	public Node getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(Node esquerda) {
		this.esquerda = esquerda;
	}

	public Node getDireita() {
		return direita;
	}

	public void setDireita(Node direita) {
		this.direita = direita;
	}

	public Node getPai() {
		return pai;
	}

	public void setPai(Node pai) {
		this.pai = pai;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isCor() {
		return cor;
	}

	public void setCor(boolean cor) {
		this.cor = cor;
	}

}
