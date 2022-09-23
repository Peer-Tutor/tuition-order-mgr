package com.peertutor.TuitionOrderMgr.service;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import com.peertutor.TuitionOrderMgr.model.TuitionOrder_;
import com.peertutor.TuitionOrderMgr.repository.TuitionOrderRepository;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderCriteria;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;
import com.peertutor.TuitionOrderMgr.service.mapper.TuitionOrderMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TuitionOrderQueryService extends QueryService<TuitionOrder> {

    private final Logger log = LoggerFactory.getLogger(TuitionOrderQueryService.class);

    private final TuitionOrderRepository tuitionOrderRepository;

    private final TuitionOrderMapper tuitionOrderMapper;

    public TuitionOrderQueryService(TuitionOrderRepository tuitionOrderRepository, TuitionOrderMapper tuitionOrderMapper) {
        this.tuitionOrderRepository = tuitionOrderRepository;
        this.tuitionOrderMapper = tuitionOrderMapper;
    }

    /**
     * Return a {@link List} of {@link TuitionOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TuitionOrderDTO> findByCriteria(TuitionOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TuitionOrder> specification = createSpecification(criteria);
        return tuitionOrderMapper.toDto(tuitionOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TuitionOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TuitionOrderDTO> findByCriteria(TuitionOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TuitionOrder> specification = createSpecification(criteria);
        return tuitionOrderRepository.findAll(specification, page)
            .map(tuitionOrderMapper::toDto);
    }

    /**
     * Function to convert {@link TuitionOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TuitionOrder> createSpecification(TuitionOrderCriteria criteria) {
        Specification<TuitionOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TuitionOrder_.id));
            }
            if (criteria.getTutorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTutorId(), TuitionOrder_.tutorId));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentId(), TuitionOrder_.studentId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), TuitionOrder_.status));
            }

        }
        return specification;
    }
}
