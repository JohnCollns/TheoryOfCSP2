/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseFiles;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SyntacticAnalyser {

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
		//Turn the List of Tokens into a ParseTree.
		ParseTree pTree = new ParseTree();
		List<Symbol> stack = new ArrayList<Symbol>();
		HashMap<String, Token> PDA = new HashMap<String, Token>();
		//return new ParseTree();

		stack.add(TreeNode.Label.prog);
	
		// Rule 1: <<prog>> → public class <<ID>> { public static void main ( String[] args ) { <<los>> } }
		Symbol[] r1 = {Token.TokenType.PUBLIC, Token.TokenType.CLASS, Token.TokenType.ID, Token.TokenType.LBRACE, Token.TokenType.PUBLIC, Token.TokenType.VOID, Token.TokenType.MAIN, 
		Token.TokenType.LPAREN, Token.TokenType.STRINGARR, Token.TokenType.ARGS, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, 
		Token.TokenType.RBRACE};
		// Rule 2: <<los>> → <<stat>> <<los>>
		Symbol[] r2 = {Token.TokenType.SEMICOLON, Token.TokenType.TYPE, Token.TokenType.PRINT, Token.TokenType.WHILE, Token.TokenType.FOR, Token.TokenType.IF, Token.TokenType.ID};
		// Rule 3: <<los>> → ε
		Symbol[] r3 = {Token.TokenType.RBRACE};
		// Rule 4: <<stat>> → <<while>>
		Symbol[] r4 = {Token.TokenType.WHILE};
		// Rule 5: <<stat>> → <<for>>
		Symbol[] r5 = {Token.TokenType.FOR};
		// Rule 6: <<stat>> → <<if>>
		Symbol[] r6 = {Token.TokenType.IF};
		// Rule 7: <<stat>> → <<assign>> ;
		Symbol[] r7 = {Token.TokenType.ID};
		// Rule 8: <<stat>> → <<decl>> ;
		Symbol[] r8 = {Token.TokenType.TYPE};
		// Rule 9: <<stat>> → <<print>> ;
		Symbol[] r9 = {Token.TokenType.PRINT};
		// Rule 10: <<stat>> → ;
		Symbol[] r10 = {Token.TokenType.SEMICOLON};
		// Rule 11: <<while>> → while ( <<rel expr>> <<bool expr>> ) { <<los>> }
		// Rule 12: <<for>> → for ( <<for start>> ; <<rel expr>> <<bool expr>> ; <<for arith>> ) { <<los>> }
		// Rule 13: <<for start>> → <<decl>>
		// Rule 14: <<for start>> →<<assign>> 
		// Rule 15: <<for start>> →  ε
		// Rule 16: <<for arith>> → <<arith expr>>
		// Rule 17: <<for arith>> → ε
		// Rule 18: <<if>> → if ( <<rel expr>> <<bool expr>> ) { <<los>> } <<else if>>
		// Rule 19: <<else if>> → <<else?if>> { <<los>> } <<else if>>
		// Rule 20: <<else if>> → ε
		// Rule 21: <<else?if>> → else <<poss if>>
		// Rule 22: <<poss if>> → if ( <<rel expr>> <<bool expr>> )
		// Rule 23: <<poss if>> → ε
		// Rule 24: <<assign>> → <<ID>> = <<expr>>
		// Rule 25: <<decl>> → <<type>> <<ID>> <<poss assign>>
		// Rule 26: <<poss assign>> → = <<expr>>
		// Rule 27: <<poss assign>> → ε
		// Rule 28: <<print>> → System.out.println ( <<print expr>> )
		// Rule 29: <<type>> → int
		// Rule 30: <<type>> → boolean 
		// Rule 31: <<type>> → char
		// Rule 32: <<expr>> → <<rel expr>> <<bool expr>>
		// Rule 33: <<expr>> → <<char expr>>
		// Rule 34: <<char expr>> → ' <<char>> '
		// Rule 35: <<bool expr>> → <<bool op>> <<rel expr>> <<bool expr>>
		// Rule 36: <<bool expr>> → ε
		// Rule 37: <<bool op>> → <<bool eq>>
		// Rule 38: <<bool op>> → <<bool log>>
		// Rule 39: <<bool eq>> → ==
		// Rule 40: <<bool eq>> → !=
		// Rule 41: <<bool log>> → &&
		// Rule 42: <<bool log>> → ||
		// Rule 43: <<rel expr>> → <<arith expr>> <<rel expr'>>
		// Rule 44: <<rel expr>> → true
		// Rule 45: <<rel expr>> → false
		// Rule 46: <<rel expr'>> → <<rel op>> <<arith expr>>
		// Rule 47: <<rel expr'>> → ε
		// Rule 48: <<rel op>> → < 
		// Rule 49: <<rel op>> → <=
		// Rule 50: <<rel op>> → >
		// Rule 51: <<rel op>> → >=
		// Rule 52: <<arith expr>> → <<term>> <<arith expr'>>
		// Rule 53: <<arith expr'>> → + <<term>> <<arith expr'>>
		// Rule 54: <<arith expr'>> → - <<term>> <<arith expr'>>
		// Rule 55: <<arith expr'>> → ε
		// Rule 56: <<term>> → <<factor>> <<term'>>
		// Rule 57: <<term'>> → * <<factor>> <<term'>>
		// Rule 58: <<term'>> →  / <<factor>> <<term'>>
		// Rule 59: <<term'>> → % <<factor>> <<term'>>
		// Rule 60: <<term'>> → ε
		// Rule 61: <<factor>> → ( <<arith expr>> )
		// Rule 62: <<factor>> → <<ID>>
		// Rule 63: <<factor>> → <<num>>
		// Rule 64: <<print expr>> → <<rel expr>> <<bool expr>>
		// Rule 65: <<print expr>> → "<<string lit>>"
		
		return pTree;
	}

}