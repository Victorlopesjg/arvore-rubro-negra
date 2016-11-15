
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		FileReader f;
		try {

			f = new FileReader("/Users/victoroliveira/Documents/Mestrado/workspace/aaa/src/dicionario.txt");

			Scanner entrada = new Scanner(f);

			String op;

			ARB arb = new ARB();

			while (entrada.hasNext()) {
				String value = entrada.next();

				Node no = arb.search(value, arb.raiz);
				switch (entrada.next()) {
				case "1":
					if (no == null) {
						if (value.length() <= 20) {
							no = new Node(value);
							arb.RBInsert(arb, no);
						} else {
							System.out.println("A palavra é maior que 20 caracteres");
						}
					} else {
						System.out.println("A palavra \"" + value + "\" já existe.");
					}
					break;
				case "0":
					if (no != null) {
						arb.RBDelete(arb, no);
					} else {
						System.out.println("A palavra \"" + value + "\" não existe.");
					}
					break;

				}
			}

			Scanner sc = new Scanner(System.in);
			System.out.print("Digite uma palava a ser buscada:");
			while (sc.hasNext()) {
				String input = sc.next();
				if (input.equalsIgnoreCase("\"\"")) {
					break;
				} else {
					Node no = arb.search(input, arb.raiz);
					if (no == null)
						System.out.println("A palavra \"" + input + "\" não existe.");
					else
						System.out.println("A palavra encontrada foi: " + no.key);
				}
				System.out.print("Digite uma palava a ser buscada:");
			}

			System.out.println("\n\n");
			arb.RBPrint(arb.raiz);
			System.out.println("\n");
			arb.RBCheck(arb.raiz);

		} catch (FileNotFoundException e) {
			System.out.print("ERROR");
		}
	}
}
