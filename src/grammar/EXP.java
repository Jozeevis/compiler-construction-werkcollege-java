/**
 * 
 */
package grammar;

/**
 * @author Flip van Spaendonck
 *
 */
public class EXP extends ExpressionTree {

	/** The singleton element of this class.*/
	public final static ExpressionTree INSTANCE = new EXP();
	
	private EXP() {
		super(new Node("Exp"));
		addNode(new Node("BoolExp2"));
		addNode(new Node("NumRng"));
		addNode(new Node("BoolExp1"));
		addNode(new Node("BoolExp0"));
		addNode(new Node("NumFld"));
		addNode(new Node("NumSng"));
		addNode(new Node("Type"));
		addNode(new Node("FunCall"));
		
		addExpressionTo("~BoolExp2", "BoolExp", "Exp");
		addExpressionTo("~NumRng", "NumExp","Exp");
		addExpressionTo("~Type '[' ']'", "SetExp", "Exp");
		addExpressionTo("'(' ~Exp ',' ~Exp ')'", "TupleExp","Exp");
		
		addExpressionTo(".TOK_PRIM_TYPE ", "BaseType", "Type");
		addExpressionTo("'('~Type ',' ~Type ')'", "TupleType", "Type");
		addExpressionTo("'['~Type ']'", "ListType", "Type");
		addExpressionTo("~id", "CustomType","Type");
		
		addExpressionTo("~BoolExp1 '&&' ~BoolExp2 ", "and", "BoolExp2");
		addExpressionTo("~BoolExp1 '||' ~BoolExp2 ", "or", "BoolExp2");
		addExpressionTo("~NumRng '==' ~NumRng ", "eq", "BoolExp2");
		addExpressionTo("~NumRng '!=' ~NumRng ", "neq", "BoolExp2");
		addExpressionTo("~NumRng '<' ~NumRng ", "smaller", "BoolExp2");
		addExpressionTo("~NumRng '>' ~NumRng ", "larger", "BoolExp2");
		addExpressionTo("~NumRng '<=' ~NumRng ", "smallerEq", "BoolExp2");
		addExpressionTo("~NumRng '>=' ~NumRng ", "largerEq", "BoolExp2");
		addExpressionTo("~BoolExp1 ", "BoolExp2");
		
		
		addExpressionTo("'!' ~BoolExp0 ", "negation", "BoolExp1");
		addExpressionTo("'!' ~BoolExp0 ", "BoolExp1");
		
		addExpressionTo(".TOK_BOOL ", "boolean", "BoolExp0");
		addExpressionTo(".TOK_IDENTIFIER ~Field", "variableBool", "BoolExp0");
		addExpressionTo(" ~FunCall", "funcallBool", "BoolExp0");
		addExpressionTo(" '(' ~BoolExp2 ')' ", "brackets", "BoolExp0");
		
		addExpressionTo(" ~NumFld '%' ~NumRng ", "modulo", "NumRng");
		addExpressionTo(" ~NumFld '/' ~NumRng ", "divide", "NumRng");
		addExpressionTo(" ~NumFld '*' ~NumRng ", "multiply", "NumRng");
		addExpressionTo(" ~NumFld", "NumRng");
		
		addExpressionTo(" ~NumSng '+' ~NumFld ", "plus", "NumFld");
		addExpressionTo(" ~NumSng '-' ~NumFld ", "minus", "NumFld");
		addExpressionTo(" ~NumSng ", "plus", "NumFld");
		
		addExpressionTo(" .TOK_INT ", "int", "NumSng");
		addExpressionTo(" .TOK_CHAR ", "char", "NumSng");
		addExpressionTo(" ~FunCall", "funcallNum", "NumSng");
		addExpressionTo(" .TOK_IDENTIFIER ~Field ", "variableNum", "NumSng");
		addExpressionTo(" '(' ~NumRng ')' ", "brackets", "NumSng");
		
		addExpressionTo(" .TOK_IDENTIFIER '('')'","FunCall");
		addExpressionTo(" .TOK_IDENTIFIER '('~ActArgs ')'","FunCall");
		addExpressionTo("~Exp","ActArgs");
		addExpressionTo("~Exp ','~ActArgs","ActArgs");	
		
		
		
		// TODO Auto-generated constructor stub
	}

}
