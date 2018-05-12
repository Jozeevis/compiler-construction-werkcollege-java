/**
 * 
 */
package tree.ast;

import tree.IDDeclaration;

/**
 * A data structure used to keep track of what should be in the local memory.
 * 
 * @author Flip van Spaendonck
 */
public class IDDeclarationBlock {

	/**
	 * The block used to store the IDDeclarations previous to the current code
	 * block.
	 **/
	public final IDDeclarationBlock previousBlock;
	/**
	 * An array containing the IDDeclarations that are important for the current
	 * code block, the index at which an IDDeclaration is stored represents the
	 * offset -1 in the local memory. (Thus they should be incremented by 1, due to
	 * offset 0 being used for the address of the previous local memory.
	 **/
	public final IDDeclaration[] block;

	public IDDeclarationBlock(IDDeclarationBlock previousBlock, IDDeclaration[] block) {
		this.previousBlock = previousBlock;
		this.block = new IDDeclaration[previousBlock.block.length + block.length];
		int i = 0;
		while (i < previousBlock.block.length) {
			this.block[i] = previousBlock.block[i];
			i++;
		}
		while (i < previousBlock.block.length + block.length) {
			this.block[i] = block[i];
			i++;
		}
	}

	public IDDeclarationBlock(IDDeclarationBlock previousBlock, IDDeclaration newDeclaration) {
		this.previousBlock = previousBlock;
		this.block = new IDDeclaration[previousBlock.block.length + 1];
		int i = 0;
		while (i < previousBlock.block.length) {
			this.block[i] = previousBlock.block[i];
			i++;
		}
		this.block[i] = newDeclaration;
	}
}
