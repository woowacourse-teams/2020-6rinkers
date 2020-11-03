package com.cocktailpick.api.cocktail.acceptance;

import static com.cocktailpick.api.cocktail.Fixtures.*;
import static com.cocktailpick.api.cocktail.acceptance.step.CocktailAcceptanceStep.*;
import static com.cocktailpick.api.common.acceptance.step.AcceptanceStep.*;
import static com.cocktailpick.api.tag.controller.Fixtures.*;
import static com.cocktailpick.api.tag.controller.acceptance.step.TagAcceptanceStep.*;
import static com.cocktailpick.api.user.controller.acceptance.step.AuthAcceptanceStep.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.MediaType;

import com.cocktailpick.api.common.acceptance.AcceptanceTest;
import com.cocktailpick.core.cocktail.dto.AbvAnswer;
import com.cocktailpick.core.cocktail.dto.FlavorAnswer;
import com.cocktailpick.core.cocktail.dto.RecommendRequest;
import com.cocktailpick.core.cocktail.vo.UserPreferenceAnswer;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.tag.dto.TagRequest;
import com.cocktailpick.core.user.dto.AuthResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

@DisplayName("Cocktail 인수/통합 테스트")
class CocktailAcceptanceTest extends AcceptanceTest {
	@Autowired
	private CacheManager cacheManager;

	@DisplayName("모든 칵테일을 조회한다.")
	@Test
	void findCocktails() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindCocktails();

