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
		addNode(new Node("Mexp"));
		addNode(new Node("SetExp"));
		addNode(new Node("Concat"));
		addNode(new PlusNode("Concat", this));
		addNode(new Node("Field"));
		addNode(new Node("SetDef"));
		addNode(new Node("BoolExp2"));
		addNode(new Node("NumRng"));
		addNode(new Node("BoolExp1"));
		addNode(new Node("BoolExp0"));
		addNode(new Node("NumFld"));
		addNode(new Node("NumSng"));
		addNode(new Node("Type"));
		addNode(new Node("FunCall"));
		addNode(new Node("ActArgs"));
		addNode(new Node("Neg"));
		
		
		try {
			addExpressionTo("~BoolExp2", "BoolExp", "Exp");
			addExpressionTo("~NumRng", "NumExp","Exp");
			addExpressionTo("~SetExp", "SetExp", "Exp");
			addExpressionTo("'(' ~Mexp ')'", "MupleExp","Exp");
			addExpressionTo("'null'", "Null", "Exp");
			
			addExpressionTo("~Exp ","Mexp");
			addExpressionTo("~Exp ',' ~Mexp ","Mexp");
			
			addExpressionTo(".TOK_PRIM_TYPE ", "BaseType", "Type");
			addExpressionTo("'('~Type ',' ~Type ')'", "MupleType", "Type");
			addExpressionTo("'['~Type ']'", "ListType", "Type");
			addExpressionTo("~id", "CustomType","Type");
			
			addExpressionTo("'&' ~ConcatPlus ~SetDef", "SetConcat", "SetExp");
			addExpressionTo("~SetDef", "SetExp");
			
			addExpressionTo("~Exp ':'", "Concat");
			
			addExpressionTo("'[' ']'", "emptySet", "SetDef");
			addExpressionTo(".TOK_IDENTIFIER ~Field", "variable", "SetDef");
			
			addExpressionTo("~BoolExp1 '&&' ~BoolExp2 ", "and", "BoolExp2");
			addExpressionTo("~BoolExp1 '||' ~BoolExp2 ", "or", "BoolExp2");
			addExpressionTo("~NumRng '==' ~NumRng ", "eq", "BoolExp2");
			addExpressionTo("~NumRng '!=' ~NumRng ", "neq", "BoolExp2");
			addExpressionTo("~NumRng '<' ~NumRng ", "smaller", "BoolExp2");
			addExpressionTo("~NumRng '>' ~NumRng ", "larger", "BoolExp2");
			addExpressionTo("~NumRng '<=' ~NumRng ", "smallerEq", "BoolExp2");
			addExpressionTo("~NumRng '>=' ~NumRng ", "largerEq", "BoolExp2");
			addExpressionTo("~BoolExp1 ", "BoolExp2");
			
			addExpressionTo("'.hd'", "Field");
			addExpressionTo("'.tl'", "Field");
			addExpressionTo("'.' .TOK_IDENTIFIER ", "Field");
			addExpressionTo("'.' '[' .TOK_INT ']'", "Field");
			
			addExpressionTo("'!' ~BoolExp0 ", "negation", "BoolExp1");
			addExpressionTo("~BoolExp0 ", "BoolExp1");
			
			addExpressionTo(".TOK_BOOL ", "boolean", "BoolExp0");
			addExpressionTo("~CallUp ~Field","callup","NumSng");
			addExpressionTo(" '(' ~BoolExp2 ')' ", "brackets", "BoolExp0");
			addExpressionTo(" 'isEmpty' ~SetDef", "isempty", "BoolExp0");
			
			addExpressionTo(" ~NumFld '+' ~NumRng ", "plus", "NumRng");
			addExpressionTo(" ~NumFld '-' ~NumRng ", "minus", "NumRng");
			addExpressionTo(" ~NumFld ", "NumRng");
			
			addExpressionTo(" ~Neg'%' ~NumFld ", "modulo", "NumFld");
			addExpressionTo(" ~Neg '/' ~NumFld ", "divide", "NumFld");
			addExpressionTo(" ~Neg '*' ~NumFld ", "multiply", "NumFld");
			addExpressionTo(" ~Neg", "NumFld");
			
			addExpressionTo(" ~NumSng", "Neg");
			addExpressionTo("'-' ~NumSng", "negative", "Neg");
			
			addExpressionTo(" .TOK_INT ", "int", "NumSng");
			addExpressionTo(" .TOK_CHAR ", "char", "NumSng");
			addExpressionTo("~CallUp ~Field","callup","NumSng");
			addExpressionTo(" '(' ~NumRng ')' ", "brackets", "NumSng");
			

			addExpressionTo(" ~FunCall", "funcall", "CallUp");
			addExpressionTo("'this'","this","CallUp");
			addExpressionTo(" .TOK_IDENTIFIER  ", "variable", "CallUp");
			addExpressionTo("'new' ~id '(' ')'", "Init","CallUp");
			addExpressionTo("'new' ~id '('~ActArgs ')'", "Init","CallUp");
			
			addExpressionTo(" .TOK_IDENTIFIER '('')'","FunCall");
			addExpressionTo(" .TOK_IDENTIFIER '('~ActArgs ')'","FunCall");
			addExpressionTo("~Exp","ActArgs");
			addExpressionTo("~Exp ','~ActArgs","ActArgs");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
