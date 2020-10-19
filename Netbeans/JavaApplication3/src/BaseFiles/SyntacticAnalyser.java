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
            ArrayList<Symbol> stack = new ArrayList<Symbol>();
            //HashMap<String, Token> PDA = new HashMap<String, Token>();
            //return new ParseTree();

            // Grammar Rules as list of symbols (terminals/tokens, variables/treenode.label's)
            // Rule 1: <<prog>> → public class <<ID>> { public static void main ( String[] args ) { <<los>> } }
// real ver           Symbol[] r0 = {Token.TokenType.PUBLIC, Token.TokenType.CLASS, Token.TokenType.ID, Token.TokenType.LBRACE, Token.TokenType.PUBLIC, Token.TokenType.VOID, Token.TokenType.MAIN, 
//            Token.TokenType.LPAREN, Token.TokenType.STRINGARR, Token.TokenType.ARGS, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, 
//            Token.TokenType.RBRACE};
            Symbol[] r0 = {Token.TokenType.CLASS, Token.TokenType.ID, Token.TokenType.LBRACE, Token.TokenType.PUBLIC, Token.TokenType.VOID, Token.TokenType.MAIN, 
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
            // Format: the key to the hashmap is a pair class, where the first object is is the token 
            // being read by the analyser and the second is the symbol ontop of the stack. 
            // This way pair[0] is the x axis of our parsing table and pair[1] is the y axis. 
            // The value inside is a rule, which is held and returned as an array of symbols (tokens and treenode.label's) 
            HashMap<Pair, Symbol[]> parseTable = new HashMap<Pair, Symbol[]>();
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
            parseTable.put(new Pair(Token.TokenType.RBRACE, TreeNode.Label.los), r2);

            parseTable.put(new Pair(Token.TokenType.WHILE, TreeNode.Label.stat), r3);
            parseTable.put(new Pair(Token.TokenType.FOR, TreeNode.Label.stat), r4);
            parseTable.put(new Pair(Token.TokenType.IF, TreeNode.Label.stat), r5);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.stat), r6);
            parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.stat), r7);
            parseTable.put(new Pair(Token.TokenType.PRINT, TreeNode.Label.stat), r8);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.stat), r9);

            parseTable.put(new Pair(Token.TokenType.WHILE, TreeNode.Label.whilestat), r10);
            parseTable.put(new Pair(Token.TokenType.FOR, TreeNode.Label.forstat), r11);
            parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.forstart), r12);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.forstart), r13);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.forstart), r14);

            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.forarith), r15);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.forarith), r15);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.forarith), r15);
            parseTable.put(new Pair(Token.TokenType.RPAREN, TreeNode.Label.forarith), r16);
            parseTable.put(new Pair(Token.TokenType.IF, TreeNode.Label.ifstat), r17);
            parseTable.put(new Pair(Token.TokenType.ELSE, TreeNode.Label.elseifstat), r18);
            parseTable.put(new Pair(Token.TokenType.RBRACE, TreeNode.Label.elseifstat), r19);
            parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.elseifstat), r19);
            parseTable.put(new Pair(Token.TokenType.PRINT, TreeNode.Label.elseifstat), r19);
            parseTable.put(new Pair(Token.TokenType.WHILE, TreeNode.Label.elseifstat), r19);
            parseTable.put(new Pair(Token.TokenType.FOR, TreeNode.Label.elseifstat), r19);
            parseTable.put(new Pair(Token.TokenType.IF, TreeNode.Label.elseifstat), r19);
            
            parseTable.put(new Pair(Token.TokenType.ELSE, TreeNode.Label.elseorelseif), r20);
            parseTable.put(new Pair(Token.TokenType.IF, TreeNode.Label.possif), r21);
            parseTable.put(new Pair(Token.TokenType.LBRACE, TreeNode.Label.possif), r22);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.assign), r23);
            // Added
            //parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.assign), r23);
            //parseTable.put(new Pair(Token.TokenType.ASSIGN, TreeNode.Label.assign), r23);
            // Added
            parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.decl), r24);
            parseTable.put(new Pair(Token.TokenType.ASSIGN, TreeNode.Label.possassign), r25);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.possassign), r26);
            parseTable.put(new Pair(Token.TokenType.PRINT, TreeNode.Label.print), r27);

            // Might be an issue with these 3 since they are int, boolean, char respectively
            parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.type), r28);
            //parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.type), r29);
            //parseTable.put(new Pair(Token.TokenType.TYPE, TreeNode.Label.type), r30);

            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.expr), r31);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.expr), r31);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.expr), r31);
            parseTable.put(new Pair(Token.TokenType.TRUE, TreeNode.Label.expr), r31);
            parseTable.put(new Pair(Token.TokenType.FALSE, TreeNode.Label.expr), r31);

            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.expr), r32);
            
            parseTable.put(new Pair(Token.TokenType.SQUOTE, TreeNode.Label.charexpr), r33);
            parseTable.put(new Pair(Token.TokenType.EQUAL, TreeNode.Label.boolexpr), r34);
            parseTable.put(new Pair(Token.TokenType.NEQUAL, TreeNode.Label.boolexpr), r34);
            parseTable.put(new Pair(Token.TokenType.AND, TreeNode.Label.boolexpr), r34);
            parseTable.put(new Pair(Token.TokenType.OR, TreeNode.Label.boolexpr), r34);

            parseTable.put(new Pair(Token.TokenType.RPAREN, TreeNode.Label.boolexpr), r35);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.boolexpr), r35);

            parseTable.put(new Pair(Token.TokenType.EQUAL, TreeNode.Label.boolop), r36);
            parseTable.put(new Pair(Token.TokenType.NEQUAL, TreeNode.Label.boolop), r36);
            parseTable.put(new Pair(Token.TokenType.AND, TreeNode.Label.boolop), r37);
            parseTable.put(new Pair(Token.TokenType.OR, TreeNode.Label.boolop), r37);

            parseTable.put(new Pair(Token.TokenType.EQUAL, TreeNode.Label.booleq), r38);
            parseTable.put(new Pair(Token.TokenType.NEQUAL, TreeNode.Label.booleq), r39);

            parseTable.put(new Pair(Token.TokenType.AND, TreeNode.Label.boollog), r40);
            parseTable.put(new Pair(Token.TokenType.OR, TreeNode.Label.boollog), r41);

            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.relexpr), r42);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.relexpr), r42);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.relexpr), r42);
            parseTable.put(new Pair(Token.TokenType.TRUE, TreeNode.Label.relexpr), r43);
            parseTable.put(new Pair(Token.TokenType.FALSE, TreeNode.Label.relexpr), r44); 
            parseTable.put(new Pair(Token.TokenType.LE, TreeNode.Label.relexprprime), r45);
            parseTable.put(new Pair(Token.TokenType.GE, TreeNode.Label.relexprprime), r45);
            parseTable.put(new Pair(Token.TokenType.LT, TreeNode.Label.relexprprime), r45);
            parseTable.put(new Pair(Token.TokenType.GT, TreeNode.Label.relexprprime), r45);

            parseTable.put(new Pair(Token.TokenType.EQUAL, TreeNode.Label.relexprprime), r46);
            parseTable.put(new Pair(Token.TokenType.NEQUAL, TreeNode.Label.relexprprime), r46);
            parseTable.put(new Pair(Token.TokenType.AND, TreeNode.Label.relexprprime), r46);
            parseTable.put(new Pair(Token.TokenType.OR, TreeNode.Label.relexprprime), r46);
            parseTable.put(new Pair(Token.TokenType.RPAREN, TreeNode.Label.relexprprime), r46);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.relexprprime), r46);
            parseTable.put(new Pair(Token.TokenType.LT, TreeNode.Label.relop), r47);
            parseTable.put(new Pair(Token.TokenType.LE, TreeNode.Label.relop), r48);
            parseTable.put(new Pair(Token.TokenType.GT, TreeNode.Label.relop), r49);
            parseTable.put(new Pair(Token.TokenType.GE, TreeNode.Label.relop), r50);
            
            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.arithexpr), r51);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.arithexpr), r51);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.arithexpr), r51);
            parseTable.put(new Pair(Token.TokenType.PLUS, TreeNode.Label.arithexprprime), r52);
            parseTable.put(new Pair(Token.TokenType.MINUS, TreeNode.Label.arithexprprime), r53);
            parseTable.put(new Pair(Token.TokenType.EQUAL, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.NEQUAL, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.LT, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.GT, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.LE, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.GE, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.AND, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.OR, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.arithexprprime), r54);
            parseTable.put(new Pair(Token.TokenType.RPAREN, TreeNode.Label.arithexprprime), r54);

            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.term), r55);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.term), r55);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.term), r55);
            parseTable.put(new Pair(Token.TokenType.TIMES, TreeNode.Label.termprime), r56);
            parseTable.put(new Pair(Token.TokenType.DIVIDE, TreeNode.Label.termprime), r57);
            parseTable.put(new Pair(Token.TokenType.MOD, TreeNode.Label.termprime), r58);

            parseTable.put(new Pair(Token.TokenType.PLUS, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.MINUS, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.EQUAL, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.NEQUAL, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.LT, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.GT, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.LE, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.GE, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.AND, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.OR, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.SEMICOLON, TreeNode.Label.termprime), r59);
            parseTable.put(new Pair(Token.TokenType.RPAREN, TreeNode.Label.termprime), r59);

            
            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.factor), r60);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.factor), r61);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.factor), r62);
            parseTable.put(new Pair(Token.TokenType.LPAREN, TreeNode.Label.printexpr), r63);
            parseTable.put(new Pair(Token.TokenType.TRUE, TreeNode.Label.printexpr), r63);
            parseTable.put(new Pair(Token.TokenType.FALSE, TreeNode.Label.printexpr), r63);
            parseTable.put(new Pair(Token.TokenType.ID, TreeNode.Label.printexpr), r63);
            parseTable.put(new Pair(Token.TokenType.NUM, TreeNode.Label.printexpr), r63);
            parseTable.put(new Pair(Token.TokenType.DQUOTE, TreeNode.Label.printexpr), r64);
            
            //stack.add(new DollarSign());
            stack.add(TreeNode.Label.prog);
            