		assertThatStatusIsOk(response);
		assertThatFindThreeCocktails(response);
	}

	@DisplayName("모든 칵테일 조회 시, 캐싱이 되어있는지 확인한다.")
	@Test
	void checkAllCocktailsCached() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> firstAttempt = requestToFindCocktails();
		ExtractableResponse<Response> secondAttempt = requestToFindCocktails();
		ExtractableResponse<Response> thirdAttempt = requestToFindCocktails();

		//then
		assertThatFirstAttemptTakeLongerThanNextAttempt(firstAttempt, secondAttempt);
		assertThatFirstAttemptTakeLongerThanNextAttempt(firstAttempt, thirdAttempt);
	}

	@DisplayName("특정 단어가 포함된 칵테일을 원하는 수 만큼 조회한다.")
	@Test
	void findPageContainingWord() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindPageContainingWord("갓", 0L, 1);

		// then
		assertThatStatusIsOk(response);
		assertThatFindOneCocktailContainingGod(response);
	}

	@DisplayName("특정 태그가 포함된 칵테일을 원하는 수 만큼 조회한다.")
	@Test
	void findPageFilteredByTags() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		List<Long> tagIds = requestToFindTagsAndGetTagIds(TagType.TEXTURE, 1, false);

		// when
		ExtractableResponse<Response> response = requestToFindPageFilteredByTags(tagIds, 0L, 2);

		// then
		assertThatStatusIsOk(response);
		assertThatFindTwoCocktailContainingSoftness(response);
	}

	@DisplayName("칵테일을 조회한다.")
	@Test
	void findCocktail() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		requestToAddTag(tagRequest, authResponse);

		String createdLocation = requestToAddCocktailAndGetLocation(KAHLUA_MILK_REQUEST,
			authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindCocktail(createdLocation);

		// then
		assertThatFindKahlua(response);
	}

	@DisplayName("칵테일 조회시, 캐싱되어 있는지 확인한다.")
	@Test
	void checkCocktailCached() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		requestToAddTag(tagRequest, authResponse);

		String createdLocation = requestToAddCocktailAndGetLocation(KAHLUA_MILK_REQUEST, authResponse);

		// when
		ExtractableResponse<Response> firstAttempt = requestToFindCocktail(createdLocation);
		ExtractableResponse<Response> secondAttempt = requestToFindCocktail(createdLocation);
		ExtractableResponse<Response> thirdAttempt = requestToFindCocktail(createdLocation);

		// then
		assertThatFirstAttemptTakeLongerThanNextAttempt(firstAttempt, secondAttempt);
		assertThatFirstAttemptTakeLongerThanNextAttempt(firstAttempt, thirdAttempt);
	}

	@DisplayName("칵테일을 추가한다.")
	@Test
	void addCocktail() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		requestToAddTag(tagRequest, authResponse);

		// when
		ExtractableResponse<Response> response = requestToAddCocktail(KAHLUA_MILK_REQUEST,
			authResponse);

		// then
		assertThatStatusIsCreated(response);
	}

	@DisplayName("칵테일을 수정한다.")
	@Test
	void updateCocktail() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest sweetTag = new TagRequest("단맛", TagType.FLAVOR.getTagType());
		TagRequest sourTag = new TagRequest("신맛", TagType.FLAVOR.getTagType());

		requestToAddTag(sweetTag, authResponse);
		requestToAddTag(sourTag, authResponse);

		String createdLocation = requestToAddCocktailAndGetLocation(KAHLUA_MILK_REQUEST,
			authResponse);

		// when
		ExtractableResponse<Response> response = requestToUpdateCocktail(createdLocation, MALIBU_ORANGE, authResponse);

		// then
		assertThatStatusIsOk(response);
	}

	@DisplayName("칵테일 수정시, 이전에 캐싱된 칵테일 데이터를 수정된 칵테일 데이터로 변경하여 캐싱한다.")
	@Test
	void checkUpdatedCocktailCached() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest sweetTag = new TagRequest("단맛", TagType.FLAVOR.getTagType());
		TagRequest sourTag = new TagRequest("신맛", TagType.FLAVOR.getTagType());

		requestToAddTag(sweetTag, authResponse);
		requestToAddTag(sourTag, authResponse);

		String createdLocation = requestToAddCocktailAndGetLocation(KAHLUA_MILK_REQUEST, authResponse);
		Long cocktailId = Long.parseLong(createdLocation.split("/")[3]);

		// when
		requestToFindCocktail(createdLocation);
		assertThatCachingCorrectData(KAHLUA_MILK_REQUEST, cocktailId, cacheManager);
		requestToUpdateCocktail(createdLocation, MALIBU_ORANGE, authResponse);

		// then
		assertThatCachingCorrectData(MALIBU_ORANGE, cocktailId, cacheManager);
	}

	@DisplayName("칵테일을 삭제한다.")
	@Test
	void deleteCocktail() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest sweetTag = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		requestToAddTag(sweetTag, authResponse);

		String createdLocation = requestToAddCocktailAndGetLocation(KAHLUA_MILK_REQUEST,
			authResponse);

		// when
		ExtractableResponse<Response> response = requestToDeleteCocktail(createdLocation,
			authResponse);

		// then
		assertThatStatusIsNoContent(response);
	}

	@DisplayName("모든 칵테일을 삭제한다.")
	@Test
	void deleteAllCocktails() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification csvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(csvFile, authResponse);

		// when
		ExtractableResponse<Response> response = requestToDeleteAllCocktails(authResponse);

		// then
		assertThatStatusIsNoContent(response);
	}

	@DisplayName("모든 칵테일을 삭제 시, 캐싱된 전체 칵테일이 삭제되는지 확인")
	@Test
	void checkCachedAllCocktailsRemoved() {
		// given
		AuthResponse authResponse = requestAdminAuth();
		Cache.ValueWrapper cachedCocktails;

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification csvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(csvFile, authResponse);
		requestToFindCocktails();

		cachedCocktails = Objects.requireNonNull(cacheManager.getCache("cocktails")).get(SimpleKey.EMPTY);
		assert cachedCocktails != null;
		assertThatCocktailResponseSizeIsSameWith(cachedCocktails, 3);

		// when
		requestToDeleteAllCocktails(authResponse);
		cachedCocktails = Objects.requireNonNull(cacheManager.getCache("cocktails")).get(SimpleKey.EMPTY);

		// then
		assertThatCachedCocktailsNull(cachedCocktails);
	}

	@DisplayName("오늘의 칵테일을 조회한다.")
	@Test
	void findCocktailOfToday() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// then
		ExtractableResponse<Response> response = requestToFindCocktailOfToday();

		assertThatStatusIsOk(response);
		assertThatFindCocktailOfToday(response);
	}

	@DisplayName("칵테일 csv 파일을 저장한다.")
	@Test
	void addCocktailsByCsv() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		// when
		MultiPartSpecification csvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		ExtractableResponse<Response> response = requestToAddCocktailsByCsv(csvFile,
			authResponse);

		// then
		assertThatStatusIsCreated(response);
	}

	@DisplayName("칵테일을 추천한다.")
	@Test
	void recommend() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		RecommendRequest recommendRequest = new RecommendRequest(new AbvAnswer(0, 20), Collections.emptyList(),
			new FlavorAnswer(UserPreferenceAnswer.YES, UserPreferenceAnswer.YES, UserPreferenceAnswer.NO),
			Collections.emptyList(), Collections.emptyList());

		ExtractableResponse<Response> response = requestToRecommend(recommendRequest);

		assertThatStatusIsOk(response);
	}

	@DisplayName("특정 문자열을 포함하는 칵테일을 조회한다.")
	@Test
	void findByNameContaining() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindCocktailsByNameContaining("갓");

		// then
		assertThatStatusIsOk(response);
		assertThatFindTwoCocktailsContainingGod(response);
	}
}
