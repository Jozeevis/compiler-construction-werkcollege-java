/**
 * 
 */
package tree.ast;

import tree.IDDeclaration;

/**
 * An interface used to show that the current node declares multiple identifiers.
 * @author Flip van Spaendonck
 *
 */
public interface ICodeBlock {

	public IDDeclaration[] getBlock();
}
