/**
 * 
 */
package lexer;

import java.util.List;

import grammar.EXP;
import grammar.ExpressionWithAST;
import parser.Parser;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.SyntaxTree;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.EmptyList;
import tree.ast.expressions.FunCall;
import tree.ast.expressions.TupleExp;
import tree.ast.expressions.Variable;
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
import tree.ast.expressions.num.Add;
import tree.ast.expressions.num.Divide;
import tree.ast.expressions.num.Minus;
import tree.ast.expressions.num.Modulo;
import tree.ast.expressions.num.Multiply;
import tree.ast.expressions.num.NumConstant;
import tree.ast.types.BaseType;
import tree.ast.types.TupleType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class TokenExpression extends Token {

	public final BaseExpr expression;
	public final Type type;
	
	public TokenExpression(List<Token> list) {
		super(TokenType.TOK_EXP);
		SyntaxTree tree = Parser.convertZambinos(Parser.explorino(EXP.INSTANCE, list));
		type = Type.inferExpressionType(tree.root);
		expression = BaseExpr.convertToExpr(tree.root);
		
	}
}
