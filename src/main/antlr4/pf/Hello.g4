// Define a grammar called Hello
grammar Hello;


r  : 'select' enhanced_list_of_paths_or_all 'from' list_of_path ('where' condition)? (temp_modifier)? EOF;
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines


// Select
enhanced_list_of_paths_or_all : enhanced_list_of_paths | '*';
enhanced_list_of_paths: enhanced_path | enhanced_path ',' enhanced_list_of_paths;
enhanced_path 
	: enhanced_element
	| outbound_enhanced_path
	| inbound_enhanced_path;
outbound_enhanced_path : enhanced_element '-' enhanced_element '->' enhanced_path;
inbound_enhanced_path : enhanced_element '<-' enhanced_element '-' enhanced_path;
enhanced_element : ID '(' attribute_list_or_all ')' | ID;
attribute_list_or_all : attribute_list | '*';
attribute_list : ID | ID ',' attribute_list;

// From
list_of_path : path | path ',' list_of_path;
path
	: element_or_alias
	| outbound_path
	| inbound_path;
outbound_path : element_or_alias '-' element_or_alias '->' path;
inbound_path : element_or_alias '<-' element_or_alias '-' path;

element_or_alias : ID ('as' ID)?;

// Where
condition
	: expr
	| condition BINARY_BOOLEAN_OPERATOR condition
	| 'not' condition;
BINARY_BOOLEAN_OPERATOR : 'AND' | 'OR';
expr : res COMP res;
COMP
	: '='
	| '<>'
	| '>'
	| '<'
	| '<='
	| '>=';
res : arg (OPER arg)?;
arg : cte | attribute_representation;
OPER : '+' | '-' | '*' | '/';
attribute_representation : ID '.' ID; // TODO: Va todo junto

temp_modifier : 'SNAPSHOT' moment | 'IN' interval;

NAT: [0-9]+;
moment : NAT ('-' NAT ('-' NAT ('-' NAT (':' NAT (':' NAT)?)?)?)?)?;
interval : moment '-' moment;

ID : [a-zA-Z][a-zA-Z0-9]*;
STR: '\''~'\''*'\'';
NUMBER : '-'?[0-9]+('.'[0-9]+)?;
number : NAT | NUMBER;
cte : STR | number;