package tree;

import java.util.List;

import lexer.Token;
import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock.Scope;
import tree.ast.LabelCounter;

/**
 * A leaf in the syntax-tree data structure.
 * 
 * @author Flip van Spaendonck
 *
 */
public class SyntaxLeaf extends SyntaxNode {

	/** The token that is represented by this leaf **/
	public final Token leaf;

	public SyntaxLeaf(Token token, SyntaxKnot parent) {
		super(parent);

		leaf = token;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		// TODO Auto-generated method stub
		/* If a syntaxLeaf is somehow accidently encountered during the code generation
		 	process, we'll just let it be and do nothing. */
		return;
	}

	@Override
	public String toString() {
		return leaf.toString();
	}

	@Override
	public void checkTypes(IDDeclarationBlock domain, Scope scope) throws TypeException, DeclarationException {
		return;
	}

	@Override
	public SyntaxNode getASTEquivalent(SyntaxKnot parent) throws Exception {
		return new SyntaxLeaf(leaf, parent);
	}

	@Override
	public boolean alwaysReturns() {
		return false;
	}
}
