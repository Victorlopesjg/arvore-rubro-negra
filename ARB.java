public class ARB {

	public Node raiz;
	public Node result;

	public static final boolean PRETO = true;
	public static final boolean VERMELHO = false;

	public ARB() {
		this.raiz = null;
	}

	public void RBCheck(Node no) { // Imprime em pré-ordem exibindo (pai,chave, cor, altura negra, filho da esquerda, filho da direita)
		if (no != null) {
			// Recuperando a altura da arvore
			int altura = alturaNegra(no);

			// Recuperando o pai do nó
			String pai = "";
			if (no == raiz) {
				pai = "NIL";
			} else {
				pai = no.pai.key;
			}
			
			// Recuperando o filho da esquerda do nó
			String esq = "";
			if (no.esquerda == null) {
				esq = "NIL";
			} else {
				esq = no.esquerda.key;
			}
			
			// Recuperando o filho da direita do nó
			String dir = "";
			if (no.direita == null) {
				dir = "NIL";
			} else {
				dir = no.direita.key;
			}

			System.out.printf( "("+pai + ", " + no.key + ", " + getColor(no) + ", " + String.valueOf(altura) + ", " + esq + ", " + dir + ")\n" );
			
			RBCheck(no.getEsquerda());
			RBCheck(no.getDireita());
		}
	}
	
	private String getColor(Node no) {
		if (no.cor == PRETO) 
			return "preto";
		
		return "vermelho";
		
	}

	private int alturaNegra(Node no) {
		int contador = 0; 
		while (no.getEsquerda() != null) {
			no = no.getEsquerda();
			if (no.cor == PRETO)
				contador++;
		}
		return contador;
	}

	public void RBPrint(Node busca) {
		if (busca != null) {
			RBPrint(busca.getEsquerda());
			System.out.printf(busca.key + " ");
			RBPrint(busca.getDireita());
		}
	}

	Node search(String value, Node node) {
		result = null;
		if (node != null) {
			if (node.getKey().toLowerCase().compareTo(value.toLowerCase()) == 0)
				return node;

			if (node.key.toLowerCase().compareTo(value.toLowerCase()) > 0) {
				result = search(value, node.getEsquerda());
			} else {
				result = search(value, node.getDireita());
			}
		}
		return result;

	}

	public void RBInsert(ARB arvore, Node z) {

		if (arvore.raiz != null) {
			Node y = null;
			Node x = arvore.raiz;

			while (x != null) {
				y = x;

				if (z.key.toLowerCase().compareTo(x.key.toLowerCase()) < 0) {
					x = x.esquerda;
				} else {
					x = x.direita;
				}
			}

			z.pai = y;

			if (y == null) {
				arvore.raiz = z;
			} else if (z.key.toLowerCase().compareTo(y.key.toLowerCase()) < 0) {
				y.esquerda = z;
			} else {
				y.direita = z;
			}

			z.esquerda = null;
			z.direita = null;
			z.cor = VERMELHO;
		} else {
			z.esquerda = null;
			z.direita = null;
			z.cor = VERMELHO;
			raiz = z;
		}

		RBInsertFixUp(arvore, z);
	}

	private void RBInsertFixUp(ARB arvore, Node z) {
		while (z.pai != null && !z.pai.cor) {
			if (z.pai == z.pai.pai.esquerda) {
				Node y = z.pai.pai.direita;
				// Caso 1
				if (y != null && y.cor == VERMELHO) {
					z.pai.cor = PRETO;
					y.cor = PRETO;
					z.pai.pai.cor = VERMELHO;
					z = z.pai.pai;
				} else {
					// Caso 2
					if (z == z.pai.direita) {
						z = z.pai;
						leftRotate(arvore.raiz, z);
					}

					z.pai.cor = PRETO;
					z.pai.pai.cor = VERMELHO;
					rightRotate(arvore.raiz, z.pai.pai);
				}
			} else {
				if (z.pai == z.pai.pai.direita) {
					Node y = z.pai.pai.esquerda;
					// Caso 1
					if (y != null && !y.cor) {
						z.pai.cor = PRETO;
						y.cor = PRETO;
						z.pai.pai.cor = VERMELHO;
						z = z.pai.pai;
					} else {
						// Caso 2
						if (z == z.pai.esquerda) {
							z = z.pai;
							rightRotate(arvore.raiz, z);
						}

						z.pai.cor = PRETO;
						z.pai.pai.cor = VERMELHO;
						leftRotate(arvore.raiz, z.pai.pai);
					}
				}
			}
		}
		arvore.raiz.cor = PRETO;
	}

	private void RBTransplant(ARB arb, Node u, Node v) {
		if (u.pai == null) {
			arb.raiz = v;
		} else if (u == u.pai.esquerda) {
			u.pai.esquerda = v;
		} else if (u.pai.direita == v) {
			v.pai = u.pai;
		}
	}

	public void RBDelete(ARB arvore, Node z) {
		Node y = z;
		Node x = null;
		boolean auxCor = z.cor;

		if (z.esquerda == null) {
			x = z.direita;
			RBTransplant(arvore, z, z.direita);
		} else if (z.direita == null) {
			x = z.esquerda;
			RBTransplant(arvore, z, z.esquerda);
		} else {
			y = min(z.direita);
			auxCor = y.cor;
			x = y.direita;

			if (y.pai == z) {
				x.pai = y;
			} else {
				RBTransplant(arvore, y, y.direita);
				y.direita = z.direita;
				y.direita.pai = y;
			}

			RBTransplant(arvore, z, y);
			y.esquerda = z.esquerda;
			y.esquerda.pai = y;
			y.cor = z.cor;
		}

		if (auxCor) {
			RBDeleteFixUp(arvore, x);
		}
	}

	private void RBDeleteFixUp(ARB arvore, Node x) {
		Node w = null;
		while (x != arvore.raiz && x.pai.cor) {
			if (x == x.pai.esquerda) {
				w = x.pai.direita;

				if (w.cor == VERMELHO) { // Caso 1
					w.cor = PRETO;
					x.pai.cor = VERMELHO;
					leftRotate(arvore.raiz, x.pai);
					w = x.pai.direita;
				}

				if ((w.esquerda.cor == PRETO) && (w.direita.cor == PRETO)) { // Caso
																				// 2
					w.cor = VERMELHO;
					x = x.pai;
				} else if (w.direita.cor == PRETO) { // Caso 3
					w.esquerda.cor = PRETO;
					w.cor = VERMELHO;
					rightRotate(arvore.raiz, w);
					w = x.pai.direita;
				}

				// Caso 4
				w.cor = x.pai.cor;
				x.pai.cor = PRETO;
				w.direita.cor = PRETO;
				leftRotate(arvore.raiz, x.pai);
				x = arvore.raiz;
			} else {
				w = x.pai.esquerda;

				if (w.cor == VERMELHO) { // Caso 1
					w.cor = PRETO;
					x.pai.cor = VERMELHO;
					rightRotate(arvore.raiz, x.pai);
					w = x.pai.esquerda;
				}

				if (w.esquerda.cor && w.direita.cor) { // Caso 2
					w.cor = VERMELHO;
					x = x.pai;
				} else if (w.esquerda.cor == PRETO) { // Caso 3
					w.direita.cor = PRETO;
					w.cor = VERMELHO;
					leftRotate(arvore.raiz, w);
					w = x.pai.esquerda;
				}

				// Caso 4
				w.cor = x.pai.cor;
				x.pai.cor = PRETO;
				w.esquerda.cor = PRETO;
				rightRotate(arvore.raiz, x.pai);
				x = arvore.raiz;
			}
		}
		x.cor = PRETO;
	}

	private Node min(Node busca) {
		result = null;

		if (busca.getEsquerda() == null) {
			result = busca;
		} else {
			min(busca.getEsquerda());
		}

		return result;
	}

	private void leftRotate(Node root, Node x) {
		Node y = x.direita;
		x.direita = y.esquerda;

		if (y.esquerda != null) {
			y.esquerda.pai = x;
		}

		y.pai = x.pai;

		if (x.pai == null) {
			root = y;
		} else if (x == x.pai.esquerda) {
			x.pai.esquerda = y;
		} else {
			x.pai.direita = y;
		}

		y.esquerda = x;
		x.pai = y;
	}

	private void rightRotate(Node root, Node x) {
		Node y = x.esquerda;
		x.esquerda = y.direita;

		if (y.direita != null) {
			y.direita.pai = x;
		}

		y.pai = x.pai;

		if (x.pai == null) {
			root = y;
		} else if (x == x.pai.direita) {
			x.pai.direita = y;
		} else {
			x.pai.esquerda = y;
		}

		y.direita = x;
		x.pai = y;
	}
}