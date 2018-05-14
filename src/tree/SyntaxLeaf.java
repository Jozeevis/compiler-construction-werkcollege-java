package tree;

import java.util.List;

import lexer.Token;
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

	public SyntaxLeaf(Token token, SyntaxExpressionKnot parent) {
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

}
