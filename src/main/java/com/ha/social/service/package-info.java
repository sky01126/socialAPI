/**
 * 프로젝트의 Service 클래스 제공.
 * AbstractService를 상속 받아서 사용하는 것을 추천한다.<br>
 *
 * <pre>
 * <b>- 샘플</b>
 * <code>
 * package com.kthcorp.archetype.service;
 *
 * import java.util.Random;
 *
 * import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.transaction.annotation.Transactional;
 *
 * import com.kthcorp.archetype.commons.service.AbstractService;
 * import com.kthcorp.archetype.dto.response.ValidationResponse;
 * import com.kthcorp.archetype.persistence.dao.ValidationDao;
 * import com.kthcorp.archetype.persistence.model.Validation;
 *
 * &#64;Service
 * &#64;Transactional(readOnly = true)
 * public class ValidationService extends AbstractService {
 *
 *  private static final Logger log = LoggerFactory.getLogger(ValidationService.class);
 *
 *  &#64;Autowired
 *  private ValidationDao validationDao;
 *     public ValidationResponse getValidation() {
 *         Random random = new Random();
 *         Validation validation = null;
 *         if (random.nextBoolean()) {
 *             validation = validationDao.selectValidationMaster();
 *         } else {
 *             validation = validationDao.selectValidationSlave();
 *         }
 *         if (validation == null) {
 *             return new ValidationResponse(412, getResponseMessage().get(412));
 *         }
 *         if (log.isDebugEnabled()) {
 *             log.debug(validation.toJsonLog());
 *         }
 *
 *         int status = HttpStatus.OK.value();
 *         ValidationResponse res = new ValidationResponse(status, getResponseMessage().get(status));
 *         res.setData(validation);
 *         return res;
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
package com.ha.social.service;
