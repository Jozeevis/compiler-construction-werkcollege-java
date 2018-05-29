package tree.ast.types;

public class VoidType extends Type {
	
	public static final VoidType instance = new VoidType();
	
	private VoidType() {};

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof VoidType);
	}

}
