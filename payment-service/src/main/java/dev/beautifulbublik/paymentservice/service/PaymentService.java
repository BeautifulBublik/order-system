package dev.beautifulbublik.paymentservice.service;

import dev.beautifulbublik.http.payment.PaymentMethod;
import dev.beautifulbublik.http.payment.PaymentStatus;
import dev.beautifulbublik.http.payment.CreatePaymentRequestDto;
import dev.beautifulbublik.http.payment.CreatePaymentResponseDto;
import dev.beautifulbublik.paymentservice.repository.PaymentRepository;
import dev.beautifulbublik.paymentservice.service.mappers.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    public CreatePaymentResponseDto makePayment(CreatePaymentRequestDto createPaymentRequestDto){
        var found=paymentRepository.findByOrderId(createPaymentRequestDto.orderId());
        if(found.isPresent()){
            log.info("Payment already exists for orderId={}", createPaymentRequestDto.orderId());
            return paymentMapper.toResponseDto(found.get());
        }

        var entity=paymentMapper.toEntity(createPaymentRequestDto);
        var status = createPaymentRequestDto.paymentMethod().equals(PaymentMethod.QR)
                ? PaymentStatus.PAYMENT_FAILED
                : PaymentStatus.PAYMENT_SUCCEEDED;
        entity.setPaymentStatus(status);

        var savedEntity = paymentRepository.save(entity);
        return paymentMapper.toResponseDto(savedEntity);
    }

}
