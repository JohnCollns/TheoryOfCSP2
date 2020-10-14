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
		HashMap<String, Token> PDA = new HashMap<String, Token>();
		//return new ParseTree();

		stack.add(TreeNode.Label.prog);
	
		
		
		return pTree;
	}

}