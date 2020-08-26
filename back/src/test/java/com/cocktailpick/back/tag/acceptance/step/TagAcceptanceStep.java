package com.cocktailpick.back.tag.acceptance.step;

import static com.cocktailpick.back.user.acceptance.step.AuthAcceptanceStep.*;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.springframework.http.MediaType;

import com.cocktailpick.back.tag.domain.TagType;
import com.cocktailpick.back.tag.dto.TagRequest;
import com.cocktailpick.back.tag.dto.TagResponse;
import com.cocktailpick.back.user.dto.AuthResponse;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

public class TagAcceptanceStep {

    public static ExtractableResponse<Response> requestToAddTagsByCsv(MultiPartSpecification multiPartSpecification,
        AuthResponse authResponse) {
        return given().log().all()
            .header(AUTHORIZATION, toHeaderValue(authResponse))
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

    public static ExtractableResponse<Response> requestToAddTag(TagRequest tagRequest, AuthResponse authResponse) {
        return given().log().all()
            .header(AUTHORIZATION, toHeaderValue(authResponse))
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
        return requestToAddTag(tagRequest, authResponse).header("location");
    }

    public static ExtractableResponse<Response> requestToUpdateTag(String url, TagRequest tagRequest,
        AuthResponse authResponse) {
        return given().log().all()
            .header(AUTHORIZATION, toHeaderValue(authResponse))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(tagRequest)
            .when()
            .put(url)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> requestToDeleteTag(String url, AuthResponse authResponse) {
        return given().log().all()
            .header(AUTHORIZATION, toHeaderValue(authResponse))
            .when()
            .delete(url)
            .then().log().all()
            .extract();
    }
}
