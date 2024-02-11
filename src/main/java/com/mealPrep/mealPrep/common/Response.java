package com.mealPrep.mealPrep.common;

import com.mealPrep.mealPrep.domain.Enum.ResponseType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.mealPrep.mealPrep.domain.Enum.ResponseType.FAILURE;
import static com.mealPrep.mealPrep.domain.Enum.ResponseType.SUCCESS;

@Getter
@NoArgsConstructor
public class Response<T> {
    private int code;
    private String message;
    private T data;

    @Builder
    public Response(ResponseType responseType, T data) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = data;
    }

    public static Response success() {
        return Response.builder()
                .responseType(SUCCESS)
                .build();
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(SUCCESS, data);
    }

    public static Response failure() {
        return Response.builder()
                .responseType(FAILURE)
                .build();
    }

}
