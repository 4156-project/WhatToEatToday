package com.whattoeattoday.recommendationservice.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class CategoryQueryRequest {

    @NonNull
    public String categoryName;
}
