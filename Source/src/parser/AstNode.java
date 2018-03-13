package parser;

import util.Visitor;

public abstract interface AstNode {
	public void accept(Visitor v);
}
