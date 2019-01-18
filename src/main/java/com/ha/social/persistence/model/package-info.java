/**
 * 프로젝트의 Persistence Model 클래스 제공.
 * AbstractModel을 상속 받아서 사용하는 것을 추천한다.<br>
 *
 * <pre>
 * <b>- 샘플</b>
 * <code>
 * package com.kthcorp.archetype.persistence.model;
 *
 * import org.apache.ibatis.type.Alias;
 * import org.springframework.stereotype.Component;
 *
 * import com.fasterxml.jackson.annotation.JsonProperty;
 * import com.fasterxml.jackson.annotation.JsonPropertyOrder;
 * import com.kthcorp.archetype.commons.config.JsonName;
 * import com.kthcorp.archetype.commons.persistence.model.AbstractModel;
 *
 * &#64;Component
 * &#64;Alias("Validation")
 * public class Validation extends AbstractModel {
 *     private static final long serialVersionUID = -1L;
 *
 *     &#64;JsonProperty("userid")
 *     private String userId;
 *
 *     &#64;JsonProperty("user_name")
 *     private String userName;
 *
 *     public String getUserId() {
 *         return userId;
 *     }
 *
 *     public void setUserId(String userId) {
 *         this.userId = userId;
 *     }
 *
 *     public String getUserName() {
 *         return userName;
 *     }
 *
 *     public void setUserName(String userName) {
 *         this.userName = userName;
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
package com.ha.social.persistence.model;
