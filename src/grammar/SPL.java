package grammar;

/**
 * A singleton class representing the SPL grammar's ExpressionTree.
 * 
 * @author Flip van Spaendonck
 */
public class SPL extends ExpressionTree {

	/** The singleton element of this class. */
	public final static ExpressionTree INSTANCE = new SPL();

	private SPL() {
		super(new Node("SPL"));
		System.out.println("SPL is being constructed");
		addNode(new Node("Decl"));
		addNode(new PlusNode("Decl", this));
		// addNode(new Node("DeclMini"));
		// addNode(new StarNode("DeclMini", this));
		addNode(new Node("StructDecl"));
		addNode(new Node("Constructor"));
		addNode(new PlusNode("Constructor", this));
		addNode(new Node("CArgs"));
		addNode(new Node("VarDecl"));
		addNode(new StarNode("VarDecl", this));
		addNode(new Node("FunDecl"));
		addNode(new StarNode("FunDecl", this));
		addNode(new Node("RetType"));
		addNode(new Node("FunType"));
		addNode(new Node("Type"));
		addNode(new StarNode("Type", this));
		addNode(new Node("FArgs"));
		addNode(new Node("Stmt"));
		addNode(new PlusNode("Stmt", this));
		addNode(new StarNode("Stmt", this));
		addNode(new Node("Exp"));
		addNode(new Node("Field"));
		addNode(new StarNode("Field", this));
		addNode(new Node("FunCall"));
		addNode(new Node("ActArgs"));
		addNode(new Node("id"));
		addNode(new Node("Mype"));
		
		System.out.println("All nodes have been added to SPL.");
		try {
			addExpressionTo("~DeclPlus", "SPL");
	
			addExpressionTo("~VarDecl", "Decl");
			addExpressionTo("~FunDecl", "Decl");
			addExpressionTo("~StructDecl", "Decl");
	
			addExpressionTo("~id '{'~VarDeclStar ~Constructor ~FunDeclStar'}'", "StructDecl");
	
			addExpressionTo("~id '(' ')' '{'~VarDeclStar ~StmtPlus '}' ", "Constructor");
			addExpressionTo("~id '(' ~CArgs ')' '{'~VarDeclStar ~StmtPlus '}'", "Constructor");
			
			addExpressionTo("~Type ~id","CArgs");
			addExpressionTo("~Type ~id ',' ~CArgs","CArgs");
			
	
			// addExpressionTo("'var' ~id '=' ~Exp ';'","VarDecl"); Currently this will not
			// be allowed in our grandma
			addExpressionTo("~Type ~id '=' ~Exp ';'", "VarInit", "VarDecl");
			addExpressionTo("~Type ~id ';'", "VarInit", "VarDecl");
	
			// addExpressionTo("~id '('')''{'~VarDeclStar ~StmtPlus '}'","FunDecl");
			// Currently this will not be allowed in our grandma
			// addExpressionTo("~id '('~FArgs ')''{'~VarDeclStar ~StmtPlus '}'","FunDecl");
			// Currently this will not be allowed in our grandma
			addExpressionTo("~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'", "FunDecl", "FunDecl");
			addExpressionTo("~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'", "FunDecl", "FunDecl");
	
			addExpressionTo("~Type", "RetType");
			addExpressionTo("'Void'", "RetType");
	
			addExpressionTo("~TypeStar '->' ~RetType", "FunType");
	
			addExpressionTo(".TOK_PRIM_TYPE ", "BaseType", "Type");
			addExpressionTo("'(' ~Mype ')'", "MupleType", "Type");
			addExpressionTo("'['~Type ']'", "ListType", "Type");
			addExpressionTo("~id", "CustomType", "Type");
			
			addExpressionTo("~Type ","Mype");
			addExpressionTo("~Type ',' ~Mype ","Mype");
			
			addExpressionTo("~id", "FArgs");
			addExpressionTo("~id ','~FArgs", "FArgs");
	
			addExpressionTo("'.hd'", "Field");
			addExpressionTo("'.tl'", "Field");
			addExpressionTo("'.' .TOK_IDENTIFIER ", "Field");
			addExpressionTo("'.' '[' .TOK_INT ']'", "Field");
			
	
			addExpressionTo("'if''(' ~Exp ')''{'~StmtStar '}''else''{'~StmtStar '}'", "IfElseStmt", "Stmt");
			addExpressionTo("'if''(' ~Exp ')''{'~StmtStar '}'", "IfElseStmt", "Stmt");
			addExpressionTo("'while''('~Exp ')''{'~StmtStar '}'", "WhileStmt", "Stmt");
			addExpressionTo("~id ~FieldStar '=' ~Exp ';'", "Assign", "Stmt");
			addExpressionTo("~FunCall ';'", "FunCall", "Stmt");
			addExpressionTo("'print' ~Exp ';'", "Print", "Stmt");
			addExpressionTo("'return' ';'", "Return", "Stmt");
			addExpressionTo("'return'~Exp ';'", "Return", "Stmt");
	
			addExpressionTo(".TOK_EXP ", "Exp");
			addExpressionTo(".TOK_IDENTIFIER ", "id");
	
			addExpressionTo("~id '('')'", "FunCall");
			addExpressionTo("~id '('~ActArgs ')'", "FunCall");
	
			addExpressionTo("~Exp", "ActArgs");
			addExpressionTo("~Exp ','~ActArgs", "ActArgs");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SPL has been succesfully constructed");
	}
}