//            System.out.println("Value of r44 (false): " + r44[0] + r44[0].getClass());
//            //System.out.println("Key to parseTable (TRUE, expr): " );
//            for (Pair i : parseTable.keySet()){
//                //System.out.println(i + " " + i.fst().getClass());
//                if (i.fst().equals(r44[0])){
//                    System.out.println("Token.tokenType FALSE is equal to: " + i);
//                }
//            }
            
            for (int i=0; i < tokens.size() - 1; i++){
                // For testing purposes
                System.out.print(i);
                System.out.println(": Starting a loop, reading token: " + tokens.get(i) + ", top of stack: " + getTop(stack) + " (type: " + getTop(stack).getClass()+")");
                //System.out.println("Stack class: " + getTop(stack).getClass() + " Token class" + tokens.get(i).getClass());
                Token.TokenType tok = tokens.get(i).getType();
                if (getTop(stack) == new DollarSign() || stack.isEmpty()){ 
                    // Analysis is complete and successful
                    break;
                }
                
                if (!getTop(stack).isVariable()){ // if top of stack is a terminal (or $)
                    if (getTop(stack) == tok){ // if top of stack is the same terminal as the token being read
                        // Add top of stack symbol to parse tree
                        
                        // Pop top of stack from stack and loop again. 
                        System.out.println("Top of stack is a terminal token that matches stack, pop from stack. ");
                        stack.remove(stack.size() - 1);
                    }
                    else {
                        System.out.println("Unexpected terminal error. On input on token: " + tokens.get(i) + ", and stack symbol: " + getTop(stack));
                        // Put a real error raise here, I don't know anything about that
                        
                        // For testing purposes, pop here, where normally we wouldn't
                        //stack.remove(stack.size() - 1);
                    }
                } else { // top of stack is a variable
                    Symbol[] testSymArray = parseTable.get(new Pair(tok, getTop(stack)));
                    System.out.println("Key Pair(" + tok + ", " + getTop(stack) + ") returns: " + testSymArray);
                    if (parseTable.containsKey(new Pair(tok, getTop(stack)))) {
                        //System.out.println("Running the try section - match between stack and grammar symbol.");
                        //parseTable.get(new Pair(tokens.get(i), stack.get(stack.size() - 2))); // this may be erroneous, but it's here to test if an entry here exists
                        Symbol oldTop = getTop(stack);
                        // Add top of stack symbol to parse tree
                        //System.out.println("Removing top of stack. Stack size: " + stack.size() + ", removing: " + (stack.size()-1));
                        stack.remove(stack.size() - 1);
                        //System.out.println("We survived the pop");
                        if (stack.size() > 0){ System.out.println("Popping stack, new top of stack: " + getTop(stack) + ", oldTop: " + oldTop); }
                        System.out.println("Checking parseTable at Pair(" + tok + ", " + oldTop + ")");
                        Symbol[] production = parseTable.get(new Pair(tok, oldTop));
                        //System.out.println("Production: " + production);
                        // Add new symbols to the stack, in reverse order of the grammar (think this works)
                        for (int j=production.length - 1; j >= 0; j--){
                            stack.add(production[j]);
                            System.out.println("Pushing: " + production[j] + " to the stack");
                        }
                    } else { // parsing table at this stack symbol and token is empty which means its an error in the string. 
                        System.out.println("Parsing table error (missing entry) on the input on token: " + tokens.get(i) + ", and stack symbol: " + getTop(stack));
                        // Call a real error, I dare you
                    }
                }
                
                System.out.println();
            }




            return pTree;
	}
        
    static Symbol getTop(List<Symbol> list){
        return list.get(list.size() - 1);
    }

}