package com.whattoeattoday.recommendationservice.demo

import lombok.Data

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/18/23
 */

abstract class GetSimilarityRequest {
  val tableName: String
  val contentId: String
  val features: List[String]
}
