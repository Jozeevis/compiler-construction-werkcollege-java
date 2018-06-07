/**
 * 
 */
package tree.ast.types;

import java.util.LinkedList;
import java.util.List;

import grammar.ExpressionWithAST;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import lexer.PrimitiveType;
import lexer.TokenIdentifier;
import lexer.TokenPrimitiveType;
import tree.SyntaxLeaf;
import tree.ast.expressions.BaseExpr;
import tree.ast.expressions.MupleExp;

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
			case "MupleType":
				List<Type> types= new LinkedList<>();
				SyntaxKnot mypeKnot = (SyntaxKnot) node.children[1];
				while(mypeKnot.children.length == 3) {
					types.add(inferType((SyntaxExpressionKnot)mypeKnot.children[0]));
					mypeKnot = (SyntaxKnot) mypeKnot.children[2];
				}
				types.add(inferType((SyntaxExpressionKnot)mypeKnot.children[0]));
				return new MupleType(types.toArray(new Type[] {}));
			case "ListType":
				return new ListType(inferType((SyntaxExpressionKnot) node.children[1]));
			case "CustomType":
				return new CustomType(((TokenIdentifier) node.children[0].reduceToToken()).getValue());
			}
		}
		return null;
	}

	public abstract BaseExpr getNullValue();

}
