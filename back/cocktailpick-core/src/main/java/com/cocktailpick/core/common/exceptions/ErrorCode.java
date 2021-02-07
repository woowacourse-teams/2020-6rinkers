package com.cocktailpick.core.common.exceptions;

public enum ErrorCode {
	INTERNAL_SERVER_ERROR(500, "C_001", "서버가 터졌습니다."),
	METHOD_NOT_ALLOWED(405, "C_002", "Api는 열려있으나 메소드는 사용 불가합니다."),
	INVALID_INPUT_VALUE(400, "C_003", "적절하지 않은 요청 값입니다."),
	INVALID_TYPE_VALUE(400, "C_004", "요청 값의 타입이 잘못되었습니다."),
	ENTITY_NOT_FOUND(400, "C_005", "지정한 Entity를 찾을 수 없습니다."),

	COCKTAIL_NOT_FOUND(400, "CO_001", "칵테일을 찾을 수 없습니다."),

	RECIPE_ITEM_DUPLICATED(400, "RE_001", "칵테일에는 중복된 재료가 들어갈 수 없습니다."),

	TAG_DUPLICATED(400, "TA_001", "이미 존재하는 태그입니다."),
	TAG_TYPE_NOT_FOUND(400, "TA_002", "해당 태그의 타입은 존재하지 않습니다."),
	TAG_NOT_FOUND(400, "TA_003", "태그를 찾을 수 없습니다."),

	FAVORITE_DUPLICATED(400, "FA_001", "중복된 즐겨찾기를 추가할 수 없습니다."),

	TERMINOLOGY_TYPE_NOT_FOUND(400, "TE_TY_001", "해당 용어의 타입은 존재하지 않습니다."),

	TERMINOLOGY_NOT_FOUND(400, "TE_001", "용어를 찾을 수 없습니다."),
	TERMINOLOGY_DUPLICATED(400, "TE_002", "중복된 용어를 추가할 수 없습니다."),

	AUTH_ERROR(400, "AU_001", "인증 관련 오류가 발생했습니다."),
	DUPLICATED_EMAIL(400, "AU_002", "이미 존재하는 E-mail입니다."),
	UNAUTHORIZED_REDIRECT_URI(400, "AU_003", "인증되지 않은 REDIRECT_URI입니다."),
	BAD_LOGIN(400, "AU_004", "잘못된 아이디 또는 패스워드입니다."),

	INGREDIENT_NOT_FOUND(400, "IN_001", "재료를 찾을 수 없습니다.");

    private final String code;
	private final String message;
	private final int status;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}

	public int getStatus() {
		return status;
	}
}