package tree.ast.expressions;

import java.util.LinkedList;
import java.util.List;

import grammar.ExpressionWithAST;
import lexer.TokenBool;
import lexer.TokenChar;
import lexer.TokenIdentifier;
import lexer.TokenInteger;
import lexer.TokenType;
import processing.DeclarationException;
import processing.TreeProcessing;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.SyntaxNode;
import tree.ast.LabelCounter;
import tree.ast.expressions.bool.And;
import tree.ast.expressions.bool.BoolConstant;
import tree.ast.expressions.bool.Equality;
import tree.ast.expressions.bool.Inequality;
import tree.ast.expressions.bool.Larger;
import tree.ast.expressions.bool.LargerEq;
import tree.ast.expressions.bool.Negate;
import tree.ast.expressions.bool.Or;
import tree.ast.expressions.bool.Smaller;
import tree.ast.expressions.bool.SmallerEq;
import tree.ast.expressions.list.Concat;
import tree.ast.expressions.list.EmptyList;
import tree.ast.expressions.num.Add;
import tree.ast.expressions.num.CharConstant;
import tree.ast.expressions.num.Divide;
import tree.ast.expressions.num.Minus;
import tree.ast.expressions.num.Modulo;
import tree.ast.expressions.num.Multiply;
import tree.ast.expressions.num.Negative;
import tree.ast.expressions.num.NumConstant;
import tree.ast.expressions.structs.InitExpr;
import tree.ast.expressions.structs.NullExpr;
import tree.ast.types.Type;

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public abstract class BaseExpr {

	public abstract BaseExpr optimize();

	public abstract void addCodeToStack(List<String> stack, LabelCounter counter);

	/**
	 * Checks whether the expression (and possibly its children) is/are well-typed.
	 * 
	 * @param domain
	 *            the IDDeclarationBlock used to describe which id's are declared,
	 *            and what type they correspond to.
	 * @return the type of this expression.
	 * @throws TypeException
	 *             this method should throw a TypeException if the expression is
	 *             incorrectly typed.
	 * @throws DeclarationException
	 */
	public abstract Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException;

	/**
	 * A factory function that converts a knot in the EXP syntax into a BaseExpr
	 * 
	 * @param knot
	 * @return
	 * @throws IllegalThisException 
	 */
	public final static BaseExpr convertToExpr(SyntaxExpressionKnot knot) throws IllegalThisException {
		if (knot.expression instanceof ExpressionWithAST) {
			switch (((ExpressionWithAST) knot.expression).id) {
			// BaseExp
			case "MupleExp":
				List<BaseExpr> expressions = new LinkedList<>();
				SyntaxKnot mexprKnot = (SyntaxKnot) knot.children[1];
				while(mexprKnot.children.length == 3) {
					expressions.add(convertToExpr((SyntaxExpressionKnot)mexprKnot.children[0]));
					mexprKnot = (SyntaxKnot) mexprKnot.children[2];
				}
				expressions.add(convertToExpr((SyntaxExpressionKnot)mexprKnot.children[0]));
				return new MupleExp(expressions.toArray(new BaseExpr[] {}));
			// BoolExp2
			case "and":
				return new And(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "or":
				return new Or(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "eq":
				return new Equality(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "neq":
				return new Inequality(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "smaller":
				return new Smaller(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "larger":
				return new Larger(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "smallerEq":
				return new SmallerEq(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "largerEq":
				return new LargerEq(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			// BoolExp1
			case "negation":
				return new Negate(convertToExpr((SyntaxExpressionKnot) knot.children[1]));
			// BoolExp0
			case "boolean":
				return new BoolConstant(((TokenBool) ((SyntaxLeaf) knot.children[0]).leaf).value);
			// NumRng
			case "mdm":
				mdm : {List<SyntaxNode> right = TreeProcessing.extractFromStarNode((SyntaxKnot) knot.children[1]);
				if (right.size() == 0)
					return convertToExpr((SyntaxExpressionKnot) knot.children[0]);
				BaseExpr leftExpr = convertToExpr((SyntaxExpressionKnot) knot.children[0]);
				System.out.println(leftExpr +"\n and now looping");
				for(SyntaxNode rightPart : right) {
					
					if (((SyntaxExpressionKnot) rightPart).children[0].reduceToToken().getTokenType() == TokenType.TOK_MOD) {
						leftExpr = new Modulo(leftExpr, convertToExpr((SyntaxExpressionKnot) ((SyntaxExpressionKnot) rightPart).children[1]));
					} else if (((SyntaxExpressionKnot) rightPart).children[0].reduceToToken().getTokenType() == TokenType.TOK_MULT)
						leftExpr = new Multiply(leftExpr, convertToExpr((SyntaxExpressionKnot) ((SyntaxExpressionKnot) rightPart).children[1]));
					else
						leftExpr = new Divide(leftExpr, convertToExpr((SyntaxExpressionKnot) ((SyntaxExpressionKnot) rightPart).children[1]));
					System.out.println(leftExpr + "    and go");
				}
				return leftExpr;}
			case "modulo":
				return new Modulo(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "divide":
				return new Divide(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			case "multiply":
				return new Multiply(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						convertToExpr((SyntaxExpressionKnot) knot.children[2]));
			// NumFld
			case "plusminus":
				List<SyntaxNode> right = TreeProcessing.extractFromStarNode((SyntaxKnot) knot.children[1]);
				if (right.size() == 0)
					return convertToExpr((SyntaxExpressionKnot) knot.children[0]);
				BaseExpr leftExpr = convertToExpr((SyntaxExpressionKnot) knot.children[0]);
				System.out.println(leftExpr +"\n and now looping");
				for(SyntaxNode rightPart : right) {
					
					if (((SyntaxExpressionKnot) rightPart).children[0].reduceToToken().getTokenType() == TokenType.TOK_PLUS) {
						leftExpr = new Add(leftExpr, convertToExpr((SyntaxExpressionKnot) ((SyntaxExpressionKnot) rightPart).children[1]));
					} else
						leftExpr = new Minus(leftExpr, convertToExpr((SyntaxExpressionKnot) ((SyntaxExpressionKnot) rightPart).children[1]));
					System.out.println(leftExpr + "    and go");
				}
				System.out.println(leftExpr);
				return leftExpr;
			//Neg
			case "negative":
				return new Negative(convertToExpr((SyntaxExpressionKnot) knot.children[1]));
			//NumSng
			case "int":
				return new NumConstant(((TokenInteger) ((SyntaxLeaf) knot.children[0]).leaf).value);
			case "char":
				return new CharConstant(((TokenChar) ((SyntaxLeaf) knot.children[0]).leaf).value);
			// SetExp
			case "SetConcat":
				// Parse out the values we want to concatenate
				List<BaseExpr> concats = new LinkedList<>();
				SyntaxExpressionKnot plusKnot;
				plusKnot = (SyntaxExpressionKnot) knot.children[1];
				while (plusKnot.children.length != 1) {
					concats.add(0, convertToExpr((SyntaxExpressionKnot) plusKnot.children[0]));
					plusKnot = (SyntaxExpressionKnot) plusKnot.children[1];
				}
				concats.add(0, convertToExpr((SyntaxExpressionKnot) plusKnot.children[0]));
				// Convert the left element into a basic expression.
				BaseExpr out = EmptyList.instanceOf; //convertToExpr((SyntaxExpressionKnot) knot.children[0]);
				// Iterate through the values we want to concatenate.
				for (BaseExpr concat : concats) {
					out = new Concat(concat, out);
					System.out.println(out);
				}
				return out;
			case "emptySet":
				return EmptyList.instanceOf;
			//Structs
			case "Null":
				return NullExpr.instanceOf;
			case "Init":
				return new InitExpr( knot);
			// Mixed
			case "callup":
				return new CallUp(convertToExpr((SyntaxExpressionKnot) knot.children[0]),
						(SyntaxExpressionKnot) knot.children[1]);
			case "this":
				return new This(knot);
			case "funcall":
				return new FunCall((SyntaxExpressionKnot) knot.children[0]);
			case "variable":
				return new Variable(((TokenIdentifier) ((SyntaxLeaf) knot.children[0]).leaf).value);
			case "brackets":
				return convertToExpr((SyntaxExpressionKnot) knot.children[1]);
			case "isEmpty":
				return new IsEmpty(convertToExpr((SyntaxExpressionKnot) knot.children[1]));
			default:
				return convertToExpr((SyntaxExpressionKnot) knot.children[0]);
			}
		}
		System.out.println(knot);
		return convertToExpr((SyntaxExpressionKnot) knot.children[0]);
	}
}
