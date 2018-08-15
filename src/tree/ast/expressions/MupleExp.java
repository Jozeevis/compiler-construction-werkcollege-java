package tree.ast.expressions;

import java.util.List;

import processing.DeclarationException;
import processing.TypeException;
import tree.IDDeclarationBlock;
import tree.ast.LabelCounter;
import tree.ast.types.MupleType;
import tree.ast.types.Type;

/**
 * @author Flip van Spaendonck
 *
 */
public class MupleExp extends BaseExpr {

	public final BaseExpr[] exps;
	
	public MupleExp(BaseExpr[] exps) {
		this.exps = exps;
	}

	/* (non-Javadoc)
	 * @see tree.ast.expressions.BaseExpr#optimize()
	 */
	@Override
	public BaseExpr optimize() {
		for(int i=0; i < exps.length; i++) {
			exps[i]  = exps[i].optimize();
		}
		return this;
	}

	@Override
	public void addCodeToStack(List<String> stack, LabelCounter counter) {
		for(BaseExpr exp: exps) {
			exp.addCodeToStack(stack, counter);
		}
		stack.add("stmh "+exps.length + "\n");
	}

	@Override
	public Type checkTypes(IDDeclarationBlock domain) throws TypeException, DeclarationException {
		Type[] types = new Type[exps.length];
		for(int i=0; i < exps.length; i++) {
			types[i] = exps[i].checkTypes(domain);
		}
		return new MupleType(types);
	}

}
