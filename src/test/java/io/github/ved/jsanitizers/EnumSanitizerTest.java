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
	void sanitizeWithArray(){
		
		String value = "test";
		
		String[] validList = new String[]
		{
			"nope", "yes", "test"
		};
		
		String result = EnumSanitizer.sanitizeValue(value, validList);
		
		assertEquals("test", result);
		
	}
	
	@Test
	void sanitizeWithList(){
		
		String value = "test";
		
		ArrayList<String> validList = new ArrayList<>();
		
		validList.add("nope");
		validList.add("yes");
		validList.add("test");
		
		String result = EnumSanitizer.sanitizeValue(value, validList);
		
		assertEquals("test", result);
		
	}
	
	@Test
	void sanitizeWithArrayNotPresent(){
		
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
	void sanitizeWithListNotPresent(){
		
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
	void sanitizeWithArrayEmpty(){
		
		String value = "test";
		
		String[] validList = new String[]{};
		
		Executable shouldThrowIllegalArgumentException = () -> EnumSanitizer
				.sanitizeValue(value, validList);
		
		assertThrows(IllegalArgumentException.class,
				shouldThrowIllegalArgumentException);
		
	}
	
	@Test
	void sanitizeWithListEmpty(){
		
		String value = "test";
		
		ArrayList<String> validList = new ArrayList<>();
		
		Executable shouldThrowIllegalArgumentException = () -> EnumSanitizer
				.sanitizeValue(value, validList);
		
		assertThrows(IllegalArgumentException.class,
				shouldThrowIllegalArgumentException);
		
	}
	
	@Test
	void singleValueNoSpace(){
		
		String value = "Hello!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello!", list.get(0));
		
	}
	
	@Test
	void singleValueSpaces(){
		
		String value = "Hello fellow kid!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello fellow kid!", list.get(0));
		
	}
	
	@Test
	void singleValueTabs(){
		
		String value = "Hello\tfellow\tkid!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello\tfellow\tkid!", list.get(0));
		
	}
	
	@Test
	void singleValueSpacesAndTabs(){
		
		String value = "Hello\t fellow \t kid!";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello\t fellow \t kid!", list.get(0));
		
	}
	
	@Test
	void singleValueTrailingSpaces(){
		
		String value = "  Hello!  ";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello!", list.get(0));
		
	}
	
	@Test
	void singleValueTrailingTabs(){
		
		String value = "\tHello!\t";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello!", list.get(0));
		
	}
	
	@Test
	void singleValueSeparatorOnly(){
		
		String value = "|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("|", list.get(0));
		
	}
	
	@Test
	void singleValueSeparatorOnlyProtected(){
		
		String value = "\\|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("\\|", list.get(0));
		
	}
	
	@Test
	void singleValueSeparatorOnlyMultiple(){
		
		String value = "||||";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("||||", list.get(0));
		
	}
	
	@Test
	void singleValueSeparatorOnlyCustom(){
		
		String value = "[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("[", list.get(0));
		
	}
	
	@Test
	void singleValueSeparatorOnlyProtectedCustom(){
		
		String value = "\\[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("\\[", list.get(0));
		
	}
	
	@Test
	void singleValueSeparatorOnlyMultipleCustom(){
		
		String value = "[[[[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("[[[[", list.get(0));
		
	}
	
	@Test
	void singleValueProtectionSyntax(){
		
		String value = "\\\\||\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("\\|", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void singleValueProtectionSyntaxCustom(){
		
		String value = "\\\\[[\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("\\[", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void singleValueProtectionSyntaxProtector(){
		
		String value = "\\\\\\ \\ \\\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("\\\\", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void singleValueProtectionSyntax2(){
		
		String value = "\\\\| | \\|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("\\|", list.get(0));
		assertEquals("|", list.get(1));
		
	}
	
	@Test
	void multipleValuesNoSpace(){
		
		String value = "First|Second|Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesSpaces(){
		
		String value = "First  |  Second  |  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesTabs(){
		
		String value = "First\t|\tSecond\t|\tThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesSpacesAndTabs(){
		
		String value = "First\t  | \t Second \t  |\t  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void singleValueProtectedBar(){
		
		String value = "Hello \\| World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello | World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedBar(){
		
		String value = "First \\| Second | Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First | Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedBarOnlyStart(){
		
		String value = "\\| | Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("|", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedBarOnlyEnd(){
		
		String value = "First | \\|";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("|", list.get(1));
		
	}
	
	@Test
	void singleValueProtectedProtectedBar(){
		
		String value = "Hello \\\\| World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello \\| World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedProtectedBar(){
		
		String value = "First \\\\| Second | Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First \\| Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void regexCharInValue(){
		
		String value = "First | Se[ond";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Se[ond", list.get(1));
		
	}
	
	@Test
	void separatorRegexChar(){
		
		String value = "First [ Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void separatorRegexCharAndRegexInValue(){
		
		String value = "First [ Secon]";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Secon]", list.get(1));
		
	}
	
	@Test
	void singleValueProtectedBarRegexSeparator(){
		
		String value = "Hello \\[ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("Hello [ World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedBarRegexSeparator(){
		
		String value = "First \\[ Second [ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First [ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedBarRegexSeparatorOnlyStart(){
		
		String value = "\\[ [ Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("[", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedBarRegexSeparatorOnlyEnd(){
		
		String value = "First [ \\[";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("[", list.get(1));
		
	}
	
	@Test
	void singleValueProtectedProtectedBarRegexSeparator(){
		
		String value = "Hello \\\\[ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\[ World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedProtectedBarRegexSeparator(){
		
		String value = "First \\\\[ Second [ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '[');
		
		assertEquals(2, list.size());
		assertEquals("First \\[ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void multipleValuesNoSpaceLetter(){
		
		String value = "FirstlSecondlThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesSpacesLetter(){
		
		String value = "First  l  Second  l  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesTabsLetter(){
		
		String value = "First\tl\tSecond\tl\tThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesSpacesAndTabsLetter(){
		
		String value = "First\t  l \t Second \t  l\t  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void singleValueProtectedBarLetter(){
		
		String value = "He\\l\\lo \\l Wor\\ld";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(1, list.size());
		assertEquals("Hello l World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedBarLetter(){
		
		String value = "First \\l Second l Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("First l Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedBarOnlyStartLetter(){
		
		String value = "\\l l Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("l", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedBarOnlyEndLetter(){
		
		String value = "First l \\l";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("l", list.get(1));
		
	}
	
	@Test
	void singleValueProtectedProtectedBarLetter(){
		
		String value = "He\\l\\lo \\\\l Wor\\ld";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\l World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedProtectedBarLetter(){
		
		String value = "First \\\\l Second l Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, 'l');
		
		assertEquals(2, list.size());
		assertEquals("First \\l Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void multipleValuesNoSpaceProtectorSeparator(){
		
		String value = "First\\Second\\Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesSpacesProtectorSeparator(){
		
		String value = "First  \\  Second  \\  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesTabsProtectorSeparator(){
		
		String value = "First\t\\\tSecond\t\\\tThird";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void multipleValuesSpacesAndTabsProtectorSeparator(){
		
		String value = "First\t  \\ \t Second \t  \\\t  Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		
	}
	
	@Test
	void singleValueProtectedProtectorSeparator(){
		
		String value = "Hello \\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\ World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedProtectorSeparator(){
		
		String value = "First \\\\ Second \\ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("First \\ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedOnlyStartProtectorSeparator(){
		
		String value = "\\\\ \\ Second";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("\\", list.get(0));
		assertEquals("Second", list.get(1));
		
	}
	
	@Test
	void multipleValuesProtectedOnlyEndProtectorSeparator(){
		
		String value = "First \\ \\\\";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("First", list.get(0));
		assertEquals("\\", list.get(1));
		
	}
	
	@Test
	void singleValueProtectedProtectedProtectorSeparator(){
		
		String value = "Hello \\\\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\\\ World", list.get(0));
		
	}
	
	@Test
	void singleValueProtectedProtectedProtectorSeparatorBig(){
		
		String value = "Hello \\\\\\\\\\\\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\\\\\\\\\\\ World", list.get(0));
		
	}
	
	@Test
	void singleValueProtectedTwice(){
		
		String value = "Hello \\| \\| World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value);
		
		assertEquals(1, list.size());
		assertEquals("Hello | | World", list.get(0));
		
	}
	
	@Test
	void singleValueProtectedProtectedProtectorSeparatorTwice(){
		
		String value = "Hello \\\\ \\\\ World";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(1, list.size());
		assertEquals("Hello \\ \\ World", list.get(0));
		
	}
	
	@Test
	void multipleValuesProtectedProtectedBarProtectorSeparator(){
		
		String value = "First \\\\\\ Second \\ Third";
		
		List<String> list = EnumSanitizer.extractEnumFromString(value, '\\');
		
		assertEquals(2, list.size());
		assertEquals("First \\\\ Second", list.get(0));
		assertEquals("Third", list.get(1));
		
	}
	
	@Test
	void emptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat1(){
		
		String value = "First|";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat2(){
		
		String value = "First||Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat3(){
		
		String value = "First|  |Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat4(){
		
		String value = "First|Second|";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat5(){
		
		String value = "|First|Second";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat6(){
		
		String value = "    |First";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormat7(){
		
		String value = "First|    ";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep1(){
		
		String value = "First[";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep2(){
		
		String value = "First[[Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep3(){
		
		String value = "First[  [Third";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep4(){
		
		String value = "First[Second[";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep5(){
		
		String value = "[First[Second";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep6(){
		
		String value = "    [First";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
	@Test
	void multipleValuesBadFormatCustomSep7(){
		
		String value = "First[    ";
		
		Executable shouldThrowBadFormatException = () -> EnumSanitizer
				.extractEnumFromString(value, '[');
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(EnumSanitizer.FORMAT_NOT_ACCEPTED,
				exception.getErrorCode());
		
	}
	
}