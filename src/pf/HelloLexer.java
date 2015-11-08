package pf;

// Generated from Hello.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HelloLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, WS=16, BINARY_BOOLEAN_OPERATOR=17, 
		COMP=18, OPER=19, MOMENT=20, ID=21, CTE=22;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "WS", "BINARY_BOOLEAN_OPERATOR", 
		"COMP", "OPER", "MOMENT", "ID", "CTE"
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


	public HelloLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Hello.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\30\u0092\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6"+
		"\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\21\6\21h\n\21\r\21\16\21i\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\5\22s\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23~\n"+
		"\23\3\24\3\24\3\25\6\25\u0083\n\25\r\25\16\25\u0084\3\26\3\26\7\26\u0089"+
		"\n\26\f\26\16\26\u008c\13\26\3\27\6\27\u008f\n\27\r\27\16\27\u0090\2\2"+
		"\30\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30\3\2\b\5\2\13\f\17\17\"\"\4\2>>"+
		"@@\5\2,-//\61\61\3\2\62;\4\2C\\c|\5\2\62;C\\c|\u009a\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2\2\5\66\3\2\2\2\7;\3"+
		"\2\2\2\tA\3\2\2\2\13C\3\2\2\2\rE\3\2\2\2\17G\3\2\2\2\21J\3\2\2\2\23M\3"+
		"\2\2\2\25O\3\2\2\2\27Q\3\2\2\2\31T\3\2\2\2\33X\3\2\2\2\35Z\3\2\2\2\37"+
		"c\3\2\2\2!g\3\2\2\2#r\3\2\2\2%}\3\2\2\2\'\177\3\2\2\2)\u0082\3\2\2\2+"+
		"\u0086\3\2\2\2-\u008e\3\2\2\2/\60\7u\2\2\60\61\7g\2\2\61\62\7n\2\2\62"+
		"\63\7g\2\2\63\64\7e\2\2\64\65\7v\2\2\65\4\3\2\2\2\66\67\7h\2\2\678\7t"+
		"\2\289\7q\2\29:\7o\2\2:\6\3\2\2\2;<\7y\2\2<=\7j\2\2=>\7g\2\2>?\7t\2\2"+
		"?@\7g\2\2@\b\3\2\2\2AB\7,\2\2B\n\3\2\2\2CD\7.\2\2D\f\3\2\2\2EF\7/\2\2"+
		"F\16\3\2\2\2GH\7/\2\2HI\7@\2\2I\20\3\2\2\2JK\7>\2\2KL\7/\2\2L\22\3\2\2"+
		"\2MN\7*\2\2N\24\3\2\2\2OP\7+\2\2P\26\3\2\2\2QR\7c\2\2RS\7u\2\2S\30\3\2"+
		"\2\2TU\7p\2\2UV\7q\2\2VW\7v\2\2W\32\3\2\2\2XY\7\60\2\2Y\34\3\2\2\2Z[\7"+
		"U\2\2[\\\7P\2\2\\]\7C\2\2]^\7R\2\2^_\7U\2\2_`\7J\2\2`a\7Q\2\2ab\7V\2\2"+
		"b\36\3\2\2\2cd\7K\2\2de\7P\2\2e \3\2\2\2fh\t\2\2\2gf\3\2\2\2hi\3\2\2\2"+
		"ig\3\2\2\2ij\3\2\2\2jk\3\2\2\2kl\b\21\2\2l\"\3\2\2\2mn\7c\2\2no\7p\2\2"+
		"os\7f\2\2pq\7q\2\2qs\7t\2\2rm\3\2\2\2rp\3\2\2\2s$\3\2\2\2tu\7?\2\2u~\7"+
		"?\2\2vw\7>\2\2w~\7@\2\2x~\t\3\2\2yz\7>\2\2z~\7?\2\2{|\7@\2\2|~\7?\2\2"+
		"}t\3\2\2\2}v\3\2\2\2}x\3\2\2\2}y\3\2\2\2}{\3\2\2\2~&\3\2\2\2\177\u0080"+
		"\t\4\2\2\u0080(\3\2\2\2\u0081\u0083\t\5\2\2\u0082\u0081\3\2\2\2\u0083"+
		"\u0084\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085*\3\2\2\2"+
		"\u0086\u008a\t\6\2\2\u0087\u0089\t\7\2\2\u0088\u0087\3\2\2\2\u0089\u008c"+
		"\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b,\3\2\2\2\u008c"+
		"\u008a\3\2\2\2\u008d\u008f\t\7\2\2\u008e\u008d\3\2\2\2\u008f\u0090\3\2"+
		"\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091.\3\2\2\2\t\2ir}\u0084"+
		"\u008a\u0090\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}