/**
 * 공통 MVC Controller 클래스 제공.
 * AbstractController를 상속 받아서 사용하는 것을 추천한다.<br>
 *
 * <pre>
 * <b>- 샘플</b>
 * <code>
 * package com.kthcorp.archetype.web.controller;
 *
 * import java.io.IOException;
 *
 * import javax.servlet.http.HttpServletRequest;
 * import javax.servlet.http.HttpServletResponse;
 * import javax.validation.Valid;
 *
 * import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.MediaType;
 * import org.springframework.http.ResponseEntity;
 * import org.springframework.validation.BindingResult;
 * import org.springframework.web.bind.annotation.GetMapping;
 * import org.springframework.web.bind.annotation.PostMapping;
 * import org.springframework.web.bind.annotation.PutMapping;
 * import org.springframework.web.bind.annotation.RequestMapping;
 * import org.springframework.web.bind.annotation.RequestParam;
 * import org.springframework.web.bind.annotation.RestController;
 *
 * import com.kthcorp.archetype.commons.web.controller.AbstractController;
 * import com.kthcorp.archetype.commons.web.util.ResponseUtils;
 * import com.kthcorp.archetype.dto.request.ValidationRequest;
 * import com.kthcorp.archetype.dto.response.ValidationResponse;
 * import com.kthcorp.archetype.service.ValidationService;
 *
 * &#64;RequestMapping(path = { "/validation" }, produces = MediaType.APPLICATION_JSON_VALUE)
 * &#64;RestController
 * public class ValidationApiController extends AbstractController {
 *     private static final Logger log = LoggerFactory.getLogger(ValidationApiController.class);
 *
 *     &#64;Autowired
 *     private ValidationService validationService;
 *
 *     &#64;GetMapping(path = "get")
 *     public ResponseEntity&lt;Object&gt; get(HttpServletRequest request, HttpServletResponse response,
 *             &#64;RequestParam(value = "name1", required = false) String name1,
 *             &#64;RequestParam(value = "name2", required = false) String name2) {
 *         if (log.isDebugEnabled()) {
 *             log.debug("Name1: {}, Name2: {}", name1, name2);
 *         }
 *         return ResponseUtils.resultJson(request, validationService.getValidation());
 *     }
 *
 *     &#64;PostMapping(path = "post")
 *     public ResponseEntity&lt;Object&gt; post(HttpServletRequest request, HttpServletResponse response,
 *             &#64;Valid ValidationRequest params, BindingResult result) {
 *         if (log.isDebugEnabled()) {
 *             log.debug("{}", params.toJsonLog());
 *         }
 *         checkForErrors(result); // 필수 파라미터 체크.
 *         return ResponseUtils.resultJson(request, validationService.getValidation());
 *     }
 *
 *     &#64;PutMapping(path = "put")
 *     public ResponseEntity&lt;Object&gt; put(HttpServletRequest request, HttpServletResponse response,
 *             &#64;Valid ValidationRequest params, BindingResult result) {
 *         if (log.isDebugEnabled()) {
 *             log.debug("{}", params.toJsonLog());
 *         }
 *         checkForErrors(result); // 필수 파라미터 체크.
 *         return ResponseUtils.resultJson(request, validationService.getValidation());
 *     }
 *
 *     &#64;PutMapping(path = "delete")
 *     public ResponseEntity&lt;Object&gt; delete(HttpServletRequest request, HttpServletResponse response,
 *             &#64;Valid ValidationRequest params, BindingResult result) {
 *         if (log.isDebugEnabled()) {
 *             log.debug("{}", params.toJsonLog());
 *         }
 *         checkForErrors(result); // 필수 파라미터 체크.
 *         return ResponseUtils.resultJson(request, validationService.getValidation());
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
package com.ha.social.web.controller;
