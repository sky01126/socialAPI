/**
 * 프로젝트의 Request DTO(Data Transfer Object) 클래스 제공.<br>
 * AbstractRequest를 상속 받아서 사용하는 것을 추천한다.<br>
 * 그리고 파라미터 형식이 UNDERSCORE(_) or HYPHEN(-)인 경우에는 RequestParamName Annotation을 이용해서 매핑한다.<br>
 *
 * <pre>
 * <b>- 샘플</b>
 * <code>
 * package com.kthcorp.archetype.dto.request;
 *
 * import org.apache.ibatis.type.Alias;
 * import org.hibernate.validator.constraints.NotEmpty;
 *
 * import com.kthcorp.archetype.commons.dto.request.AbstractRequest;
 * import com.kthcorp.commons.web.annotation.RequestParamName;
 *
 * import io.swagger.annotations.ApiModel;
 * import io.swagger.annotations.ApiModelProperty;
 *
 * &#64;Alias("ValidationRequest")
 * public class ValidationRequest extends AbstractRequest {
 *     private static final long serialVersionUID = 1L;
 *
 *     &#64;NotEmpty(message = "Validation 파라미터는 필수 값입니다.")
 *     private String validation;
 *
 *     &#64;NotEmpty(message = "ValidationTest 파라미터는 필수 값입니다.")
 *     &#64;RequestParamName("validation_test")
 *     private String validationTest;
 * }
 * </code>
 * </pre>
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
package com.ha.social.dto.request;
