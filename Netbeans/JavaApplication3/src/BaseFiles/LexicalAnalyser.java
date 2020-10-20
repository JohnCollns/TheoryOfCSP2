/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

public class LexicalAnalyser {

	public static List<Token> analyse(String sourceCode) throws LexicalException {
		//Turn the input String into a list of Tokens!
                List<Token> tokenList = new ArrayList<Token>();
		String[] sourceArray = sourceCode.split("((?<=(\\{|\\}|\\|\\||&&|<|>|!|=|\\+|\\*|-|%|/|\\)|\\(|;|\\s|\"|'))|(?=(\\{|\\}|\\|\\||&&|<|>|=|!|\\+|\\*|-|%|/|\\)|\\(|;|\\s|\"|')))");
		//for (String word : sourceArray){
			//if (word.trim().length() > 0){
				//System.out.println(word);
				//System.out.println(tokenFromString(word));
			//}
		//}
		//return Collections.emptyList();
                
                if(sourceCode.equals("")) {
			return null;
		}
                
                for (int i = 0; i < sourceArray.length; i++) {
			if(sourceArray[i].equals(" ")) {
				//i += 1;
			}
			else if (sourceArray[i].equals("=") && i != sourceArray.length-1 && sourceArray[i+1].equals("=")){
				i += 1;
				tokenList.add(tokenFromString("==").get());
			}
                        else if (sourceArray[i].equals(">") && i != sourceArray.length-1 && sourceArray[i+1].equals("=")){
				i += 1;
				tokenList.add(tokenFromString(">=").get());
			}
			else if (sourceArray[i].equals("<") && i != sourceArray.length-1 && sourceArray[i+1].equals("=")){
				i += 1;
				tokenList.add(tokenFromString("<=").get());
			}
			else if (i != sourceArray.length-1 && i != 0 && sourceArray[i+1].equals("\"") && sourceArray[i-1].equals("\"")){
				tokenList.add(new Token(Token.TokenType.STRINGLIT, sourceArray[i]));
			}
			else if (i != sourceArray.length-1 && i != 0 && sourceArray[i+1].equals("''") && sourceArray[i-1].equals("''")){
				tokenList.add(new Token(Token.TokenType.CHARLIT, sourceArray[i]));
			}
			else {
				Optional<Token> s = tokenFromString(sourceArray[i]);
				if (s.isPresent()){
					tokenList.add(s.get());
				}
				else {
					throw new LexicalException("Invalid Input: " + sourceArray[i]);
				}
			}
		}

		return tokenList;
	}


	private static Optional<Token> tokenFromString(String t) {
		Optional<Token.TokenType> type = tokenTypeOf(t);
		if (type.isPresent())
			return Optional.of(new Token(type.get(), t));
		return Optional.empty();
	}

	private static Optional<Token.TokenType> tokenTypeOf(String t) {
		switch (t) {
		case "public":
			return Optional.of(Token.TokenType.PUBLIC);
		case "class":
			return Optional.of(Token.TokenType.CLASS);
		case "static":
			return Optional.of(Token.TokenType.STATIC);
		case "main":
			return Optional.of(Token.TokenType.MAIN);
		case "{":
			return Optional.of(Token.TokenType.LBRACE);
		case "void":
			return Optional.of(Token.TokenType.VOID);
		case "(":
			return Optional.of(Token.TokenType.LPAREN);
		case "String[]":
			return Optional.of(Token.TokenType.STRINGARR);
		case "args":
			return Optional.of(Token.TokenType.ARGS);
		case ")":
			return Optional.of(Token.TokenType.RPAREN);
		case "int":
		case "char":
		case "boolean":
			return Optional.of(Token.TokenType.TYPE);
		case "=":
			return Optional.of(Token.TokenType.ASSIGN);
		case ";":
			return Optional.of(Token.TokenType.SEMICOLON);
		case "if":
			return Optional.of(Token.TokenType.IF);
		case "for":
			return Optional.of(Token.TokenType.FOR);
		case "while":
			return Optional.of(Token.TokenType.WHILE);
		case "==":
			return Optional.of(Token.TokenType.EQUAL);
		case "+":
			return Optional.of(Token.TokenType.PLUS);
		case "-":
			return Optional.of(Token.TokenType.MINUS);
		case "*":
			return Optional.of(Token.TokenType.TIMES);
		case "/":
			return Optional.of(Token.TokenType.DIVIDE);
		case "%":
			return Optional.of(Token.TokenType.MOD);
		case "}":
			return Optional.of(Token.TokenType.RBRACE);
		case "else":
			return Optional.of(Token.TokenType.ELSE);
		case "System.out.println":
			return Optional.of(Token.TokenType.PRINT);
		case "||":
			return Optional.of(Token.TokenType.OR);
		case "&&":
			return Optional.of(Token.TokenType.AND);
		case "true":
			return Optional.of(Token.TokenType.TRUE);
		case "false":
			return Optional.of(Token.TokenType.FALSE);
                case "\"":
			return Optional.of(Token.TokenType.DQUOTE);
		case "'":
			return Optional.of(Token.TokenType.SQUOTE);
                case "!=":
			return Optional.of(Token.TokenType.NEQUAL);
		case "<":
			return Optional.of(Token.TokenType.LT);
		case ">":
			return Optional.of(Token.TokenType.GT);
		case "<=":
			return Optional.of(Token.TokenType.LE);
		case ">=":
			return Optional.of(Token.TokenType.GE);
		}

		if (t.matches("\\d+"))
			return Optional.of(Token.TokenType.NUM);
		if (Character.isAlphabetic(t.charAt(0)) && t.matches("[\\d|\\w]+")) {
			return Optional.of(Token.TokenType.ID);
		}
		return Optional.empty();
	}

}