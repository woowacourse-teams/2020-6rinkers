package com.cocktailpick.back.tag;

import java.util.Arrays;
import java.util.List;

import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagType;

public class Fixtures {
	public static final String THREE_TAGS_CSV_CONTENT = "name,tag\n" + "두강,CONCEPT\n" + "두중,ABV\n" + "두약,INGREDIENT";
	public static final List<Tag> FOUR_TAGS_FROM_TAG_CSV = Arrays.asList(
		new Tag("아몬드", TagType.INGREDIENT),
		new Tag("부드러움", TagType.TEXTURE),
		new Tag("민트", TagType.INGREDIENT),
		new Tag("초코", TagType.INGREDIENT));
}
