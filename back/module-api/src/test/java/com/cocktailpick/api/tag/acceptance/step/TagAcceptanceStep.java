package com.cocktailpick.api.tag.acceptance.step;

import static com.cocktailpick.api.user.acceptance.step.AuthAcceptanceStep.*;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cocktailpick.api.tag.dto.TagRequest;
import com.cocktailpick.api.tag.dto.TagResponse;
import com.cocktailpick.api.user.dto.AuthResponse;
import com.cocktailpick.core.tag.domain.TagType;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

public class TagAcceptanceStep {

    public static ExtractableResponse<Response> requestToAddTagsByCsv(MultiPartSpecification multiPartSpecification,
        AuthResponse authResponse) {
        return given().log().all()
            .header(HttpHeaders.AUTHORIZATION, toHeaderValue(authResponse))
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart(multiPartSpecification)
            .when()
            .post("/api/tags/upload/csv")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> requestToFindTags(TagType tagType, int size, boolean random) {
        return given().log().all()
            .param("tagType", tagType)
            .param("size", size)
            .param("random", random)
            .when()
            .get("/api/tags")
            .then().log().all()
            .extract();
    }

    public static List<Long> requestToFindTagsAndGetTagIds(TagType tagType, int size, boolean random) {
        return requestToFindTags(tagType, size, random).jsonPath().getList(".", TagResponse.class)
            .stream()
            .map(TagResponse::getTagId)
            .collect(Collectors.toList());
    }

    public static ExtractableResponse<Response> requestToAddTag(TagRequest tagRequest, AuthResponse authResponse) {
        return given().log().all()
            .header(HttpHeaders.AUTHORIZATION, toHeaderValue(authResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(tagRequest)
            .when()
            .post("/api/tags")
            .then().log().all()
            .extract();
    }

    public static void assertThatFindTagsOfSweetAndSour(ExtractableResponse<Response> response) {
        List<TagResponse> tagResponses = response.jsonPath().getList(".", TagResponse.class);

        assertAll(
            () -> assertThat(tagResponses).hasSize(2),
            () -> assertThat(tagResponses.get(0).getName()).isEqualTo("단맛"),
            () -> assertThat(tagResponses.get(0).getTagType()).isEqualTo(TagType.FLAVOR.getTagType()),
            () -> assertThat(tagResponses.get(1).getName()).isEqualTo("신맛"),
            () -> assertThat(tagResponses.get(1).getTagType()).isEqualTo(TagType.FLAVOR.getTagType())
        );
    }

    public static String requestToAddTagAndGetLocation(TagRequest tagRequest, AuthResponse authResponse) {
        return requestToAddTag(tagRequest, authResponse).header(HttpHeaders.LOCATION);
    }

    public static ExtractableResponse<Response> requestToUpdateTag(String url, TagRequest tagRequest,
        AuthResponse authResponse) {
        return given().log().all()
            .header(HttpHeaders.AUTHORIZATION, toHeaderValue(authResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(tagRequest)
            .when()
            .put(url)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> requestToDeleteTag(String url, AuthResponse authResponse) {
        return given().log().all()
            .header(HttpHeaders.AUTHORIZATION, toHeaderValue(authResponse))
            .when()
            .delete(url)
            .then().log().all()
            .extract();
    }
}
