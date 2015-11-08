package pf;

// Generated from Hello.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HelloParser#r}.
	 * @param ctx the parse tree
	 */
	void enterR(HelloParser.RContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#r}.
	 * @param ctx the parse tree
	 */
	void exitR(HelloParser.RContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#enhanced_list_of_paths_or_all}.
	 * @param ctx the parse tree
	 */
	void enterEnhanced_list_of_paths_or_all(HelloParser.Enhanced_list_of_paths_or_allContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#enhanced_list_of_paths_or_all}.
	 * @param ctx the parse tree
	 */
	void exitEnhanced_list_of_paths_or_all(HelloParser.Enhanced_list_of_paths_or_allContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#enhanced_list_of_paths}.
	 * @param ctx the parse tree
	 */
	void enterEnhanced_list_of_paths(HelloParser.Enhanced_list_of_pathsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#enhanced_list_of_paths}.
	 * @param ctx the parse tree
	 */
	void exitEnhanced_list_of_paths(HelloParser.Enhanced_list_of_pathsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#enhanced_path}.
	 * @param ctx the parse tree
	 */
	void enterEnhanced_path(HelloParser.Enhanced_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#enhanced_path}.
	 * @param ctx the parse tree
	 */
	void exitEnhanced_path(HelloParser.Enhanced_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#outbound_enhanced_path}.
	 * @param ctx the parse tree
	 */
	void enterOutbound_enhanced_path(HelloParser.Outbound_enhanced_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#outbound_enhanced_path}.
	 * @param ctx the parse tree
	 */
	void exitOutbound_enhanced_path(HelloParser.Outbound_enhanced_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#inbound_enhanced_path}.
	 * @param ctx the parse tree
	 */
	void enterInbound_enhanced_path(HelloParser.Inbound_enhanced_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#inbound_enhanced_path}.
	 * @param ctx the parse tree
	 */
	void exitInbound_enhanced_path(HelloParser.Inbound_enhanced_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#enhanced_element}.
	 * @param ctx the parse tree
	 */
	void enterEnhanced_element(HelloParser.Enhanced_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#enhanced_element}.
	 * @param ctx the parse tree
	 */
	void exitEnhanced_element(HelloParser.Enhanced_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#attribute_list_or_all}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_list_or_all(HelloParser.Attribute_list_or_allContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#attribute_list_or_all}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_list_or_all(HelloParser.Attribute_list_or_allContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#attribute_list}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_list(HelloParser.Attribute_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#attribute_list}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_list(HelloParser.Attribute_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#list_of_path}.
	 * @param ctx the parse tree
	 */
	void enterList_of_path(HelloParser.List_of_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#list_of_path}.
	 * @param ctx the parse tree
	 */
	void exitList_of_path(HelloParser.List_of_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(HelloParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(HelloParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#outbound_path}.
	 * @param ctx the parse tree
	 */
	void enterOutbound_path(HelloParser.Outbound_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#outbound_path}.
	 * @param ctx the parse tree
	 */
	void exitOutbound_path(HelloParser.Outbound_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#inbound_path}.
	 * @param ctx the parse tree
	 */
	void enterInbound_path(HelloParser.Inbound_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#inbound_path}.
	 * @param ctx the parse tree
	 */
	void exitInbound_path(HelloParser.Inbound_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#element_or_alias}.
	 * @param ctx the parse tree
	 */
	void enterElement_or_alias(HelloParser.Element_or_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#element_or_alias}.
	 * @param ctx the parse tree
	 */
	void exitElement_or_alias(HelloParser.Element_or_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(HelloParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(HelloParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(HelloParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(HelloParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#res}.
	 * @param ctx the parse tree
	 */
	void enterRes(HelloParser.ResContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#res}.
	 * @param ctx the parse tree
	 */
	void exitRes(HelloParser.ResContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(HelloParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(HelloParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#attribute_representation}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_representation(HelloParser.Attribute_representationContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#attribute_representation}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_representation(HelloParser.Attribute_representationContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#temp_modifier}.
	 * @param ctx the parse tree
	 */
	void enterTemp_modifier(HelloParser.Temp_modifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#temp_modifier}.
	 * @param ctx the parse tree
	 */
	void exitTemp_modifier(HelloParser.Temp_modifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(HelloParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(HelloParser.IntervalContext ctx);
}