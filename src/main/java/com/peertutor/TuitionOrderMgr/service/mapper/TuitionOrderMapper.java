package com.peertutor.TuitionOrderMgr.service.mapper;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link com.peertutor.TuitionOrderMgr.model.TuitionOrder} and its DTO {@link TuitionOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface TuitionOrderMapper extends EntityMapper<TuitionOrderDTO, TuitionOrder> {

    TuitionOrder toEntity(TuitionOrderDTO accountDTO);

    TuitionOrderDTO toDto(TuitionOrder account);

    default TuitionOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        TuitionOrder account = new TuitionOrder();
        account.setId(id);
        return account;
    }
}
