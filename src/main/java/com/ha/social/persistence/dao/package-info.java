/**
 * 프로젝트의 DAO(Data Access Object) 클래스 제공.
 * AbstractMybatisDao에 DataSource에 관한 설정과 select / insert / update Method가 있다.
 *
 * <pre>
 * <b>- 샘플</b>
 * <code>
 * package com.kthcorp.archetype.persistence.dao;
 *
 * import org.springframework.cache.annotation.CacheConfig;
 * import org.springframework.cache.annotation.Cacheable;
 * import org.springframework.stereotype.Repository;
 *
 * import com.kthcorp.archetype.commons.config.CacheName;
 * import com.kthcorp.archetype.commons.persistence.dao.AbstractMybatisDao;
 * import com.kthcorp.archetype.persistence.model.Validation;
 * import com.kthcorp.commons.lang.ClassUtils;
 *
 * &#64;Repository
 * &#64;CacheConfig(cacheNames = CacheName.DEFAULT_CACHE)
 * public class ValidationDao extends AbstractMybatisDao {
 *     &#64;Cacheable(keyGenerator = "keyGenerator")
 *     public Validation selectValidationMaster() {
 *         String statement = ClassUtils.getAllCurrentMethodName();
 *         return selectOne(statement);
 *     }
 *
 *     &#64;Cacheable(
 *             // Cache Key 설정.
 *             key = "T(com.kthcorp.commons.lang.CacheUtils).generate(" // Create Key
 *                     + "#root.targetClass.name, " // Class Name
 *                     + "#root.methodName, " // Method Name
 *                     + "'user_id', " // Parameter #1
 *                     + "'passwd')", // Parameter #1
 *             // 리턴값이 NULL 인 경우 Cache 하지 않도록 설정.
 *             unless = "#result == null" //
 *     )
 *     public Validation selectValidationSlave() {
 *         String statement = ClassUtils.getAllCurrentMethodName();
 *         return selectOne(statement);
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
package com.ha.social.persistence.dao;
