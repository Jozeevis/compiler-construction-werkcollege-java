package util;

import parser.AstExprBool;
import parser.AstExprBinOp;
import parser.AstExprInteger;

public interface Visitor {
	public void visit(AstExprInteger i);

	public void visit(AstExprBinOp e);

	public void visit(AstExprBool astBool);
}