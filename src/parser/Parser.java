package parser;

import java.util.LinkedList;
import java.util.List;

import grammar.Expression;
import grammar.ExpressionTree;
import grammar.Node;
import grammar.SPL;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
import tree.SyntaxTree;

/**
 * @author Flip van Spaendonck
 *
 */
public class Parser {
	private Lexer lexer;
	private List<Token> tokenList;

	// =========================================================
	// Constructors
	// =========================================================

	public Parser(Lexer l) {
		lexer = l;
		tokenList = new LinkedList<>();
		while (true) {
			Token token = lexer.nextToken();
			if (token.getTokenType() == TokenType.TOK_EOF)
				break;
			else
				tokenList.add(token);
		}
		try {
			SPLExpressionParser.packExpressions(tokenList);
		} catch (ParsingException e) {
			e.printStackTrace();
		}

		//Parses the list of tokens and turns it into a syntax tree.
		convertZambinos(explorino(SPL.INSTANCE, tokenList));

	}

	public Parser(String input) {
		this(new Lexer(input));
	}

	// ==========================================================
	// Support functions and methods
	// ==========================================================

	public static List<Zambino> explorino(ExpressionTree syntax, List<Token> code) {
		List<Zambino> currentZambinos = Zambino.explorinoZambino(null, syntax.root, new LinkedList<Pair>());
		List<Zambino> newZambinos = new LinkedList<>();
		while(!code.isEmpty()) {
			Token token = code.remove(0);
			if (token.getTokenType() == TokenType.TOK_EOF)
				break;
			newZambinos = new LinkedList<>();
			for(Zambino zambino : currentZambinos) {
				if(zambino.token.getTokenType() == TokenType.TOK_NIL) {
					for(Zambino next : zambino.next()) {
						if(next.token.getTokenType() == token.getTokenType()) {
							newZambinos.addAll(next.next());
						}
					}
				}
				if(zambino.token.getTokenType() == token.getTokenType()) {
					newZambinos.addAll(zambino.next());
				} else {
					//Maybe do some garbage collection here, this is probably a good spot for making the algorithm less Memory intensive.
				}
			}
			currentZambinos = newZambinos;
		}
		List<Zambino> legalZambinos = new LinkedList<>();
		for(Zambino zambino : currentZambinos) {
			if (zambino.token.getTokenType() == TokenType.TOK_EOF) {
				Zambino current = zambino;
				legalZambinos.add(current);
				while(current.leftZambino != null) {
					current = current.leftZambino;
					legalZambinos.add(0, current);
					
				}
				return legalZambinos;
			}
			
		}
		return null;
	}

	/**
	 * Transforms a list of Zambinos into a syntax tree.
	 */
	public static SyntaxTree convertZambinos(List<Zambino> zambinos) {
		SyntaxTree tree = new SyntaxTree( new SyntaxExpressionKnot(zambinos.get(0).expressions.get(0).expression, null));
		for(Zambino currentZambino : zambinos) {
			currentZambino.affixTo(tree);
		}
		
		return tree;
	}
	
	/**
	 * I don't even know if I can explain this XD.
	 * 
	 * @author Vizu
	 *
	 */
	public static class Zambino {
		/** The token this Zambino represents */
		private Token token;
		/** The expression it is part of */
		private List<Pair> expressions;
		/** The zambino holding the token left of this one.**/
		private final Zambino leftZambino;

		public Zambino(Token token, List<Pair> newExpressions, Zambino prev) {
			this.token = token;
			if (prev != null) {
				expressions = newExpressions.subList(0, prev.expressions.size());
			} else {
				expressions = new LinkedList<>();
			}
			leftZambino = prev;
		}
		
		public List<Zambino> next() {
			return next(this.expressions,this);
		}
		
		public static List<Zambino> next(List<Pair> pairs ,Zambino prevOne) {
			List<Zambino> out = new LinkedList<>();
			//Check whether pairs.size()==0 and then?
			if (pairs.size() == 0) {
				out.add(new Zambino(new Token(TokenType.TOK_EOF), null, prevOne));
				return out;
			}
			Pair tail = pairs.get(pairs.size()-1);
			if(!tail.isLast()) {
				List<Pair> newExpressions = pairs.subList(0, pairs.size()-1);
				newExpressions.add(new Pair(tail.expression, tail.index+1));
				if (tail.expression.expression[tail.index+1] instanceof Token) {
					out.add(new Zambino((Token)tail.expression.expression[tail.index+1], newExpressions, prevOne));
				} else {
					out.addAll(explorinoZambino(prevOne,(Node)tail.expression.expression[tail.index+1], newExpressions));
				}
			} else {
				out.addAll(next(pairs.subList(0, pairs.size()-1), prevOne));
			}
			return out;
		}

		public static List<Zambino> explorinoZambino(Zambino previousZambino, Node node, List<Pair> nonZambinoedExpressions) {
			List<Zambino> out = new LinkedList<>();
			for (Expression expression : node.expressions) {
				List<Pair> newlyEncounteredExpressions = nonZambinoedExpressions.subList(0, nonZambinoedExpressions.size());
				newlyEncounteredExpressions.add(new Pair(expression, 0));
				if (expression.expression[0] instanceof Token) {
					out.add(new Zambino((Token) expression.expression[0], newlyEncounteredExpressions, previousZambino));
				} else if (expression.expression[0] instanceof Node) {
					out.addAll(explorinoZambino(previousZambino, (Node)expression.expression[0], newlyEncounteredExpressions));
				}
			}
			return out;
		}
		
		/**
		 * Affixes the list of expressions to the current tree.
		 */
		public void affixTo(SyntaxTree tree) {
			for(int d=tree.frontier.depth+1; d < expressions.size(); d++) {
				SyntaxExpressionKnot currentNode = new SyntaxExpressionKnot(expressions.get(d).expression, (SyntaxExpressionKnot) tree.frontier);
				tree.frontier.addChild(currentNode);
				tree.frontier = currentNode;
			}
			tree.frontier.addChild(new SyntaxLeaf(token, (SyntaxExpressionKnot) tree.frontier));
			while(tree.frontier != null && tree.frontier.isComplete()) {
				tree.frontier = tree.frontier.parent;
			}
		}
		
		
		
	}
	
	private static class Pair {

		  private final Expression expression;
		  private final int index;

		  public Pair(Expression left, int right) {
		    expression = left;
		    index = right;
		  }
		  
		  public boolean isLast() {
			  return (index >= expression.expression.length-1);
		  }
	}

}
