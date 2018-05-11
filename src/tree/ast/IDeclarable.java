package tree.ast;

import tree.IDDeclaration;

/**
 * An interface used to show that the current node declares an identifier.
 * @author Flip van Spaendonck
 *
 */
public interface IDeclarable {

	public IDDeclaration getDeclaration();
}
