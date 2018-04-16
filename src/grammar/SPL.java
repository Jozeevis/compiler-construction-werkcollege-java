package grammar;


/**
 * A singleton class representing the SPL grammar's ExpressionTree.
 * @author Flip van Spaendonck
 */
public class SPL extends ExpressionTree {
	
	/** The singleton element of this class.*/
	public final static ExpressionTree INSTANCE = new SPL();
	
	private SPL() {
		super(new Node("SPL"));
		addNode(new Node("DeclPlus"));
		addNode(new Node("Decl"));
		addNode(new Node("VarDecl"));
		addNode(new StarNode("VarDecl", this));
		addNode(new Node("FunDecl"));
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
		addNode(new Node("FunCall"));
		addNode(new Node("ActArgs"));
		addNode(new Node("id"));
		
		addExpressionTo("~DeclPlus", "SPL");
		
		addExpressionTo("~Decl ~DeclPlus","DeclPlus");
		addExpressionTo("~Decl", "DeclPlus");
		
		addExpressionTo("~VarDecl","Decl");
		addExpressionTo("~FunDecl","Decl");
		
		// addExpressionTo("'var' ~id '=' ~Exp ';'","VarDecl"); Currently this will not be allowed in our grandma
		addExpressionTo("~Type ~id '=' ~Exp ';'", "VarInit","VarDecl");
		
		//addExpressionTo("~id '('')''{'~VarDeclStar ~StmtPlus '}'","FunDecl"); Currently this will not be allowed in our grandma
		//addExpressionTo("~id '('~FArgs ')''{'~VarDeclStar ~StmtPlus '}'","FunDecl"); Currently this will not be allowed in our grandma
		addExpressionTo("~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl");
		addExpressionTo("~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl");
		
		addExpressionTo("~Type","RetType");
		addExpressionTo("Void","RetType");
		
		addExpressionTo("~TypeStar '->' ~RetType","FunType");
		
		addExpressionTo(".TOK_PRIM_TYPE ", "BaseType", "Type");
		addExpressionTo("'('~Type ',' ~Type ')'", "TupleType", "Type");
		addExpressionTo("'['~Type ']'", "ListType", "Type");
		addExpressionTo("~id", "CustomType","Type");
		
		addExpressionTo("~id","FArgs");
		addExpressionTo("~id ','~FArgs","FArgs");
		
		addExpressionTo("'if''(' ~Exp ')''{'~StmtStar '}''else''{'~StmtStar '}'","IfElseStmt","Stmt");
		addExpressionTo("'if''(' ~Exp ')''{'~StmtStar '}'","IfElseStmt","Stmt");
		addExpressionTo("'while''('~Exp ')''{'~StmtStar '}'","WhileStmt","Stmt");
		addExpressionTo("~id ~Field '=' ~Exp ';'", "Assign","Stmt");
		addExpressionTo("~FunCall ';'", "FunCall" ,"Stmt");
		addExpressionTo("'return;'","Stmt");
		addExpressionTo("'return'~Exp ';'","Stmt");
		
		//TODO: Decide on what to do with the following 6 expressions, probably check for an expression token, so expression optimalization can be handled by the lexer
		addExpressionTo(".TOK_EXP ","Exp");
		addExpressionTo(".TOK_IDENTIFIER ","id");
		
		addExpressionTo("~id '('')'","FunCall");
		addExpressionTo("~id '('~ActArgs ')'","FunCall");
		
		addExpressionTo("~Exp","ActArgs");
		addExpressionTo("~Exp ','~ActArgs","ActArgs");	
	}
}
