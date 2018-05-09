package tree.ast.types;

public class VoidType extends Type {
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof VoidType);
	}

}
