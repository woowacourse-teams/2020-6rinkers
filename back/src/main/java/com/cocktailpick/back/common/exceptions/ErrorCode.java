package com.cocktailpick.back.common.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
	INTERNAL_SERVER_ERROR(500, "C001", "서버가 터졌습니다."),
	METHOD_NOT_ALLOWED(405, "C002", "Api는 열려있으나 메소드는 사용 불가합니다."),
	INVALID_TYPE_VALUE(400, "C005", " 요청 값의 타입이 잘못되었습니다."),

	COCKTAIL_NOT_FOUND(400, "CO001", "칵테일을 찾을 수 없습니다."),
	RECIPE_ITEM_DUPLICATED(400, "RE001", "칵테일에는 중복된 재료가 들어갈 수 없습니다.");

	private final String code;
	private final String message;
	private int status;

	ErrorCode(final int status, final String code, final String message) {
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