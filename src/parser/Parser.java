package parser;

import java.util.ArrayList;
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
import tree.ast.expressions.IllegalThisException;

/**
 * @author Flip van Spaendonck
 *
 */
public class Parser {
	private Lexer lexer;
	private List<Token> tokenList;
	
	public SyntaxTree tree;

	// =========================================================
	// Constructors
	// =========================================================

	public Parser(Lexer l) {
		lexer = l;
		tokenList = l.allNextTokens();
		try {
			SPLExpressionParser.packExpressions(tokenList);
		} catch (ParsingException | IllegalThisException e) {
			e.printStackTrace();
		}
		System.out.println(tokenList);
		//Parses the list of tokens and turns it into a syntax tree.
		System.out.println("Tokens have been succesfully packed. \n Parsing now commences.");
		List<TokenTrace> lineage;
		try {
			lineage = parseCode(SPL.INSTANCE, tokenList);
			if (lineage == null) {
				System.err.println("This code is a pile of flaming hot garbage and thus could not be parsed.");
				return;
			}
			System.out.println("Code has been parsed succesfully");
			tree = convertTokenTraces(lineage);

		} catch (ParsingException e) {
			e.printStackTrace();
		}
	}

	public Parser(String input) {
		this(new Lexer(input));
	}
	
	// ==========================================================
	// Support functions and methods
	// ==========================================================

	public static List<TokenTrace> parseCode(ExpressionTree syntax, List<Token> code) throws ParsingException {
		List<TokenTrace> currentZambinos = TokenTrace.getAllTokenTracesFromNode(null, syntax.root, new LinkedList<Pair>());
		List<TokenTrace> newZambinos = new LinkedList<>();
		while(!code.isEmpty()) {
			Token token = code.remove(0);
			System.out.println("Trying to fit: "+token);
			if (token.getTokenType() == TokenType.TOK_EOF)
				break;
			System.out.println(currentZambinos);
			newZambinos = new LinkedList<>();
			for(TokenTrace zambino : currentZambinos) {
				if(zambino.token.getTokenType() == TokenType.TOK_NIL) {
					for(TokenTrace next : zambino.next()) {
						if(next.token.getTokenType() == token.getTokenType()) {
							next.token = token;
							newZambinos.addAll(next.next());
						}
					}
				}
				//System.out.println("Trying to match "+zambino.token.getTokenType()+" with "+token.getTokenType()+".");
				if(zambino.token.getTokenType() == token.getTokenType()) {
					zambino.token = token;
					newZambinos.addAll(zambino.next());
				} else {
					//Maybe do some garbage collection here, this is probably a good spot for making the algorithm less Memory intensive.
				}
			}
			currentZambinos = newZambinos;
		}
		List<TokenTrace> legalZambinos = new LinkedList<>();
		for(TokenTrace zambino : currentZambinos) {
			if (zambino.token.getTokenType() == TokenType.TOK_EOF) {
				TokenTrace current = zambino;
				System.out.println(current);
				legalZambinos.add(current);
				while(current.leftTokenTrace != null) {
					current = current.leftTokenTrace;
					legalZambinos.add(0, current);
					
				}
				return legalZambinos;
			}
			
		}
		throw new ParsingException("No correct parse was possible");
	}

	/**
	 * Transforms a list of Zambinos into a syntax tree.
	 */
	public static SyntaxTree convertTokenTraces(List<TokenTrace> tokenTraces) {
		//prune possible EOF tokentrace
		if (tokenTraces.get(tokenTraces.size()-1).token.getTokenType() == TokenType.TOK_EOF) {
			tokenTraces.remove(tokenTraces.size()-1);
			//System.out.println("trace pruned\n"+zambinos.get(0).token );
		}
		System.out.println("fixing time");
		SyntaxTree tree = new SyntaxTree( new SyntaxExpressionKnot(tokenTraces.get(0).expressions.get(0).expression, null));
		for(TokenTrace currentZambino : tokenTraces) {
			currentZambino.affixTo(tree);
		}
		
		System.out.println("boompje geplakt");
		return tree;
	}
	
	
	/**
	 * I don't even know if I can explain this XD.
	 * 
	 * @author Vizu
	 *
	 */
	public static class TokenTrace {
		/** The token this TokenTrace represents */
		public Token token;
		/** The expression it is part of */
		public final List<Pair> expressions;
		/** The TokenTrace holding the token left of this one.**/
		public final TokenTrace leftTokenTrace;

