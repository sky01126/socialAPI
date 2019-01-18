/**
 * 프로젝트의 Response DTO(Data Transfer Object) 클래스 제공.
 * ValidationResponse를 상속 받아서 사용하는 것을 추천한다.<br>
 *
 *
 * <pre>
 * <b>- 샘플</b>
 * <code>
 * package com.kthcorp.archetype.dto.response;
 *
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * import com.fasterxml.jackson.annotation.JsonInclude;
 * import com.fasterxml.jackson.annotation.JsonProperty;
 * import com.fasterxml.jackson.annotation.JsonPropertyOrder;
 * import com.fasterxml.jackson.databind.annotation.JsonSerialize;
 * import com.kthcorp.archetype.commons.config.JsonName;
 * import com.kthcorp.archetype.commons.dto.response.AbstractResponse;
 * import com.kthcorp.archetype.persistence.model.Validation;
 *
 * &#64;JsonSerialize
 * &#64;JsonInclude(JsonInclude.Include.NON_NULL)
 * &#64;JsonIgnoreProperties(ignoreUnknown = true)
 * public class ValidationResponse extends AbstractResponse {
 *     private static final long serialVersionUID = 1717973168197980472L;
 *
 *     &#64;JsonProperty("data")
 *     private Validation data;
 *
 *     public ValidationResponse() {
 *         // ignore..
 *     }
 *
 *     public ValidationResponse(final int code, final String message) {
 *         super(code, message);
 *     }
 *
 *     public Validation getData() {
 *         return data;
 *     }
 *
 *     public void setData(Validation data) {
 *         this.data = data;
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
package com.ha.social.dto.response;
