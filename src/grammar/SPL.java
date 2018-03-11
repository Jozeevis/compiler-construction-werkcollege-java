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
		addNode(new Node("BasicType"));
		addNode(new Node("FArgs"));
		addNode(new Node("Stmt"));
		addNode(new PlusNode("Stmt", this));
		addNode(new StarNode("Stmt", this));
		addNode(new Node("Exp"));
		addNode(new Node("Field"));
		addNode(new Node("FunCall"));
		addNode(new Node("ActArgs"));
		addNode(new Node("Op2"));
		addNode(new Node("Op1"));
		addNode(new Node("int"));
		addNode(new Node("id"));
		
		addExpressionTo("~DeclPlus", "SPL");
		
		addExpressionTo("~Decl ~DeclPlus","DeclPlus");
		addExpressionTo("~Decl", "DeclPlus");
		
		addExpressionTo("~VarDecl","Decl");
		addExpressionTo("~FunDecl","Decl");
		
		addExpressionTo("'var' ~id '=' ~Exp ';'","VarDecl");
		addExpressionTo("~Type ~id '=' ~Exp ';'","VarDecl");
		
		addExpressionTo("~id '('')''{'~VarDeclStar ~StmtPlus '}'","FunDecl");
		addExpressionTo("~id '('~FArgs ')''{'~VarDeclStar ~StmtPlus '}'","FunDecl");
		addExpressionTo("~id '('')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl");
		addExpressionTo("~id '('~FArgs ')''::'~FunType '{'~VarDeclStar ~StmtPlus '}'","FunDecl");
		
		addExpressionTo("~Type","RetType");
		addExpressionTo("null","RetType");
		
		addExpressionTo("~TypeStar '->' ~RetType","FunType");
		
		addExpressionTo("~BasicType", "Type");
		addExpressionTo("'('~Type ',' ~Type ')'", "Type");
		addExpressionTo("'['~Type ']'", "Type");
		addExpressionTo("~id", "Type");
		
		addExpressionTo("'Int'","BasicType");
		addExpressionTo("'Bool'","BasicType");
		addExpressionTo("'Char'","BasicType");
		
		addExpressionTo("~id","FArgs");
		addExpressionTo("~id ','~FArgs","FArgs");
		
		addExpressionTo("'if''(' ~Exp ')''{'~StmtStar '}''else''{'~StmtStar '}'","Stmt");
		addExpressionTo("'if''(' ~Exp ')''{'~StmtStar '}'","Stmt");
		addExpressionTo("'while''('~Exp ')''{'~StmtStar '}'","Stmt");
		addExpressionTo("~id ~Field '=' ~Exp ';'","Stmt");
		addExpressionTo("~FunCall ';'","Stmt");
		addExpressionTo("'return;'","Stmt");
		addExpressionTo("'return'~Exp ';'","Stmt");
		
		//TODO: Decide on what to do with the following 6 expressions, probably check for an expression token, so expression optimalization can be handled by the lexer
		addExpressionTo("","Exp");
		addExpressionTo("","Field");
		addExpressionTo("","Op2");
		addExpressionTo("","Op1");
		addExpressionTo("","int");
		addExpressionTo("","id");
		
		
		addExpressionTo("~id '('')'","FunCall");
		addExpressionTo("~id '('~ActArgs ')'","FunCall");
		
		addExpressionTo("~Exp","ActArgs");
		addExpressionTo("~Exp ','~ActArgs","ActArgs");
		
		
		
		
		
		
	}
}
