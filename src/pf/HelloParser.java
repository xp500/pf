package pf;

// Generated from Hello.g4 by ANTLR 4.5.1
import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HelloParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, WS=16, BINARY_BOOLEAN_OPERATOR=17, 
		COMP=18, OPER=19, MOMENT=20, ID=21, CTE=22;
	public static final int
		RULE_r = 0, RULE_enhanced_list_of_paths_or_all = 1, RULE_enhanced_list_of_paths = 2, 
		RULE_enhanced_path = 3, RULE_outbound_enhanced_path = 4, RULE_inbound_enhanced_path = 5, 
		RULE_enhanced_element = 6, RULE_attribute_list_or_all = 7, RULE_attribute_list = 8, 
		RULE_list_of_path = 9, RULE_path = 10, RULE_outbound_path = 11, RULE_inbound_path = 12, 
		RULE_element_or_alias = 13, RULE_condition = 14, RULE_expr = 15, RULE_res = 16, 
		RULE_arg = 17, RULE_attribute_representation = 18, RULE_temp_modifier = 19, 
		RULE_interval = 20;
	public static final String[] ruleNames = {
		"r", "enhanced_list_of_paths_or_all", "enhanced_list_of_paths", "enhanced_path", 
		"outbound_enhanced_path", "inbound_enhanced_path", "enhanced_element", 
		"attribute_list_or_all", "attribute_list", "list_of_path", "path", "outbound_path", 
		"inbound_path", "element_or_alias", "condition", "expr", "res", "arg", 
		"attribute_representation", "temp_modifier", "interval"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'select'", "'from'", "'where'", "'*'", "','", "'-'", "'->'", "'<-'", 
		"'('", "')'", "'as'", "'not'", "'.'", "'SNAPSHOT'", "'IN'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "WS", "BINARY_BOOLEAN_OPERATOR", "COMP", "OPER", 
		"MOMENT", "ID", "CTE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Hello.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HelloParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RContext extends ParserRuleContext {
		public Enhanced_list_of_paths_or_allContext enhanced_list_of_paths_or_all() {
			return getRuleContext(Enhanced_list_of_paths_or_allContext.class,0);
		}
		public List_of_pathContext list_of_path() {
			return getRuleContext(List_of_pathContext.class,0);
		}
		public TerminalNode EOF() { return getToken(HelloParser.EOF, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public Temp_modifierContext temp_modifier() {
			return getRuleContext(Temp_modifierContext.class,0);
		}
		public RContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitR(this);
		}
	}

	public final RContext r() throws RecognitionException {
		RContext _localctx = new RContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_r);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__0);
			setState(43);
			enhanced_list_of_paths_or_all();
			setState(44);
			match(T__1);
			setState(45);
			list_of_path();
			setState(48);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(46);
				match(T__2);
				setState(47);
				condition(0);
				}
			}

			setState(51);
			_la = _input.LA(1);
			if (_la==T__13 || _la==T__14) {
				{
				setState(50);
				temp_modifier();
				}
			}

			setState(53);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enhanced_list_of_paths_or_allContext extends ParserRuleContext {
		public Enhanced_list_of_pathsContext enhanced_list_of_paths() {
			return getRuleContext(Enhanced_list_of_pathsContext.class,0);
		}
		public Enhanced_list_of_paths_or_allContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhanced_list_of_paths_or_all; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterEnhanced_list_of_paths_or_all(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitEnhanced_list_of_paths_or_all(this);
		}
	}

	public final Enhanced_list_of_paths_or_allContext enhanced_list_of_paths_or_all() throws RecognitionException {
		Enhanced_list_of_paths_or_allContext _localctx = new Enhanced_list_of_paths_or_allContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_enhanced_list_of_paths_or_all);
		try {
			setState(57);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				enhanced_list_of_paths();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enhanced_list_of_pathsContext extends ParserRuleContext {
		public Enhanced_pathContext enhanced_path() {
			return getRuleContext(Enhanced_pathContext.class,0);
		}
		public Enhanced_list_of_pathsContext enhanced_list_of_paths() {
			return getRuleContext(Enhanced_list_of_pathsContext.class,0);
		}
		public Enhanced_list_of_pathsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhanced_list_of_paths; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterEnhanced_list_of_paths(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitEnhanced_list_of_paths(this);
		}
	}

	public final Enhanced_list_of_pathsContext enhanced_list_of_paths() throws RecognitionException {
		Enhanced_list_of_pathsContext _localctx = new Enhanced_list_of_pathsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_enhanced_list_of_paths);
		try {
			setState(64);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				enhanced_path();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(60);
				enhanced_path();
				setState(61);
				match(T__4);
				setState(62);
				enhanced_list_of_paths();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enhanced_pathContext extends ParserRuleContext {
		public Enhanced_elementContext enhanced_element() {
			return getRuleContext(Enhanced_elementContext.class,0);
		}
		public Outbound_enhanced_pathContext outbound_enhanced_path() {
			return getRuleContext(Outbound_enhanced_pathContext.class,0);
		}
		public Inbound_enhanced_pathContext inbound_enhanced_path() {
			return getRuleContext(Inbound_enhanced_pathContext.class,0);
		}
		public Enhanced_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhanced_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterEnhanced_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitEnhanced_path(this);
		}
	}

	public final Enhanced_pathContext enhanced_path() throws RecognitionException {
		Enhanced_pathContext _localctx = new Enhanced_pathContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_enhanced_path);
		try {
			setState(69);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				enhanced_element();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				outbound_enhanced_path();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				inbound_enhanced_path();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Outbound_enhanced_pathContext extends ParserRuleContext {
		public List<Enhanced_elementContext> enhanced_element() {
			return getRuleContexts(Enhanced_elementContext.class);
		}
		public Enhanced_elementContext enhanced_element(int i) {
			return getRuleContext(Enhanced_elementContext.class,i);
		}
		public Enhanced_pathContext enhanced_path() {
			return getRuleContext(Enhanced_pathContext.class,0);
		}
		public Outbound_enhanced_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outbound_enhanced_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterOutbound_enhanced_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitOutbound_enhanced_path(this);
		}
	}

	public final Outbound_enhanced_pathContext outbound_enhanced_path() throws RecognitionException {
		Outbound_enhanced_pathContext _localctx = new Outbound_enhanced_pathContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_outbound_enhanced_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			enhanced_element();
			setState(72);
			match(T__5);
			setState(73);
			enhanced_element();
			setState(74);
			match(T__6);
			setState(75);
			enhanced_path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Inbound_enhanced_pathContext extends ParserRuleContext {
		public List<Enhanced_elementContext> enhanced_element() {
			return getRuleContexts(Enhanced_elementContext.class);
		}
		public Enhanced_elementContext enhanced_element(int i) {
			return getRuleContext(Enhanced_elementContext.class,i);
		}
		public Enhanced_pathContext enhanced_path() {
			return getRuleContext(Enhanced_pathContext.class,0);
		}
		public Inbound_enhanced_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inbound_enhanced_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterInbound_enhanced_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitInbound_enhanced_path(this);
		}
	}

	public final Inbound_enhanced_pathContext inbound_enhanced_path() throws RecognitionException {
		Inbound_enhanced_pathContext _localctx = new Inbound_enhanced_pathContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_inbound_enhanced_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			enhanced_element();
			setState(78);
			match(T__7);
			setState(79);
			enhanced_element();
			setState(80);
			match(T__5);
			setState(81);
			enhanced_path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enhanced_elementContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HelloParser.ID, 0); }
		public Attribute_list_or_allContext attribute_list_or_all() {
			return getRuleContext(Attribute_list_or_allContext.class,0);
		}
		public Enhanced_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhanced_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterEnhanced_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitEnhanced_element(this);
		}
	}

	public final Enhanced_elementContext enhanced_element() throws RecognitionException {
		Enhanced_elementContext _localctx = new Enhanced_elementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_enhanced_element);
		try {
			setState(89);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				match(ID);
				setState(84);
				match(T__8);
				setState(85);
				attribute_list_or_all();
				setState(86);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(88);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attribute_list_or_allContext extends ParserRuleContext {
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Attribute_list_or_allContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_list_or_all; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterAttribute_list_or_all(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitAttribute_list_or_all(this);
		}
	}

	public final Attribute_list_or_allContext attribute_list_or_all() throws RecognitionException {
		Attribute_list_or_allContext _localctx = new Attribute_list_or_allContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_attribute_list_or_all);
		try {
			setState(93);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				attribute_list();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attribute_listContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HelloParser.ID, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Attribute_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterAttribute_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitAttribute_list(this);
		}
	}

	public final Attribute_listContext attribute_list() throws RecognitionException {
		Attribute_listContext _localctx = new Attribute_listContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_attribute_list);
		try {
			setState(99);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(95);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(96);
				match(ID);
				setState(97);
				match(T__4);
				setState(98);
				attribute_list();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class List_of_pathContext extends ParserRuleContext {
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public List_of_pathContext list_of_path() {
			return getRuleContext(List_of_pathContext.class,0);
		}
		public List_of_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_of_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterList_of_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitList_of_path(this);
		}
	}

	public final List_of_pathContext list_of_path() throws RecognitionException {
		List_of_pathContext _localctx = new List_of_pathContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_list_of_path);
		try {
			setState(106);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				path();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				path();
				setState(103);
				match(T__4);
				setState(104);
				list_of_path();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public Element_or_aliasContext element_or_alias() {
			return getRuleContext(Element_or_aliasContext.class,0);
		}
		public Outbound_pathContext outbound_path() {
			return getRuleContext(Outbound_pathContext.class,0);
		}
		public Inbound_pathContext inbound_path() {
			return getRuleContext(Inbound_pathContext.class,0);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitPath(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_path);
		try {
			setState(111);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(108);
				element_or_alias();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				outbound_path();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				inbound_path();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Outbound_pathContext extends ParserRuleContext {
		public List<Element_or_aliasContext> element_or_alias() {
			return getRuleContexts(Element_or_aliasContext.class);
		}
		public Element_or_aliasContext element_or_alias(int i) {
			return getRuleContext(Element_or_aliasContext.class,i);
		}
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public Outbound_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outbound_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterOutbound_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitOutbound_path(this);
		}
	}

	public final Outbound_pathContext outbound_path() throws RecognitionException {
		Outbound_pathContext _localctx = new Outbound_pathContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_outbound_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			element_or_alias();
			setState(114);
			match(T__5);
			setState(115);
			element_or_alias();
			setState(116);
			match(T__6);
			setState(117);
			path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Inbound_pathContext extends ParserRuleContext {
		public List<Element_or_aliasContext> element_or_alias() {
			return getRuleContexts(Element_or_aliasContext.class);
		}
		public Element_or_aliasContext element_or_alias(int i) {
			return getRuleContext(Element_or_aliasContext.class,i);
		}
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public Inbound_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inbound_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterInbound_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitInbound_path(this);
		}
	}

	public final Inbound_pathContext inbound_path() throws RecognitionException {
		Inbound_pathContext _localctx = new Inbound_pathContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_inbound_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			element_or_alias();
			setState(120);
			match(T__7);
			setState(121);
			element_or_alias();
			setState(122);
			match(T__5);
			setState(123);
			path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_or_aliasContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(HelloParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(HelloParser.ID, i);
		}
		public Element_or_aliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_or_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterElement_or_alias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitElement_or_alias(this);
		}
	}

	public final Element_or_aliasContext element_or_alias() throws RecognitionException {
		Element_or_aliasContext _localctx = new Element_or_aliasContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_element_or_alias);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(ID);
			setState(128);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(126);
				match(T__10);
				setState(127);
				match(ID);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode BINARY_BOOLEAN_OPERATOR() { return getToken(HelloParser.BINARY_BOOLEAN_OPERATOR, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		return condition(0);
	}

	private ConditionContext condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
		ConditionContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_condition, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			switch (_input.LA(1)) {
			case T__11:
				{
				setState(131);
				match(T__11);
				setState(132);
				condition(1);
				}
				break;
			case ID:
			case CTE:
				{
				setState(133);
				expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(141);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_condition);
					setState(136);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(137);
					match(BINARY_BOOLEAN_OPERATOR);
					setState(138);
					condition(3);
					}
					} 
				}
				setState(143);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<ResContext> res() {
			return getRuleContexts(ResContext.class);
		}
		public ResContext res(int i) {
			return getRuleContext(ResContext.class,i);
		}
		public TerminalNode COMP() { return getToken(HelloParser.COMP, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			res();
			setState(145);
			match(COMP);
			setState(146);
			res();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResContext extends ParserRuleContext {
		public List<ArgContext> arg() {
			return getRuleContexts(ArgContext.class);
		}
		public ArgContext arg(int i) {
			return getRuleContext(ArgContext.class,i);
		}
		public TerminalNode OPER() { return getToken(HelloParser.OPER, 0); }
		public ResContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_res; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterRes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitRes(this);
		}
	}

	public final ResContext res() throws RecognitionException {
		ResContext _localctx = new ResContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_res);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			arg();
			setState(151);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(149);
				match(OPER);
				setState(150);
				arg();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgContext extends ParserRuleContext {
		public TerminalNode CTE() { return getToken(HelloParser.CTE, 0); }
		public Attribute_representationContext attribute_representation() {
			return getRuleContext(Attribute_representationContext.class,0);
		}
		public ArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitArg(this);
		}
	}

	public final ArgContext arg() throws RecognitionException {
		ArgContext _localctx = new ArgContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_arg);
		try {
			setState(155);
			switch (_input.LA(1)) {
			case CTE:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				match(CTE);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				attribute_representation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attribute_representationContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(HelloParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(HelloParser.ID, i);
		}
		public Attribute_representationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_representation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterAttribute_representation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitAttribute_representation(this);
		}
	}

	public final Attribute_representationContext attribute_representation() throws RecognitionException {
		Attribute_representationContext _localctx = new Attribute_representationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_attribute_representation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(ID);
			setState(158);
			match(T__12);
			setState(159);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Temp_modifierContext extends ParserRuleContext {
		public TerminalNode MOMENT() { return getToken(HelloParser.MOMENT, 0); }
		public IntervalContext interval() {
			return getRuleContext(IntervalContext.class,0);
		}
		public Temp_modifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_temp_modifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterTemp_modifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitTemp_modifier(this);
		}
	}

	public final Temp_modifierContext temp_modifier() throws RecognitionException {
		Temp_modifierContext _localctx = new Temp_modifierContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_temp_modifier);
		try {
			setState(165);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				match(T__13);
				setState(162);
				match(MOMENT);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				match(T__14);
				setState(164);
				interval();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntervalContext extends ParserRuleContext {
		public List<TerminalNode> MOMENT() { return getTokens(HelloParser.MOMENT); }
		public TerminalNode MOMENT(int i) {
			return getToken(HelloParser.MOMENT, i);
		}
		public IntervalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).enterInterval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HelloListener ) ((HelloListener)listener).exitInterval(this);
		}
	}

	public final IntervalContext interval() throws RecognitionException {
		IntervalContext _localctx = new IntervalContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_interval);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			match(MOMENT);
			setState(168);
			match(T__5);
			setState(169);
			match(MOMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 14:
			return condition_sempred((ConditionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\30\u00ae\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\2\3\2\3\2\5\2\63"+
		"\n\2\3\2\5\2\66\n\2\3\2\3\2\3\3\3\3\5\3<\n\3\3\4\3\4\3\4\3\4\3\4\5\4C"+
		"\n\4\3\5\3\5\3\5\5\5H\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b\\\n\b\3\t\3\t\5\t`\n\t\3\n\3\n\3\n\3"+
		"\n\5\nf\n\n\3\13\3\13\3\13\3\13\3\13\5\13m\n\13\3\f\3\f\3\f\5\fr\n\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\5"+
		"\17\u0083\n\17\3\20\3\20\3\20\3\20\5\20\u0089\n\20\3\20\3\20\3\20\7\20"+
		"\u008e\n\20\f\20\16\20\u0091\13\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\5\22\u009a\n\22\3\23\3\23\5\23\u009e\n\23\3\24\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\25\5\25\u00a8\n\25\3\26\3\26\3\26\3\26\3\26\2\3\36\27\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*\2\2\u00aa\2,\3\2\2\2\4;\3\2\2"+
		"\2\6B\3\2\2\2\bG\3\2\2\2\nI\3\2\2\2\fO\3\2\2\2\16[\3\2\2\2\20_\3\2\2\2"+
		"\22e\3\2\2\2\24l\3\2\2\2\26q\3\2\2\2\30s\3\2\2\2\32y\3\2\2\2\34\177\3"+
		"\2\2\2\36\u0088\3\2\2\2 \u0092\3\2\2\2\"\u0096\3\2\2\2$\u009d\3\2\2\2"+
		"&\u009f\3\2\2\2(\u00a7\3\2\2\2*\u00a9\3\2\2\2,-\7\3\2\2-.\5\4\3\2./\7"+
		"\4\2\2/\62\5\24\13\2\60\61\7\5\2\2\61\63\5\36\20\2\62\60\3\2\2\2\62\63"+
		"\3\2\2\2\63\65\3\2\2\2\64\66\5(\25\2\65\64\3\2\2\2\65\66\3\2\2\2\66\67"+
		"\3\2\2\2\678\7\2\2\38\3\3\2\2\29<\5\6\4\2:<\7\6\2\2;9\3\2\2\2;:\3\2\2"+
		"\2<\5\3\2\2\2=C\5\b\5\2>?\5\b\5\2?@\7\7\2\2@A\5\6\4\2AC\3\2\2\2B=\3\2"+
		"\2\2B>\3\2\2\2C\7\3\2\2\2DH\5\16\b\2EH\5\n\6\2FH\5\f\7\2GD\3\2\2\2GE\3"+
		"\2\2\2GF\3\2\2\2H\t\3\2\2\2IJ\5\16\b\2JK\7\b\2\2KL\5\16\b\2LM\7\t\2\2"+
		"MN\5\b\5\2N\13\3\2\2\2OP\5\16\b\2PQ\7\n\2\2QR\5\16\b\2RS\7\b\2\2ST\5\b"+
		"\5\2T\r\3\2\2\2UV\7\27\2\2VW\7\13\2\2WX\5\20\t\2XY\7\f\2\2Y\\\3\2\2\2"+
		"Z\\\7\27\2\2[U\3\2\2\2[Z\3\2\2\2\\\17\3\2\2\2]`\5\22\n\2^`\7\6\2\2_]\3"+
		"\2\2\2_^\3\2\2\2`\21\3\2\2\2af\7\27\2\2bc\7\27\2\2cd\7\7\2\2df\5\22\n"+
		"\2ea\3\2\2\2eb\3\2\2\2f\23\3\2\2\2gm\5\26\f\2hi\5\26\f\2ij\7\7\2\2jk\5"+
		"\24\13\2km\3\2\2\2lg\3\2\2\2lh\3\2\2\2m\25\3\2\2\2nr\5\34\17\2or\5\30"+
		"\r\2pr\5\32\16\2qn\3\2\2\2qo\3\2\2\2qp\3\2\2\2r\27\3\2\2\2st\5\34\17\2"+
		"tu\7\b\2\2uv\5\34\17\2vw\7\t\2\2wx\5\26\f\2x\31\3\2\2\2yz\5\34\17\2z{"+
		"\7\n\2\2{|\5\34\17\2|}\7\b\2\2}~\5\26\f\2~\33\3\2\2\2\177\u0082\7\27\2"+
		"\2\u0080\u0081\7\r\2\2\u0081\u0083\7\27\2\2\u0082\u0080\3\2\2\2\u0082"+
		"\u0083\3\2\2\2\u0083\35\3\2\2\2\u0084\u0085\b\20\1\2\u0085\u0086\7\16"+
		"\2\2\u0086\u0089\5\36\20\3\u0087\u0089\5 \21\2\u0088\u0084\3\2\2\2\u0088"+
		"\u0087\3\2\2\2\u0089\u008f\3\2\2\2\u008a\u008b\f\4\2\2\u008b\u008c\7\23"+
		"\2\2\u008c\u008e\5\36\20\5\u008d\u008a\3\2\2\2\u008e\u0091\3\2\2\2\u008f"+
		"\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\37\3\2\2\2\u0091\u008f\3\2\2"+
		"\2\u0092\u0093\5\"\22\2\u0093\u0094\7\24\2\2\u0094\u0095\5\"\22\2\u0095"+
		"!\3\2\2\2\u0096\u0099\5$\23\2\u0097\u0098\7\25\2\2\u0098\u009a\5$\23\2"+
		"\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a#\3\2\2\2\u009b\u009e\7"+
		"\30\2\2\u009c\u009e\5&\24\2\u009d\u009b\3\2\2\2\u009d\u009c\3\2\2\2\u009e"+
		"%\3\2\2\2\u009f\u00a0\7\27\2\2\u00a0\u00a1\7\17\2\2\u00a1\u00a2\7\27\2"+
		"\2\u00a2\'\3\2\2\2\u00a3\u00a4\7\20\2\2\u00a4\u00a8\7\26\2\2\u00a5\u00a6"+
		"\7\21\2\2\u00a6\u00a8\5*\26\2\u00a7\u00a3\3\2\2\2\u00a7\u00a5\3\2\2\2"+
		"\u00a8)\3\2\2\2\u00a9\u00aa\7\26\2\2\u00aa\u00ab\7\b\2\2\u00ab\u00ac\7"+
		"\26\2\2\u00ac+\3\2\2\2\22\62\65;BG[_elq\u0082\u0088\u008f\u0099\u009d"+
		"\u00a7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}