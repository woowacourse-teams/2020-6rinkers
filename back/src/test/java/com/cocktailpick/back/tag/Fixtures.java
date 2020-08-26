package com.cocktailpick.back.tag;

import java.util.Arrays;
import java.util.List;

import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagType;

public class Fixtures {
    public static final String THREE_TAGS_CSV_CONTENT = "tag,type\n" + "두강,CONCEPT\n" + "두중,ABV\n" + "두약,INGREDIENT";

    public static final String FOUR_TAGS_CSV_CONTENT =
        "tag,type\n" + "아몬드,INGREDIENT\n" + "부드러움,TEXTURE\n" + "민트,INGREDIENT\n" + "초코,INGREDIENT";

    public static final List<Tag> FOUR_TAGS = Arrays.asList(
        Tag.builder().name("아몬드").tagType(TagType.INGREDIENT).build(),
        Tag.builder().name("부드러움").tagType(TagType.TEXTURE).build(),
        Tag.builder().name("민트").tagType(TagType.INGREDIENT).build(),
        Tag.builder().name("초코").tagType(TagType.INGREDIENT).build());
}
