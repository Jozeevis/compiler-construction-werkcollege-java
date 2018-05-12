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

/**
 * @author Flip van Spaendonck
 *
 */
public abstract class Type {

	
	/**
	 * A factory function that infers a Type from the given SyntaxKnot
	 */
	public static Type inferType(SyntaxExpressionKnot node) {
		if (node.expression instanceof ExpressionWithAST) {
			switch (((ExpressionWithAST) node.expression).id) {
			case "BaseType":
				return new BaseType(((TokenPrimitiveType) ((SyntaxLeaf) node.children[0]).leaf).getType());
			case "TupleType":
				return new TupleType(inferType((SyntaxExpressionKnot) node.children[1]),
						inferType((SyntaxExpressionKnot) node.children[3]));
			case "ListType":
				return new ListType(inferType((SyntaxExpressionKnot) node.children[1]));
			case "CustomType":
				return new CustomType(((TokenIdentifier) node.children[0].reduceToToken()).getValue());
			}
		}
		return null;
	}
	
	/**
	 * A factory function that infers a Type from the given SyntaxKnot, given that the knot is the root of an expression.
	 */
	public static Type inferExpressionType(SyntaxExpressionKnot knot) {
		switch (((ExpressionWithAST)knot.expression).id) {
		case "BoolExp":
			return new BaseType(PrimitiveType.PRIMTYPE_BOOL);
		case "NumExp":
			return new BaseType(PrimitiveType.PRIMTYPE_INT);
		case "SetExp":
			return Type.inferType((SyntaxExpressionKnot) knot.children[0]);
		case "TupleExp":
			return new TupleType(inferExpressionType((SyntaxExpressionKnot) knot.children[1])
					, inferExpressionType((SyntaxExpressionKnot) knot.children[3])
					);
		}
		return null;
	}

}
