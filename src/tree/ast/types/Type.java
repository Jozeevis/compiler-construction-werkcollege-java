/**
 * 
 */
package tree.ast.types;

import grammar.ExpressionWithAST;
import tree.SyntaxKnot;
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
	public static Type inferType(SyntaxKnot node) {
		if (node.expression instanceof ExpressionWithAST) {
			switch (((ExpressionWithAST) node.expression).id) {
			case "BaseType":
				return new BaseType(((TokenPrimitiveType) ((SyntaxLeaf) node.children[0]).leaf).getType());
			case "TupleType":
				return new TupleType(inferType((SyntaxKnot) node.children[1]),
						inferType((SyntaxKnot) node.children[3]));
			case "ListType":
				return new ListType(inferType((SyntaxKnot) node.children[1]));
			case "CustomType":
				return new CustomType(((TokenIdentifier) node.children[0].reduceToToken()).getValue());
			}
		}
		return null;
	}

}
