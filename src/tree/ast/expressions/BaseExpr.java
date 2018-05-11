package tree.ast.expressions;

import java.util.Map;

import grammar.ExpressionWithAST;
import lexer.PrimitiveType;
import lexer.TokenBool;
import lexer.TokenIdentifier;
import lexer.TokenInteger;
import tree.SyntaxExpressionKnot;
import tree.SyntaxLeaf;
import tree.ast.ITypeCheckable;
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

/**
 *
 * @author Loes Kruger, Geertje Peters Rit and Flip van Spaendonck
 */
public abstract class BaseExpr implements ITypeCheckable{

    public abstract BaseExpr optimize();

    public abstract String getCode();

    /**
     * A factory function that converts a knot in the EXP syntax into a BaseExpr
     * @param knot
     * @return
     */
    public final static BaseExpr convertToExpr(SyntaxExpressionKnot knot) {
		switch (((ExpressionWithAST)knot.expression).id) {
		//BaseExp
		case "SetExp":
			return new EmptyList();
		case "TupleExp":
			return new TupleExp(convertToExpr((SyntaxExpressionKnot) knot.children[1]), convertToExpr((SyntaxExpressionKnot) knot.children[3]));
		//BoolExp2
		case "and":
			return new And(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "or":
			return new Or(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "eq":
			return new Equality(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "neq":
			return new Inequality(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "smaller":
			return new Smaller(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "larger":
			return new Larger(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "smallerEq":
			return new SmallerEq(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "largerEq":
			return new LargerEq(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		//BoolExp1
		case "negation":
			return new Negate(convertToExpr((SyntaxExpressionKnot) knot.children[1]));
		//BoolExp0
		case "variableBool":
			return new Variable(((TokenIdentifier)((SyntaxLeaf)knot.children[0]).leaf).value, new BaseType(PrimitiveType.PRIMTYPE_BOOL));
		case "funcallBool":
			return new FunCall((SyntaxExpressionKnot) knot.children[0], new BaseType(PrimitiveType.PRIMTYPE_BOOL));
		case "boolean":
			return new BoolConstant(((TokenBool)((SyntaxLeaf)knot.children[0]).leaf).value);
		//NumRng
		case "modulo":
			return new Modulo(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "divide":
			return new Divide(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "multiply":
			return new Multiply(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		//NumFld
		case "plus":
			return new Add(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		case "minus":
			return new Minus(convertToExpr((SyntaxExpressionKnot) knot.children[0]), convertToExpr((SyntaxExpressionKnot) knot.children[2]));
		//NumSng
		case "variableNum":
			return new Variable(((TokenIdentifier)((SyntaxLeaf)knot.children[0]).leaf).value, new BaseType(PrimitiveType.PRIMTYPE_INT));
		case "funcallNum":
			return new FunCall((SyntaxExpressionKnot) knot.children[0], new BaseType(PrimitiveType.PRIMTYPE_INT));
		case "int":
		case "char":
			return new NumConstant(((TokenInteger)((SyntaxLeaf)knot.children[0]).leaf).value);
		//Mixed
		case "brackets":
			return convertToExpr((SyntaxExpressionKnot) knot.children[1]);
		default:
			return convertToExpr((SyntaxExpressionKnot) knot.children[0]);
		}
	}
}
