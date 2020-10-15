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
		//HashMap<String, Token> PDA = new HashMap<String, Token>();
		//return new ParseTree();

                // We should add a $ to stack first, not sure how, it's neither a token nor treeNode. 
		stack.add(TreeNode.Label.prog);
                
                for (int i=0; i < tokens.size() - 1; i++){
                    
                    if (!getTop(stack).isVariable()){ // if top of stack is a terminal
                        if (getTop(stack) == tokens.get(i)){ // if top of stack is the same terminal as the token being read
                            // Pop top of stack from stack and loop again. 
                            stack.remove(stack.size() - 1);
                        }
                    }
                }
	
                // Grammar Rules as list of symbols (terminals/tokens, variables/treenode.label's)
		// Rule 1: <<prog>> → public class <<ID>> { public static void main ( String[] args ) { <<los>> } }
		Symbol[] r0 = {Token.TokenType.PUBLIC, Token.TokenType.CLASS, Token.TokenType.ID, Token.TokenType.LBRACE, Token.TokenType.PUBLIC, Token.TokenType.VOID, Token.TokenType.MAIN, 
		Token.TokenType.LPAREN, Token.TokenType.STRINGARR, Token.TokenType.ARGS, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, 
		Token.TokenType.RBRACE};
                
		// Rule 2: <<los>> → <<stat>> <<los>>
		Symbol[] r1 = {TreeNode.Label.stat, TreeNode.Label.los};
		// Rule 3: <<los>> → ε
		Symbol[] r2 = {TreeNode.Label.epsilon};
		// Rule 4: <<stat>> → <<while>>
		Symbol[] r3 = {TreeNode.Label.whilestat};
		// Rule 5: <<stat>> → <<for>>
		Symbol[] r4 = {TreeNode.Label.forstat};
		// Rule 6: <<stat>> → <<if>>
		Symbol[] r5 = {TreeNode.Label.ifstat};
		// Rule 7: <<stat>> → <<assign>> ;
		Symbol[] r6 = {TreeNode.Label.assign, Token.TokenType.SEMICOLON};
		// Rule 8: <<stat>> → <<decl>> ;
		Symbol[] r7 = {TreeNode.Label.decl, Token.TokenType.SEMICOLON};
		// Rule 9: <<stat>> → <<print>> ;
		Symbol[] r8 = {Token.TokenType.PRINT, Token.TokenType.SEMICOLON};
		// Rule 10: <<stat>> → ;
		Symbol[] r9 = {Token.TokenType.SEMICOLON};
		// Rule 11: <<while>> → while ( <<rel expr>> <<bool expr>> ) { <<los>> }
		Symbol[] r10 = {Token.TokenType.WHILE, Token.TokenType.LPAREN, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.RPAREN,
		Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE};		
                // Rule 12: <<for>> → for ( <<for start>> ; <<rel expr>> <<bool expr>> ; <<for arith>> ) { <<los>> }
                Symbol[] r11 = {Token.TokenType.FOR, Token.TokenType.LPAREN, TreeNode.Label.forstart, Token.TokenType.SEMICOLON, TreeNode.Label.relexpr, 
                TreeNode.Label.boolexpr, Token.TokenType.SEMICOLON, TreeNode.Label.forarith, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE};
		// Rule 13: <<for start>> → <<decl>>
                Symbol[] r12 = {TreeNode.Label.decl};
		// Rule 14: <<for start>> →<<assign>> 
                Symbol[] r13 = {TreeNode.Label.assign};
		// Rule 15: <<for start>> →  ε
                Symbol[] r14 = {TreeNode.Label.epsilon};
		// Rule 16: <<for arith>> → <<arith expr>>
                Symbol[] r15 = {TreeNode.Label.arithexpr};
		// Rule 17: <<for arith>> → ε
                Symbol[] r16 = {TreeNode.Label.epsilon};
		// Rule 18: <<if>> → if ( <<rel expr>> <<bool expr>> ) { <<los>> } <<else if>>
                Symbol[] r17 = {Token.TokenType.IF, Token.TokenType.LPAREN, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.RPAREN,Token.TokenType.LBRACE, TreeNode.Label.los, 
                Token.TokenType.RBRACE, TreeNode.Label.elseifstat};
		// Rule 19: <<else if>> → <<else?if>> { <<los>> } <<else if>>
                Symbol[] r18 = {TreeNode.Label.elseorelseif, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, TreeNode.Label.elseifstat};
		// Rule 20: <<else if>> → ε
                Symbol[] r19 = {TreeNode.Label.epsilon};
		// Rule 21: <<else?if>> → else <<poss if>>
                Symbol[] r20 = {Token.TokenType.ELSE, TreeNode.Label.possif};
		// Rule 22: <<poss if>> → if ( <<rel expr>> <<bool expr>> )
                Symbol[] r21 = {Token.TokenType.IF, Token.TokenType.LPAREN, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.RPAREN};
		// Rule 23: <<poss if>> → ε
                Symbol[] r22 = {TreeNode.Label.epsilon};
		// Rule 24: <<assign>> → <<ID>> = <<expr>>
                Symbol[] r23 = {Token.TokenType.ID, Token.TokenType.ASSIGN, TreeNode.Label.expr};
		// Rule 25: <<decl>> → <<type>> <<ID>> <<poss assign>>
                Symbol[] r24 = {TreeNode.Label.type, Token.TokenType.ID, TreeNode.Label.possassign};
		// Rule 26: <<poss assign>> → = <<expr>>
                Symbol[] r25 = {Token.TokenType.ASSIGN, TreeNode.Label.expr};
		// Rule 27: <<poss assign>> → ε
                Symbol[] r26 = {TreeNode.Label.epsilon};
		// Rule 28: <<print>> → System.out.println ( <<print expr>> )
                Symbol[] r27 = {Token.TokenType.PRINT, Token.TokenType.LPAREN, TreeNode.Label.printexpr, Token.TokenType.RPAREN};
		// Rule 29: <<type>> → int
                Symbol[] r28 = {Token.TokenType.TYPE};
		// Rule 30: <<type>> → boolean 
                Symbol[] r29 = {Token.TokenType.TYPE};
		// Rule 31: <<type>> → char
                Symbol[] r30 = {Token.TokenType.TYPE};
		// Rule 32: <<expr>> → <<rel expr>> <<bool expr>>
                Symbol[] r31 = {TreeNode.Label.relexpr, TreeNode.Label.boolexpr};
		// Rule 33: <<expr>> → <<char expr>>
                Symbol[] r32 = {TreeNode.Label.charexpr};
		// Rule 34: <<char expr>> → ' <<char>> '
                Symbol[] r33 = {Token.TokenType.SQUOTE, Token.TokenType.TYPE, Token.TokenType.SQUOTE};
		// Rule 35: <<bool expr>> → <<bool op>> <<rel expr>> <<bool expr>>
                Symbol[] r34 = {TreeNode.Label.boolop, TreeNode.Label.relexpr, TreeNode.Label.boolexpr};
		// Rule 36: <<bool expr>> → ε
                Symbol[] r35 = {TreeNode.Label.epsilon};
		// Rule 37: <<bool op>> → <<bool eq>>
                Symbol[] r36 = {TreeNode.Label.booleq};
		// Rule 38: <<bool op>> → <<bool log>>
                Symbol[] r37 = {TreeNode.Label.boollog};
		// Rule 39: <<bool eq>> → ==
                Symbol[] r38 = {Token.TokenType.EQUAL};
		// Rule 40: <<bool eq>> → !=
                Symbol[] r39 = {Token.TokenType.NEQUAL};
		// Rule 41: <<bool log>> → &&
                Symbol[] r40 = {Token.TokenType.AND};
		// Rule 42: <<bool log>> → ||
                Symbol[] r41 = {Token.TokenType.OR};
		// Rule 43: <<rel expr>> → <<arith expr>> <<rel expr'>>
                Symbol[] r42 = {TreeNode.Label.arithexpr, TreeNode.Label.relexprprime};
		// Rule 44: <<rel expr>> → true
                Symbol[] r43 = {Token.TokenType.TRUE};
		// Rule 45: <<rel expr>> → false
                Symbol[] r44 = {Token.TokenType.FALSE};
		// Rule 46: <<rel expr'>> → <<rel op>> <<arith expr>>
                Symbol[] r45 = {TreeNode.Label.relop, TreeNode.Label.arithexpr};
		// Rule 47: <<rel expr'>> → ε
                Symbol[] r46 = {TreeNode.Label.epsilon};
		// Rule 48: <<rel op>> → < 
                Symbol[] r47 = {Token.TokenType.LT};
		// Rule 49: <<rel op>> → <=
                Symbol[] r48 = {Token.TokenType.LE};
		// Rule 50: <<rel op>> → >
                Symbol[] r49 = {Token.TokenType.GT};
		// Rule 51: <<rel op>> → >=
                Symbol[] r50 = {Token.TokenType.GE};
		// Rule 52: <<arith expr>> → <<term>> <<arith expr'>>
                Symbol[] r51 = {TreeNode.Label.term, TreeNode.Label.arithexprprime};
		// Rule 53: <<arith expr'>> → + <<term>> <<arith expr'>>
                Symbol[] r52 = {Token.TokenType.PLUS, TreeNode.Label.term, TreeNode.Label.arithexprprime};
		// Rule 54: <<arith expr'>> → - <<term>> <<arith expr'>>
                Symbol[] r53 = {Token.TokenType.MINUS, TreeNode.Label.term, TreeNode.Label.arithexprprime};
		// Rule 55: <<arith expr'>> → ε
                Symbol[] r54 = {TreeNode.Label.epsilon};
		// Rule 56: <<term>> → <<factor>> <<term'>>
                Symbol[] r55 = {TreeNode.Label.factor, TreeNode.Label.termprime};
		// Rule 57: <<term'>> → * <<factor>> <<term'>>
                Symbol[] r56 = {Token.TokenType.TIMES, TreeNode.Label.factor, TreeNode.Label.termprime};
		// Rule 58: <<term'>> →  / <<factor>> <<term'>>
                Symbol[] r57 = {Token.TokenType.DIVIDE, TreeNode.Label.factor, TreeNode.Label.termprime};
		// Rule 59: <<term'>> → % <<factor>> <<term'>>
                Symbol[] r58 = {Token.TokenType.MOD, TreeNode.Label.factor, TreeNode.Label.termprime};
		// Rule 60: <<term'>> → ε
                Symbol[] r59 = {TreeNode.Label.epsilon};
		// Rule 61: <<factor>> → ( <<arith expr>> )
                Symbol[] r60 = {Token.TokenType.LPAREN, TreeNode.Label.arithexpr, Token.TokenType.RPAREN};
		// Rule 62: <<factor>> → <<ID>>
                Symbol[] r61 = {Token.TokenType.ID};
		// Rule 63: <<factor>> → <<num>>
                Symbol[] r62 = {Token.TokenType.NUM};
		// Rule 64: <<print expr>> → <<rel expr>> <<bool expr>>
                Symbol[] r63 = {TreeNode.Label.relexpr, TreeNode.Label.boolexpr};
		// Rule 65: <<print expr>> → "<<string lit>>"
                Symbol[] r64 = {Token.TokenType.DQUOTE, Token.TokenType.STRINGLIT, Token.TokenType.DQUOTE};
                // rule list = all of above rules. 
                
                // Parsing Table
                HashMap<Pair, Symbol[]> parseTable = new HashMap<Pair, Symbol[]>();
		// Format: the key to the hashmap is a pair class, where the first object is is the token 
                // being read by the analyser and the second is the symbol ontop of the stack. 
                // This way pair[0] is the x axis of our parsing table and pair[1] is the y axis. 
                // The value inside is a rule, which is held and returned as an array of symbols (tokens and treenode.label's) 
                //parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.RPAREN, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.LBRACE, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.RBRACE, TreeNode.Label.prog), r0);
                parseTable.put(new Pair(Token.TokenType.PUBLIC, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.CLASS, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.STATIC, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.VOID, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.MAIN, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.STRINGARR, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.ARGS, TreeNode.Label.prog), r0);
                //parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.prog), r0);
                // We need a rule for popping the $ off the stack, not sure how to 
                // even put it on in the first place. Might find another workaround. 
                parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.los), r1);
                parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.los), r1);
                parseTable.put(new Pair(Token.TokenType.PRINT, TreeNode.Label.los), r1);
                parseTable.put(new Pair(Token.TokenType.WHILE, TreeNode.Label.los), r1);
                parseTable.put(new Pair(Token.TokenType.FOR, TreeNode.Label.los), r1);
                parseTable.put(new Pair(Token.TokenType.IF, TreeNode.Label.los), r1);
                parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.los), r1);
                
                // There are the first two rows of the parsing table, I hope you can understand and continue on
                
                
                
		return pTree;
	}
        
    static Symbol getTop(List<Symbol> list){
        return list.get(list.size() - 1);
    }

}