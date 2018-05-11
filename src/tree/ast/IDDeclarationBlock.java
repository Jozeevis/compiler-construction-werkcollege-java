/**
 * 
 */
package tree.ast;

import tree.IDDeclaration;

/**
 * @author Flip van Spaendonck
 *
 */
public class IDDeclarationBlock {

	public final IDDeclarationBlock previousBlock;
	
	public final IDDeclaration[] block;
	
	public IDDeclarationBlock(IDDeclarationBlock previousBlock, IDDeclaration[] block) {
		this.previousBlock = previousBlock;
		this.block = new IDDeclaration[previousBlock.block.length + block.length];
		int i=0;
		while(i<previousBlock.block.length) {
			this.block[i] = previousBlock.block[i];
			i++;
		}
		while(i<previousBlock.block.length + block.length) {
			this.block[i] = block[i];
			i++;
		}
	}

	public IDDeclarationBlock(IDDeclarationBlock previousBlock, IDDeclaration newDeclaration) {
		this.previousBlock = previousBlock;
		this.block = new IDDeclaration[previousBlock.block.length + 1];
		int i=0;
		while(i<previousBlock.block.length) {
			this.block[i] = previousBlock.block[i];
			i++;
		}
		this.block[i] = newDeclaration;
	}
}
