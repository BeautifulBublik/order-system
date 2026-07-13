package dev.beautifulbublik.paymentservice.service.mappers;

import dev.beautifulbublik.paymentservice.domain.PaymentEntity;
import dev.beautifulbublik.http.payment.CreatePaymentRequestDto;
import dev.beautifulbublik.http.payment.CreatePaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface PaymentMapper {

    PaymentEntity toEntity(CreatePaymentRequestDto dto);
    @Mapping(source = "id", target = "paymentId")
    @Mapping(source = "paymentStatus", target = "status")
    CreatePaymentResponseDto toResponseDto(PaymentEntity paymentEntity);
}
