package dev.beautifulbublik.paymentservice.service.mappers;

import dev.beautifulbublik.http.payment.CreatePaymentRequestDto;
import dev.beautifulbublik.http.payment.CreatePaymentResponseDto;
import dev.beautifulbublik.http.payment.PaymentMethod;
import dev.beautifulbublik.http.payment.PaymentStatus;
import dev.beautifulbublik.paymentservice.domain.PaymentEntity;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-11T15:11:22+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.3.0.jar, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentEntity toEntity(CreatePaymentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setOrderId( dto.orderId() );
        paymentEntity.setAmount( dto.amount() );
        paymentEntity.setPaymentMethod( dto.paymentMethod() );

        return paymentEntity;
    }

    @Override
    public CreatePaymentResponseDto toResponseDto(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }

        Long paymentId = null;
        PaymentStatus status = null;
        Long orderId = null;
        PaymentMethod paymentMethod = null;
        BigDecimal amount = null;

        paymentId = paymentEntity.getId();
        status = paymentEntity.getPaymentStatus();
        orderId = paymentEntity.getOrderId();
        paymentMethod = paymentEntity.getPaymentMethod();
        amount = paymentEntity.getAmount();

        CreatePaymentResponseDto createPaymentResponseDto = new CreatePaymentResponseDto( paymentId, orderId, paymentMethod, status, amount );

        return createPaymentResponseDto;
    }
}
