package com.cocktailpick.back.tag;

import java.util.Arrays;
import java.util.List;

import com.cocktailpick.back.tag.domain.Tag;

public class Fixtures {
	public static final String THREE_TAGS_CSV_CONTENT = "태그\n두강\n두중\n두약";
	public static final List<Tag> FOUR_TAGS_FROM_TAG_CSV = Arrays.asList(
		new Tag("아몬드"),
		new Tag("부드러움"),
		new Tag("민트"),
		new Tag("초코"));
}
