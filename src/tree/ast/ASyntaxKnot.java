package tree.ast;

import java.util.List;

import grammar.Expression;
import tree.SyntaxExpressionKnot;
import tree.SyntaxKnot;
import tree.SyntaxLeaf;
import tree.SyntaxNode;

/**
 * An abstract syntax knot.
 * This class is currently a placeholder for later additions.
 * @author Flip van Spaendonck
 */
public abstract class ASyntaxKnot extends SyntaxKnot{

	public ASyntaxKnot(SyntaxKnot parent) {
		super(parent);
	}

	/**
	 * Checks whether the amount of children this node has, is equal to the amount of children allowed by the expression.
	 * ASyntaxKnots should make sure their children are filled in directly after construction.
	 */
	@Override
	public boolean isComplete() {
		return true;
	}
	
}
