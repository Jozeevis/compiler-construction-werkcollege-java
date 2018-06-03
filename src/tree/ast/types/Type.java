/**
 * 
 */
package tree.ast.types;

import grammar.ExpressionWithAST;
import tree.SyntaxExpressionKnot;
import lexer.PrimitiveType;
import lexer.TokenIdentifier;
import lexer.TokenPrimitiveType;
import tree.SyntaxLeaf;
import tree.ast.expressions.BaseExpr;

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class Type {

	public abstract boolean matches(Type t);
	
	/**
	 * A factory function that infers a Type from the given SyntaxKnot
	 */
	public static Type inferType(SyntaxExpressionKnot node) {
		if (node.expression instanceof ExpressionWithAST) {
			switch (((ExpressionWithAST) node.expression).id) {
			case "BaseType":
				return BaseType.instanceOf(((TokenPrimitiveType) ((SyntaxLeaf) node.children[0]).leaf).getType());
			case "TupleType":
				return new TupleType(inferType((SyntaxExpressionKnot) node.children[1]),
						inferType((SyntaxExpressionKnot) node.children[3]));
			case "ListType":
				return new ListType(inferType((SyntaxExpressionKnot) node.children[1]));
			case "CustomType":
				return new StructType(((TokenIdentifier) node.children[0].reduceToToken()).getValue());
			}
		}
		return null;
	}
	
	/**
	 * A factory function that infers a Type from the given SyntaxKnot, given that the knot is the root of an expression.
	 * @deprecated
	 */
	public static Type inferExpressionType(SyntaxExpressionKnot knot) {
		switch (((ExpressionWithAST)knot.expression).id) {
		case "BoolExp":
			return BaseType.instanceBool;
		case "NumExp":
			return BaseType.instanceInt;
		case "SetExp":
			return new ListType(Type.inferType((SyntaxExpressionKnot) knot.children[0]));
		case "TupleExp":
			return new TupleType(inferExpressionType((SyntaxExpressionKnot) knot.children[1])
					, inferExpressionType((SyntaxExpressionKnot) knot.children[3])
					);
		}
		return null;
	}

	public abstract BaseExpr getNullValue();

}
