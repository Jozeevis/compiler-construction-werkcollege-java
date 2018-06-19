package tree.ast;

import java.util.List;

import grammar.Expression;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.IDDeclarationBlock.Scope;
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

	@Override
	public SyntaxNode getASTEquivalent(SyntaxKnot parent) throws Exception {
		throw new Exception("This node is already an ASyntaxKnot and should not be converted!");
	}
	
	
	
}
