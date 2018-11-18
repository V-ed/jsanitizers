package io.github.ved.jsanitizers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

class EnumSanitizerTest {
	
	@Test
	void testSanitizeWithArray(){
		
		String value = "test";
		
		String[] validList = new String[]
		{
			"nope", "yes", "test"
		};
		
		String result = EnumSanitizer.sanitizeValue(value, validList);
		
		assertEquals("test", result);
		
	}
	
	@Test
	void testSanitizeWithList(){
		
		String value = "test";
		
		ArrayList<String> validList = new ArrayList<>();
		
		validList.add("nope");
		validList.add("yes");
		validList.add("test");
		
		String result = EnumSanitizer.sanitizeValue(value, validList);
		
		assertEquals("test", result);
		
	}
	
	@Test
	void testSanitizeWithArrayNotPresent(){
		
		String value = "test";
		
		String[] validList = new String[]
		{
			"nope", "yes"
		};
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.sanitizeValue(value, validList);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_A_CHOICE,
				exception.getErrorCode());
		
	}
	
	@Test
	void testSanitizeWithListNotPresent(){
		
		String value = "test";
		
		ArrayList<String> validList = new ArrayList<>();
		
		validList.add("nope");
		validList.add("yes");
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.sanitizeValue(value, validList);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_A_CHOICE,
				exception.getErrorCode());
		
	}
	
	@Test
	void testSingleValueNoSpace(){
		
		String value = "Hello!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello!", list.get(0));
		
	}
	
	@Test
	void testSingleValueSpaces(){
		
		String value = "Hello fellow kid!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello fellow kid!", list.get(0));
		
	}
	
	@Test
	void testSingleValueTabs(){
		
		String value = "Hello\tfellow\tkid!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello\tfellow\tkid!", list.get(0));
		
	}
	
	@Test
	void testSingleValueSpacesAndTabs(){
		
		String value = "Hello\t fellow \t kid!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello\t fellow \t kid!", list.get(0));
		
	}
	
	@Test
	void testSingleValueTrailingSpaces(){
		
		String value = "  Hello!  ";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello!", list.get(0));
		
	}
	
	@Test
	void testSingleValueTrailingTabs(){
		
		String value = "\tHello!\t";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello!", list.get(0));
		
	}
	
	@Test
	void testSingleValueSeparatorOnly(){
		
		String value = "|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("|", list.get(0));
		
	}
	
	@Test
	void testSingleValueSeparatorOnlyProtected(){
		
		String value = "\\|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("\\|", list.get(0));
		
	}
	
	@Test
	void testSingleValueSeparatorOnlyMultiple(){
		
		String value = "||||";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("||||", list.get(0));
		
	}
	
	@Test
	void testSingleValueSeparatorOnlyCustom(){
		
		String value = "[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("[", list.get(0));
		
	}
	
	@Test
	void testSingleValueSeparatorOnlyProtectedCustom(){
		
		String value = "\\[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("\\[", list.get(0));
		
	}
	
	@Test
	void testSingleValueSeparatorOnlyMultipleCustom(){
		
		String value = "[[[[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("[[[[", list.get(0));
		
	}
	
	@Test
	void testSingleValueProtectionSyntax(){
		
		String value = "\\\\||\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("\\|", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectionSyntaxCustom(){
		
		String value = "\\\\[[\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("\\[", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectionSyntaxProtector(){
		
		String value = "\\\\\\ \\ \\\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("\\\\", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectionSyntax2(){
		
		String value = "\\\\| | \\|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("\\|", list.get(0));
		assertEquals("|", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesNoSpace(){
		
		String value = "First|Second|Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesSpaces(){
		
		String value = "First  |  Second  |  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesTabs(){
		
		String value = "First\t|\tSecond\t|\tThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesSpacesAndTabs(){
		
		String value = "First\t  | \t Second \t  |\t  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testSingleValueProtectedBar(){
		
		String value = "Hello \\| World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello | World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedBar(){
		
		String value = "First \\| Second | Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First | Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarOnlyStart(){
		
		String value = "\\| | Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("|", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarOnlyEnd(){
		
		String value = "First | \\|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("|", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectedProtectedBar(){
		
		String value = "Hello \\\\| World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello \\| World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedProtectedBar(){
		
		String value = "First \\\\| Second | Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First \\| Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testRegexCharInValue(){
		
		String value = "First | Se[ond";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Se[ond", list.get(1));
		
	}
	
	@Test
	void testSeparatorRegexChar(){
		
		String value = "First [ Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void testSeparatorRegexCharAndRegexInValue(){
		
		String value = "First [ Secon]";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Secon]", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectedBarRegexSeparator(){
		
		String value = "Hello \\[ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("Hello [ World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarRegexSeparator(){
		
		String value = "First \\[ Second [ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First [ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarRegexSeparatorOnlyStart(){
		
		String value = "\\[ [ Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("[", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarRegexSeparatorOnlyEnd(){
		
		String value = "First [ \\[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("[", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectedProtectedBarRegexSeparator(){
		
		String value = "Hello \\\\[ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\[ World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedProtectedBarRegexSeparator(){
		
		String value = "First \\\\[ Second [ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First \\[ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesNoSpaceLetter(){
		
		String value = "FirstlSecondlThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesSpacesLetter(){
		
		String value = "First  l  Second  l  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesTabsLetter(){
		
		String value = "First\tl\tSecond\tl\tThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesSpacesAndTabsLetter(){
		
		String value = "First\t  l \t Second \t  l\t  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testSingleValueProtectedBarLetter(){
		
		String value = "He\\l\\lo \\l Wor\\ld";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(1, list.size());
		assertEquals("Hello l World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarLetter(){
		
		String value = "First \\l Second l Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("First l Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarOnlyStartLetter(){
		
		String value = "\\l l Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("l", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedBarOnlyEndLetter(){
		
		String value = "First l \\l";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("l", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectedProtectedBarLetter(){
		
		String value = "He\\l\\lo \\\\l Wor\\ld";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\l World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedProtectedBarLetter(){
		
		String value = "First \\\\l Second l Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("First \\l Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesNoSpaceProtectorSeparator(){
		
		String value = "First\\Second\\Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesSpacesProtectorSeparator(){
		
		String value = "First  \\  Second  \\  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesTabsProtectorSeparator(){
		
		String value = "First\t\\\tSecond\t\\\tThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testMultipleValuesSpacesAndTabsProtectorSeparator(){
		
		String value = "First\t  \\ \t Second \t  \\\t  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void testSingleValueProtectedProtectorSeparator(){
		
		String value = "Hello \\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\ World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedProtectorSeparator(){
		
		String value = "First \\\\ Second \\ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("First \\ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedOnlyStartProtectorSeparator(){
		
		String value = "\\\\ \\ Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("\\", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void testMultipleValuesProtectedOnlyEndProtectorSeparator(){
		
		String value = "First \\ \\\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void testSingleValueProtectedProtectedProtectorSeparator(){
		
		String value = "Hello \\\\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\\\ World", list.get(0));
		
	}
	
	@Test
	void testSingleValueProtectedProtectedProtectorSeparatorBig(){
		
		String value = "Hello \\\\\\\\\\\\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\\\\\\\\\\\ World", list.get(0));
		
	}
	
	@Test
	void testSingleValueProtectedTwice(){
		
		String value = "Hello \\| \\| World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello | | World", list.get(0));
		
	}
	
	@Test
	void testSingleValueProtectedProtectedProtectorSeparatorTwice(){
		
		String value = "Hello \\\\ \\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\ \\ World", list.get(0));
		
	}
	
	@Test
	void testMultipleValuesProtectedProtectedBarProtectorSeparator(){
		
		String value = "First \\\\\\ Second \\ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("First \\\\ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void testEmptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat1(){
		
		String value = "First|";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat2(){
		
		String value = "First||Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat3(){
		
		String value = "First|  |Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat4(){
		
		String value = "First|Second|";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat5(){
		
		String value = "|First|Second";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat6(){
		
		String value = "    |First";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormat7(){
		
		String value = "First|    ";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep1(){
		
		String value = "First[";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep2(){
		
		String value = "First[[Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep3(){
		
		String value = "First[  [Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep4(){
		
		String value = "First[Second[";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep5(){
		
		String value = "[First[Second";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep6(){
		
		String value = "    [First";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void testMultipleValuesBadFormatCustomSep7(){
		
		String value = "First[    ";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
}