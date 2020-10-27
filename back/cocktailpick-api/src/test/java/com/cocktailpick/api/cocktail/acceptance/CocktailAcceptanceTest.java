package com.cocktailpick.api.cocktail.acceptance;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.cocktailpick.api.cocktail.acceptance.step.CocktailAcceptanceStep;
import com.cocktailpick.api.common.acceptance.AcceptanceTest;
import com.cocktailpick.api.common.acceptance.step.AcceptanceStep;
import com.cocktailpick.api.tag.Fixtures;
import com.cocktailpick.api.tag.acceptance.step.TagAcceptanceStep;
import com.cocktailpick.api.user.acceptance.step.AuthAcceptanceStep;
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

	@DisplayName("모든 칵테일을 조회한다.")
	@Test
	void findCocktails() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToFindCocktails();

		AcceptanceStep.assertThatStatusIsOk(response);
		CocktailAcceptanceStep.assertThatFindThreeCocktails(response);
	}

	@DisplayName("특정 단어가 포함된 칵테일을 원하는 수 만큼 조회한다.")
	@Test
	void findPageContainingWord() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToFindPageContainingWord("갓", 0L, 1);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		CocktailAcceptanceStep.assertThatFindOneCocktailContainingGod(response);
	}

	@DisplayName("특정 태그가 포함된 칵테일을 원하는 수 만큼 조회한다.")
	@Test
	void findPageFilteredByTags() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		List<Long> tagIds = TagAcceptanceStep.requestToFindTagsAndGetTagIds(TagType.TEXTURE, 1, false);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToFindPageFilteredByTags(tagIds, 0L, 2);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		CocktailAcceptanceStep.assertThatFindTwoCocktailContainingSoftness(response);
	}

	@DisplayName("칵테일을 조회한다.")
	@Test
	void findCocktail() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		TagAcceptanceStep.requestToAddTag(tagRequest, authResponse);

		String createdLocation = CocktailAcceptanceStep.requestToAddCocktailAndGetLocation(
			com.cocktailpick.api.cocktail.Fixtures.KAHLUA_MILK_REQUEST, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToFindCocktail(createdLocation);

		// then
		CocktailAcceptanceStep.assertThatFindKahlua(response);
	}

	@DisplayName("칵테일을 추가한다.")
	@Test
	void addCocktail() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		TagAcceptanceStep.requestToAddTag(tagRequest, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToAddCocktail(
			com.cocktailpick.api.cocktail.Fixtures.KAHLUA_MILK_REQUEST, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("칵테일을 수정한다.")
	@Test
	void updateCocktail() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TagRequest sweetTag = new TagRequest("단맛", TagType.FLAVOR.getTagType());
		TagRequest sourTag = new TagRequest("신맛", TagType.FLAVOR.getTagType());

		TagAcceptanceStep.requestToAddTag(sweetTag, authResponse);
		TagAcceptanceStep.requestToAddTag(sourTag, authResponse);

		String createdLocation = CocktailAcceptanceStep.requestToAddCocktailAndGetLocation(
			com.cocktailpick.api.cocktail.Fixtures.KAHLUA_MILK_REQUEST, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToUpdateCocktail(createdLocation,
			com.cocktailpick.api.cocktail.Fixtures.MALIBU_ORANGE, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
	}

	@DisplayName("칵테일을 삭제한다.")
	@Test
	void deleteCocktail() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TagRequest sweetTag = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		TagAcceptanceStep.requestToAddTag(sweetTag, authResponse);

		String createdLocation = CocktailAcceptanceStep.requestToAddCocktailAndGetLocation(
			com.cocktailpick.api.cocktail.Fixtures.KAHLUA_MILK_REQUEST, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToDeleteCocktail(createdLocation,
			authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}

	@DisplayName("모든 칵테일을 삭제한다.")
	@Test
	void deleteAllCocktails() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification csvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(csvFile, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToDeleteAllCocktails(authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}

	@DisplayName("오늘의 칵테일을 조회한다.")
	@Test
	void findCocktailOfToday() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// then
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToFindCocktailOfToday();

		AcceptanceStep.assertThatStatusIsOk(response);
		CocktailAcceptanceStep.assertThatFindCocktailOfToday(response);
	}

	@DisplayName("칵테일 csv 파일을 저장한다.")
	@Test
	void addCocktailsByCsv() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		// when
		MultiPartSpecification csvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToAddCocktailsByCsv(csvFile,
			authResponse);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("칵테일을 추천한다.")
	@Test
	void recommend() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		RecommendRequest recommendRequest = new RecommendRequest(new AbvAnswer(0, 20), Collections.emptyList(),
			new FlavorAnswer(UserPreferenceAnswer.YES, UserPreferenceAnswer.YES, UserPreferenceAnswer.NO),
			Collections.emptyList(), Collections.emptyList());

		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToRecommend(recommendRequest);

		AcceptanceStep.assertThatStatusIsOk(response);
	}

	@DisplayName("특정 문자열을 포함하는 칵테일을 조회한다.")
	@Test
	void findByNameContaining() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		MultiPartSpecification tagCsvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TAGS_CSV_CONTENT.getBytes())
			.fileName("tags.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		TagAcceptanceStep.requestToAddTagsByCsv(tagCsvFile, authResponse);

		MultiPartSpecification cocktailCsvFile = new MultiPartSpecBuilder(
			com.cocktailpick.api.cocktail.Fixtures.THREE_COCKTAILS_CSV_CONTENT.getBytes())
			.fileName("cocktails.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		CocktailAcceptanceStep.requestToAddCocktailsByCsv(cocktailCsvFile, authResponse);

		// when
		ExtractableResponse<Response> response = CocktailAcceptanceStep.requestToFindCocktailsByNameContaining("갓");

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		CocktailAcceptanceStep.assertThatFindTwoCocktailsContainingGod(response);
	}
}
