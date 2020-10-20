/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseFiles;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		try {
			List<Token> results = LexicalAnalyser.analyse("public class foo { public static void main(String[] args){ int i = 0; if (i == 2) { i = i + 1; System.out.println(\"Hi\"); } else { i = i * 2; } } }");
			//List<Token> results = LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ for ( ; 5 ;) {; } }}");
                        System.out.println(results);
			ParseTree tree = SyntacticAnalyser.parse(results);
			System.out.println(tree);
		} catch (LexicalException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}

	}

}