		public TokenTrace(Token token, List<Pair> newExpressions, TokenTrace prev) {
			this.token = token;
			if (newExpressions != null) {
				expressions = new ArrayList<>(newExpressions);
			} else {
				expressions = new ArrayList<>();
			}
			
			leftTokenTrace = prev;
		}
		
		/**
		 * This one is used for the final tokentrace.
		 */
		private TokenTrace(TokenTrace prev) {
			token = new Token(TokenType.TOK_EOF);
			expressions = new LinkedList<>();
			leftTokenTrace= prev;
		}
		
		public List<TokenTrace> next() {
			List<TokenTrace> nextTraces = next(this.expressions,this);
			int i=0;
			while (i < nextTraces.size()) {
				if(nextTraces.get(i).token.getTokenType() == TokenType.TOK_NIL) {
					TokenTrace nilTrace = nextTraces.remove(i);
					nextTraces.addAll(i, nilTrace.next());
				} else {
					i++;
				}
			}
			return nextTraces;
		}
		
		public static List<TokenTrace> next(List<Pair> pairs ,TokenTrace prevOne) {
			List<TokenTrace> out = new LinkedList<>();
			//Check whether pairs.size()==0 and then?
			if (pairs.size() == 0) {
				out.add(new TokenTrace(prevOne));
				return out;
			}
			Pair tail = pairs.get(pairs.size()-1);
			if(!tail.isLast()) {
				List<Pair> newExpressions = new LinkedList<>(pairs.subList(0, pairs.size()-1));
				newExpressions.add(new Pair(tail.expression, tail.index+1));
				if (tail.expression.expression[tail.index+1] instanceof Token) {
					out.add(new TokenTrace((Token)tail.expression.expression[tail.index+1], newExpressions, prevOne));
				} else {
					out.addAll(getAllTokenTracesFromNode(prevOne,(Node)tail.expression.expression[tail.index+1], newExpressions));
				}
			} else {
				//System.out.println("tail filled, pairs above: "+ (pairs.size()-1));
				out.addAll(next(pairs.subList(0, pairs.size()-1), prevOne));
			}
			return out;
		}

		/**
		 * Finds all possible tokentraces from the given node.
		 * @param leftTokenTrace the previous tokentrace
		 * @param node the node through which the tokentraces go
		 * @param expressionsNotCoveredUnderTokenTrace
		 * @return
		 */
		public static List<TokenTrace> getAllTokenTracesFromNode(TokenTrace leftTokenTrace, Node node, List<Pair> expressionsNotCoveredUnderTokenTrace) {
			List<TokenTrace> out = new LinkedList<>();
			for (Expression expression : node.expressions) {
				List<Pair> newlyEncounteredExpressions = new LinkedList<>(expressionsNotCoveredUnderTokenTrace);
				newlyEncounteredExpressions.add(new Pair(expression, 0));
				if (expression.expression[0] instanceof Token) {
					out.add(new TokenTrace((Token) expression.expression[0], newlyEncounteredExpressions, leftTokenTrace));
				} else if (expression.expression[0] instanceof Node) {
					out.addAll(getAllTokenTracesFromNode(leftTokenTrace, (Node)expression.expression[0], newlyEncounteredExpressions));
				}
			}
			return out;
		}
		
		/**
		 * Affixes the list of expressions to the current tree.
		 */
		public void affixTo(SyntaxTree tree) {
			System.out.println("Affixing: "+this.token);
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
		
		@Override
		public String toString() {
			if (expressions.size() == 0)
				return "End Token Trace";
			return token+ " in expression: "+expressions.get(expressions.size()-1);
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
		  
		  @Override
		  public String toString() {
			  return expression.toString() + ":"+ index;
		  }
	}

}
